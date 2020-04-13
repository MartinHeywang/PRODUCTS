package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Appareil_Trieur extends Appareil {

	private Paquet crit1, crit2;

	public Appareil_Trieur(AppareilModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);
		Session session = Connect_SQLite.getSession();
		Transaction tx = session.getTransaction();
		try {
			session.beginTransaction();

			Query<Paquet> query = session.createQuery(
					"from Paquet where appareil = "
							+ model.getIdAppareilModel(),
					Paquet.class);
			List<Paquet> list = query.list();

			if (list.size() == 0) {
				crit1 = new Paquet(Ressource.NONE, 1, model);
				session.save(crit1);
				crit2 = new Paquet(Ressource.NONE, 1, model);
				session.save(crit2);
				tx.commit();
			} else if (list.size() == 1) {
				crit1 = list.get(0);
				crit2 = list.get(1);
			}
		} catch (HibernateException e) {
			System.err
					.println("Error when loading resource. Error message :\n");
			e.printStackTrace();
		} finally {
			session.close();
		}

		entrances = Entrées.listForUp(model.getDirection());
		exits = Sorties.listForNone();
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {

		final Direction direction = model.getDirection();
		if (resATraiter.get(0).getRessource().equals(crit1.getRessource())) {
			switch (model.getDirection()) {
			case UP:
				exits = Sorties.listForRight(direction);
				break;
			case RIGHT:
				exits = Sorties.listForCenter(direction);
				break;
			case DOWN:
				exits = Sorties.listForLeft(direction);
				break;
			case LEFT:
				exits = Sorties.listForUp(direction);
				break;
			default:
				break;
			}
		} else if (resATraiter.get(0).getRessource()
				.equals(crit2.getRessource())) {
			switch (model.getDirection()) {
			case UP:
				exits = Sorties.listForLeft(direction);
				break;
			case RIGHT:
				exits = Sorties.listForUp(direction);
				break;
			case DOWN:
				exits = Sorties.listForRight(direction);
				break;
			case LEFT:
				exits = Sorties.listForCenter(direction);
				break;
			default:
				break;
			}
		} else {
			switch (model.getDirection()) {
			case UP:
				exits = Sorties.listForCenter(direction);
				break;
			case RIGHT:
				exits = Sorties.listForLeft(direction);
				break;
			case DOWN:
				exits = Sorties.listForUp(direction);
				break;
			case LEFT:
				exits = Sorties.listForRight(direction);
				break;
			default:
				break;
			}
		}

		// Todo : add behaviour in action
		behaviour.action(resATraiter);
	}

	public void setCritère1(Ressource res) {
		this.crit1 = new Paquet(res, 1);
	}

	public void setCritère2(Ressource res) {
		this.crit2 = new Paquet(res, 1);
	}

	public Ressource getCritère1() {
		return crit1.getRessource();
	}

	public Ressource getCritère2() {
		return crit2.getRessource();
	}
}