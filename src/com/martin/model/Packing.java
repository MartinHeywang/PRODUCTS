package com.martin.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Database;
import com.martin.model.appareils.DeviceModel;

public class Packing {

	private Long idPaquet;

	private Resource resource;

	private int quantité;

	private DeviceModel appareil;

	public Packing() {
	}

	public Packing(Resource resource, int quantité) {
		this.resource = resource;
		this.quantité = quantité;
	}

	public Packing(Resource resource, int quantité, DeviceModel model) {
		this.resource = resource;
		this.quantité = quantité;
		this.appareil = model;
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
	public Resource getRessource() {
		return resource;
	}

	/**
	 * 
	 * @param res the resource to set
	 */
	public void setRessource(Resource res) {
		this.resource = res;
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
	public DeviceModel getAppareil() {
		return appareil;
	}

	/**
	 * 
	 * @param appareil the device to set
	 */
	public void setAppareil(DeviceModel appareil) {
		this.appareil = appareil;
	}

	@Override
	public String toString() {
		return "Object type Paquet. Id : " + idPaquet + ". Ressource : "
				+ resource + ". Quantité : " + quantité + ".";
	}

	/**
	 * This method returns a List of Coordonnees, from table coordonnées.
	 * May be expensive to invoke; if you have to use it, stock the result
	 * in a list.
	 * 
	 * @return a list of coordinates
	 */
	public static List<Packing> query() {
		// Creating a Session and a List
		Session session = Database.getSession();
		List<Packing> list;
		try {
			// Query for all objects and stock it in the List created before
			Query<Packing> query = session.createQuery(
					"from Paquet",
					Packing.class);
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
	 * @param packing the object to save.
	 */
	public static void insert(Packing packing) {
		// Creating a Session and a Transaction
		Session session = Database.getSession();
		Transaction transaction = null;
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			session.save(packing);
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
