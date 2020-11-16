/*

 Copyright (c) 2017-2020, Carlos Amengual.

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
import org.w3c.dom.DOMException;

import io.sf.carte.doc.style.css.om.AbstractCSSStyleSheet;
import io.sf.carte.doc.style.css.om.DOMCSSStyleSheetFactory;

@Fork(value = 2, warmups = 2)
@Measurement(iterations = 18)
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
	public void markParseCSSStyleSheet() throws DOMException, IOException {
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		if (!css.parseStyleSheet(new StringReader(documentText))) {
			throw new DOMException(DOMException.SYNTAX_ERR, "CSS errors.");
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
