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

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;

import io.sf.carte.doc.style.css.om.AbstractCSSStyleSheet;
import io.sf.carte.doc.style.css.om.DOMCSSStyleSheetFactory;

@Fork(value = 2, warmups = 2)
@Measurement(iterations = 18)
public class CSSOMParseBenchmark {

	@Benchmark
	public void markParseCSSStyleSheet() throws CSSException, IOException {
		System.setProperty("org.w3c.css.sac.parser", "io.sf.carte.doc.style.css.parser.CSSParser");
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/sample.css");
		InputSource source = new InputSource(new InputStreamReader(is, "UTF-8"));
		css.parseStyleSheet(source);
		is.close();
	}

	@Benchmark
	public void markParseCSSStyleSheetSSParser() throws CSSException, IOException {
		System.setProperty("org.w3c.css.sac.parser", "com.steadystate.css.parser.SACParserCSS3");
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/sample.css");
		InputSource source = new InputSource(new InputStreamReader(is, "UTF-8"));
		css.parseStyleSheet(source);
		is.close();
	}

	@Benchmark
	public void markParseCSSStyleSheetBatik() throws CSSException, IOException {
		System.setProperty("org.w3c.css.sac.parser", "org.apache.batik.css.parser.Parser");
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/sample.css");
		InputSource source = new InputSource(new InputStreamReader(is, "UTF-8"));
		css.parseStyleSheet(source);
		is.close();
	}

	private InputStream loadFilefromClasspath(final String cssFilename) {
		return java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<InputStream>() {
			@Override
			public InputStream run() {
				return getClass().getResourceAsStream(cssFilename);
			}
		});
	}

}
