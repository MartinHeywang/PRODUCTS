package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.util.List;

import javax.management.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.martin.Database;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.Stock;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.model.appareils.orientation.Exits;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Sorter extends Device {

	private Packing crit1, crit2;

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.EXIT,
			PointerTypes.EXIT);

	public Sorter(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);
		Session session = Database.getSession();
		Transaction tx = session.getTransaction();
		try {
			session.beginTransaction();

			Query<Packing> query = session.createQuery(
					"from Paquet where appareil = "
							+ model.getIdAppareilModel(),
					Packing.class);
			List<Packing> list = query.list();

			if (list.size() == 0) {
				crit1 = new Packing(Resource.NONE, 1, model);
				session.save(crit1);
				crit2 = new Packing(Resource.NONE, 1, model);
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

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());

	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {

		final Direction direction = model.getDirection();
		if (resATraiter.get(0).getRessource().equals(crit1.getRessource())) {
			switch (model.getDirection()) {
			case UP:
				exits = Exits.listForRight(direction);
				break;
			case RIGHT:
				exits = Exits.listForCenter(direction);
				break;
			case DOWN:
				exits = Exits.listForLeft(direction);
				break;
			case LEFT:
				exits = Exits.listForUp(direction);
				break;
			default:
				break;
			}
		} else if (resATraiter.get(0).getRessource()
				.equals(crit2.getRessource())) {
			switch (model.getDirection()) {
			case UP:
				exits = Exits.listForLeft(direction);
				break;
			case RIGHT:
				exits = Exits.listForUp(direction);
				break;
			case DOWN:
				exits = Exits.listForRight(direction);
				break;
			case LEFT:
				exits = Exits.listForCenter(direction);
				break;
			default:
				break;
			}
		} else {
			switch (model.getDirection()) {
			case UP:
				exits = Exits.listForCenter(direction);
				break;
			case RIGHT:
				exits = Exits.listForLeft(direction);
				break;
			case DOWN:
				exits = Exits.listForUp(direction);
				break;
			case LEFT:
				exits = Exits.listForRight(direction);
				break;
			default:
				break;
			}
		}

		// Todo : add behaviour in action
	}

	public void setCritère1(Resource res) {
		this.crit1 = new Packing(res, 1);
	}

	public void setCritère2(Resource res) {
		this.crit2 = new Packing(res, 1);
	}

	public Resource getCritère1() {
		return crit1.getRessource();
	}

	public Resource getCritère2() {
		return crit2.getRessource();
	}
}