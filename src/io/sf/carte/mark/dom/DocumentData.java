/*

 Copyright (c) 2020-2025, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import io.sf.carte.doc.dom.CSSDOMImplementation;
import io.sf.carte.doc.dom.XMLDocumentBuilder;
import io.sf.carte.doc.dom4j.XHTMLDocumentFactory;
import io.sf.carte.doc.xml.dtd.DefaultEntityResolver;
import io.sf.carte.mark.Util;

public class DocumentData {

	final int minimumCount;

	final String tagName;

	final int nameCount;

	final int elementCount;

	private final String filename;

	private final boolean compressed;

	Document jdkDoc;

	Document domDoc;

	Document dom4jDoc;

	DocumentData(String filename, boolean compressed, int minimumCount, int elementCount, String tagName,
			int nameCount) {
		super();
		this.filename = filename;
		this.compressed = compressed;
		this.tagName = tagName;
		this.minimumCount = minimumCount;
		this.elementCount = elementCount;
		this.nameCount = nameCount;
	}

	public String getTagName() {
		return tagName;
	}

	public void init() {
		DefaultEntityResolver entityResolver = new DefaultEntityResolver();
		final String documentText;
		if (compressed) {
			documentText = Util.loadCompressedFilefromClasspath(filename);
		} else {
			documentText = Util.loadFilefromClasspath(filename);
		}
		jdkDoc = loadJdkDocument(documentText, entityResolver);
		dom4jDoc = loadDOM4JDocument(documentText, entityResolver);
		domDoc = loadDOMDocument(documentText, entityResolver);
	}

	private static Document loadJdkDocument(String documentText, EntityResolver entityResolver) {
		Document doc;
		DOMImplementationRegistry registry = null;
		try {
			registry = DOMImplementationRegistry.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e2) {
		}
		DOMImplementation domImpl = registry.getDOMImplementation("XML 3.0 traversal");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
			factory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		} catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e1) {
		}
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder(domImpl, factory);
		docbuilder.setEntityResolver(new DefaultEntityResolver());
		InputSource source = new InputSource(new StringReader(documentText));
		try {
			doc = docbuilder.parse(source);
		} catch (SAXException | IOException e) {
			doc = null;
		}
		return doc;
	}

	private static Document loadDOMDocument(String documentText, EntityResolver entityResolver) {
		Document doc;
		CSSDOMImplementation domImpl = new CSSDOMImplementation();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
			factory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		} catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e) {
		}
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder(domImpl, factory);
		docbuilder.setEntityResolver(entityResolver);
		InputSource source = new InputSource(new StringReader(documentText));
		try {
			doc = docbuilder.parse(source);
		} catch (SAXException | IOException e) {
			doc = null;
		}
		return doc;
	}

	private static Document loadDOM4JDocument(String documentText, EntityResolver entityResolver) {
		Document doc;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
			factory.setFeature("http://xml.org/sax/features/xmlns-uris", true);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		} catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException e) {
		}
		SAXReader docbuilder = new SAXReader(XHTMLDocumentFactory.getInstance());
		try {
			docbuilder.setXMLReader(factory.newSAXParser().getXMLReader());
		} catch (SAXException | ParserConfigurationException e1) {
		}
		docbuilder.setEntityResolver(entityResolver);
		InputSource source = new InputSource(new StringReader(documentText));
		try {
			doc = (Document) docbuilder.read(source);
		} catch (DocumentException e) {
			doc = null;
		}
		return doc;
	}
}
