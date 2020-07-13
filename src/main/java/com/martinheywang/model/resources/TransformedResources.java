package com.martinheywang.model.resources;

import java.math.BigInteger;
import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.resources.info.Prices;

public enum TransformedResources implements Resource {

	PLATE_OF_IRON("Plaque de fer", Prices.transformedPrice(),
			"/Plaque_De_Fer.png"),
	IRON_WIRE("Fil de fer", Prices.transformedPrice(), "/Fil_De_Fer.png"),
	IRON_INGOT("Lingot de fer", Prices.transformedPrice(),
			"/Lingot_De_Fer.png"),

	PLATE_OF_GOLD("Plaque d'or", Prices.transformedPrice(),
			"/Plaque_De_Or.png"),
	GOLD_WIRE("Fil d'or", Prices.transformedPrice(), "/Fil_De_Or.png"),
	GOLD_INGOT("Lingot d'or", Prices.transformedPrice(), "/Lingot_De_Or.png"),

	PLATE_OF_COPPER("Plaque de cuivre", Prices.transformedPrice(),
			"/Plaque_De_Cuivre.png"),
	COPPER_WIRE("Fil de cuivre", Prices.transformedPrice(),
			"/Fil_De_Cuivre.png"),
	COPPER_INGOT("Lingot de cuivre", Prices.transformedPrice(),
			"/Lingot_De_Cuivre.png"),

	PLATE_OF_SILVER("Plaque d'argent", Prices.transformedPrice(),
			"/Plaque_De_Argent.png"),
	SILVER_WIRE("Fil d'argent", Prices.transformedPrice(),
			"/Fil_De_Argent.png"),
	SILVER_INGOT("Lingot d'argent", Prices.transformedPrice(),
			"/Lingot_De_Argent.png"),

	PLATE_OF_ALUMINIUM("Plaque d'aluminium", Prices.transformedPrice(),
			"/Plaque_De_Aluminium.png"),
	ALUMINIUM_WIRE("Fil d'aluminium", Prices.transformedPrice(),
			"/Fil_De_Aluminium.png"),
	ALUMINIUM_INGOT("Lingot d'aluminium", Prices.transformedPrice(),
			"/Lingot_De_Aluminium.png");

	private String name;
	private BigInteger price;
	private String url;

	TransformedResources(String name, BigInteger price, String url) {
		this.name = name;
		this.price = price;
		this.url = "/images/resources" + url;

		Resource.addReferences(this);
		// Todo : add accepted resource for both press and wire drawer
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
