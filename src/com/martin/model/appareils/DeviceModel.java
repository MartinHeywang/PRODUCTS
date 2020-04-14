package com.martin.model.appareils;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.martin.Database;
import com.martin.model.Coordinates;
import com.martin.model.Game;

/**
 * This is about <i>AppareilModel</i> class. This class represents all
 * the data of a <i>device</i>. An instance of this object is required
 * to create a <i>device</i>. He defines basic informations about the
 * created device, like the type or the coordinates and what else does
 * matter in a device that must be saved in the database. This class
 * is a persistent class (using <strong>Hibernate</strong>).
 * 
 * @see com.martin.model.appareils.Device
 *
 */
public class DeviceModel {

	/**
	 * The id in the database
	 */
	private Long idAppareilModel;

	/**
	 * An XY coordinates.
	 * 
	 * @see com.martin.model.Coordinates
	 */
	private Coordinates coordinates;
	/**
	 * The associated game
	 * 
	 * @see com.martin.model.Game
	 */
	private Game game;

	/**
	 * The type
	 * 
	 * @see com.martin.model.appareils.Type
	 */
	private Type type;
	/**
	 * The level
	 * 
	 * @see com.martin.model.appareils.Level
	 */
	private Level level;
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
	public DeviceModel() {
	}

	/**
	 * Creates a new AppareilModel with default values for type, niveau,
	 * and direction properties, respectively initializated to SOL,
	 * NIVEAU_1, and UP. Saves automatically the new object in the
	 * database.
	 * 
	 * @param coordinates the coordinates
	 * @param game      the game
	 */
	public DeviceModel(Coordinates coordinates, Game game) {
		this.coordinates = coordinates;
		this.game = game;
		this.type = Type.SOL;
		this.level = Level.NIVEAU_1;
		this.direction = Direction.UP;

		DeviceModel.insert(this);
	}

	/**
	 * Creates a new AppareilModel. Saves automatically the new object in
	 * the database.
	 * 
	 * @param coordinates the coordinates
	 * @param game      the game
	 * @param type        the type
	 * @param level      the level
	 * @param direction   the direction
	 */
	public DeviceModel(Coordinates coordinates, Game game, Type type,
			Level level, Direction direction) {
		this.coordinates = coordinates;
		this.game = game;
		this.type = type;
		this.level = level;
		this.direction = direction;

		coordinates = Coordinates.createOrQuery(coordinates);
		DeviceModel.insert(this);
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
	public Coordinates getCoordonnees() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordonnees to set
	 */
	public void setCoordonnees(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the partie
	 */
	public Game getPartie() {
		return game;
	}

	/**
	 * @param game the partie to set
	 */
	public void setPartie(Game game) {
		this.game = game;
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
	public Level getNiveau() {
		return level;
	}

	/**
	 * @param level the niveau to set
	 */
	public void setNiveau(Level level) {
		this.level = level;
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
	public static void insert(DeviceModel objToSave) {
		// Creating a Session and a Transaction
		Session session = Database.getSession();
		Transaction transaction = null;
		try {
			// Begining Transaction
			transaction = session.beginTransaction();

			// Query for the table appareils
			Query<DeviceModel> query = session.createQuery(
					"from AppareilModel",
					DeviceModel.class);
			List<DeviceModel> list = query.list();
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
