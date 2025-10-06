/*

 Copyright (c) 2020-2025, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Branch;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import io.sf.carte.doc.dom.DOMDocument;
import io.sf.carte.doc.dom.DOMElement;
import io.sf.carte.doc.dom.DOMNode;
import io.sf.carte.doc.dom4j.XHTMLDocument;

@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 6, time = 10)
public class DOMIteratorMark {

	@Benchmark
	public void markIteratorDOM(DOMData data) {
		int count = 0;
		count = iterate((DOMDocument) data.domDoc, count);
		if (count < data.minimumCount) {
			throw new IllegalStateException(
					"Expected a count of at least " + data.minimumCount + ", obtained " + count);
		}
	}

	private int iterate(DOMNode node, int count) {
		if (node.hasChildNodes()) {
			Iterator<DOMNode> it = node.getChildNodes().iterator();
			while (it.hasNext()) {
				DOMNode child = it.next();
				count++;
				count = iterate(child, count);
			}
		}
		return count;
	}

	@Benchmark
	public void markElementIteratorDOM(DOMData data) {
		int count = 0;
		DOMElement root = ((DOMDocument) data.domDoc).getDocumentElement();
		count = iterateElements(root, count);
		if (count != data.elementCount) {
			throw new IllegalStateException("Expected a count of " + data.elementCount + ", obtained " + count);
		}
	}

	private int iterateElements(DOMElement element, int count) {
		Iterator<DOMElement> it = element.elementIterator();
		while (it.hasNext()) {
			DOMElement child = it.next();
			count++;
			count = iterateElements(child, count);
		}
		return count;
	}

	@Benchmark
	public void markIteratorDOM4J(DOMData data) {
		int count = 0;
		XHTMLDocument doc = (XHTMLDocument) data.dom4jDoc;
		count = iterateDOM4J(doc, count);
		if (count < data.minimumCount) {
			throw new IllegalStateException(
					"Expected a count of at least " + data.minimumCount + ", obtained " + count);
		}
	}

	private int iterateDOM4J(org.dom4j.Branch node, int count) {
		if (node.hasContent()) {
			Iterator<org.dom4j.Node> it = node.nodeIterator();
			while (it.hasNext()) {
				org.dom4j.Node child = it.next();
				count++;
				if (child instanceof Branch) {
					count = iterateDOM4J((Branch) child, count);
				}
			}
		}
		return count;
	}

	@Benchmark
	public void markElementIteratorDOM4J(DOMData data) {
		int count = 0;
		XHTMLDocument doc = (XHTMLDocument) data.dom4jDoc;
		org.dom4j.Element root = doc.getDocumentElement();
		count = iterateDOM4JElements(root, count);
		if (count != data.elementCount) {
			throw new IllegalStateException("Expected a count of " + data.elementCount + ", obtained " + count);
		}
	}

	private int iterateDOM4JElements(org.dom4j.Element element, int count) {
		Iterator<org.dom4j.Element> it = element.elementIterator();
		while (it.hasNext()) {
			org.dom4j.Element child = it.next();
			count++;
			count = iterateDOM4JElements(child, count);
		}
		return count;
	}

	@Benchmark
	public void markIteratorJsoup(DOMDataSmall data) {
		int count = iterateJsoup(data.jsoupDoc.childNodes(), 0);
		if (count < data.minimumCount) {
			throw new IllegalStateException(
					"Expected a count of at least " + data.minimumCount + " obtained " + count);
		}
	}

	private int iterateJsoup(List<org.jsoup.nodes.Node> list, int count) {
		if (!list.isEmpty()) {
			Iterator<org.jsoup.nodes.Node> it = list.iterator();
			while (it.hasNext()) {
				count++;
				count = iterateJsoup(it.next().childNodes(), count);
			}
		}
		return count;
	}

	@Benchmark
	public void markElementIteratorJsoup(DOMDataSmall data) {
		int count = iterateJsoupElements(data.jsoupDoc.children(), 0);
		if (count != data.elementCount + 3) {
			throw new IllegalStateException(
					"Expected a count of " + data.elementCount + ", obtained " + count);
		}
	}

	private int iterateJsoupElements(Elements elements, int count) {
		Iterator<Element> it = elements.iterator();
		while (it.hasNext()) {
			Element child = it.next();
			count++;
			count = iterateJsoupElements(child.children(), count);
		}
		return count;
	}

}
