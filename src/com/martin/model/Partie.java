package com.martin.model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.AppareilModel;
import com.martin.model.exceptions.NegativeArgentException;

public class Partie {

	private Long idPartie;

	private String nom;

	private LocalDateTime lastView;

	private long argent;

	private int tailleGrille;

	private List<AppareilModel> listAppareilsModel = Arrays.asList();

	public Partie() {
	}

	/**
	 * Creates a new <i>game</i>.
	 * 
	 * @param nom the name of the new game
	 * @throws SQLException if this object can't be registered in the
	 *                      database
	 */
	public Partie(String nom) {
		this.nom = nom;
		this.lastView = LocalDateTime.now();
		this.tailleGrille = 5;
		this.argent = 1250;

		Partie.insert(this);

	}

	/**
	 * Saves this object int the database, with all its devices
	 * 
	 * @see Connect_SQLite#getAppareilDao()
	 * @see Connect_SQLite#getPartieDao()
	 */
	public void save(List<Appareil> listAppareils) {
		// Todo : save
		// Does actually nothing because of hibernates replacements
	}

	/**
	 * This method deletes this game and all its devices in the database.
	 * After invoking this method, all changes won't be saved. This object
	 * wont' be useful.
	 * 
	 * @throws SQLException if ther is a problem when deleting this
	 *                      object.
	 */
	public void delete() throws SQLException {
		// Todo : delete
		// Does actually nothing because of hibernates replacements
	}

	/**
	 * 
	 * @return a list of all devices of this game
	 */
	public List<AppareilModel> getAppareilsModel() {

		final Session session = Connect_SQLite.getSession();

		try {
			// Queryiiinng for all devices models who reference this game
			Query<AppareilModel> query = session.createQuery(
					"from AppareilModel AM where AM.partie = " + idPartie,
					AppareilModel.class);
			// Execute it in a list
			List<AppareilModel> list = query.list();

			// Initialize (before closing the session) the many-to-one
			// references
			for (AppareilModel model : list) {
				Hibernate.initialize(model.getCoordonnees());
				Hibernate.initialize(model.getPartie());
			}

			// Updating the list field
			listAppareilsModel = list;
		} catch (HibernateException e) {
			// Print in case of exception
			e.printStackTrace();
		} finally {
			session.close();
		}
		// Returning the list
		return listAppareilsModel;
	}

	/**
	 * Iterrates over the list of devices and checks if a device with the
	 * corresponding coordinates can be found.
	 * 
	 * @param xy the coordinates to compare
	 * @return a device from the list with the corresponding coordinate.
	 * @throws NullPointerException if no devices can be found
	 */
	public Appareil getAppareil(Coordonnees xy) throws NullPointerException {
		// Todo : method Partie.getAppareil(Coordonnees)
		// Does nothing because of hibernate replacements
		return null;
	}

	/**
	 * 
	 * @return the id of this game
	 */
	public Long getIdPartie() {
		return idPartie;
	}

	/**
	 * 
	 * @return the name of the game
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * 
	 * @return the date of the latest save
	 */
	public LocalDateTime getLastView() {
		return lastView;
	}

	/**
	 * 
	 * @return how many money the game counts
	 */
	public long getArgent() {
		return argent;
	}

	/**
	 * 
	 * @return the grid-size
	 */
	public int getTailleGrille() {
		return tailleGrille;
	}

	/**
	 * 
	 * @param id the new id
	 */
	public void setIdPartie(Long id) {
		this.idPartie = id;
	}

	/**
	 * 
	 * @param newName the new name
	 */
	public void setNom(String newName) {
		this.nom = newName;
	}

	/**
	 * 
	 * @param lastView a parsable string to LocalDateTime object
	 */
	public void setLastView(LocalDateTime lastView) {
		this.lastView = lastView;
	}

	/**
	 * 
	 * @param argent the new money
	 */
	public void setArgent(long argent) {
		if (argent < 0) {
		} else {
			this.argent = argent;
		}
	}

	/**
	 * 
	 * @param tailleGrille the new grid-size
	 */
	public void setTailleGrille(int tailleGrille) {
		this.tailleGrille = tailleGrille;
	}

	/**
	 * Adds or substract money to the total.
	 * 
	 * @param argent   How many money should me added or substracted.
	 * @param increase If the money should be added.
	 * @throws NegativeArgentException if not enough money is available.
	 */
	public void setArgent(long argent, boolean increase)
			throws NegativeArgentException {
		if (increase) {
			this.argent += argent;
		} else {
			if (this.argent > argent) {
				this.argent -= argent;
			} else {
				throw new NegativeArgentException();
			}
		}
	}

	/**
	 * Sets in the list of all the device referenced for this <i>game</i>,
	 * the new device at the corresponding index. In fact, where the
	 * coordinates matches, because we can't find two devices with the
	 * same <i>coordinates</i> and the same <i>game</i>.
	 * 
	 * @param appareil the new device
	 */
	public List<Appareil> setAppareil(Appareil appareil) {
		// Todo : Partie.setAppareil(Appareil) method
		// Does nothing because of hibernate replacements
		return null;
	}

	/**
	 * This method returns a List of Partie, from table parties. May be
	 * expensive to invoke; if you have to use it, stock the result in a
	 * list.
	 * 
	 * @return a list of games
	 */
	public static List<Partie> query() {
		// Creating a Session and a List
		Session session = Connect_SQLite.getSession();
		List<Partie> list;
		try {
			// Query for all objects and stock it in the List created before
			Query<Partie> query = session.createQuery(
					"from Partie",
					Partie.class);
			list = query.list();
		} catch (HibernateException e) {
			System.err.println("Unable to query in table parties");
			return null;
		} finally {
			// Closing the session
			session.close();
		}
		// Returning the result
		return list;
	}

	/**
	 * Insert in table parties the object in parameters.
	 * 
	 * @param objToSave the object to save.
	 */
	public static void insert(Partie objToSave) {
		// Creating a Session and a Transaction
		Session session = Connect_SQLite.getSession();
		Transaction transaction = null;
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			session.save(objToSave);
			transaction.commit();

		} catch (HibernateException e) {
			System.err
					.println("Unable to run insert stmt in table coordonnées");
			if (transaction != null)
				transaction.rollback();
		} finally {
			// Closing the session
			session.close();
		}
	}

	@Override
	public String toString() {
		return "Object type Partie. ID : " + idPartie + ". Argent : " + argent
				+ ".";
	}
}
