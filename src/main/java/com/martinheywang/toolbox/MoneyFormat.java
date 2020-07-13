package com.martinheywang.toolbox;

import java.math.BigInteger;

/**
 * Singleton used to format big amount of money.
 * 
 * @author Martin Heywang
 */
public final class MoneyFormat {

	private static final MoneyFormat instance = new MoneyFormat();

	private MoneyFormat() {
	}

	public final String format(BigInteger value) {
		final StringBuilder builder = new StringBuilder();

		/*
		 * <?> The rule to apply depends on the length of the String
		 * (thousands, millions, ...)
		 */
		switch (value.toString().length()) {
		case 4:
		case 5:
		case 6:
			divideAndAppend(builder, value, BigInteger.TEN.pow(3), "K");
			break;
		case 7:
		case 8:
		case 9:
			divideAndAppend(builder, value, BigInteger.TEN.pow(6), "M");
			break;
		case 10:
		case 11:
		case 12:
			divideAndAppend(builder, value, BigInteger.TEN.pow(9), "Md");
			break;
		default:
			// Default
			builder.append(value.toString());
			builder.append(" €");
			break;
		}

		return builder.toString();
	}

	/**
	 * Formats the given BigInteger by dividing his value and appending
	 * the corresponding suffix.
	 * 
	 * @param sb      the builder to append the formatted string
	 * @param value   the value to format
	 * @param divider by how many the value should be divided
	 * @param suffix  the suffix to append after the value.
	 */
	private void divideAndAppend(StringBuilder sb, BigInteger value,
			BigInteger divider,
			String suffix) {
		final BigInteger[] n = value.divideAndRemainder(divider);
		final String integer = n[0].toString();

		sb.append(integer);

		if (!n[1].toString().equals("0")) {
			sb.append(".");
			sb.append(n[1].toString());
		}

		sb.append(suffix);
	}

	public static final MoneyFormat getSingleton() {
		return instance;
	}
}
