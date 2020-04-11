package com.martin.model.appareils;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnees;
import com.martin.model.Partie;

/**
 * This is about <i>AppareilModel</i> class. This class represents all
 * the data of a <i>device</i>. An instance of this object is required
 * to create a <i>device</i>. He defines basic informations about the
 * created device, like the type or the coordinates and what else does
 * matter in a device that must be saved in the database. This class
 * is a persistent class (using <strong>Hibernate</strong>).
 * 
 * @see com.martin.model.appareils.Appareil
 *
 */
public class AppareilModel {

	/**
	 * The id in the database
	 */
	private Long idAppareilModel;

	/**
	 * An XY coordinates.
	 * 
	 * @see com.martin.model.Coordonnees
	 */
	private Coordonnees coordonnees;
	/**
	 * The associated game
	 * 
	 * @see com.martin.model.Partie
	 */
	private Partie partie;

	/**
	 * The type
	 * 
	 * @see com.martin.model.appareils.Type
	 */
	private Type type;
	/**
	 * The level
	 * 
	 * @see com.martin.model.appareils.Niveau
	 */
	private Niveau niveau;
	/**
	 * The direction (or orientation)
	 * 
	 * @see com.martin.model.appareils.Direction
	 */
	private Direction direction;

	/**
	 * Creates a new AppareilModel object WITHOUT INITIALIZING ANYTHING.
	 * Use the others constructors to build this object properly. Used by
	 * Hibernate to fetch the data from the database.
	 */
	public AppareilModel() {
	}

	/**
	 * Creates a new AppareilModel with default values for type, niveau,
	 * and direction properties, respectively initializated to SOL,
	 * NIVEAU_1, and UP. Saves automatically the new object in the
	 * database.
	 * 
	 * @param coordonnees the coordinates
	 * @param partie      the game
	 */
	public AppareilModel(Coordonnees coordonnees, Partie partie) {
		this.coordonnees = coordonnees;
		this.partie = partie;
		this.type = Type.SOL;
		this.niveau = Niveau.NIVEAU_1;
		this.direction = Direction.UP;

		AppareilModel.insert(this);
	}

	/**
	 * Creates a new AppareilModel. Saves automatically the new object in
	 * the database.
	 * 
	 * @param coordonnees the coordinates
	 * @param partie      the game
	 * @param type        the type
	 * @param niveau      the level
	 * @param direction   the direction
	 */
	public AppareilModel(Coordonnees coordonnees, Partie partie, Type type,
			Niveau niveau, Direction direction) {
		this.coordonnees = coordonnees;
		this.partie = partie;
		this.type = type;
		this.niveau = niveau;
		this.direction = direction;

		coordonnees = Coordonnees.createOrQuery(coordonnees);
		AppareilModel.insert(this);
	}

	/**
	 * @return the idAppareilModel
	 */
	public Long getIdAppareilModel() {
		return idAppareilModel;
	}

	/**
	 * @param idAppareilModel the idAppareilModel to set
	 */
	public void setIdAppareilModel(Long idAppareilModel) {
		this.idAppareilModel = idAppareilModel;
	}

	/**
	 * @return the coordonnees
	 */
	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	/**
	 * @param coordonnees the coordonnees to set
	 */
	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	/**
	 * @return the partie
	 */
	public Partie getPartie() {
		return partie;
	}

	/**
	 * @param partie the partie to set
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the niveau
	 */
	public Niveau getNiveau() {
		return niveau;
	}

	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Insert in table coordonnées the object in parameters. May be
	 * expensive to invoke. Checks before inserting if all constraints are
	 * respected. Because Hibernate doesn't fully support SQLite (like
	 * UniqueCombo constraints), I had to do this before inserting.
	 * 
	 * @param objToSave the object to save.
	 */
	public static void insert(AppareilModel objToSave) {
		// Creating a Session and a Transaction
		Session session = Connect_SQLite.getSession();
		Transaction transaction = null;
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			// Query for the table appareils
			Query<AppareilModel> query = session.createQuery(
					"from AppareilModel",
					AppareilModel.class);
			List<AppareilModel> list = query.list();
			// Using a Stream, checking if the constraints are fully respected.
			if (list.stream().filter(
					x -> x.getCoordonnees().equals(objToSave.getCoordonnees()))
					.filter(y -> y.getPartie().equals(objToSave.getPartie()))
					.count() == 0) {
				session.save(objToSave);
				transaction.commit();
			} else {
				// Little log in case a constraint would not be respected
				System.err.println(
						"Couldn't run insert : UNIQUE constraint failed (coordonnees, partie)");
			}

		} catch (HibernateException e) {
			System.err
					.println(
							"Unable to run insert statement in table appareils");
			if (transaction != null)
				transaction.rollback();
		} finally {
			// Closing the session
			session.close();
		}
	}

}
