/*

 Copyright (c) 2020-2026, Carlos Amengual.

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

// SPDX-License-Identifier: BSD-2-Clause OR BSD-3-Clause

package io.sf.carte.mark.dom;

import org.jsoup.Jsoup;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import io.sf.carte.mark.Util;

@State(Scope.Benchmark)
public class DOMDataSmall extends DocumentData {

	public DOMDataSmall() {
		super("/io/sf/carte/mark/dom/xhtml1.xml", false, 1566, 713, "li", 55);
	}

	@Setup(Level.Trial)
	public void init() {
		super.init();
		String documentText = Util.loadFilefromClasspath("/io/sf/carte/mark/dom/xhtml1.xml");
		jsoupDoc = Jsoup.parse(documentText);
	}

}
