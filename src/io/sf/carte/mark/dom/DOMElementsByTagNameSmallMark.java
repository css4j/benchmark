/*

 Copyright (c) 2020-2026, Carlos Amengual.

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
public class DOMElementsByTagNameSmallMark {

	@Benchmark
	public void markElementsByTagNameJdk(DOMDataSmall data) {
		NodeList list = data.jdkDoc.getElementsByTagName(data.getTagName());
		int count = countElementsByTagName(list, data.getTagName());
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameDOM4J(DOMDataSmall data) {
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
	public void markElementsByTagNameDOM(DOMDataSmall data) {
		NodeList list = data.domDoc.getElementsByTagName(data.getTagName());
		int count = countElementsByTagName(list, data.getTagName());
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	@Benchmark
	public void markElementsByTagNameJsoup(DOMDataSmall data) {
		Elements list = data.jsoupDoc.getElementsByTag(data.getTagName());
		int count = countElementsByTagName(list, data.getTagName());
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

	private int countElementsByTagName(Elements list, String tagName) {
		int len = list.size();
		for (int i = 0; i < len; i++) {
			Element element = list.get(i);
			// The following test is silly but could prevent optimizations
			if (!tagName.equals(element.tagName())) {
				throw new IllegalStateException();
			}
		}
		return len;
	}

	@Benchmark
	public void markElementsByTagNameDOMit(DOMDataSmall data) {
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
	public void markElementsByTagNameJsoupIt(DOMDataSmall data) {
		int count = 0;
		Elements list = data.jsoupDoc.getElementsByTag(data.getTagName());
		for (Element element : list) {
			// The following test is silly but could prevent optimizations
			if (!data.getTagName().equals(element.tagName())) {
				throw new IllegalStateException();
			}
			count++;
		}
		if (count != data.nameCount) {
			throw new IllegalStateException("Expected a count of " + data.nameCount + ", obtained " + count);
		}
	}

}
