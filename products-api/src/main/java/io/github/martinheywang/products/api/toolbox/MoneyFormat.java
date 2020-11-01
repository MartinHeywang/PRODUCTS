package io.github.martinheywang.products.api.toolbox;

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
			"kk", "ll" };

	private MoneyFormat() {
	}

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

	public static final MoneyFormat getSingleton() {
		return instance;
	}
}
