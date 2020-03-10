package com.martin.model.appareils.comportement;

import java.util.ArrayList;

import com.martin.model.Coordonnées;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Assembleur implements Comportement {

	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;

	private Paquet produit = new Paquet(Ressource.NONE, 1);
	private ArrayList<Ressource> ressources = new ArrayList<Ressource>();
	private ArrayList<Ressource> recette = new ArrayList<Ressource>();

	public Comportement_Assembleur(Coordonnées xy, NiveauAppareil niveau,
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX() + xToAdd, xy.getY() + yToAdd);

		// FixMe : charger le produit
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
	 * Vérifie si les ressources de l'appareil sont suffisantes pour créer
	 * l'objet de la recette
	 * </p>
	 * 
	 * @return boolean if the requires resources are available
	 * 
	 */
	private boolean checkIngrédients() {
		ArrayList<Ressource> stock = new ArrayList<Ressource>();
		recette = new ArrayList<Ressource>();
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

		for (int j = 0; j < produit.getRessource().getRecette().size(); j++) {
			if (ressources.contains(recette.get(j))) {
				stock.add(recette.get(j));
				ressources.remove(recette.get(j));
			} else {
				ressources.addAll(stock);
				if (pointer.isInGrid(
						controller.getPartieEnCours().getTailleGrille())) {
					return false;
				}
			}
		}
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
		switch (produit) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			break;
		default:
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
