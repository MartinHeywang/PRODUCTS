package com.martin.model.appareils.comportement;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Database;
import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.Stock;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.DeviceModel;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Buyer_ implements Behaviour {
	private Level level;
	private JeuContrôle controller;

	private Packing resDistribuée;

	public Buyer_(DeviceModel model, JeuContrôle controller) {
		this.level = model.getNiveau();
		this.controller = controller;

		Session session = Database.getSession();
		Transaction tx = session.getTransaction();
		try {
			session.beginTransaction();

			Query<Packing> query = session.createQuery(
					"from Packing where appareil = "
							+ model.getIdAppareilModel(),
					Packing.class);
			List<Packing> list = query.list();

			if (list.size() == 0) {
				resDistribuée = new Packing(Resource.NONE, 1, model);
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
	public void action(Stock resATraiter, Coordinates pointer)
			throws MoneyException {
		System.out.println("The behaviour of a buyer has begun !");

		final Stock tempoStock = new Stock();

		for (int niveau = 0; niveau < this.level.getNiveau(); niveau++) {

			if (!resDistribuée.getRessource().equals(Resource.NONE)) {
				if (controller.getPartieEnCours().getArgent() < 5
						+ Device.getElectricity())
					throw new MoneyException("Le comportement d'un "
							+ "acheteur n'a pas pu être réalisé car le solde "
							+ "d'argent n'était pas assez important.");
				else {
					tempoStock.add(resDistribuée);
					controller.setArgent(5 + Device.getElectricity(), false);
				}
			} else {
				tempoStock.add(Resource.NONE);
			}
		}

		controller.findDevice(pointer).action(tempoStock);
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
	public void setRessourceDistribuée(Packing resDistribuée) {
		switch (resDistribuée.getRessource()) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			this.resDistribuée = resDistribuée;
			Session session = Database.getSession();
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
	public Packing getRessourceDistribuée() {
		return resDistribuée;
	}
}