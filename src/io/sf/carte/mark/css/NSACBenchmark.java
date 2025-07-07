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
import org.openjdk.jmh.annotations.Warmup;

import io.sf.carte.doc.style.css.BooleanCondition;
import io.sf.carte.doc.style.css.MediaQueryList;
import io.sf.carte.doc.style.css.nsac.CSSHandler;
import io.sf.carte.doc.style.css.nsac.LexicalUnit;
import io.sf.carte.doc.style.css.nsac.PageSelectorList;
import io.sf.carte.doc.style.css.nsac.Parser;
import io.sf.carte.doc.style.css.nsac.ParserControl;
import io.sf.carte.doc.style.css.nsac.SelectorList;
import io.sf.carte.mark.Util;

@Warmup(iterations = 22)
public class NSACBenchmark {

	private final static String documentText = Util.loadFilefromClasspath("/io/sf/carte/mark/css/sample.css");

	@Benchmark
	public void markSACParseStyleSheet() throws IOException {
		Parser cssParser = new io.sf.carte.doc.style.css.parser.CSSParser();
		BenchmarkDocumentHandler handler = new BenchmarkDocumentHandler();
		cssParser.setDocumentHandler(handler);
		cssParser.parseStyleSheet(new StringReader(documentText));
	}

	static class BenchmarkDocumentHandler implements CSSHandler {

		// To avoid the effect of possible optimizations, put a counter
		int counter = 0;

		@Override
		public void parseStart(ParserControl parserctl) {
			counter++;
		}

		@Override
		public void endOfStream() {
			counter++;
		}

		@Override
		public void comment(String text, boolean precededByLF) {
			counter++;
		}

		@Override
		public void ignorableAtRule(String atRule) {
			counter++;
		}

		@Override
		public void namespaceDeclaration(String prefix, String uri) {
			counter++;
		}

		@Override
		public void importStyle(String uri, String layerName, BooleanCondition supportsCondition,
				MediaQueryList media, String defaultNamespaceURI) {
			counter++;
		}

		@Override
		public void startMedia(MediaQueryList media) {
			counter++;
		}

		@Override
		public void endMedia(MediaQueryList media) {
			counter++;
		}

		@Override
		public void startPage(PageSelectorList pageSelectorList) {
			counter++;
		}

		@Override
		public void endPage(PageSelectorList pageSelectorList) {
			counter++;
		}

		@Override
		public void startMargin(String name) {
			counter++;
		}

		@Override
		public void endMargin() {
			counter++;
		}

		@Override
		public void startFontFace() {
			counter++;
		}

		@Override
		public void endFontFace() {
			counter++;
		}

		@Override
		public void startCounterStyle(String name) {
			counter++;
		}

		@Override
		public void endCounterStyle() {
			counter++;
		}

		@Override
		public void startKeyframes(String name) {
			counter++;
		}

		@Override
		public void endKeyframes() {
			counter++;
		}

		@Override
		public void startKeyframe(LexicalUnit keyframeSelector) {
			counter++;
		}

		@Override
		public void endKeyframe() {
			counter++;
		}

		@Override
		public void startFontFeatures(String[] familyName) {
			counter++;
		}

		@Override
		public void endFontFeatures() {
			counter++;
		}

		@Override
		public void startFeatureMap(String mapName) {
			counter++;
		}

		@Override
		public void endFeatureMap() {
			counter++;
		}

		@Override
		public void startSupports(BooleanCondition condition) {
			counter++;
		}

		@Override
		public void endSupports(BooleanCondition condition) {
			counter++;
		}

		@Override
		public void startSelector(SelectorList selectors) {
			counter++;
		}

		@Override
		public void endSelector(SelectorList selectors) {
			counter++;
		}

		@Override
		public void startProperty(String name) {
			counter++;
		}

		@Override
		public void endProperty(boolean discard) {
			counter++;
		}

		@Override
		public void startViewport() {
			counter++;
		}

		@Override
		public void endViewport() {
			counter++;
		}

		@Override
		public void property(String name, LexicalUnit value, boolean important) {
			counter++;
		}

		@Override
		public void lexicalProperty(String propertyName, LexicalUnit lunit, boolean important) {
			counter++;
		}

	}
}
