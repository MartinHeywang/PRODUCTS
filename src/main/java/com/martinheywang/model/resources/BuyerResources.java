package com.martinheywang.model.resources;

import java.math.BigInteger;
import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.behaviours.Buyer;
import com.martinheywang.model.resources.info.Prices;

public enum BuyerResources implements Resource {

	DIAMOND("Diamant", Prices.rawPrice(), "/Minerai_De_Diamant.png");

	private String name;
	private BigInteger price;
	private String url;

	BuyerResources(String name, BigInteger price, String url) {
		this.name = name;
		this.price = price;
		this.url = "/images/resources" + url;

		Resource.addReferences(this);
		Buyer.addAcceptedResource(this);
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
