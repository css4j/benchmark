/*

 Copyright (c) 2020-2025, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import io.sf.carte.doc.dom.CSSDOMImplementation;
import io.sf.carte.doc.dom4j.XHTMLDocumentFactory;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 6, time = 10)
public class DOMChangeMark {

	@Benchmark
	public void markChangeJdk() {
		DOMImplementationRegistry registry = null;
		try {
			registry = DOMImplementationRegistry.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e2) {
		}
		DOMImplementation domImpl = registry.getDOMImplementation("XML 3.0 traversal");
		change(domImpl);
	}

	@Benchmark
	public void markChangeDOM() {
		CSSDOMImplementation domImpl = new CSSDOMImplementation();
		change(domImpl);
	}

	@Benchmark
	public void markChangeDOM4J() {
		XHTMLDocumentFactory domImpl = XHTMLDocumentFactory.getInstance();
		change(domImpl);
	}

	private void change(DOMImplementation domImpl) {
		Document doc = domImpl.createDocument(null, "doc", null);
		Element root = doc.getDocumentElement();
		if (root == null) {
			throw new IllegalStateException("Document has no element child.");
		}

		for (int i = 0; i < 20000; i++) {
			Element element = doc.createElement("element");
			element.setAttribute("foo", "bar");
			Text text = doc.createTextNode("text");
			element.appendChild(text);
			root.appendChild(element);
		}

		for (int i = 0; i < 20000; i++) {
			Node element = root.getFirstChild();
			root.removeChild(element);
		}
	}

	@Benchmark
	public void markChangeJsoup() {
		org.jsoup.nodes.Document doc = Jsoup.parse("<html><body></body></html>");
		org.jsoup.nodes.Element body = doc.body();
		if (body == null) {
			throw new IllegalStateException("Document has no body.");
		}

		for (int i = 0; i < 20000; i++) {
			org.jsoup.nodes.Element element = doc.createElement("element");
			element.attr("foo", "bar");
			TextNode text = TextNode.createFromEncoded("text");
			element.appendChild(text);
			body.appendChild(element);
		}

		for (int i = 0; i < 20000; i++) {
			org.jsoup.nodes.Element element = body.firstElementChild();
			element.remove();
		}
	}

}
