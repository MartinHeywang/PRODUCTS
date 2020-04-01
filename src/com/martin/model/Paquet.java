package com.martin.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;
import com.martin.model.appareils.Appareil;

public class Paquet {

	private Long idPaquet;

	private Ressource ressource;

	private int quantité;

	private Appareil appareil;

	public Paquet() {
	}

	public Paquet(Ressource ressource, int quantité) {
		this.ressource = ressource;
		this.quantité = quantité;
	}

	public Paquet(Ressource ressource, int quantité, Appareil appareil) {
		this.ressource = ressource;
		this.quantité = quantité;
		this.appareil = appareil;
	}

	/**
	 * 
	 * @return the id
	 */
	public Long getIdPaquet() {
		return idPaquet;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setIdPaquet(Long id) {
		this.idPaquet = id;
	}

	/**
	 * 
	 * @return the resource
	 */
	public Ressource getRessource() {
		return ressource;
	}

	/**
	 * 
	 * @param res the resource to set
	 */
	public void setRessource(Ressource res) {
		this.ressource = res;
	}

	/**
	 * 
	 * @return the quantity
	 */
	public int getQuantité() {
		return quantité;
	}

	/**
	 * 
	 * @param quantité the quantity to set
	 */
	public void setQuantité(int quantité) {
		this.quantité = quantité;
	}

	/**
	 * 
	 * @return the associated-device
	 */
	public Appareil getAppareil() {
		return appareil;
	}

	/**
	 * 
	 * @param appareil the device to set
	 */
	public void setAppareil(Appareil appareil) {
		this.appareil = appareil;
	}

	@Override
	public String toString() {
		return "Object type Paquet. Id : " + idPaquet + ". Ressource : "
				+ ressource + ". Quantité : " + quantité + ".";
	}

	/**
	 * This method returns a List of Coordonnees, from table coordonnées.
	 * May be expensive to invoke; if you have to use it, stock the result
	 * in a list.
	 * 
	 * @return a list of coordinates
	 */
	public static List<Paquet> query() {
		// Creating a Session and a List
		Session session = Connect_SQLite.getSession();
		List<Paquet> list;
		try {
			// Query for all objects and stock it in the List created before
			Query<Paquet> query = session.createQuery(
					"from Paquet",
					Paquet.class);
			list = query.list();
		} catch (HibernateException e) {
			System.err.println("Unable to query in table paquets");
			return null;
		} finally {
			// Closing the session
			session.close();
		}
		// Returning the result
		return list;
	}

	/**
	 * Insert in table paquets the object in parameters.
	 * 
	 * @param paquet the object to save.
	 */
	public static void insert(Paquet paquet) {
		// Creating a Session and a Transaction
		Session session = Connect_SQLite.getSession();
		Transaction transaction = null;
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			session.save(paquet);
			transaction.commit();

		} catch (HibernateException e) {
			System.err
					.println("Unable to run insert stmt in table paquets");
			if (transaction != null)
				transaction.rollback();
		} finally {
			// Closing the session
			session.close();
		}
	}
}
