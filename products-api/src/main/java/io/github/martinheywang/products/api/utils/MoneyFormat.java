/*
   Copyright 2021 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton used to format big amount of money.
 * 
 * @author Martin Heywang
 */
public final class MoneyFormat {

	private static final MoneyFormat instance = new MoneyFormat();

	private final String[] suffixes = { "€", "K", "M", "T", "aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj",
			"kk", "ll", "mm", "nn", "oo", "pp", "qq", "rr", "ss", "tt", "uu", "vv", "ww", "xx", "yy", "zz", "you are awesome guy"};

	private MoneyFormat() {
	}

	/**
	 * Formats the given {@link java.math.BigInteger} to a string with currency sufix.
	 * 
	 * @param value the value to format
	 * @return the formated number
	 */
	public final String format(BigInteger value) {

		// As a string
		String stringValue = value.toString();
		final boolean isMinus = stringValue.toCharArray()[0] == '-' ? true : false;
		if (isMinus)
			stringValue = stringValue.substring(1);
		// Packets are groups of three numbers
		final List<String> packets = new ArrayList<>();

		// Create packets
		for (int i = stringValue.length(); 0 < i; i -= 3) {
			if (i <= 3) {
				packets.add(0, stringValue);
				break;
			}
			packets.add(0, stringValue.substring(i - 3));
			stringValue = stringValue.substring(0, i - 3);
		}

		// Create the number according to its length
		final StringBuilder sb = new StringBuilder();
		if (packets.size() > 1) {
			sb.append(packets.get(0));
			sb.append(".");
			sb.append(packets.get(1).substring(0, 2));
			sb.append(suffixes[packets.size() - 1]);
		} else {
			sb.append(packets.get(0));
			sb.append(suffixes[0]);
		}
		if (isMinus)
			sb.insert(0, '-');
		return sb.toString();
	}

	/**
	 * 
	 * @return the singleton.
	 */
	public static final MoneyFormat getSingleton() {
		return instance;
	}
}
