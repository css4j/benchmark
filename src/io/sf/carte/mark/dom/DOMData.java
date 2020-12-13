/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class DOMData extends DocumentData {

	public DOMData() {
		super("/io/sf/carte/mark/dom/mondial-3.0.xml.gz", true, 57369, 22422, "city", 3152);
	}

	@Setup(Level.Trial)
	public void init() {
		super.init();
	}

}
