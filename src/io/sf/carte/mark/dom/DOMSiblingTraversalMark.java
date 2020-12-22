/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.Node;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
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

	private int traverse(Node node, int count) {
		Node child = node.getFirstChild();
		while (child != null) {
			count++;
			count = traverse(child, count);
			child = child.getNextSibling();
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

}
