package com.martin;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.model.Coordonnées;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Sol;
import com.martin.model.appareils.Direction;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;

@DatabaseTable(tableName = "parties")
public class Partie {

	@DatabaseField(generatedId = true, unique = true, columnName = "idPartie")
	private int idPartie;

	@DatabaseField(canBeNull = false)
	private String nom;

	@DatabaseField(canBeNull = false)
	private String lastView;

	@DatabaseField(canBeNull = false, defaultValue = "1250")
	private long argent;

	@DatabaseField(canBeNull = false, defaultValue = "3")
	private int tailleGrille;

	private List<Appareil> listAppareils = Arrays.asList();

	public Partie() {
	}

	public Partie(String nom) throws SQLException {
		this.nom = nom;
		this.lastView = LocalDateTime.now().toString();
		this.tailleGrille = 20;
		this.argent = 1250;

		Connect_SQLite.getPartieDao().create(this);

		for (int x = 0; x < tailleGrille; x++) {
			for (int y = 0; y < tailleGrille; y++) {
				try {
					final Appareil appareil = new Appareil_Sol(
							new Coordonnées(x, y), Direction.UP,
							NiveauAppareil.NIVEAU_1, null);

					appareil.setPartie(this);

					Connect_SQLite.getAppareilDao().create(appareil);
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				}
			}
		}
		listAppareils = Connect_SQLite.getAppareilDao().queryBuilder()
				.where().eq("partie_idPartie", idPartie)
				.query();

		this.save(listAppareils);
	}

	public Partie(String nom, String lastView) throws SQLException {
		this.nom = nom;
		this.lastView = lastView;
		this.tailleGrille = 3;

		Connect_SQLite.getPartieDao().create(this);
		for (int x = 0; x < tailleGrille; x++) {
			for (int y = 0; y < tailleGrille; y++) {
				try {
					final Appareil appareil = new Appareil_Sol(
							new Coordonnées(x, y), Direction.UP,
							NiveauAppareil.NIVEAU_1, null);

					appareil.setPartie(this);

					Connect_SQLite.getAppareilDao().create(appareil);
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				}
			}
		}
		listAppareils = Connect_SQLite.getAppareilDao().queryBuilder()
				.where().eq("partie_idPartie", idPartie)
				.query();

		this.save(listAppareils);
	}

	/**
	 * Renames this object
	 * 
	 * @param newName the new name
	 */
	public void rename(String newName) {
		this.nom = newName;
	}

	/**
	 * Saves this object int the database, with all its devices
	 * 
	 * @see Connect_SQLite#getAppareilDao()
	 * @see Connect_SQLite#getPartieDao()
	 */
	public void save(List<Appareil> listAppareils) {
		try {
			this.lastView = LocalDateTime.now().toString();

			Connect_SQLite.getPartieDao().update(this);
			// Todo : save devices
		} catch (SQLException e) {
			System.err.println("La partie n'a pas pu être sauvegardée.");

		}
	}

	/**
	 * This methods is meant to destroy this game in the database. Method
	 * is still to do. Actually it does nothing.
	 */
	public void delete() {
		// Todo : delete method
	}

	public int getID() {
		return idPartie;
	}

	public String getNom() {
		return nom;
	}

	public long getArgent() {
		return argent;
	}

	public int getTailleGrille() {
		return tailleGrille;
	}

	public List<Appareil> getAppareils() {
		try {
			listAppareils = Connect_SQLite.getAppareilDao().queryBuilder()
					.where().eq("partie_idPartie", idPartie)
					.query();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return listAppareils;
	}

	public LocalDateTime getLastView() {
		return LocalDateTime.parse(lastView);
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
	 * same coordinates and the same <i>game</i>.
	 * 
	 * @param appareil the new device
	 */
	public void setAppareil(Appareil appareil) {
		for (Appareil app : listAppareils) {
			if (app.getXy().getX() == appareil.getXy().getX()
					&& app.getXy().getY() == appareil.getXy().getY()) {
				listAppareils.set(listAppareils.indexOf(app), appareil);
			}
		}
	}

	/**
	 * Iterrates over the list of devices and checks if a device with the
	 * corresponding coordinates can be found.
	 * 
	 * @param xy the coordinates to compare
	 * @return a device from the list with the corresponding coordinate.
	 * @throws NullPointerException if no devices can be found
	 */
	public Appareil getAppareil(Coordonnées xy) throws NullPointerException {
		for (Appareil appareil : listAppareils) {
			if (appareil.getXy().getX() == xy.getX()
					&& appareil.getXy().getY() == xy.getY()) {
				return appareil;
			}
		}
		return null;
	}
}
