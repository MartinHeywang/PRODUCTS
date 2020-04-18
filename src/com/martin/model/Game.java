package com.martin.model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.Database;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.DeviceModel;
import com.martin.model.exceptions.MoneyException;

@DatabaseTable(tableName = "games")
public class Game {

	@DatabaseField(columnName = "id", generatedId = true)
	private Long idPartie;

	@DatabaseField
	private String name;

	@DatabaseField
	private String lastView;

	@DatabaseField(columnName = "money")
	private long argent;

	@DatabaseField
	private int gridSize;

	private List<DeviceModel> listAppareilsModel;

	public Game() {
	}

	/**
	 * Creates a new <i>game</i>. Saves it directly in the database.
	 * 
	 * @param nom the name of the new game
	 * @throws SQLException if this object can't be registered in the
	 *                      database
	 */
	public Game(String nom) throws SQLException {
		this.name = nom;
		this.lastView = LocalDateTime.now().toString();
		this.gridSize = 5;
		this.argent = 1250;

		Database.daoGame().createIfNotExists(this);
	}

	/**
	 * Saves this object int the database, with all its devices
	 * 
	 * @see Database#getAppareilDao()
	 * @see Database#getPartieDao()
	 */
	public void save(List<Device> listAppareils) {
		// Todo : save
		// Does actually nothing because of ORMLite replacements
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
	public List<DeviceModel> getAppareilsModel() {
		try {
			listAppareilsModel = Database.daoDeviceModel().queryBuilder()
					.where()
					.eq("game", idPartie)
					.query();
		} catch (SQLException e) {
			System.err.println("Couldn't load the devices for the game : "
					+ this.toString());
			e.printStackTrace();
		}
		return listAppareilsModel;
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
		return name;
	}

	/**
	 * 
	 * @return the date of the latest save
	 */
	public LocalDateTime getLastView() {
		return LocalDateTime.parse(lastView);
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
		return gridSize;
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
		this.name = newName;
	}

	/**
	 * 
	 * @param lastView a parsable string to LocalDateTime object
	 */
	public void setLastView(String lastView) {
		this.lastView = lastView;
	}

	/**
	 * 
	 * @param lastView a parsable string to LocalDateTime object
	 */
	public void setLastView(LocalDateTime lastView) {
		this.lastView = lastView.toString();
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
		this.gridSize = tailleGrille;
	}

	/**
	 * Adds or substract money to the total.
	 * 
	 * @param argent   How many money should me added or substracted.
	 * @param increase If the money should be added.
	 * @throws MoneyException if not enough money is available.
	 */
	public void setArgent(long argent, boolean increase)
			throws MoneyException {
		if (increase) {
			this.argent += argent;
		} else {
			if (this.argent > argent) {
				this.argent -= argent;
			} else {
				throw new MoneyException();
			}
		}
	}
}
