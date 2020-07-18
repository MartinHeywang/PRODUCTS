package com.martinheywang.model.resources;

import java.math.BigInteger;

/**
 * This enum defines a NONE resource. It actually has no effect to any
 * device.
 * 
 * @author Heywang
 */
public enum DefaultResource implements Resource {

	NONE("None", 0, "/None.png");

	private String name;
	private BigInteger price;
	private String url;

	DefaultResource(String name, long value, String url) {
		this.name = name;
		this.price = BigInteger.valueOf(value);
		this.url = url;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BigInteger getPrice() {
		return price;
	}

	@Override
	public String getURL() {
		return url;
	}
}
