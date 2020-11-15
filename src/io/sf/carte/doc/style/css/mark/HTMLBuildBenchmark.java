/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.doc.style.css.mark;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

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

	private final static String documentText;

	static {
		char[] array = new char[4096];
		StringBuilder buffer = new StringBuilder(array.length);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/usage.html");
		InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
		int nc;
		try {
			while ((nc = reader.read(array)) != -1) {
				buffer.append(array, 0, nc);
			}
		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		documentText = buffer.toString();
	}

	@Benchmark
	public void markBuildPlainJDK() throws Exception {
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementation domImpl = registry.getDOMImplementation("XML 3.0");
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

	private static InputStream loadFilefromClasspath(final String cssFilename) {
		return java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<InputStream>() {
			@Override
			public InputStream run() {
				return getClass().getResourceAsStream(cssFilename);
			}
		});
	}

}
