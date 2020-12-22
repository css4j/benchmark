/*

 Copyright (c) 2017-2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.css;

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
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 5, time = 10)
public class SACBenchmark {

	private final static String documentText = loadFilefromClasspath("/io/sf/carte/mark/css/sample.css");

	@Benchmark
	public void markSACParseStyleSheet() throws CSSException, IOException {
		Parser cssParser = new io.sf.carte.doc.style.css.parser.CSSParser();
		sacBenchmark(cssParser);
	}

	private void sacBenchmark(Parser cssParser) throws CSSException, IOException {
		BenchmarkDocumentHandler handler = new BenchmarkDocumentHandler();
		cssParser.setDocumentHandler(handler);
		BenchmarkErrorHandler errHandler = new BenchmarkErrorHandler();
		cssParser.setErrorHandler(errHandler);
		InputSource source = new InputSource(new StringReader(documentText));
		cssParser.parseStyleSheet(source);
		final int expected = 13064;
		// final int expected = 632; // small file
		if (handler.counter != expected ) {
			throw new RuntimeException("Expected " + expected + ", found " + handler.counter + '.');
		}
	}

	@Benchmark
	public void markSACParseStyleSheetSSParser() throws CSSException, IOException {
		Parser cssParser = new com.steadystate.css.parser.SACParserCSS3();
		sacBenchmark(cssParser);
	}

	@Benchmark
	public void markSACParseStyleSheetBatik() throws CSSException, IOException {
		Parser cssParser = new org.apache.batik.css.parser.Parser();
		sacBenchmark(cssParser);
	}

	private static String loadFilefromClasspath(final String cssFilename) {
		char[] array = new char[4096];
		StringBuilder buffer = new StringBuilder(array.length);
		InputStream is = readFilefromClasspath(cssFilename);
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
		return buffer.toString();
	}

	private static InputStream readFilefromClasspath(final String cssFilename) {
		return java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<InputStream>() {
			@Override
			public InputStream run() {
				return getClass().getResourceAsStream(cssFilename);
			}
		});
	}

	private class BenchmarkErrorHandler implements ErrorHandler {

		@Override
		public void warning(CSSParseException exception) throws CSSException {
		}

		@Override
		public void error(CSSParseException exception) throws CSSException {
			throw new RuntimeException(
					"Error at line " + exception.getLineNumber() + " column " + exception.getColumnNumber(), exception);
		}

		@Override
		public void fatalError(CSSParseException exception) throws CSSException {
			error(exception);
		}

	}

}
