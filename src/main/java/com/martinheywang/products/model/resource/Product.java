package com.martinheywang.products.model.resource;

import java.math.BigInteger;

import com.martinheywang.products.model.resource.Craftable.RemotePack;

/**
 * This enum defines all the products available in the game.
 * 
 * @author Heywang
 */
public enum Product implements Resource {

	/*
	 * For forkers and stargazers on Github: this enum will be extended a lot. This
	 * is just a minimal set to test some of the features that my game may need one
	 * day. I will greatly appreciate some ideas about what I can add here. You can
	 * send your ideas either to 'martin.heywang1@gmail.com' or by opening an issue.
	 */

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	CIRCUIT("Circuit", "250", "/Circuit.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE", quantity = "2") })
	HOTPLATE("Plaque chauffante", "250", "/Plaque_Chauffante.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "DIAMOND"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE", quantity = "2") })
	COOLER_PLATE("Plaque réfrigérante", "250", "/Plaque_Refroidissante.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	AMPOULE("Ampoule", "325", "/Ampoule.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "3"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_SILVER", quantity = "2") })
	CLOCK("Horloge", "450", "/Horloge.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "4"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "1") })
	PROCESSOR("Processeur", "500", "/Processeur.png"),
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "DIAMOND"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	ANTENNA("Antenne", "600", "/Antenne.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_SILVER") })
	TOASTER("Toaster", "650", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "4") })
	GRILL("Grill", "700", "/Grill.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "5"),
			@RemotePack(clazz = Product.class, field = "COOLER_PLATE", quantity = "1") })
	AIR_CONDITIONER("Climatisation", "900", "/Climatisation.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "5") })
	BATTERY("Pile", "1050", "/Pile.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "BATTERY"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_IRON") })
	RADIO("Radio", "1200", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "COOLER_PLATE", quantity = "2"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_SILVER", quantity = "8") })
	FRIDGE("Réfrigérateur", "1500", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Ore.class, field = "GOLD", quantity = "2") })
	SMARTPHONE("Smartphone", "1600", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Ore.class, field = "GOLD", quantity = "3") })
	TABLET("Tablette", "1900", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD", quantity = "5"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2") })
	SOLAR_PANEL("Panneau solaire", "2000", "/Panneau_Solaire.png"),

	@Craftable(recipe = { @RemotePack(clazz = Plate.class, field = "PLATE_OF_IRON"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	POWER_SUPPLY("Alimentation", "1500", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Plate.class, field = "PLATE_OF_ALUMINIUM", quantity = "2"),
			@RemotePack(clazz = Product.class, field = "ANTENNA") })
	MICROWAVE("Micro-ondes", "2500", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "POWER_SUPPLY") })
	COMPUTER("Ordinateur", "3000", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_ALUMINIUM") })
	DRONE("Drone", "4000", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Plate.class, field = "PLATE_OF_SILVER", quantity = "5"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "3") })
	ENGINE("Moteur", "5000", "/Moteur.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "POWER_SUPPLY"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_IRON") })
	TV("TV", "7000", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "POWER_SUPPLY"),
			@RemotePack(clazz = Ore.class, field = "GOLD"), @RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	GENERATOR("Générateur", "9000", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE", quantity = "2"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_ALUMINIUM", quantity = "5") })
	OVEN("Four", "10000", "/None.png"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD", quantity = "10"),
			@RemotePack(clazz = Ingot.class, field = "GOLD_INGOT", quantity = "7") })
	PRODUCTS("Icon", "30000", "/None.png");

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
