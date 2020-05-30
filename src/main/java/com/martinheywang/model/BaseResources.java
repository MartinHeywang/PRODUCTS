package com.martinheywang.model;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public enum BaseResources implements Displayable<BaseResources>, Resource {
	NONE("None", 0, "/None.png"),

	FER("Fer", 30, "/Minerai_De_Fer.png"),
	PLAQUE_DE_FER("Plaque de fer", 60, "/Plaque_De_Fer.png"),
	FIL_DE_FER("Fil de fer", 60, "/Fil_De_Fer.png"),
	LINGOT_DE_FER("Lingot de fer", 60, "/Lingot_De_Fer.png"),

	OR("Or", 30, "/Minerai_De_Or.png"),
	PLAQUE_DE_OR("Plaque d'or", 60, "/Plaque_De_Or.png"),
	FIL_DE_OR("Fil d'or", 60, "/Fil_De_Or.png"),
	LINGOT_DE_OR("Lingot d'or", 60, "/Lingot_De_Or.png"),

	CUIVRE("Cuivre", 30, "/Minerai_De_Cuivre.png"),
	PLAQUE_DE_CUIVRE("Plaque de cuivre", 60,
			"/Plaque_De_Cuivre.png"),
	FIL_DE_CUIVRE("Fil de cuivre", 60, "/Fil_De_Cuivre.png"),
	LINGOT_DE_CUIVRE("Lingot de cuivre", 60,
			"/Lingot_De_Cuivre.png"),

	ARGENT("Argent", 30, "/Minerai_De_Argent.png"),
	PLAQUE_DE_ARGENT("Plaque d'argent", 60,
			"/Plaque_De_Argent.png"),
	FIL_DE_ARGENT("Fil d'argent", 60, "/Fil_De_Argent.png"),
	LINGOT_DE_ARGENT("Lingot d'argent", 60,
			"/Lingot_De_Argent.png"),

	DIAMANT("Diamant", 30, "/Minerai_De_Diamant.png"),
	PLAQUE_DE_DIAMANT("Plaque de diamant", 60, "/None.png"),
	FIL_DE_DIAMANT("Fil de diamant", 60, "/None.png"),
	LINGOT_DE_DIAMANT("Lingot de diamant", 60, "/None.png"),

	ALUMINIUM("Aluminium", 30, "/Minerai_De_Aluminium.png"),
	PLAQUE_DE_ALUMINIUM("Plaque d'aluminium", 60,
			"/Plaque_De_Aluminium.png"),
	FIL_DE_ALUMINIUM("Fil d'aluminium", 60,
			"/Fil_De_Aluminium.png"),
	LINGOT_DE_ALUMINIUM("Lingot d'aluminium", 60,
			"/Lingot_De_Aluminium.png"),

	CIRCUIT("Circuit", 250, "/Circuit.png",
			new Pack(BaseResources.OR, 1),
			new Pack(BaseResources.FIL_DE_CUIVRE, 1)),

	PLAQUE_CHAUFFANTE("Plaque chauffante", 250, "/Plaque_Chauffante.png",
			new Pack(BaseResources.OR, 1),
			new Pack(BaseResources.FIL_DE_CUIVRE, 1)),

	PLAQUE_REFROIDISSANTE("Plaque refroidissante", 250,
			"/Plaque_Refroidissante.png",
			new Pack(BaseResources.DIAMANT, 1),
			new Pack(BaseResources.FIL_DE_CUIVRE, 1)),

	AMPOULE("Ampoule", 325, "/Ampoule.png",
			new Pack(BaseResources.FER, 2),
			new Pack(BaseResources.FIL_DE_CUIVRE, 1)),

	HORLOGE("Horloge", 450, "/Horloge.png",
			new Pack(BaseResources.FER, 3),
			new Pack(BaseResources.PLAQUE_DE_OR, 2)),

	ANTENNE("Antenne", 600, "/Antenne.png",
			new Pack(BaseResources.FER, 3),
			new Pack(BaseResources.DIAMANT, 1)),

	GRILL("Grill", 600, "/Grill.png",
			new Pack(BaseResources.PLAQUE_CHAUFFANTE, 1),
			new Pack(BaseResources.FER, 4)),

	MOTEUR("Moteur", 375, "/Moteur.png",
			new Pack(BaseResources.PLAQUE_DE_ARGENT, 2),
			new Pack(BaseResources.CIRCUIT, 2)),

	CLIMATISATION("Climatisation", 800, "/Climatisation.png",
			new Pack(BaseResources.PLAQUE_REFROIDISSANTE, 1),
			new Pack(BaseResources.ALUMINIUM, 4)),

	PILE("Pile", 850, "/Pile.png",
			new Pack(BaseResources.CIRCUIT, 1),
			new Pack(BaseResources.ALUMINIUM, 5)),

	PANNEAU_SOLAIRE("Panneau solaire", 850, "/Panneau_Solaire.png",
			new Pack(BaseResources.CIRCUIT, 2),
			new Pack(BaseResources.OR, 4)),

	PROCESSEUR("Processeur", 850, "/Processeur.png",
			new Pack(BaseResources.CIRCUIT, 2),
			new Pack(BaseResources.ALUMINIUM, 7));

	private String name;
	private int price;
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
	public Displayer<BaseResources> getDisplayer() {

		BorderPane root = new BorderPane();

		Label nom = new Label();
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(this.getName());
		if (nom.getText().length() > 15)
			nom.setFont(new Font(10d));
		nom.setPrefHeight(20d);
		root.setTop(nom);

		ImageView image = new ImageView();
		image.setImage(
				new Image(getClass().getResourceAsStream(this.getURL())));
		root.setCenter(image);

		Label infos = new Label();
		infos.setAlignment(Pos.TOP_CENTER);
		infos.setText(
				String.valueOf(NumberFormat.getInstance(Locale.getDefault())
						.format(this.getPrice())) + " €");
		root.setBottom(infos);
		return new Displayer<BaseResources>(root, this);
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