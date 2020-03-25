package com.martin.model.appareils.comportement;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnees;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Acheteur implements Comportement {

	private Coordonnees pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;

	private Paquet resDistribuée;

	public Comportement_Acheteur(Coordonnees xy, NiveauAppareil niveau,
			int xToAdd, int yToAdd, JeuContrôle controller, Appareil appareil) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnees(xy.getX() + xToAdd, xy.getY() + yToAdd);

		try {
			if (Connect_SQLite.getPaquetDao().queryBuilder().where()
					.eq("idAppareil", appareil.getID()).query()
					.size() != 0)
				resDistribuée = Connect_SQLite.getPaquetDao().queryBuilder()
						.where()
						.eq("idAppareil", appareil.getID())
						.queryForFirst();
			else {
				resDistribuée = new Paquet(Ressource.NONE, 1, appareil);
				Connect_SQLite.getPaquetDao().create(resDistribuée);
			}
		} catch (SQLException e) {
		}
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {
		final Stock tempoStock = new Stock();

		for (int niveau = 0; niveau < this.niveau.getNiveau(); niveau++) {

			if (!resDistribuée.getRessource().equals(Ressource.NONE)) {
				if (controller.getPartieEnCours().getArgent() < 5
						+ Appareil.getÉlectricité())
					throw new NegativeArgentException("Le comportement d'un "
							+ "acheteur n'a pas pu être réalisé car le solde "
							+ "d'argent n'était pas assez important.");
				else {
					tempoStock.add(resDistribuée);
					controller.setArgent(5 + Appareil.getÉlectricité(), false);
				}
			} else {
				tempoStock.add(Ressource.NONE);
			}
		}

		controller.getGrilleAppareils(pointer).action(tempoStock);
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
	public void setRessourceDistribuée(Paquet resDistribuée) {
		switch (resDistribuée.getRessource()) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			for (int i = 0; i < this.niveau.getNiveau(); i++) {

				try {
					Connect_SQLite.getPaquetDao().createIfNotExists(
							resDistribuée);
					this.resDistribuée = resDistribuée;
				} catch (SQLException e) {
					System.err.println(e.getLocalizedMessage());

				}
			}
		default:
			break;
		}

	}

	/**
	 * 
	 * @return resDistribuée the distributed resource
	 */
	public Paquet getRessourceDistribuée() {
		return resDistribuée;
	}
}