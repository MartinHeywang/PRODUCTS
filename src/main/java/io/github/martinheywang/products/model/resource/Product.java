package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.resource.Craftable;
import io.github.martinheywang.products.api.model.resource.Craftable.RemotePack;
import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * This enum defines all the products available in the game.
 * 
 * @author Heywang
 */
@Extension(ordinal = 3)
public enum Product implements Resource {

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD"),
			@RemotePack(clazz = Ore.class, field = "COPPER", quantity = "2") })
	CIRCUIT("Circuit", "330", "Circuit"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "GOLD", quantity = "2"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	HOTPLATE("Plaque chauffante", "450", "Plaque_Chauffante"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "DIAMOND", quantity = "2"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE") })
	COOLER_PLATE("Plaque réfirgérante", "450", "Plaque_Refroidissante"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "COPPER", quantity = "3") })
	PILE("Pile", "600", "Pile"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "2"),
			@RemotePack(clazz = Plate.class, field = "PLATE_OF_ALUMINIUM", quantity = "2") })
	CLOCK("Horloge", "850", "Horloge"),

	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "DIAMOND", quantity = "3") })
	ANTENNA("Antenne", "950", "Antenne"),
	@Craftable(recipe = { @RemotePack(clazz = Ore.class, field = "IRON", quantity = "2"),
			@RemotePack(clazz = Wire.class, field = "COPPER_WIRE", quantity = "3") })
	AMPOULE("Ampoule", "1000", "Ampoule"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "2") })
	TOASTER("Grille-pain", "1200", "Toaster"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	GRILL("Grill", "1500", "Grill"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "COOLER_PLATE"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	AIR_CONDITIONNER("Climatisation", "1800", "Climatisation"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "4") })
	RADIO("Radio", "2200", "Radio"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "3"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "6") })
	PROCESSOR("Processeur", "3000", "Processeur"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "4") })
	ENGINE("Moteur", "3500", "Moteur"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "5") })
	DRONE("Drone", "4500", "Drone"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "2") })
	SMARTPHONE("Smartphone", "6000", "Smartphone"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "ANTENNA"),
			@RemotePack(clazz = Ore.class, field = "IRON", quantity = "3") })
	TV("TV", "6200", "TV"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "PROCESSOR"),
			@RemotePack(clazz = Product.class, field = "CIRCUIT", quantity = "2"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "3") })
	TABLET("Tablette", "7000", "Tablet"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "COOLER_PLATE", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "8") })
	FRIDGE("Réfrigérateur", "11000", "Fridge"),

	@Craftable(recipe = { @RemotePack(clazz = Product.class, field = "HOTPLATE", quantity = "4"),
			@RemotePack(clazz = Ore.class, field = "ALUMINIUM", quantity = "8") })
	OVEN("Four", "11000", "Oven"),

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
