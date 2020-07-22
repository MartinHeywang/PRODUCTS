package com.martinheywang.model.resources;

import java.math.BigInteger;

import com.martinheywang.model.resources.Craftable.RemotePack;

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

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	CIRCUIT("Circuit", "250", "/Circuit.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE", quantity = "2") })
	HOTPLATE("Plaque chauffante", "250", "/Plaque_Chauffante.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "DIAMOND"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE", quantity = "2") })
	COOLER_PLATE("Plaque refroidissante", "250", "/Plaque_Refroidissante.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	AMPOULE("Ampoule", "325", "/Ampoule.png"),

	@Craftable(recipe = {
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3"),
			@RemotePack(clazz = Plate.class, field = "SILVER_PLATE", quantity = "2") })
	CLOCK("Horloge", "450", "/Horloge.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "DIAMOND"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	ANTENNA("Antenne", "600", "/Antenne.png"),

	@Craftable(recipe = {
			@RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Wire.class, field = "IRON", quantity = "4") })
	GRILL("Grill", "600", "/Grill.png"),

	@Craftable(recipe = {
			@RemotePack(clazz = Plate.class, field = "SILVER_PLATE", quantity = "2"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2") })
	ENGINE("Moteur", "375", "/Moteur.png"),

	@Craftable(recipe = {
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "4"),
			@RemotePack(clazz = Product.class, field = "COOLER_PLATE", quantity = "1") })
	AIR_CONDITIONER("Climatisation", "800", "/Climatisation.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "5") })
	BATTERY("Pile", "850", "/Pile.png"),

	@Craftable(recipe = {
			@RemotePack(clazz = Ore.class, field = "GOLD", quantity = "4"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2") })
	SOLAR_PANEL("Panneau solaire", "850", "/Panneau_Solaire.png"),

	@Craftable(recipe = {
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "7"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2") })
	PROCESSOR("Processeur", "850", "/Processeur.png");

	private String name;
	private BigInteger price;
	private String url;

	Product(String nom, String value, String urlImg) {
		this.name = nom;
		this.price = new BigInteger(value);
		this.url = "/images/resources" + urlImg;
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
