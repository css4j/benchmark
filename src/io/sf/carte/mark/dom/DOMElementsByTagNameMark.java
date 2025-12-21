/*

 Copyright (c) 2020-2025, Carlos Amengual.

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

// SPDX-License-Identifier: BSD-2-Clause OR BSD-3-Clause

package io.sf.carte.mark.dom;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.sf.carte.doc.dom.DOMDocument;
import io.sf.carte.doc.dom.DOMElement;
import io.sf.carte.doc.dom.ElementList;

@Fork(value = 1)
@Measurement(iterations = 8, time = 10)
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

	@Benchmark
	public void markElementsByTagNameJsoup(DOMData data) {
		int count = 0;
		Elements list = data.jsoupDoc.getElementsByTag(data.getTagName());
		int n = list.size();
		for (int i = 0; i < n; i++) {
			Element element = list.get(i);
			// The following test is silly but could prevent optimizations
			if (!data.getTagName().equals(element.nodeName())) {
				throw new IllegalStateException();
			}
			count++;
		}
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameJsoupIt(DOMData data) {
		int count = 0;
		Elements list = data.jsoupDoc.getElementsByTag(data.getTagName());
		for (Element element : list) {
			// The following test is silly but could prevent optimizations
			if (!data.getTagName().equals(element.nodeName())) {
				throw new IllegalStateException();
			}
			count++;
		}
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

}
