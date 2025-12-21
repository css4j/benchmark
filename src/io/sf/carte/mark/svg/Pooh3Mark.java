/*

 Copyright (c) 2017-2025, Carlos Amengual.

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

// SPDX-License-Identifier: BSD-2-Clause OR BSD-3-Clause

package io.sf.carte.mark.svg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import io.sf.carte.echosvg.anim.dom.SVGDOMImplementation;
import io.sf.carte.echosvg.dom.util.SAXDocumentFactory;
import io.sf.carte.echosvg.transcoder.TranscoderException;
import io.sf.carte.echosvg.transcoder.TranscoderInput;
import io.sf.carte.echosvg.transcoder.TranscoderOutput;
import io.sf.carte.echosvg.transcoder.image.PNGTranscoder;
import io.sf.carte.mark.Util;

@Fork(value = 2, warmups = 1)
@Warmup(iterations = 8)
@Measurement(iterations = 18)
public class Pooh3Mark {

	private final static String svgImage = Util.loadFilefromClasspath("svg/pooh3.svg");

	private final static String imageURI = Pooh3Mark.class.getResource("pooh3.svg").toExternalForm();

	@Benchmark
	public void markPooh3() throws TranscoderException, IOException {
		org.w3c.dom.DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		SAXDocumentFactory f = new SAXDocumentFactory(impl);
		Reader re = new StringReader(svgImage);
		org.w3c.dom.Document document = f.createDocument(imageURI, re);

		PNGTranscoder trans = new PNGTranscoder();
		TranscoderInput input = new TranscoderInput(document);
		ByteArrayOutputStream ostream = new ByteArrayOutputStream(2000);
		TranscoderOutput output = new TranscoderOutput(ostream);

		trans.transcode(input, output);

		assert (ostream.size() > 2000);
	}

	@Benchmark
	public void markPooh3_Batik()
			throws org.apache.batik.transcoder.TranscoderException, IOException {
		org.w3c.dom.DOMImplementation impl = org.apache.batik.anim.dom.SVGDOMImplementation
				.getDOMImplementation();
		org.apache.batik.dom.util.SAXDocumentFactory f = new org.apache.batik.dom.util.SAXDocumentFactory(
				impl, null);
		Reader re = new StringReader(svgImage);
		org.w3c.dom.Document document = f.createDocument(imageURI, re);

		org.apache.batik.transcoder.image.PNGTranscoder trans = new org.apache.batik.transcoder.image.PNGTranscoder();
		org.apache.batik.transcoder.TranscoderInput input = new org.apache.batik.transcoder.TranscoderInput(
				document);
		ByteArrayOutputStream ostream = new ByteArrayOutputStream(2000);
		org.apache.batik.transcoder.TranscoderOutput output = new org.apache.batik.transcoder.TranscoderOutput(
				ostream);

		trans.transcode(input, output);

		assert (ostream.size() > 2000);
	}

}
