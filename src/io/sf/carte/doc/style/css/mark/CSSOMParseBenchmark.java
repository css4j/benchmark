/*

 Copyright (c) 2017-2019, Carlos Amengual.

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
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;

import io.sf.carte.doc.style.css.om.AbstractCSSStyleSheet;
import io.sf.carte.doc.style.css.om.DOMCSSStyleSheetFactory;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 5, time = 10)
public class CSSOMParseBenchmark {

	private final static String documentText;

	static {
		char[] array = new char[4096];
		StringBuilder buffer = new StringBuilder(array.length);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/sample.css");
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
	public void markParseCSSStyleSheet() throws CSSException, IOException {
		System.setProperty("org.w3c.css.sac.parser", "io.sf.carte.doc.style.css.parser.CSSParser");
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		InputSource source = new InputSource(new StringReader(documentText));
		css.parseStyleSheet(source);
	}

	@Benchmark
	public void markParseCSSStyleSheetSSParser() throws CSSException, IOException {
		System.setProperty("org.w3c.css.sac.parser", "com.steadystate.css.parser.SACParserCSS3");
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		InputSource source = new InputSource(new StringReader(documentText));
		css.parseStyleSheet(source);
	}

	@Benchmark
	public void markParseCSSStyleSheetBatik() throws CSSException, IOException {
		System.setProperty("org.w3c.css.sac.parser", "org.apache.batik.css.parser.Parser");
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		InputSource source = new InputSource(new StringReader(documentText));
		css.parseStyleSheet(source);
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
