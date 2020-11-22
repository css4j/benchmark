/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.doc.style.css.mark;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.sf.carte.doc.dom.CSSDOMImplementation;
import io.sf.carte.doc.dom.XMLDocumentBuilder;
import io.sf.carte.doc.dom4j.XHTMLDocumentFactory;
import io.sf.carte.doc.xml.dtd.DefaultEntityResolver;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 6, time = 10)
public class XMLBuildBenchmark {

	private static DefaultEntityResolver entityResolver = new DefaultEntityResolver();

	private final static String documentText = Util
			.loadCompressedFilefromClasspath("/io/sf/carte/doc/style/css/mark/mondial-3.0.xml.gz");

	@Benchmark
	public void markBuildPlainJdk() throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		DocumentBuilder docbuilder = factory.newDocumentBuilder();
		docbuilder.setEntityResolver(entityResolver);
		InputSource source = new InputSource(new StringReader(documentText));
		Document doc = docbuilder.parse(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markBuildDOM() throws IOException, SAXException, ParserConfigurationException {
		CSSDOMImplementation domImpl = new CSSDOMImplementation();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		factory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder(domImpl, factory);
		docbuilder.setEntityResolver(entityResolver);
		InputSource source = new InputSource(new StringReader(documentText));
		Document doc = docbuilder.parse(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markBuildDOM4J() throws IOException, DocumentException, SAXException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		factory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		SAXReader docbuilder = new SAXReader(factory.newSAXParser().getXMLReader());
		docbuilder.setEntityResolver(entityResolver);
		InputSource source = new InputSource(new StringReader(documentText));
		org.dom4j.Document doc = docbuilder.read(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markBuildCss4jDOM4J() throws IOException, DocumentException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		factory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		SAXReader docbuilder = new SAXReader(XHTMLDocumentFactory.getInstance());
		docbuilder.setXMLReader(factory.newSAXParser().getXMLReader());
		docbuilder.setEntityResolver(entityResolver);
		InputSource source = new InputSource(new StringReader(documentText));
		org.dom4j.Document doc = docbuilder.read(source);
		if (doc == null) {
			throw new RuntimeException();
		}
	}

}
