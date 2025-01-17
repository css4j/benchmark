/*

 Copyright (c) 2020-2025, Carlos Amengual.

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
import org.w3c.dom.traversal.DocumentTraversal;

import io.sf.carte.doc.dom.DOMDocument;
import io.sf.carte.doc.dom.NodeFilter;
import io.sf.carte.doc.dom.NodeIterator;
import io.sf.carte.doc.dom.TreeWalker;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 6, time = 10)
public class DOMTraversalMark {

	@Benchmark
	public void markNodeIteratorJdk(DOMData data) {
		int count = 0;
		Node root = data.jdkDoc.getDocumentElement();
		if (root == null) {
			throw new IllegalStateException("Document has no element child.");
		}
		org.w3c.dom.traversal.NodeIterator it = ((DocumentTraversal) data.jdkDoc).createNodeIterator(root,
				org.w3c.dom.traversal.NodeFilter.SHOW_ELEMENT, null, false);
		while (it.nextNode() != null) {
			count++;
		}
		if (count != data.elementCount + 1) {
			throw new IllegalStateException("Expected a count of " + (data.elementCount + 1) + ", obtained " + count);
		}
	}

	@Benchmark
	public void markNodeIteratorDOM(DOMData data) {
		int count = 0;
		Node root = data.domDoc.getDocumentElement();
		NodeIterator it = ((DOMDocument) data.domDoc).createNodeIterator(root, NodeFilter.SHOW_ELEMENT, null);
		while (it.nextNode() != null) {
			count++;
		}
		if (count != data.elementCount + 1) {
			throw new IllegalStateException("Expected a count of " + (data.elementCount + 1) + ", obtained " + count);
		}
	}

	@Benchmark
	public void markTreeWalkerJdk(DOMData data) {
		int count = 0;
		Node root = data.jdkDoc.getDocumentElement();
		if (root == null) {
			throw new IllegalStateException("Document has no element child.");
		}
		org.w3c.dom.traversal.TreeWalker tw = ((DocumentTraversal) data.jdkDoc).createTreeWalker(root,
				org.w3c.dom.traversal.NodeFilter.SHOW_ELEMENT, null, false);
		while (tw.nextNode() != null) {
			count++;
		}
		if (count != data.elementCount) {
			throw new IllegalStateException("Expected a count of " + data.elementCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markTreeWalkerDOM(DOMData data) {
		int count = 0;
		Node root = data.domDoc.getDocumentElement();
		TreeWalker tw = ((DOMDocument) data.domDoc).createTreeWalker(root, NodeFilter.SHOW_ELEMENT, null);
		while (tw.nextNode() != null) {
			count++;
		}
		if (count != data.elementCount) {
			throw new IllegalStateException("Expected a count of " + data.elementCount + ", obtained " + count);
		}
	}

}
