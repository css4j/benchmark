/*

 Copyright (c) 2019-2026, Carlos Amengual.

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */

// SPDX-License-Identifier: BSD-2-Clause OR BSD-3-Clause

package io.sf.carte.mark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;

/**
 * Compare two EqualsIgnoreCase methods.
 */
@Fork(value = 2, warmups = 2)
public class EqualsIgnoreCaseBenchmark {

	@Benchmark
	public void markEqualsIgnoreCaseSHORT() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("123");
		boolean result = equalsIgnoreCase(buf, "123");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseLONGEQ() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("abcdefghijklmnopqrstuvwxyz");
		boolean result = equalsIgnoreCase(buf, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseSHORTNOTEQ() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("abcdefghijklmnopqrstuvwxyz");
		boolean result = equalsIgnoreCase(buf, "ABCCEFGHIJKLMNOPQRSTUVWXYZ");
		if (result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseEQUALS() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("12345678901234567890");
		boolean result = equalsIgnoreCase(buf, "12345678901234567890");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseEQUALSIGNORE() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("12345678901234567890a");
		boolean result = equalsIgnoreCase(buf, "12345678901234567890A");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseNOTEQUALS() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("12345678901234567890");
		boolean result = equalsIgnoreCase(buf, "12345678901234567891");
		if (result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseSHORT1() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("123");
		boolean result = equalsIgnoreCase1(buf, "123");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseLONGEQ1() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("abcdefghijklmnopqrstuvwxyz");
		boolean result = equalsIgnoreCase1(buf, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseSHORTNOTEQ1() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("abcdefghijklmnopqrstuvwxyz");
		boolean result = equalsIgnoreCase1(buf, "ABCCEFGHIJKLMNOPQRSTUVWXYZ");
		if (result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseEQUALS1() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("12345678901234567890");
		boolean result = equalsIgnoreCase1(buf, "12345678901234567890");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseEQUALSIGNORE1() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("12345678901234567890a");
		boolean result = equalsIgnoreCase1(buf, "12345678901234567890A");
		if (!result) {
			throw new RuntimeException();
		}
	}

	@Benchmark
	public void markEqualsIgnoreCaseNOTEQUALS1() {
		StringBuilder buf = new StringBuilder(64);
		buf.append("12345678901234567890");
		boolean result = equalsIgnoreCase1(buf, "12345678901234567891");
		if (result) {
			throw new RuntimeException();
		}
	}

	private static boolean equalsIgnoreCase(CharSequence seq, String lcString) {
		int len = seq.length();
		if (lcString.length() != len) {
			return false;
		}
		for (int i = 0; i < len; i++) {
			char c = seq.charAt(i);
			char lc = lcString.charAt(i);
			if (c != lc) {
				if (Character.isLowerCase(c) || Character.toLowerCase(c) != lc) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean equalsIgnoreCase1(CharSequence seq, String lcString) {
		int len = seq.length();
		if (lcString.length() != len) {
			return false;
		}
		char[] lcArray = lcString.toCharArray();
		for (int i = 0; i < len; i++) {
			char c = seq.charAt(i);
			char lc = lcArray[i];
			if (c != lc) {
				if (Character.isLowerCase(c) || Character.toLowerCase(c) != lc) {
					return false;
				}
			}
		}
		return true;
	}

}
