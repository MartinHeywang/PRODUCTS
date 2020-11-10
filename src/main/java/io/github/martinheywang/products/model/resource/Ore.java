/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.resource.Buyable;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.ToIngot;
import io.github.martinheywang.products.api.model.resource.ToPlate;
import io.github.martinheywang.products.api.model.resource.ToWire;
import io.github.martinheywang.products.api.model.resource.info.Prices;

/**
 * This class register all the ores that can be found in the game.
 * Each of them is marked with some annotation (visible in the
 * JavaDoc), that defines their default behaviours.
 * 
 * @author Martin Heywang
 */
@Extension(ordinal = 1)
public enum Ore implements Resource {

	/**
     * <p>
     * Iron
     * </p>
     * <p>
     * Price : default price for raw resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
	@Buyable
	@ToWire(clazz = Wire.class, field = "IRON_WIRE")
	@ToPlate(clazz = Plate.class, field = "PLATE_OF_IRON")
	@ToIngot(clazz = Ingot.class, field = "IRON_INGOT")
	IRON("Fer", Prices.rawPrice(), "/Minerai_De_Fer.png"),

	/**
     * <p>
     * Gold
     * </p>
     * <p>
     * Price : default price for raw resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
	@Buyable
	@ToWire(clazz = Wire.class, field = "GOLD_WIRE")
	@ToPlate(clazz = Plate.class, field = "PLATE_OF_GOLD")
	@ToIngot(clazz = Ingot.class, field = "GOLD_INGOT")
	GOLD("Or", Prices.rawPrice(), "/Minerai_De_Or.png"),

	/**
     * <p>
     * Copper
     * </p>
     * <p>
     * Price : default price for raw resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
	@Buyable
	@ToWire(clazz = Wire.class, field = "COPPER_WIRE")
	@ToPlate(clazz = Plate.class, field = "PLATE_OF_COPPER")
	@ToIngot(clazz = Ingot.class, field = "COPPER_INGOT")
	COPPER("Cuivre", Prices.rawPrice(), "/Minerai_De_Cuivre.png"),

	/**
     * <p>
     * Silver
     * </p>
     * <p>
     * Price : default price for raw resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
	@Buyable
	@ToWire(clazz = Wire.class, field = "SILVER_WIRE")
	@ToPlate(clazz = Plate.class, field = "PLATE_OF_SILVER")
	@ToIngot(clazz = Ingot.class, field = "SILVER_INGOT")
	SILVER("Argent", Prices.rawPrice(), "/Minerai_De_Argent.png"),

	/**
     * <p>
     * Aluminium
     * </p>
     * <p>
     * Price : default price for raw resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
	@Buyable
	@ToWire(clazz = Wire.class, field = "ALUMINIUM_WIRE")
	@ToPlate(clazz = Plate.class, field = "PLATE_OF_ALUMINIUM")
	@ToIngot(clazz = Ingot.class, field = "ALUMINIUM_INGOT")
	ALUMINIUM("Aluminium", Prices.rawPrice(), "/Minerai_De_Aluminium.png"),

	/**
     * <p>
     * Diamond
     * </p>
     * <p>
     * Price : default price for raw resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
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
		return this.name;
	}

	@Override
	public BigInteger getPrice() {
		return this.price;
	}

	@Override
	public String getURL() {
		return this.url;
	}
}
