package com.martin;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.model.Coordonnées;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Sol;
import com.martin.model.appareils.Direction;
import com.martin.model.appareils.NiveauAppareil;

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

	private List<? extends Appareil> listAppareils;

	public Partie() {
	}

	public Partie(String nom) throws SQLException {
		this.nom = nom;
		this.lastView = LocalDateTime.now().toString();
		this.tailleGrille = 3;
		this.argent = 1250;

		Connect_SQLite.getPartieDao().createIfNotExists(this);

		for (int x = 0; x < tailleGrille; x++) {
			for (int y = 0; y < tailleGrille; y++) {
				try {
					final Appareil appareil = new Appareil_Sol(
							new Coordonnées(x, y), Direction.UP,
							NiveauAppareil.NIVEAU_1, null);

					appareil.setPartie(this);
					Connect_SQLite.getAppareilDao()
							.createIfNotExists(appareil);
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				}
			}
		}
		listAppareils = Connect_SQLite.getAppareilDao().queryBuilder().where()
				.eq("partie_idPartie", idPartie)
				.query();

		this.save();
	}

	public Partie(String nom, String lastView) throws SQLException {
		this.nom = nom;
		this.lastView = lastView;
		this.tailleGrille = 3;

		Connect_SQLite.getPartieDao().createIfNotExists(this);
		for (int x = 0; x < tailleGrille; x++) {
			for (int y = 0; y < tailleGrille; y++) {
				try {
					final Appareil appareil = new Appareil_Sol(
							new Coordonnées(x, y), Direction.UP,
							NiveauAppareil.NIVEAU_1, null);

					appareil.setPartie(this);
					Connect_SQLite.getAppareilDao()
							.createIfNotExists(appareil);
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				}
			}
		}
		listAppareils = Connect_SQLite.getAppareilDao().queryBuilder().where()
				.eq("partie_idPartie", idPartie)
				.query();

		this.save();
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
	public void save() {
		try {
			this.lastView = LocalDateTime.now().toString();

			Connect_SQLite.getPartieDao().createOrUpdate(this);
			for (int i = 0; i < this.getAppareils().size(); i++) {
				this.getAppareils().get(i).save();
			}
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

	public List<? extends Appareil> getAppareils() {
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

	public boolean setArgent(long argent, boolean increase) {
		if (increase) {
			this.argent += argent;
			return true;
		} else {
			if (this.argent > argent) {
				this.argent -= argent;
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean setAppareil(Appareil appareil, int idOldAppareil,
			boolean ignoreCost) {
		try {
			Connect_SQLite.getAppareilDao().updateId(appareil, idOldAppareil);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
