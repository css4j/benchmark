/*

 Copyright (c) 2024-2025, Carlos Amengual.

 Licensed under a BSD-style License. You can find the license here:
 https://css4j.github.io/LICENSE.txt

 */
/*
 * SPDX-License-Identifier: BSD-3-Clause
 */

package io.sf.carte.mark;

import java.util.HashMap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;

import io.sf.carte.doc.style.css.CSSUnit;

/**
 * Compare ways to convert a unit string to a numeric ID.
 */
@Fork(value = 2, warmups = 2)
public class UnitStringToNumberMark {

	private static HashMap<String, Short> unitMap = createUnitMap();

	private static HashMap<String, Short> createUnitMap() {
		HashMap<String, Short> unitMap = new HashMap<>(37);
		unitMap.put("%", CSSUnit.CSS_PERCENTAGE);
		unitMap.put("em", CSSUnit.CSS_EM);
		unitMap.put("ex", CSSUnit.CSS_EX);
		unitMap.put("cap", CSSUnit.CSS_CAP);
		unitMap.put("ch", CSSUnit.CSS_CH);
		unitMap.put("lh", CSSUnit.CSS_LH);
		unitMap.put("ic", CSSUnit.CSS_IC);
		unitMap.put("rem", CSSUnit.CSS_REM);
		unitMap.put("rex", CSSUnit.CSS_REX);
		unitMap.put("rch", CSSUnit.CSS_RCH);
		unitMap.put("ric", CSSUnit.CSS_RIC);
		unitMap.put("rlh", CSSUnit.CSS_RLH);
		unitMap.put("vw", CSSUnit.CSS_VW);
		unitMap.put("vh", CSSUnit.CSS_VH);
		unitMap.put("vi", CSSUnit.CSS_VI);
		unitMap.put("vb", CSSUnit.CSS_VB);
		unitMap.put("vmin", CSSUnit.CSS_VMIN);
		unitMap.put("vmax", CSSUnit.CSS_VMAX);
		unitMap.put("cm", CSSUnit.CSS_CM);
		unitMap.put("mm", CSSUnit.CSS_MM);
		unitMap.put("q", CSSUnit.CSS_QUARTER_MM);
		unitMap.put("in", CSSUnit.CSS_IN);
		unitMap.put("pt", CSSUnit.CSS_PT);
		unitMap.put("pc", CSSUnit.CSS_PC);
		unitMap.put("px", CSSUnit.CSS_PX);
		unitMap.put("deg", CSSUnit.CSS_DEG);
		unitMap.put("grad", CSSUnit.CSS_GRAD);
		unitMap.put("rad", CSSUnit.CSS_RAD);
		unitMap.put("turn", CSSUnit.CSS_TURN);
		unitMap.put("s", CSSUnit.CSS_S);
		unitMap.put("ms", CSSUnit.CSS_MS);
		unitMap.put("hz", CSSUnit.CSS_HZ);
		unitMap.put("khz", CSSUnit.CSS_KHZ);
		unitMap.put("dpi", CSSUnit.CSS_DPI);
		unitMap.put("dpcm", CSSUnit.CSS_DPCM);
		unitMap.put("dppx", CSSUnit.CSS_DPPX);
		unitMap.put("fr", CSSUnit.CSS_FR);
		return unitMap;
	}

	@Benchmark
	public void markHashMap() {
		int unit = unitFromStringHashMap("%");
		unit += unitFromStringHashMap("em");
		unit += unitFromStringHashMap("ex");
		unit += unitFromStringHashMap("rem");
		unit += unitFromStringHashMap("rex");
		unit += unitFromStringHashMap("in");
		unit += unitFromStringHashMap("pt");
		unit += unitFromStringHashMap("pc");
		unit += unitFromStringHashMap("px");
		unit += unitFromStringHashMap("deg");
		unit += unitFromStringHashMap("turn");
		unit += unitFromStringHashMap("ooo");
		unit += unitFromStringHashMap("s");
		unit += unitFromStringHashMap("ms");

		// px and pt are very frequent
		for (int i = 0; i < 200; i++) {
			unit += unitFromStringHashMap("px");
			unit += unitFromStringHashMap("pt");
		}

		assert (unit > 2200);
	}

	@Benchmark
	public void markHashMapDefault() {
		int unit = unitFromStringHashMapDefault("%");
		unit += unitFromStringHashMapDefault("em");
		unit += unitFromStringHashMapDefault("ex");
		unit += unitFromStringHashMapDefault("rem");
		unit += unitFromStringHashMapDefault("rex");
		unit += unitFromStringHashMapDefault("in");
		unit += unitFromStringHashMapDefault("pt");
		unit += unitFromStringHashMapDefault("pc");
		unit += unitFromStringHashMapDefault("px");
		unit += unitFromStringHashMapDefault("deg");
		unit += unitFromStringHashMapDefault("turn");
		unit += unitFromStringHashMapDefault("ooo");
		unit += unitFromStringHashMapDefault("s");
		unit += unitFromStringHashMapDefault("ms");

		// px and pt are very frequent
		for (int i = 0; i < 200; i++) {
			unit += unitFromStringHashMapDefault("px");
			unit += unitFromStringHashMapDefault("pt");
		}

		assert (unit > 2200);
	}

	@Benchmark
	public void markEquals() {
		int unit = unitFromStringEQUALS("%");
		unit += unitFromStringEQUALS("em");
		unit += unitFromStringEQUALS("ex");
		unit += unitFromStringEQUALS("rem");
		unit += unitFromStringEQUALS("rex");
		unit += unitFromStringEQUALS("in");
		unit += unitFromStringEQUALS("pt");
		unit += unitFromStringEQUALS("pc");
		unit += unitFromStringEQUALS("px");
		unit += unitFromStringEQUALS("deg");
		unit += unitFromStringEQUALS("turn");
		unit += unitFromStringEQUALS("ooo");
		unit += unitFromStringEQUALS("s");
		unit += unitFromStringEQUALS("ms");

		// px and pt are very frequent
		for (int i = 0; i < 200; i++) {
			unit += unitFromStringEQUALS("px");
			unit += unitFromStringEQUALS("pt");
		}

		assert (unit > 2200);
	}

	/**
	 * Retrieves the CSS unit associated to the given unit string.
	 * 
	 * @param unitString the unit string.
	 * @return the associated CSS unit, or <code>CSS_OTHER</code> if the unit is
	 *         not known.
	 */
	public static short unitFromStringHashMap(String unitString) {
		Short unit = unitMap.get(unitString);
		return unit != null ? unit.shortValue() : CSSUnit.CSS_OTHER;
	}

	/**
	 * Retrieves the CSS unit associated to the given unit string.
	 * 
	 * @param unitString the unit string.
	 * @return the associated CSS unit, or <code>CSS_OTHER</code> if the unit is
	 *         not known.
	 */
	public static short unitFromStringHashMapDefault(String unitString) {
		Short other = Short.valueOf(CSSUnit.CSS_OTHER);
		return unitMap.getOrDefault(unitString, other).shortValue();
	}

	/**
	 * Retrieves the CSS unit associated to the given unit string.
	 * 
	 * @param unit the unit string (must be interned).
	 * @return the associated CSS unit, or <code>CSS_OTHER</code> if the unit is
	 *         not known.
	 */
	public static short unitFromStringEQUALS(String unit) {
		if (unit == "%") {
			return CSSUnit.CSS_PERCENTAGE;
		} else if (unit == "em") {
			return CSSUnit.CSS_EM;
		} else if (unit == "ex") {
			return CSSUnit.CSS_EX;
		} else if (unit == "cap") {
			return CSSUnit.CSS_CAP;
		} else if (unit == "ch") {
			return CSSUnit.CSS_CH;
		} else if (unit == "lh") {
			return CSSUnit.CSS_LH;
		} else if (unit == "ic") {
			return CSSUnit.CSS_IC;
		} else if (unit == "rem") {
			return CSSUnit.CSS_REM;
		} else if (unit == "rex") {
			return CSSUnit.CSS_REX;
		} else if (unit == "rch") {
			return CSSUnit.CSS_RCH;
		} else if (unit == "ric") {
			return CSSUnit.CSS_RIC;
		} else if (unit == "rlh") {
			return CSSUnit.CSS_RLH;
		} else if (unit == "vw") {
			return CSSUnit.CSS_VW;
		} else if (unit == "vh") {
			return CSSUnit.CSS_VH;
		} else if (unit == "vi") {
			return CSSUnit.CSS_VI;
		} else if (unit == "vb") {
			return CSSUnit.CSS_VB;
		} else if (unit == "vmin") {
			return CSSUnit.CSS_VMIN;
		} else if (unit == "vmax") {
			return CSSUnit.CSS_VMAX;
		} else if (unit == "cm") {
			return CSSUnit.CSS_CM;
		} else if (unit == "mm") {
			return CSSUnit.CSS_MM;
		} else if (unit == "q") {
			return CSSUnit.CSS_QUARTER_MM;
		} else if (unit == "in") {
			return CSSUnit.CSS_IN;
		} else if (unit == "pt") {
			return CSSUnit.CSS_PT;
		} else if (unit == "pc") {
			return CSSUnit.CSS_PC;
		} else if (unit == "px") {
			return CSSUnit.CSS_PX;
		} else if (unit == "deg") {
			return CSSUnit.CSS_DEG;
		} else if (unit == "grad") {
			return CSSUnit.CSS_GRAD;
		} else if (unit == "rad") {
			return CSSUnit.CSS_RAD;
		} else if (unit == "turn") {
			return CSSUnit.CSS_TURN;
		} else if (unit == "s") {
			return CSSUnit.CSS_S;
		} else if (unit == "ms") {
			return CSSUnit.CSS_MS;
		} else if (unit == "hz") {
			return CSSUnit.CSS_HZ;
		} else if (unit == "khz") {
			return CSSUnit.CSS_KHZ;
		} else if (unit == "dpi") {
			return CSSUnit.CSS_DPI;
		} else if (unit == "dpcm") {
			return CSSUnit.CSS_DPCM;
		} else if (unit == "dppx") {
			return CSSUnit.CSS_DPPX;
		} else if (unit == "fr") {
			return CSSUnit.CSS_FR;
		}
		return CSSUnit.CSS_OTHER;
	}

}
