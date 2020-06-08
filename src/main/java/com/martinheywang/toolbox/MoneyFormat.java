package com.martinheywang.toolbox;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Singleton used to format big amount of money.
 * 
 * @author Martin Heywang
 */
public final class MoneyFormat {

	private static final HashMap<Integer, String> associations = new HashMap<>();
	private static final MoneyFormat instance = new MoneyFormat();

	private MoneyFormat() {
		associations.put(4, "K");
		associations.put(5, "K");
		associations.put(6, "K");
		associations.put(7, "M");
		associations.put(8, "M");
		associations.put(9, "M");
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

	private void divideAndAppend(StringBuilder sb, BigInteger value,
			BigInteger divider,
			String suffix) {
		BigInteger[] n;
		String integer;
		String decimal = "";

		n = value.divideAndRemainder(divider);

		integer = String.valueOf(n[0].intValue());
		if (n[1].toString().length() > 1)
			decimal = String.valueOf(n[1].intValue()).substring(0, 2);

		sb.append(integer + "." + decimal);
		sb.append(suffix);
	}

	public static final MoneyFormat getSingleton() {
		return instance;
	}
}
