/*

 Copyright (c) 2020-2021, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import java.io.IOException;
import java.io.StringReader;

import org.dom4j.DocumentException;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.sf.carte.doc.dom.CSSDOMImplementation;
import io.sf.carte.doc.dom.XMLDocumentBuilder;
import io.sf.carte.doc.dom4j.XHTMLDocumentFactory;
import io.sf.carte.mark.Util;
import nu.validator.htmlparser.common.XmlViolationPolicy;
import nu.validator.htmlparser.dom.HtmlDocumentBuilder;
import nu.validator.htmlparser.sax.HtmlParser;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 8, time = 10)
public class HTMLBuildBenchmark {

	private final static String documentText = Util.loadFilefromClasspath("/io/sf/carte/mark/dom/usage.html");

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
	public void markBuildDOMXHTMLBuilder() throws IOException, SAXException {
		CSSDOMImplementation domImpl = new CSSDOMImplementation();
		HtmlParser parser = new HtmlParser(XmlViolationPolicy.ALTER_INFOSET);
		parser.setCommentPolicy(XmlViolationPolicy.ALLOW);
		parser.setXmlnsPolicy(XmlViolationPolicy.ALLOW);
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder(domImpl);
		docbuilder.setHTMLProcessing(true);
		docbuilder.setXMLReader(parser);
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

	@Benchmark
	public void markBuildDOM4J() throws IOException, DocumentException {
		HtmlParser parser = new HtmlParser(XmlViolationPolicy.ALTER_INFOSET);
		parser.setCommentPolicy(XmlViolationPolicy.ALLOW);
		parser.setXmlnsPolicy(XmlViolationPolicy.ALLOW);
		SAXReader docbuilder = new SAXReader(DOMDocumentFactory.getInstance());
		docbuilder.setXMLReader(parser);
		ErrorHandler errorHandler = new PermissiveErrorHandler();
		docbuilder.setErrorHandler(errorHandler);
		Document doc = (Document) docbuilder.read(new StringReader(documentText));
		if (doc == null) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markBuildJsoup() throws IOException {
		org.jsoup.nodes.Document doc = Jsoup.parse(documentText);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

}
