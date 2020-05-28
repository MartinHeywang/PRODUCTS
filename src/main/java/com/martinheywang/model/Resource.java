package com.martinheywang.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

@SuppressWarnings("serial")
public enum Resource implements Displayable<Resource> {
	NONE("None", 0, "/None.png", new ArrayList<Packing>() {
		{
			add(new Packing(Resource.NONE, 0));
			add(new Packing(Resource.NONE, 0));
			add(new Packing(Resource.NONE, 0));
		}
	}),

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
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.OR, 1));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	PLAQUE_CHAUFFANTE("Plaque chauffante", 250,
			"/Plaque_Chauffante.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.OR, 1));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	PLAQUE_REFROIDISSANTE("Plaque refroidissante", 250,
			"/Plaque_Refroidissante.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.DIAMANT, 1));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	AMPOULE("Ampoule", 325, "/Ampoule.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.FER, 2));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	HORLOGE("Horloge", 450, "/Horloge.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.FER, 3));
					add(new Packing(Resource.PLAQUE_DE_OR, 2));
				}
			}),
	ANTENNE("Antenne", 600, "/Antenne.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.FER, 3));
					add(new Packing(Resource.DIAMANT, 2));
				}
			}),
	GRILL("Grill", 600, "/Grill.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.PLAQUE_CHAUFFANTE, 1));
					add(new Packing(Resource.FER, 4));
				}
			}),
	MOTEUR("Moteur", 375, "/Moteur.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.PLAQUE_DE_ARGENT, 2));
					add(new Packing(Resource.CIRCUIT, 2));
				}
			}),
	CLIMATISATION("Climatisation", 800, "/Climatisation.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.PLAQUE_REFROIDISSANTE, 1));
					add(new Packing(Resource.ALUMINIUM, 4));
				}
			}),
	PILE("Pile", 850, "/Pile.png", new ArrayList<Packing>() {
		{
			add(new Packing(Resource.CIRCUIT, 1));
			add(new Packing(Resource.ALUMINIUM, 5));
		}
	}),
	PANNEAU_SOLAIRE("Panneau solaire", 850,
			"/Panneau_Solaire.png", new ArrayList<Packing>() {
				{
					add(new Packing(Resource.CIRCUIT, 2));
					add(new Packing(Resource.OR, 4));
				}
			}),
	PROCESSEUR("Processeur", 850, "/Processeur.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.CIRCUIT, 2));
					add(new Packing(Resource.ALUMINIUM, 7));
				}
			});

	String nom;
	int value;
	String urlImg;
	ArrayList<Packing> recette;

	Resource(String nom, int value, String urlImg) {
		this.nom = nom;
		this.value = value;
		this.urlImg = urlImg;
	}

	Resource(String nom, int value, String urlImg, ArrayList<Packing> list) {
		this.nom = nom;
		this.value = value;
		this.urlImg = urlImg;
		this.recette = list;
	}

	@Override
	public Displayer<Resource> getDisplayer() {

		BorderPane root = new BorderPane();

		Label nom = new Label();
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(this.getNom());
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
						.format(this.getValue())) + " €");
		root.setBottom(infos);
		return new Displayer<Resource>(root, this);
	}

	public int getValue() {
		return value;
	}

	public String getNom() {
		return nom;
	}

	public String getURL() {
		return urlImg;
	}

	public ArrayList<Packing> getRecette() {
		return recette;
	}
}