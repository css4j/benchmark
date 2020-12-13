/*

 Copyright (c) 2020, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.dom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.sf.carte.doc.dom.DOMDocument;
import io.sf.carte.doc.dom.DOMElement;
import io.sf.carte.doc.dom.ElementList;

@Threads(4)
@Fork(value = 2)
@Measurement(iterations = 16, time = 10)
@Warmup(iterations = 6, time = 10)
public class DOMElementsByTagNameMark {

	@Benchmark
	public void markElementsByTagNameJdk(DOMData data) {
		NodeList list = data.jdkDoc.getElementsByTagName(data.getTagName());
		int count = countElementsByTagName(list, data.getTagName());
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameDOM4J(DOMData data) {
		NodeList list = data.dom4jDoc.getElementsByTagName(data.getTagName());
		int count = countElementsByTagName(list, data.getTagName());
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	private int countElementsByTagName(NodeList list, String tagName) {
		int len = list.getLength();
		for (int i = 0; i < len; i++) {
			Node element = list.item(i);
			// The following test is silly but could prevent optimizations
			if (!tagName.equals(element.getNodeName())) {
				throw new IllegalStateException();
			}
		}
		return len;
	}

	@Benchmark
	public void markElementsByTagNameDOM(DOMData data) {
		NodeList list = data.domDoc.getElementsByTagName(data.getTagName());
		int count = countElementsByTagName(list, data.getTagName());
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameDOMit(DOMData data) {
		int count = 0;
		ElementList list = ((DOMDocument) data.domDoc).getElementsByTagName(data.getTagName());
		for (DOMElement element : list) {
			// The following test is silly but could prevent optimizations
			if (!data.getTagName().equals(element.getNodeName())) {
				throw new IllegalStateException();
			}
			count++;
		}
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

}
