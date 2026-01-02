/*

 Copyright (c) 2020-2026, Carlos Amengual.

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

// SPDX-License-Identifier: BSD-2-Clause OR BSD-3-Clause

package io.sf.carte.mark.dom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.Node;

@Fork(value = 1)
@Measurement(iterations = 8, time = 10)
@Warmup(iterations = 6, time = 10)
public class DOMSiblingTraversalMark {

	@Benchmark
	public void markTraverseJdk(DOMData data) {
		Node node = data.jdkDoc.getDocumentElement();
		if (node == null) {
			throw new IllegalStateException("Document has no element child.");
		}
		int count = traverse(node, 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException("Expected a minimum count of " + data.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraverseDOM(DOMData data) {
		int count = traverse(data.domDoc.getDocumentElement(), 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException("Expected a count of " + data.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraverseDOM4J(DOMData data) {
		int count = traverse(data.dom4jDoc.getDocumentElement(), 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException("Expected a count of " + data.minimumCount + " obtained " + count);
		}
	}

	private static int traverse(Node node, int count) {
		Node child = node.getFirstChild();
		while (child != null) {
			count++;
			count = traverse(child, count);
			child = child.getNextSibling();
		}
		return count;
	}

	@Benchmark
	public void markTraverseJsoup(DOMData data) {
		org.jsoup.nodes.Node child = data.jsoupDoc.firstElementChild();
		int count = traverseJsoup(child, 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException(
					"Expected a count of " + data.minimumCount + " obtained " + count);
		}
	}

	private static int traverseJsoup(org.jsoup.nodes.Node node, int count) {
		org.jsoup.nodes.Node child = node.firstChild();
		while (child != null) {
			count++;
			count = traverseJsoup(child, count);
			child = child.nextSibling();
		}
		return count;
	}

	@Benchmark
	public void markTraversePrevJdk(DOMData data) {
		Node node = data.jdkDoc.getDocumentElement();
		if (node == null) {
			throw new IllegalStateException("Document has no element child.");
		}
		int count = traversePrev(node, 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException("Expected a minimum count of " + data.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraversePrevDOM(DOMData data) {
		int count = traversePrev(data.domDoc.getDocumentElement(), 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException("Expected a count of " + data.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraversePrevDOM4J(DOMData data) {
		int count = traversePrev(data.dom4jDoc.getDocumentElement(), 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException("Expected a count of " + data.minimumCount + " obtained " + count);
		}
	}

	private int traversePrev(Node node, int count) {
		Node child = node.getLastChild();
		while (child != null) {
			count++;
			count = traverse(child, count);
			child = child.getPreviousSibling();
		}
		return count;
	}

	@Benchmark
	public void markTraversePrevJsoup(DOMData data) {
		org.jsoup.nodes.Node child = data.jsoupDoc.firstElementChild();
		int count = traversePrevJsoup(child, 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException(
					"Expected a count of " + data.minimumCount + " obtained " + count);
		}
	}

	private static int traversePrevJsoup(org.jsoup.nodes.Node node, int count) {
		org.jsoup.nodes.Node child = node.lastChild();
		while (child != null) {
			count++;
			count = traversePrevJsoup(child, count);
			child = child.previousSibling();
		}
		return count;
	}

}
