package com.martinheywang.model.resources;

import java.math.BigInteger;

/**
 * This enum defines all the products available in the game.
 * 
 * @author Heywang
 */
public enum Product implements Resource {

	/*
	 * For forkers and stargazers on Github: this enum will be extended a
	 * lot. This is just a minimal set to test some of the features that
	 * my game may need one day. I will greatly appreciate some ideas
	 * about what I can add here. You can send your ideas either to
	 * 'martin.heywang1@gmail.com' or by opening an issue.
	 */

	@Craftable(recipe = { "1|com.martinheywang.model.resources.Ore.GOLD",
			"1|com.martinheywang.model.resources.Wire.COPPER_WIRE" })
	CIRCUIT("Circuit", "250", "/Circuit.png"),

	@Craftable(recipe = { "1|com.martinheywang.model.resources.Ore.GOLD",
			"1|com.martinheywang.model.resources.Wire.COPPER_WIRE" })
	HOTPLATE("Plaque chauffante", "250", "/Plaque_Chauffante.png"),

	@Craftable(recipe = { "1|com.martinheywang.model.resources.Ore.DIAMOND",
			"1|com.martinheywang.model.resources.Wire.COPPER_WIRE" })
	COOLER_PLATE("Plaque refroidissante", "250", "/Plaque_Refroidissante.png"),

	@Craftable(recipe = { "1|com.martinheywang.model.resources.Ore.IRON",
			"1|com.martinheywang.model.resources.Wire.COPPER_WIRE" })
	AMPOULE("Ampoule", "325", "/Ampoule.png"),

	@Craftable(recipe = { "3|com.martinheywang.model.resources.Ore.IRON",
			"2|com.martinheywang.model.resources.Plate.GOLD_PLATE" })
	CLOCK("Horloge", "450", "/Horloge.png"),

	@Craftable(recipe = { "3|com.martinheywang.model.resources.Ore.IRON",
			"1|com.martinheywang.model.resources.Ore.DIAMOND" })
	ANTENNA("Antenne", "600", "/Antenne.png"),

	@Craftable(recipe = {
			"1|com.martinheywang.model.resources.Product.HOTPLATE",
			"4|com.martinheywang.model.resources.Ore.IRON" })
	GRILL("Grill", "600", "/Grill.png"),

	@Craftable(recipe = {
			"2|com.martinheywang.model.resources.Plate.SILVER_PLATE",
			"2|com.martinheywang.model.resources.Product.CIRCUIT" })
	ENGINE("Moteur", "375", "/Moteur.png"),

	@Craftable(recipe = {
			"1|com.martinheywang.model.resources.Product.COOLER_PLATE",
			"4|com.martinheywang.model.resources.Ore.ALUMINIUM" })
	AIR_CONDITIONER("Climatisation", "800", "/Climatisation.png"),

	@Craftable(recipe = { "1|com.martinheywang.model.resources.Product.CIRCUIT",
			"5|com.martinheywang.model.resources.Ore.ALUMINIUM" })
	BATTERY("Pile", "850", "/Pile.png"),

	@Craftable(recipe = { "2|com.martinheywang.model.resources.Product.CIRCUIT",
			"4|com.martinheywang.model.resources.Ore.GOLD" })
	SOLAR_PANEL("Panneau solaire", "850", "/Panneau_Solaire.png"),

	@Craftable(recipe = { "2|com.martinheywang.model.resources.Product.CIRCUIT",
			"7|com.martinheywang.model.resources.Ore.ALUMINIUM" })
	PROCESSOR("Processeur", "850", "/Processeur.png");

	private String name;
	private BigInteger price;
	private String url;

	Product(String nom, String value, String urlImg) {
		this.name = nom;
		this.price = new BigInteger(value);
		this.url = urlImg;
	}

	@Override
	public BigInteger getPrice() {
		return price;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getURL() {
		return url;
	}
}
