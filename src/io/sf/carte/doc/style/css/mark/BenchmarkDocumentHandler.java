/*

 Copyright (c) 2017-2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.doc.style.css.mark;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;

class BenchmarkDocumentHandler implements DocumentHandler {

	// To avoid the effect of possible optimizations, put a counter
	int counter = 0;

	@Override
	public void startDocument(InputSource source) throws CSSException {
	}

	@Override
	public void endDocument(InputSource source) throws CSSException {
	}

	@Override
	public void comment(String text) throws CSSException {
		// counter++; SS CSSParser does not support SAC comments
	}

	@Override
	public void ignorableAtRule(String atRule) throws CSSException {
		counter++;
	}

	@Override
	public void namespaceDeclaration(String prefix, String uri) throws CSSException {
		counter++;
	}

	@Override
	public void importStyle(String uri, SACMediaList media, String defaultNamespaceURI) throws CSSException {
		counter++;
	}

	@Override
	public void startMedia(SACMediaList media) throws CSSException {
		counter++;
	}

	@Override
	public void endMedia(SACMediaList media) throws CSSException {
		counter++;
	}

	@Override
	public void startPage(String name, String pseudo_page) throws CSSException {
		counter++;
	}

	@Override
	public void endPage(String name, String pseudo_page) throws CSSException {
		counter++;
	}

	@Override
	public void startFontFace() throws CSSException {
		counter++;
	}

	@Override
	public void endFontFace() throws CSSException {
		counter++;
	}

	@Override
	public void startSelector(SelectorList selectors) throws CSSException {
		counter++;
	}

	@Override
	public void endSelector(SelectorList selectors) throws CSSException {
		counter++;
	}

	@Override
	public void property(String name, LexicalUnit value, boolean important) throws CSSException {
		counter++;
	}

}
