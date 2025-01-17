/*

 Copyright (c) 2017-2025, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark.svg;

import java.awt.Dimension;
import java.awt.Font;
import java.io.CharArrayWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import io.sf.carte.echosvg.svggen.SVGGeneratorContext;
import io.sf.carte.echosvg.svggen.SVGGeneratorContext.GraphicContextDefaults;
import io.sf.carte.echosvg.svggen.SVGGraphics2D;
import io.sf.carte.echosvg.transcoder.TranscoderException;

@Fork(value = 2, warmups = 2)
@Measurement(iterations = 18)
public class SVGGraphics2DMark {

	@Benchmark
	public void markSVGGraphics2D() throws TranscoderException, IOException {
		FontDecorationPainter painter = new FontDecorationPainter();

		SVGGraphics2D g2d = createSVGGraphics2D(createDocument());

		// Set some appropriate dimension
		g2d.setSVGCanvasSize(new Dimension(300, 400));

		painter.paint(g2d);

		CharArrayWriter wri = new CharArrayWriter(1900);
		g2d.stream(wri);

		int len = wri.toCharArray().length;
		assert (len > 1800);
	}

	@Benchmark
	public void markSVGGraphics2D_Batik() throws TranscoderException, IOException {
		FontDecorationPainter painter = new FontDecorationPainter();

		org.apache.batik.svggen.SVGGraphics2D g2d = createBatikSVGGraphics2D(createDocument());

		// Set some appropriate dimension
		g2d.setSVGCanvasSize(new Dimension(300, 400));

		painter.paint(g2d);

		CharArrayWriter wri = new CharArrayWriter(1900);
		g2d.stream(wri);

		int len = wri.toCharArray().length;
		assert (len > 1800);
	}

	/**
	 * Creates a <code>Document</code> with an SVG roots.
	 * 
	 * @return the <code>Document</code>.
	 */
	private static Document createDocument() {
		// We need a Document that holds an SVG root element.
		// First obtain a DocumentBuilder as a way to get it.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}

		// Now the document which is what is needed
		Document doc = builder.newDocument();

		// Create a SVG DTD
		DocumentType dtd = builder.getDOMImplementation().createDocumentType("svg",
				"-//W3C//DTD SVG 1.1//EN", "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd");
		// And the root element in the SVG namespace
		Element svgRoot = doc.createElementNS("http://www.w3.org/2000/svg", "svg");

		// Append those to the document
		doc.appendChild(dtd);
		doc.appendChild(svgRoot);

		return doc;
	}

	/**
	 * Creates a <code>SVGGraphics2D</code> with certain defaults.
	 * 
	 * @return the <code>SVGGraphics2D</code>.
	 */
	static SVGGraphics2D createSVGGraphics2D(Document doc) {
		/*
		 * Now the document is ready: let's create some context objects and then the
		 * SVGGraphics2D.
		 */

		// For simplicity, create a generator context with some defaults
		SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(doc);

		// Create the context defaults, with a default font just in case
		GraphicContextDefaults defaults = new GraphicContextDefaults();
		defaults.setFont(new Font("Arial", Font.PLAIN, 12));
		// Set the defaults
		ctx.setGraphicContextDefaults(defaults);

		return new SVGGraphics2D(ctx, false);
	}

	/**
	 * Creates a Batik <code>SVGGraphics2D</code> with certain defaults.
	 * 
	 * @return the <code>SVGGraphics2D</code>.
	 */
	static org.apache.batik.svggen.SVGGraphics2D createBatikSVGGraphics2D(Document doc) {
		/*
		 * Now the document is ready: let's create some context objects and then the
		 * SVGGraphics2D.
		 */

		// For simplicity, create a generator context with some defaults
		org.apache.batik.svggen.SVGGeneratorContext ctx = org.apache.batik.svggen.SVGGeneratorContext
				.createDefault(doc);

		// Create the context defaults, with a default font just in case
		org.apache.batik.svggen.SVGGeneratorContext.GraphicContextDefaults defaults = new org.apache.batik.svggen.SVGGeneratorContext.GraphicContextDefaults();
		defaults.setFont(new Font("Arial", Font.PLAIN, 12));
		// Set the defaults
		ctx.setGraphicContextDefaults(defaults);

		return new org.apache.batik.svggen.SVGGraphics2D(ctx, false);
	}

}
