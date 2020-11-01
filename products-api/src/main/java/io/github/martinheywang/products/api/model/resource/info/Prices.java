package io.github.martinheywang.products.api.model.resource.info;

import java.math.BigInteger;

public final class Prices {

	// This class is mostly used as an enum, with static methods returning
	// values.
	private Prices() {

	}

	public static BigInteger rawPrice() {
		return new BigInteger("75");
	}

	public static BigInteger transformedPrice() {
		return new BigInteger("130");
	}
}
