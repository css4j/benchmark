/*

 Copyright (c) 2021-2024, Carlos Amengual.

 SPDX-License-Identifier: BSD-3-Clause

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

package io.sf.carte.mark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;

/**
 * Compare two ways to append a codepoint to a StringBuilder.
 */
@Fork(value = 2, warmups = 2)
public class AppendCpMark {

	private static final int NUMITER = 32768;

	private static final int[] array = {'a','b','c','d','e','f','g','h','i','j','k','l','m'
			,'n','ñ','o','ö','p','q','r','s','t','u','ü','v','x','y','z',0x1f44d
			,'a','b','c','d',0x1f6a7,'b','b','b','b','b','b','b','b','b','b','b'};

	@Benchmark
	public void markAppendCodePoint() {
		StringBuilder buf = new StringBuilder(array.length + 2);
		for (int i = 0; i < NUMITER; i++) {
			for (int j = 0; j < array.length; j++) {
				buf.appendCodePoint(array[j]);
			}
			buf.setLength(0);
		}
	}

	@Benchmark
	public void markAppendCpArray() {
		StringBuilder buf = new StringBuilder(array.length + 2);
		for (int i = 0; i < NUMITER; i++) {
			for (int j = 0; j < array.length; j++) {
				buf.append(Character.toChars(array[j]));
			}
			buf.setLength(0);
		}
	}

}
