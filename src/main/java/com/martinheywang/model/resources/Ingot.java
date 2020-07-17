package com.martinheywang.model.resources;

import java.math.BigInteger;

import com.martinheywang.model.resources.info.Prices;

public enum Ingot implements Resource {

	IRON_INGOT("Lingot de fer", Prices.transformedPrice(),
			"/Lingot_De_Fer.png"),

	GOLD_INGOT("Lingot d'or", Prices.transformedPrice(), "/Lingot_De_Or.png"),

	COPPER_INGOT("Lingot de cuivre", Prices.transformedPrice(),
			"/Lingot_De_Cuivre.png"),

	SILVER_INGOT("Lingot d'argent", Prices.transformedPrice(),
			"/Lingot_De_Argent.png"),

	ALUMINIUM_INGOT("Lingot d'aluminium", Prices.transformedPrice(),
			"/Lingot_De_Aluminium.png");

	private String name;
	private BigInteger price;
	private String url;

	Ingot(String name, BigInteger price, String url) {
		this.name = name;
		this.price = price;
		this.url = "/images/resources" + url;
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
