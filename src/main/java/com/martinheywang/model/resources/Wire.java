package com.martinheywang.model.resources;

import java.math.BigInteger;

import com.martinheywang.model.resources.info.Prices;

/**
 * This enum defines all the wire that are available by default in the
 * game.
 * 
 * @author Martin Heywang
 */
public enum Wire implements Resource {

	IRON_WIRE("Fil de fer", Prices.transformedPrice(), "/Fil_De_Fer.png"),

	GOLD_WIRE("Fil d'or", Prices.transformedPrice(), "/Fil_De_Or.png"),

	COPPER_WIRE("Fil de cuivre", Prices.transformedPrice(),
			"/Fil_De_Cuivre.png"),

	SILVER_WIRE("Fil d'argent", Prices.transformedPrice(),
			"/Fil_De_Argent.png"),

	ALUMINIUM_WIRE("Fil d'aluminium", Prices.transformedPrice(),
			"/Fil_De_Aluminium.png");

	private String name;
	private BigInteger price;
	private String url;

	Wire(String name, BigInteger price, String url) {
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
