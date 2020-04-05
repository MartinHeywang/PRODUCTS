package com.martin.model;

import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;

/**
 * <h1>class Coordonnées</h1>
 * <p>
 * Object who defines x and y coordinates.<br/>
 * Mainly used by devices.
 * </p>
 *
 */
public class Coordonnees {

	private Long idCoordonnees;

	private int x, y;

	/**
	 * <h1>constructor Coordonnées</h1>
	 * <p>
	 * Creates coordinates.
	 * </p>
	 * 
	 * @param x x coordinates of the object
	 * @param y y coordinates of the object
	 */
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordonnees() {
	}

	public Long getIdCoordonnees() {
		return idCoordonnees;
	}

	/**
	 * @return the field x of this object
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the field y of this object
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param id the new id
	 */
	public void setIdCoordonnees(Long id) {
		this.idCoordonnees = id;
	}

	/**
	 * 
	 * @param x the new x value
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y the new y value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * This method checks if this <i>coordinnates</i> object is in the
	 * grid, for a given size. Really useful for <i>devices</i> when we
	 * want to know if his pointer is in grid.
	 * 
	 * @param tailleMax the current size of the grid.
	 * @return
	 *         <ul>
	 *         <li>true if the coordinate is in the grid
	 *         <li>false if the coordinate isn't in grid
	 *         </ul>
	 */
	public boolean isInGrid(int tailleMax) {
		if (x < 0 || y < 0 || x > tailleMax || y > tailleMax) {
			return false;
		}
		return true;
	}

	/**
	 * This methods checks if the given coordinates is near from this
	 * object. Two coordinates are 'near' when they have at least one side
	 * in common.
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if the coordinate is near from the other
	 *         <li>false if the coordinate isn't near from the other
	 *         </ul>
	 * @param coordonnées a coordinate object to compare
	 */
	public boolean isNearFrom(Coordonnees coordonnées) {
		if (this.x == coordonnées.getX() + 1
				|| this.x == coordonnées.getX() - 1) {
			return true;
		}
		if (this.y == coordonnées.getY() + 1
				|| this.x == coordonnées.getY() - 1) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Object type Coordonnees. ID : " + idCoordonnees + ". X : " + x
				+ ". Y : " + y + ".";
	}

	/**
	 * This method returns a List of Coordonnees, from table coordonnées.
	 * May be expensive to invoke; if you have to use it, stock the result
	 * in a list.
	 * 
	 * @return a list of coordinates
	 */
	public static List<Coordonnees> query() {
		// Creating a Session and a List
		Session session = Connect_SQLite.getSession();
		List<Coordonnees> list;
		try {
			// Query for all objects and stock it in the List created before
			Query<Coordonnees> query = session.createQuery(
					"from Coordonnees",
					Coordonnees.class);
			list = query.list();
		} catch (HibernateException e) {
			System.err.println("Unable to query in table coordonnées");
			return null;
		} finally {
			// Closing the session
			session.close();
		}
		// Returning the result
		return list;
	}

	/**
	 * Insert in table coordonnées the object in parameters. May be
	 * expensive to invoke. Checks before inserting if all constraints are
	 * respected. Because Hibernate doesn't fully support SQLite (like
	 * UniqueCombo constraints), I had to do this before inserting.
	 * 
	 * @param objToSave the object to save.
	 */
	public static void insert(Coordonnees objToSave) {
		// Creating a Session and a Transaction
		Session session = Connect_SQLite.getSession();
		Transaction transaction = null;
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			// Query for the table coordonnées
			Query<Coordonnees> query = session.createQuery(
					"from Coordonnees",
					Coordonnees.class);
			List<Coordonnees> list = query.list();
			// Using a Stream, checking if the constraints are fully respected.
			if (list.stream().filter(x -> x.getX() == objToSave.getX())
					.filter(y -> y.getY() == objToSave.getY()).count() == 0) {
				session.save(objToSave);
				transaction.commit();
			} else {
				// Little log in case a constraint would not be respected
				System.err.println(
						"Couldn't run insert : UNIQUE constraint failed (x, y)");
			}

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

	public static Coordonnees createOrQuery(Coordonnees xy) {
		// Creating a Session and a Transaction
		Session session = Connect_SQLite.getSession();
		Transaction transaction = null;
		List<Coordonnees> list = Arrays.asList();
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			// Query for the table coordonnées
			Query<Coordonnees> query = session.createQuery(
					"from Coordonnees",
					Coordonnees.class);
			list = query.list();
			// Using a Stream, checking if the constraints are fully respected.
			if (list.stream().filter(x -> x.getX() == xy.getX())
					.filter(y -> y.getY() == xy.getY()).count() == 0)
				session.save(xy);

		} catch (HibernateException e) {
			System.err
					.println(
							"Unable to run insert statement in table coordonnées");
			if (transaction != null)
				transaction.rollback();
		} finally {
			// Closing the session
			session.close();
		}
		return list.stream().filter(x -> x.getX() == xy.getX())
				.filter(y -> y.getY() == xy.getY()).findFirst().get();
	}

	public boolean equals(Coordonnees xy) {
		if (this.x == xy.getX() && this.y == xy.getY()) {
			System.out
					.println("Coordonnees.equals(Coordonnees) returned true.");
			return true;
		}
		System.out.println("Coordonnees.equals(Coordonnees) returned false.");
		return false;
	}
}
