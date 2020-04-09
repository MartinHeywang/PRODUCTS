package com.martin.model.appareils.comportement;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnees;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Niveau;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Acheteur implements Comportement {

	private Coordonnees pointer;
	private Niveau niveau;
	private JeuContrôle controller;

	private Paquet resDistribuée;

	public Comportement_Acheteur(Coordonnees xy, Niveau niveau,
			int xToAdd, int yToAdd, JeuContrôle controller, Appareil appareil) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnees(xy.getX() + xToAdd, xy.getY() + yToAdd);

		Session session = Connect_SQLite.getSession();
		Transaction tx = session.getTransaction();
		try {
			session.beginTransaction();

			Query<Paquet> query = session.createQuery(
					"from Paquet where appareil = " + appareil.getId(),
					Paquet.class);
			List<Paquet> list = query.list();

			if (list.size() == 0) {
				resDistribuée = new Paquet(Ressource.NONE, 1, appareil);
				session.save(resDistribuée);
				tx.commit();
			} else if (list.size() == 1) {
				resDistribuée = list.get(0);
			}
		} catch (HibernateException e) {
			System.err
					.println("Error when loading resource. Error message :\n");
			e.printStackTrace();
		} finally {
			session.close();
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

		controller.getPartieEnCours().getAppareil(pointer).action(tempoStock);
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
			this.resDistribuée = resDistribuée;
			Session session = Connect_SQLite.getSession();
			Transaction tx = session.getTransaction();
			try {
				session.beginTransaction();
				session.update(resDistribuée);
				tx.commit();
			} catch (HibernateException e) {
				System.err
						.println(
								"Error when setting resource. Error message :\n");
				e.printStackTrace();
			} finally {
				session.close();
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