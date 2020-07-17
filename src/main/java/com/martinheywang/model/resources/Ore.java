package com.martinheywang.model.resources;

import java.math.BigInteger;

import com.martinheywang.model.behaviours.Buyer;
import com.martinheywang.model.behaviours.Furnace;
import com.martinheywang.model.resources.info.Prices;

public enum Ore implements Resource {

	@Buyable
	@ToWire(to = "com.martinheywang.model.resources.Wire.IRON_WIRE")
	@ToPlate(to = "com.martinheywang.model.resources.Plate.IRON_PLATE")
	@ToIngot(to = "com.martinheywang.model.resources.Ingot.IRON_INGOT")
	IRON("Fer", Prices.rawPrice(), "/Minerai_De_Fer.png"),

	@Buyable
	@ToWire(to = "com.martinheywang.model.resources.Wire.GOLD_WIRE")
	@ToPlate(to = "com.martinheywang.model.resources.Plate.GOLD_PLATE")
	@ToIngot(to = "com.martinheywang.model.resources.Ingot.IRON_INGOT")
	GOLD("Or", Prices.rawPrice(), "/Minerai_De_Or.png"),

	@Buyable
	@ToWire(to = "com.martinheywang.model.resources.Wire.COPPER_WIRE")
	@ToPlate(to = "com.martinheywang.model.resources.Plate.COPPER_PLATE")
	@ToIngot(to = "com.martinheywang.model.resources.Ingot.IRON_INGOT")
	COPPER("Cuivre", Prices.rawPrice(), "/Minerai_De_Cuivre.png"),

	@Buyable
	@ToWire(to = "com.martinheywang.model.resources.Wire.SILVER_WIRE")
	@ToPlate(to = "com.martinheywang.model.resources.Plate.SILVER_PLATE")
	@ToIngot(to = "com.martinheywang.model.resources.Ingot.IRON_INGOT")
	SILVER("Argent", Prices.rawPrice(), "/Minerai_De_Argent.png"),

	@Buyable
	@ToWire(to = "com.martinheywang.model.resources.Wire.ALUMINIUM_WIRE")
	@ToPlate(to = "com.martinheywang.model.resources.Plate.ALUMINIUM_PLATE")
	@ToIngot(to = "com.martinheywang.model.resources.Ingot.IRON_INGOT")
	ALUMINIUM("Aluminium", Prices.rawPrice(), "/Minerai_De_Aluminium.png"),

	@Buyable
	DIAMOND("Diamant", Prices.rawPrice(), "/Minerai_De_Diamant.png");

	private String name;
	private BigInteger price;
	private String url;

	Ore(String name, BigInteger price, String url) {
		this.name = name;
		this.price = price;
		this.url = "/images/resources" + url;

		Resource.addReferences(this);
		Buyer.addAcceptedResource(this);
		Furnace.addAcceptedResource(this);
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
}
