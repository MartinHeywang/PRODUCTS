package com.martinheywang.products.model.resources;

import java.math.BigInteger;

import com.martinheywang.products.model.resources.info.Prices;

/**
 * This class register all the ores that can be found in the game.
 * Each of them is marked with some annotation (visible in the
 * JavaDoc), that defines their default behaviours.
 * 
 * @author Martin Heywang
 */
public enum Ore implements Resource {

    @Buyable
    @ToWire(clazz = Wire.class, field = "IRON_WIRE")
    @ToPlate(clazz = Plate.class, field = "PLATE_OF_IRON")
    @ToIngot(clazz = Ingot.class, field = "IRON_INGOT")
    IRON("Fer", Prices.rawPrice(), "/Minerai_De_Fer.png"),

    @Buyable
    @ToWire(clazz = Wire.class, field = "GOLD_WIRE")
    @ToPlate(clazz = Plate.class, field = "PLATE_OF_GOLD")
    @ToIngot(clazz = Ingot.class, field = "GOLD_INGOT")
    GOLD("Or", Prices.rawPrice(), "/Minerai_De_Or.png"),

    @Buyable
    @ToWire(clazz = Wire.class, field = "COPPER_WIRE")
    @ToPlate(clazz = Plate.class, field = "PLATE_OF_COPPER")
    @ToIngot(clazz = Ingot.class, field = "COPPER_INGOT")
    COPPER("Cuivre", Prices.rawPrice(), "/Minerai_De_Cuivre.png"),

    @Buyable
    @ToWire(clazz = Wire.class, field = "SILVER_WIRE")
    @ToPlate(clazz = Plate.class, field = "PLATE_OF_SILVER")
    @ToIngot(clazz = Ingot.class, field = "SILVER_INGOT")
    SILVER("Argent", Prices.rawPrice(), "/Minerai_De_Argent.png"),

    @Buyable
    @ToWire(clazz = Wire.class, field = "PLATE_OF_ALUMINIUM")
    @ToIngot(clazz = Ingot.class, field = "ALUMINIUM_INGOT")
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
