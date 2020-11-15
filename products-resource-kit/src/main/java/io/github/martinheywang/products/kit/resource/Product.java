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
package io.github.martinheywang.products.kit.resource;

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
	CIRCUIT("Circuit", "330", "Circuit.png"),

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
	HOTPLATE("Plaque chauffante", "450", "Hot-Plate.png"),

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
	COOLER_PLATE("Plaque réfirgérante", "450", "Cooler-Plate.png"),

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
	PILE("Pile", "600", "Battery.png"),

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
	CLOCK("Horloge", "850", "Clock.png"),

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
	ANTENNA("Antenne", "950", "Antenna.png"),

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
	AMPOULE("Ampoule", "1000", "Bulb.png"),

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
	TOASTER("Grille-pain", "1200", "Toaster.png"),

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
	GRILL("Grill", "1500", "Grill.png"),

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
	AIR_CONDITIONNER("Climatisation", "1800", "Air-Conditioner.png"),

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
	RADIO("Radio", "2200", "Radio.png"),

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
	PROCESSOR("Processeur", "3000", "Processor.png"),

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
	ENGINE("Moteur", "3500", "Engine.png"),

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
	DRONE("Drone", "4500", "Drone.png"),

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
	SMARTPHONE("Smartphone", "6000", "Smartphone.png"),

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
	TV("TV", "6200", "TV.png"),

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
	TABLET("Tablette", "7000", "Tablet.png"),

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
	FRIDGE("Réfrigérateur", "11000", "Fridge.png"),

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
	OVEN("Four", "11000", "Furnace.png"),

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
	COMPUTER("Ordinateur", "15000", "PC.png");

	private String name;
	private BigInteger price;
	private String url;

	Product(String nom, String value, String file) {
		this.name = nom;
          this.price = new BigInteger(value);
          System.out.println(this);
		this.url = Product.class.getResource("/images/resources/Product/"+file).toExternalForm();
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
