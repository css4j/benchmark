/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.doc.style.css.mark;

import java.util.Iterator;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;

import io.sf.carte.doc.dom.DOMDocument;
import io.sf.carte.doc.dom.DOMElement;
import io.sf.carte.doc.dom.ElementList;
import io.sf.carte.doc.dom.NodeFilter;
import io.sf.carte.doc.dom.NodeIterator;
import io.sf.carte.doc.dom.TreeWalker;
import io.sf.carte.doc.dom4j.XHTMLDocument;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 6, time = 10)
public class DOMTraverseMark {

	@Benchmark
	public void markTraverseJdk(DOMData data) {
		Node node = data.jdkDoc.getDocumentElement();
		if (node == null) {
			throw new IllegalStateException("Document has no element child.");
		}
		int count = traverse(node, 0);
		if (count < DOMData.minimumCount) {
			throw new IllegalStateException("Expected a minimum count of " + DOMData.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraverseDOM(DOMData data) {
		int count = traverse(data.domDoc.getDocumentElement(), 0);
		if (count < DOMData.minimumCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraverseDOM4J(DOMData data) {
		int count = traverse(data.dom4jDoc.getDocumentElement(), 0);
		if (count < DOMData.minimumCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.minimumCount + " obtained " + count);
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
		if (count < DOMData.minimumCount) {
			throw new IllegalStateException("Expected a minimum count of " + DOMData.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraversePrevDOM(DOMData data) {
		int count = traversePrev(data.domDoc.getDocumentElement(), 0);
		if (count < DOMData.minimumCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.minimumCount + " obtained " + count);
		}
	}

	@Benchmark
	public void markTraversePrevDOM4J(DOMData data) {
		int count = traversePrev(data.dom4jDoc.getDocumentElement(), 0);
		if (count < DOMData.minimumCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.minimumCount + " obtained " + count);
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
		if (count != DOMData.elementCount + 1) {
			throw new IllegalStateException("Expected a count of " + (DOMData.elementCount + 1) + ", obtained " + count);
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
		if (count != DOMData.elementCount + 1) {
			throw new IllegalStateException("Expected a count of " + (DOMData.elementCount + 1) + ", obtained " + count);
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
		if (count != DOMData.elementCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.elementCount + ", obtained " + count);
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
		if (count != DOMData.elementCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.elementCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markIteratorDOM(DOMData data) {
		int count = 0;
		DOMElement root = ((DOMDocument) data.domDoc).getDocumentElement();
		count = iterate(root, count);
		if (count != DOMData.elementCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.elementCount + ", obtained " + count);
		}
	}

	private int iterate(DOMElement element, int count) {
		Iterator<DOMElement> it = element.elementIterator();
		while (it.hasNext()) {
			DOMElement child = it.next();
			count++;
			count = iterate(child, count);
		}
		return count;
	}

	@Benchmark
	public void markIteratorDOM4J(DOMData data) {
		int count = 0;
		XHTMLDocument doc = (XHTMLDocument) data.dom4jDoc;
		org.dom4j.Element root = doc.getDocumentElement();
		count = iterateDOM4J(root, count);
		if (count != DOMData.elementCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.elementCount + ", obtained " + count);
		}
	}

	private int iterateDOM4J(org.dom4j.Element element, int count) {
		Iterator<org.dom4j.Element> it = element.elementIterator();
		while (it.hasNext()) {
			org.dom4j.Element child = it.next();
			count++;
			count = iterateDOM4J(child, count);
		}
		return count;
	}

	@Benchmark
	public void markElementsByTagNameJdk(DOMData data) {
		NodeList list = data.jdkDoc.getElementsByTagName("city");
		int count = countElementsByTagName(list);
		if (count != DOMData.cityCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.cityCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameDOM4J(DOMData data) {
		NodeList list = data.dom4jDoc.getElementsByTagName("city");
		int count = countElementsByTagName(list);
		if (count != DOMData.cityCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.cityCount + ", obtained " + count);
		}
	}

	private int countElementsByTagName(NodeList list) {
		int len = list.getLength();
		for (int i = 0; i < len; i++) {
			Node element = list.item(i);
			if (!"city".equals(element.getNodeName())) {
				throw new IllegalStateException();
			}
		}
		return len;
	}

	@Benchmark
	public void markElementsByTagNameDOM(DOMData data) {
		NodeList list = data.domDoc.getElementsByTagName("city");
		int count = countElementsByTagName(list);
		if (count != DOMData.cityCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.cityCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameDOMit(DOMData data) {
		int count = 0;
		ElementList list = ((DOMDocument) data.domDoc).getElementsByTagName("city");
		for (DOMElement element : list) {
			if (!"city".equals(element.getNodeName())) {
				throw new IllegalStateException();
			}
			count++;
		}
		if (count != DOMData.cityCount) {
			throw new IllegalStateException("Expected a count of " + DOMData.cityCount + ", obtained " + count);
		}
	}

}
