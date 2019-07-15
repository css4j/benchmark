/*

 Copyright (c) 2017-2019, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://carte.sourceforge.io/LICENSE.txt

 */

package io.sf.carte.doc.style.css.mark;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;

@Warmup(iterations = 22)
public class SACBatikSSBenchmark {

	@Benchmark
	public void markSACParseStyleSheetSSParser() throws CSSException, IOException {
		Parser cssParser = new com.steadystate.css.parser.SACParserCSS3();
		DocumentHandler handler = new SACBenchmark.BenchmarkDocumentHandler();
		cssParser.setDocumentHandler(handler);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/sample.css");
		InputSource source = new InputSource(new InputStreamReader(is, "UTF-8"));
		cssParser.parseStyleSheet(source);
		is.close();
	}

	@Benchmark
	public void markSACParseStyleSheetBatik() throws CSSException, IOException {
		Parser cssParser = new org.apache.batik.css.parser.Parser();
		DocumentHandler handler = new SACBenchmark.BenchmarkDocumentHandler();
		cssParser.setDocumentHandler(handler);
		InputStream is = loadFilefromClasspath("/io/sf/carte/doc/style/css/mark/sample.css");
		InputSource source = new InputSource(new InputStreamReader(is, "UTF-8"));
		cssParser.parseStyleSheet(source);
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
