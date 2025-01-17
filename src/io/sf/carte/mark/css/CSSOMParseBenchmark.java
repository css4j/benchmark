/*

 Copyright (c) 2017-2025, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.css;

import java.io.IOException;
import java.io.StringReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.w3c.dom.DOMException;

import io.sf.carte.doc.style.css.om.AbstractCSSStyleSheet;
import io.sf.carte.doc.style.css.om.DOMCSSStyleSheetFactory;
import io.sf.carte.mark.Util;

@Fork(value = 2, warmups = 2)
@Measurement(iterations = 18)
public class CSSOMParseBenchmark {

	private final static String documentText = Util.loadFilefromClasspath("/io/sf/carte/mark/css/sample.css");

	@Benchmark
	public void markParseCSSStyleSheet() throws DOMException, IOException {
		DOMCSSStyleSheetFactory factory = new DOMCSSStyleSheetFactory();
		AbstractCSSStyleSheet css = factory.createStyleSheet(null, null);
		if (!css.parseStyleSheet(new StringReader(documentText))) {
			throw new DOMException(DOMException.SYNTAX_ERR, "CSS errors.");
		}
	}

}
