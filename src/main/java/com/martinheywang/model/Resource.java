package com.martinheywang.model;

import java.util.ArrayList;

import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("serial")
public enum Resource implements Displayable {
	NONE("None", 0, "/None.png", new ArrayList<Packing>() {
		{
			add(new Packing(Resource.NONE, 0));
			add(new Packing(Resource.NONE, 0));
			add(new Packing(Resource.NONE, 0));
		}
	}),

	FER("Fer", 30, "/Minerai_De_Fer.png", null),
	PLAQUE_DE_FER("Plaque de fer", 60, "/Plaque_De_Fer.png",
			null),
	FIL_DE_FER("Fil de fer", 60, "/Fil_De_Fer.png", null),
	LINGOT_DE_FER("Lingot de fer", 60, "/Lingot_De_Fer.png",
			null),

	OR("Or", 30, "/Minerai_De_Or.png", null),
	PLAQUE_DE_OR("Plaque d'or", 60, "/Plaque_De_Or.png", null),
	FIL_DE_OR("Fil d'or", 60, "/Fil_De_Or.png", null),
	LINGOT_DE_OR("Lingot d'or", 60, "/Lingot_De_Or.png", null),

	CUIVRE("Cuivre", 30, "/Minerai_De_Cuivre.png", null),
	PLAQUE_DE_CUIVRE("Plaque de cuivre", 60,
			"/Plaque_De_Cuivre.png", null),
	FIL_DE_CUIVRE("Fil de cuivre", 60, "/Fil_De_Cuivre.png",
			null),
	LINGOT_DE_CUIVRE("Lingot de cuivre", 60,
			"/Lingot_De_Cuivre.png", null),

	ARGENT("Argent", 30, "/Minerai_De_Argent.png", null),
	PLAQUE_DE_ARGENT("Plaque d'argent", 60,
			"/Plaque_De_Argent.png", null),
	FIL_DE_ARGENT("Fil d'argent", 60, "/Fil_De_Argent.png",
			null),
	LINGOT_DE_ARGENT("Lingot d'argent", 60,
			"/Lingot_De_Argent.png", null),

	DIAMANT("Diamant", 30, "/Minerai_De_Diamant.png", null),
	PLAQUE_DE_DIAMANT("Plaque de diamant", 60, "/None.png",
			null),
	FIL_DE_DIAMANT("Fil de diamant", 60, "/None.png", null),
	LINGOT_DE_DIAMANT("Lingot de diamant", 60, "/None.png",
			null),

	ALUMINIUM("Aluminium", 30, "/Minerai_De_Aluminium.png",
			null),
	PLAQUE_DE_ALUMINIUM("Plaque d'aluminium", 60,
			"/Plaque_De_Aluminium.png", null),
	FIL_DE_ALUMINIUM("Fil d'aluminium", 60,
			"/Fil_De_Aluminium.png", null),
	LINGOT_DE_ALUMINIUM("Lingot d'aluminium", 60,
			"/Lingot_De_Aluminium.png", null),

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
		root.setTop(nom);

		ImageView image = new ImageView();
		image.setImage(
				new Image(getClass().getResourceAsStream(this.getURL())));
		root.setCenter(image);

		Label infos = new Label();
		infos.setAlignment(Pos.TOP_CENTER);
		infos.setText("Prix de vente : "
				+ String.valueOf(this.getValue()) + " €");
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