package com.martinheywang.model.resources;

import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Pack;

public enum BaseResources implements Resource {
	NONE("None", 0, "/None.png"),

	IRON("Fer", 30, "/Minerai_De_Fer.png"),
	PLATE_OF_IRON("Plaque de fer", 60, "/Plaque_De_Fer.png"),
	IRON_WIRE("Fil de fer", 60, "/Fil_De_Fer.png"),
	IRON_INGOT("Lingot de fer", 60, "/Lingot_De_Fer.png"),

	GOLD("Or", 30, "/Minerai_De_Or.png"),
	PLATE_OF_GOLD("Plaque d'or", 60, "/Plaque_De_Or.png"),
	GOLD_WIRE("Fil d'or", 60, "/Fil_De_Or.png"),
	GOLD_INGOT("Lingot d'or", 60, "/Lingot_De_Or.png"),

	COPPER("Cuivre", 30, "/Minerai_De_Cuivre.png"),
	PLATE_OF_COPPER("Plaque de cuivre", 60,
			"/Plaque_De_Cuivre.png"),
	COPPER_WIRE("Fil de cuivre", 60, "/Fil_De_Cuivre.png"),
	COPPER_INGOT("Lingot de cuivre", 60,
			"/Lingot_De_Cuivre.png"),

	SILVER("Argent", 30, "/Minerai_De_Argent.png"),
	PLATE_OF_SILVER("Plaque d'argent", 60,
			"/Plaque_De_Argent.png"),
	SILVER_WIRE("Fil d'argent", 60, "/Fil_De_Argent.png"),
	SILVER_INGOT("Lingot d'argent", 60,
			"/Lingot_De_Argent.png"),

	DIAMOND("Diamant", 30, "/Minerai_De_Diamant.png"),
	DIAMOND_PLATE("Plaque de diamant", 60, "/None.png"),
	DIAMOND_WIRE("Fil de diamant", 60, "/None.png"),
	DIAMOND_INGOT("Lingot de diamant", 60, "/None.png"),

	ALUMINIUM("Aluminium", 30, "/Minerai_De_Aluminium.png"),
	PLATE_OF_ALUMINIUM("Plaque d'aluminium", 60,
			"/Plaque_De_Aluminium.png"),
	ALUMINIUM_WIRE("Fil d'aluminium", 60,
			"/Fil_De_Aluminium.png"),
	ALUMINIUM_INGOT("Lingot d'aluminium", 60,
			"/Lingot_De_Aluminium.png"),

	CIRCUIT("Circuit", 250, "/Circuit.png",
			new Pack(BaseResources.GOLD, 1),
			new Pack(BaseResources.COPPER_WIRE, 1)),

	HOTPLATE("Plaque chauffante", 250, "/Plaque_Chauffante.png",
			new Pack(BaseResources.GOLD, 1),
			new Pack(BaseResources.COPPER_WIRE, 1)),

	COOLER_PLATE("Plaque refroidissante", 250,
			"/Plaque_Refroidissante.png",
			new Pack(BaseResources.DIAMOND, 1),
			new Pack(BaseResources.COPPER_WIRE, 1)),

	AMPOULE("Ampoule", 325, "/Ampoule.png",
			new Pack(BaseResources.IRON, 2),
			new Pack(BaseResources.COPPER_WIRE, 1)),

	CLOCK("Horloge", 450, "/Horloge.png",
			new Pack(BaseResources.IRON, 3),
			new Pack(BaseResources.PLATE_OF_GOLD, 2)),

	ANTENNA("Antenne", 600, "/Antenne.png",
			new Pack(BaseResources.IRON, 3),
			new Pack(BaseResources.DIAMOND, 1)),

	GRILL("Grill", 600, "/Grill.png",
			new Pack(BaseResources.HOTPLATE, 1),
			new Pack(BaseResources.IRON, 4)),

	ENGINE("Moteur", 375, "/Moteur.png",
			new Pack(BaseResources.PLATE_OF_SILVER, 2),
			new Pack(BaseResources.CIRCUIT, 2)),

	AIR_CONDITIONER("Climatisation", 800, "/Climatisation.png",
			new Pack(BaseResources.COOLER_PLATE, 1),
			new Pack(BaseResources.ALUMINIUM, 4)),

	BATTERY("Pile", 850, "/Pile.png",
			new Pack(BaseResources.CIRCUIT, 1),
			new Pack(BaseResources.ALUMINIUM, 5)),

	SOLAR_PANEL("Panneau solaire", 850, "/Panneau_Solaire.png",
			new Pack(BaseResources.CIRCUIT, 2),
			new Pack(BaseResources.GOLD, 4)),

	PROCESSOR("Processeur", 850, "/Processeur.png",
			new Pack(BaseResources.CIRCUIT, 2),
			new Pack(BaseResources.ALUMINIUM, 7));

	private String name;
	private long price;
	private String url;
	private List<Pack> recipe;

	BaseResources(String nom, int value, String urlImg) {
		this.name = nom;
		this.price = value;
		this.url = urlImg;
		Resource.addReferenceResource(this);
	}

	BaseResources(String nom, int value, String urlImg, Pack... ingredients) {
		this.name = nom;
		this.price = value;
		this.url = urlImg;
		this.recipe = Arrays.asList(ingredients);
		Resource.addReferenceResource(this);
	}

	@Override
	public long getPrice() {
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

	@Override
	public List<Pack> getRecipe() {
		return recipe;
	}
}