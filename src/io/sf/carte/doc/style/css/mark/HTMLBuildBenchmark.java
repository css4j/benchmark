/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.doc.style.css.mark;

import java.io.IOException;
import java.io.StringReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.sf.carte.doc.dom.CSSDOMImplementation;
import io.sf.carte.doc.dom4j.XHTMLDocumentFactory;
import nu.validator.htmlparser.dom.HtmlDocumentBuilder;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 8, time = 10)
public class HTMLBuildBenchmark {

	private final static String documentText = Util.loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/usage.html");

	@Benchmark
	public void markBuildPlainJDK() throws Exception {
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementation domImpl = registry.getDOMImplementation("XML 3.0 traversal");
		HtmlDocumentBuilder docbuilder = new HtmlDocumentBuilder(domImpl);
		docbuilder.setIgnoringComments(false);
		InputSource source = new InputSource(new StringReader(documentText));
		Document doc = docbuilder.parse(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markBuildDOM() throws IOException, SAXException {
		CSSDOMImplementation domImpl = new CSSDOMImplementation();
		HtmlDocumentBuilder docbuilder = new HtmlDocumentBuilder(domImpl);
		docbuilder.setIgnoringComments(false);
		InputSource source = new InputSource(new StringReader(documentText));
		Document doc = docbuilder.parse(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markBuildCss4jDOM4J() throws IOException, SAXException {
		HtmlDocumentBuilder docbuilder = new HtmlDocumentBuilder(XHTMLDocumentFactory.getInstance());
		docbuilder.setIgnoringComments(false);
		InputSource source = new InputSource(new StringReader(documentText));
		Document doc = docbuilder.parse(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

}
