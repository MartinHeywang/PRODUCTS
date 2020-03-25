package com.martin.model.appareils.comportement;

import java.sql.SQLException;
import java.util.ArrayList;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnees;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Assembleur implements Comportement {

	private Coordonnees pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;

	private Paquet produit;
	private ArrayList<Ressource> ressources = new ArrayList<Ressource>();
	private ArrayList<Ressource> recette = new ArrayList<Ressource>();

	public Comportement_Assembleur(Coordonnees xy, NiveauAppareil niveau,
			int xToAdd, int yToAdd, JeuContrôle controller, Appareil appareil) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnees(xy.getX() + xToAdd, xy.getY() + yToAdd);

		try {
			if (Connect_SQLite.getPaquetDao().queryBuilder().where()
					.eq("idAppareil", appareil.getID()).query()
					.size() != 0)
				produit = Connect_SQLite.getPaquetDao().queryBuilder()
						.where()
						.eq("idAppareil", appareil.getID())
						.queryForFirst();
			else {
				produit = new Paquet(Ressource.NONE, 1, appareil);
				Connect_SQLite.getPaquetDao().create(produit);
			}
		} catch (SQLException e) {
		}
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {
		Stock tempoStock = new Stock();

		for (int level = 0; level < niveau.getNiveau()
				|| level < resATraiter.size(); level++) {
			if (controller.getPartieEnCours().getArgent() < 5
					+ Appareil.getÉlectricité())
				throw new NegativeArgentException(
						"Le comportement d'un acheteur "
								+ "n'a pas pu être réalisé car le solde "
								+ "d'argent n'était pas assez important.");

			if (checkIngrédients()) {
				tempoStock.add(produit);
				controller.setArgent(Appareil.getÉlectricité(), false);
			}
		}

		controller.getGrilleAppareils(pointer).action(tempoStock);
	}

	/**
	 * 
	 * <h1>checkIngrédients</h1>
	 * <p>
	 * Checks if thre are enough resources to built the product
	 * </p>
	 * 
	 * @return boolean if the requires resources are available
	 * 
	 */
	private boolean checkIngrédients() {
		// Le stock temporaire pour mettre les de côté les ressources
		// réservées au produit
		ArrayList<Ressource> stock = new ArrayList<Ressource>();
		// On vide les éléments de la recette
		recette = new ArrayList<Ressource>();
		// Puis on la re-remplie en fonction des ressources de la quantité
		// Cette appareil prend en charge tous les schéma à 2 paquets
		for (int i = 0; i < produit.getRessource().getRecette().get(0)
				.getQuantité(); i++) {
			recette.add(
					produit.getRessource().getRecette().get(0).getRessource());
		}
		for (int i = 0; i < produit.getRessource().getRecette().get(1)
				.getQuantité(); i++) {
			recette.add(
					produit.getRessource().getRecette().get(1).getRessource());
		}

		// Pour la taille de la recette crée
		for (int j = 0; j < produit.getRessource().getRecette().size(); j++) {
			// Si la ressourc est présente dans le stock
			if (ressources.contains(recette.get(j))) {
				// On l'ajoute au stock temporaire et on l'enlève du stockage
				stock.add(recette.get(j));
				ressources.remove(recette.get(j));
			}
			// Sinon...
			else {
				// On remet les ressource du stock dans le stockage principal
				ressources.addAll(stock);
				// On retourne faux pour dire que l'action ne peut pas continuer
				// (les ressources ne sont pas suffisantes)
				return false;
			}
		}
		// On retourne vrai car toutes les ressources nécessaires sont
		// disponibles
		return true;
	}

	/**
	 * <h1>setProduit</h1>
	 * <p>
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * </p>
	 * 
	 * @param produit the resource to set
	 */
	public void setProduit(Ressource produit) {
		// On vérifie que la ressource donné est prise en charge par
		// l'appareil
		switch (produit) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			break;
		default:
			// Si oui, on modifie le produit
			this.produit = new Paquet(produit, 1);
			break;
		}

	}

	/**
	 * 
	 * @return produit the current product od this device
	 */
	public Paquet getProduit() {
		return produit;
	}
}
