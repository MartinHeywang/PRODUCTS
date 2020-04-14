package com.martin.model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public enum Resource {
	NONE("None", 0, "images/ressources/None.png", new ArrayList<Packing>() {
		{
			add(new Packing(Resource.NONE, 0));
			add(new Packing(Resource.NONE, 0));
			add(new Packing(Resource.NONE, 0));
		}
	}),

	FER("Fer", 30, "images/ressources/Minerai_De_Fer.png", null),
	PLAQUE_DE_FER("Plaque de fer", 60, "images/ressources/Plaque_De_Fer.png",
			null),
	FIL_DE_FER("Fil de fer", 60, "images/ressources/Fil_De_Fer.png", null),
	LINGOT_DE_FER("Lingot de fer", 60, "images/ressources/Lingot_De_Fer.png",
			null),

	OR("Or", 30, "images/ressources/Minerai_De_Or.png", null),
	PLAQUE_DE_OR("Plaque d'or", 60, "images/ressources/Plaque_De_Or.png", null),
	FIL_DE_OR("Fil d'or", 60, "images/ressources/Fil_De_Or.png", null),
	LINGOT_DE_OR("Lingot d'or", 60, "images/ressources/Lingot_De_Or.png", null),

	CUIVRE("Cuivre", 30, "images/ressources/Minerai_De_Cuivre.png", null),
	PLAQUE_DE_CUIVRE("Plaque de cuivre", 60,
			"images/ressources/Plaque_De_Cuivre.png", null),
	FIL_DE_CUIVRE("Fil de cuivre", 60, "images/ressources/Fil_De_Cuivre.png",
			null),
	LINGOT_DE_CUIVRE("Lingot de cuivre", 60,
			"images/ressources/Lingot_De_Cuivre.png", null),

	ARGENT("Argent", 30, "images/ressources/Minerai_De_Argent.png", null),
	PLAQUE_DE_ARGENT("Plaque d'argent", 60,
			"images/ressources/Plaque_De_Argent.png", null),
	FIL_DE_ARGENT("Fil d'argent", 60, "images/ressources/Fil_De_Argent.png",
			null),
	LINGOT_DE_ARGENT("Lingot d'argent", 60,
			"images/ressources/Lingot_De_Argent.png", null),

	DIAMANT("Diamant", 30, "images/ressources/Minerai_De_Diamant.png", null),
	PLAQUE_DE_DIAMANT("Plaque de diamant", 60, "images/ressources/None.png",
			null),
	FIL_DE_DIAMANT("Fil de diamant", 60, "images/ressources/None.png", null),
	LINGOT_DE_DIAMANT("Lingot de diamant", 60, "images/ressources/None.png",
			null),

	ALUMINIUM("Aluminium", 30, "images/ressources/Minerai_De_Aluminium.png",
			null),
	PLAQUE_DE_ALUMINIUM("Plaque d'aluminium", 60,
			"images/ressources/Plaque_De_Aluminium.png", null),
	FIL_DE_ALUMINIUM("Fil d'aluminium", 60,
			"images/ressources/Fil_De_Aluminium.png", null),
	LINGOT_DE_ALUMINIUM("Lingot d'aluminium", 60,
			"images/ressources/Lingot_De_Aluminium.png", null),

	CIRCUIT("Circuit", 250, "images/ressources/Circuit.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.OR, 1));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	PLAQUE_CHAUFFANTE("Plaque chauffante", 250,
			"images/ressources/Plaque_Chauffante.png", new ArrayList<Packing>() {
				{
					add(new Packing(Resource.OR, 1));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	PLAQUE_REFROIDISSANTE("Plaque refroidissante", 250,
			"images/ressources/Plaque_Refroidissante.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.DIAMANT, 1));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	AMPOULE("Ampoule", 325, "images/ressources/Ampoule.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.FER, 2));
					add(new Packing(Resource.FIL_DE_CUIVRE, 1));
				}
			}),
	HORLOGE("Horloge", 450, "images/ressources/Horloge.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.FER, 3));
					add(new Packing(Resource.PLAQUE_DE_OR, 2));
				}
			}),
	ANTENNE("Antenne", 600, "images/ressources/Antenne.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.FER, 3));
					add(new Packing(Resource.DIAMANT, 2));
				}
			}),
	GRILL("Grill", 600, "images/ressources/Grill.png", new ArrayList<Packing>() {
		{
			add(new Packing(Resource.PLAQUE_CHAUFFANTE, 1));
			add(new Packing(Resource.FER, 4));
		}
	}),
	MOTEUR("Moteur", 375, "images/ressources/Moteur.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.PLAQUE_DE_ARGENT, 2));
					add(new Packing(Resource.CIRCUIT, 2));
				}
			}),
	CLIMATISATION("Climatisation", 800, "images/ressources/Climatisation.png",
			new ArrayList<Packing>() {
				{
					add(new Packing(Resource.PLAQUE_REFROIDISSANTE, 1));
					add(new Packing(Resource.ALUMINIUM, 4));
				}
			}),
	PILE("Pile", 850, "images/ressources/Pile.png", new ArrayList<Packing>() {
		{
			add(new Packing(Resource.CIRCUIT, 1));
			add(new Packing(Resource.ALUMINIUM, 5));
		}
	}),
	PANNEAU_SOLAIRE("Panneau solaire", 850,
			"images/ressources/Panneau_Solaire.png", new ArrayList<Packing>() {
				{
					add(new Packing(Resource.CIRCUIT, 2));
					add(new Packing(Resource.OR, 4));
				}
			}),
	PROCESSEUR("Processeur", 850, "images/ressources/Processeur.png",
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

	public static Resource getForURL(String url) {
		switch (url) {
		case "images/ressources/None.png":
			return Resource.NONE;

		case "images/ressources/Minerai_De_Fer.png":
			return Resource.FER;
		case "images/ressources/Minerai_De_Or.png":
			return Resource.OR;
		case "images/ressources/Minerai_De_Cuivre.png":
			return Resource.CUIVRE;
		case "images/ressources/Minerai_De_Argent.png":
			return Resource.ARGENT;
		case "images/ressources/Minerai_De_Diamant.png":
			return Resource.DIAMANT;
		case "images/ressources/Minerai_De_Aluminium.png":
			return Resource.ALUMINIUM;

		case "images/ressources/Plaque_De_Fer.png":
			return Resource.PLAQUE_DE_FER;
		case "images/ressources/Plaque_De_Or.png":
			return Resource.PLAQUE_DE_OR;
		case "images/ressources/Plaque_De_Cuivre.png":
			return Resource.PLAQUE_DE_CUIVRE;
		case "images/ressources/Plaque_De_Argent.png":
			return Resource.PLAQUE_DE_ARGENT;
		case "images/ressources/Plaque_De_Diamant.png":
			return Resource.PLAQUE_DE_DIAMANT;
		case "images/ressources/Plaque_De_Aluminium.png":
			return Resource.PLAQUE_DE_ALUMINIUM;

		case "images/ressources/Fil_De_Fer.png":
			return Resource.FIL_DE_FER;
		case "images/ressources/Fil_De_Or.png":
			return Resource.FIL_DE_OR;
		case "images/ressources/Fil_De_Cuivre.png":
			return Resource.FIL_DE_CUIVRE;
		case "images/ressources/Fil_De_Argent.png":
			return Resource.FIL_DE_ARGENT;
		case "images/ressources/Fil_De_Diamant.png":
			return Resource.FIL_DE_DIAMANT;
		case "images/ressources/Fil_De_Aluminium.png":
			return Resource.FIL_DE_ALUMINIUM;

		case "images/ressources/Lingot_De_Fer.png":
			return Resource.LINGOT_DE_FER;
		case "images/ressources/Lingot_De_Or.png":
			return Resource.LINGOT_DE_FER;
		case "images/ressources/Lingot_De_Cuivre.png":
			return Resource.LINGOT_DE_FER;
		case "images/ressources/Lingot_De_Argent.png":
			return Resource.LINGOT_DE_FER;
		case "images/ressources/Lingot_De_Diamant.png":
			return Resource.LINGOT_DE_FER;
		case "images/ressources/Lingot_De_Aluminium.png":
			return Resource.LINGOT_DE_FER;

		case "images/ressources/Circuit.png":
			return Resource.CIRCUIT;
		case "images/ressources/Moteur.png":
			return Resource.MOTEUR;
		case "images/ressources/Plaque_Chauffante.png":
			return Resource.PLAQUE_CHAUFFANTE;
		case "images/ressources/Plaque_Refroidissante.png":
			return Resource.PLAQUE_REFROIDISSANTE;
		case "images/ressources/Ampoule.png":
			return Resource.AMPOULE;
		case "images/ressources/Horloge.png":
			return Resource.HORLOGE;
		case "images/ressources/Antenne.png":
			return Resource.ANTENNE;
		case "images/ressources/Grill.png":
			return Resource.GRILL;
		case "images/ressources/Climatisation.png":
			return Resource.CLIMATISATION;
		case "images/ressources/Pile.png":
			return Resource.PILE;
		case "images/ressources/Panneau_Solaire.png":
			return Resource.PANNEAU_SOLAIRE;
		case "images/ressources/Processeur.png":
			return Resource.PROCESSEUR;

		default:
			return Resource.NONE;
		}
	}
}
