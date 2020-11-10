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

 

import io.github.martinheywang.products.api.model.resource.Craftable;
import io.github.martinheywang.products.api.model.resource.Craftable.RemotePack;
import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * This enum defines all the products available in the game.
 * 
 * @author Martin Heywang
 */
 
public enum Product implements Resource {

	/**
     * <p>
     * Circuit 
     * </p>
     * <p>
     * Price : 330.
	 * Base of the craftable resource, used for a lot of more advanced products.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD"),
			@RemotePack(clazz = Ore.class, field = "COPPER", quantity = "2") })
	CIRCUIT("Circuit", "330", "Circuit"),

	/**
     * <p>
     * Hot Plate 
     * </p>
     * <p>
     * Price : 450.
	 * Base of the craftable resource, used for a lot of more advanced products.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD", quantity = "2"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	HOTPLATE("Plaque chauffante", "450", "Plaque_Chauffante"),

	/**
     * <p>
     * Cooler Plate 
     * </p>
     * <p>
     * Price : 450.
	 * Base of the craftable resource, used for a lot of more advanced products.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "DIAMOND", quantity = "2"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	COOLER_PLATE("Plaque réfirgérante", "450", "Plaque_Refroidissante"),

	/**
     * <p>
     * Battery 
     * </p>
     * <p>
     * Price : 600.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "COPPER", quantity = "3") })
	PILE("Pile", "600", "Pile"),

	/**
     * <p>
     * Clock 
     * </p>
     * <p>
     * Price : 850.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "2"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_ALUMINIUM", quantity = "2") })
	CLOCK("Horloge", "850", "Horloge"),

	/**
     * <p>
     * Antenna 
     * </p>
     * <p>
     * Price : 950.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "DIAMOND", quantity = "3") })
	ANTENNA("Antenne", "950", "Antenne"),

	/**
     * <p>
     * Ampoule 
     * </p>
     * <p>
     * Price : 1000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "2"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE", quantity = "3") })
	AMPOULE("Ampoule", "1000", "Ampoule"),

	/**
     * <p>
     * Toaster 
     * </p>
     * <p>
     * Price : 1200.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "2") })
	TOASTER("Grille-pain", "1200", "Toaster"),

	/**
     * <p>
     * Grill 
     * </p>
     * <p>
     * Price : 1500.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	GRILL("Grill", "1500", "Grill"),

	/**
     * <p>
     * Air conditionner 
     * </p>
     * <p>
     * Price : 1800.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "COOLER_PLATE"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	AIR_CONDITIONNER("Climatisation", "1800", "Climatisation"),

	/**
     * <p>
     * Radio 
     * </p>
     * <p>
     * Price : 2200.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "4") })
	RADIO("Radio", "2200", "Radio"),

	/**
     * <p>
     * Processor 
     * </p>
     * <p>
     * Price : 3000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "3"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "6") })
	PROCESSOR("Processeur", "3000", "Processeur"),

	/**
     * <p>
     * Engine 
     * </p>
     * <p>
     * Price : 3500.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "4") })
	ENGINE("Moteur", "3500", "Moteur"),

	/**
     * <p>
     * Drone 
     * </p>
     * <p>
     * Price : 4500.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "5") })
	DRONE("Drone", "4500", "Drone"),

	/**
     * <p>
     * Smartphone 
     * </p>
     * <p>
     * Price : 6000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "2") })
	SMARTPHONE("Smartphone", "6000", "Smartphone"),

	/**
     * <p>
     * TV 
     * </p>
     * <p>
     * Price : 6200.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "ANTENNA"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	TV("TV", "6200", "TV"),

	/**
     * <p>
     * Tablet 
     * </p>
     * <p>
     * Price : 7000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "3") })
	TABLET("Tablette", "7000", "Tablet"),

	/**
     * <p>
     * Fridge 
     * </p>
     * <p>
     * Price : 11000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "COOLER_PLATE", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "8") })
	FRIDGE("Réfrigérateur", "11000", "Fridge"),

	/**
     * <p>
     * Oven 
     * </p>
     * <p>
     * Price : 11000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "8") })
	OVEN("Four", "11000", "Oven"),

	/**
     * <p>
     * Computer 
     * </p>
     * <p>
     * Price : 15000.
     * </p>
     */
	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "5") })
	COMPUTER("Ordinateur", "15000", "PC");

	private String name;
	private BigInteger price;
	private String url;

	Product(String nom, String value, String urlImg) {
		this.name = nom;
		this.price = new BigInteger(value);
		this.url = "/images/resources/" + urlImg +".png";
	}

	@Override
	public BigInteger getPrice() {
		return this.price;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getURL() {
		return this.url;
	}
}