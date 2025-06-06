/*

 Copyright (c) 2020-2025, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class Util {

	public static String loadFilefromClasspath(final String cssFilename) {
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
		return Util.class.getResourceAsStream(cssFilename);
	}

	public static String loadCompressedFilefromClasspath(final String cssFilename) {
		char[] array = new char[4096];
		StringBuilder buffer = new StringBuilder(array.length);
		InputStream is = readCompressedFilefromClasspath(cssFilename);
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

	private static InputStream readCompressedFilefromClasspath(final String cssFilename) {
		try {
			return new GZIPInputStream(Util.class.getResourceAsStream(cssFilename), 4096);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
