package com.martinheywang.model.resources;

import java.math.BigInteger;
import java.util.List;

import com.martinheywang.model.Pack;

public enum DefaultResources implements Resource {
	NONE("None", 0, "/None.png");

	private String name;
	private BigInteger price;
	private String url;

	DefaultResources(String name, long value, String url) {
		this.name = name;
		this.price = BigInteger.valueOf(value);
		this.url = url;

		Resource.addReferences(this);
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

	@Override
	public List<Pack> getRecipe() {
		return null;
	}
}
