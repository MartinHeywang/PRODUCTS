package com.martinheywang.model.resources;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.behaviours.Constructor;

public enum ConstructorResources implements Resource {
	CIRCUIT("Circuit", 250, "/Circuit.png",
			new Pack(BuyerAndTransformResources.GOLD, 1),
			new Pack(BuyerAndTransformResources.COPPER_WIRE, 1)),

	HOTPLATE("Plaque chauffante", 250, "/Plaque_Chauffante.png",
			new Pack(BuyerAndTransformResources.GOLD, 1),
			new Pack(BuyerAndTransformResources.COPPER_WIRE, 1)),

	COOLER_PLATE("Plaque refroidissante", 250,
			"/Plaque_Refroidissante.png",
			new Pack(BuyerResources.DIAMOND, 1),
			new Pack(BuyerAndTransformResources.COPPER_WIRE, 1)),

	AMPOULE("Ampoule", 325, "/Ampoule.png",
			new Pack(BuyerAndTransformResources.IRON, 2),
			new Pack(BuyerAndTransformResources.COPPER_WIRE, 1)),

	CLOCK("Horloge", 450, "/Horloge.png",
			new Pack(BuyerAndTransformResources.IRON, 3),
			new Pack(BuyerAndTransformResources.PLATE_OF_GOLD, 2)),

	ANTENNA("Antenne", 600, "/Antenne.png",
			new Pack(BuyerAndTransformResources.IRON, 3),
			new Pack(BuyerResources.DIAMOND, 1)),

	GRILL("Grill", 600, "/Grill.png",
			new Pack(ConstructorResources.HOTPLATE, 1),
			new Pack(BuyerAndTransformResources.IRON, 4)),

	ENGINE("Moteur", 375, "/Moteur.png",
			new Pack(BuyerAndTransformResources.PLATE_OF_SILVER, 2),
			new Pack(ConstructorResources.CIRCUIT, 2)),

	AIR_CONDITIONER("Climatisation", 800, "/Climatisation.png",
			new Pack(ConstructorResources.COOLER_PLATE, 1),
			new Pack(BuyerAndTransformResources.ALUMINIUM, 4)),

	BATTERY("Pile", 850, "/Pile.png",
			new Pack(ConstructorResources.CIRCUIT, 1),
			new Pack(BuyerAndTransformResources.ALUMINIUM, 5)),

	SOLAR_PANEL("Panneau solaire", 850, "/Panneau_Solaire.png",
			new Pack(ConstructorResources.CIRCUIT, 2),
			new Pack(BuyerAndTransformResources.GOLD, 4)),

	PROCESSOR("Processeur", 850, "/Processeur.png",
			new Pack(ConstructorResources.CIRCUIT, 2),
			new Pack(BuyerAndTransformResources.ALUMINIUM, 7));

	private String name;
	private BigInteger price;
	private String url;
	private List<Pack> recipe;

	ConstructorResources(String nom, long value, String urlImg,
			Pack... ingredients) {
		this.name = nom;
		this.price = BigInteger.valueOf(value);
		this.url = urlImg;
		this.recipe = Arrays.asList(ingredients);

		Resource.addReferences(this);
		Constructor.addAcceptedResource(this);
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

	@Override
	public List<Pack> getRecipe() {
		return recipe;
	}
}
