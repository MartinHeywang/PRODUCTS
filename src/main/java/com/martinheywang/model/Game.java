package com.martinheywang.model;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.Database;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

@DatabaseTable(tableName = "games")
public class Game implements Displayable {

	@DatabaseField(columnName = "id", generatedId = true)
	private Long idGame;

	@DatabaseField
	private String name;

	@DatabaseField
	private String lastSave;

	@DatabaseField(columnName = "money")
	private long argent;

	@DatabaseField
	private int gridSize;

	private List<DeviceModel> devicesModel;

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
		this.lastSave = LocalDateTime.now().toString();
		this.gridSize = 3;
		this.argent = 1250;

		Database.daoGame().createIfNotExists(this);
	}

	/**
	 * Saves this object int the database, with all its devices.
	 * 
	 * @see Database
	 * @throws SQLException if the saving process fails.
	 */
	public void save() throws SQLException {
		this.lastSave = LocalDateTime.now().toString();
		Database.daoGame().update(this);
	}

	/**
	 * This method deletes this game and all its devices in the database.
	 * After invoking this method, all changes won't be saved. This object
	 * won't be useful.
	 * 
	 * @throws SQLException if the deletion process fails.
	 */
	public void delete() throws SQLException {
		Database.daoGame().delete(this);
		List<DeviceModel> list = Database.daoDeviceModel().queryBuilder()
				.where().eq("partie", idGame).query();
		Database.daoDeviceModel().delete(list);
	}

	/**
	 * 
	 * @return a list of all devices of this game
	 */
	public List<DeviceModel> getAppareilsModel() {
		try {
			devicesModel = Database.daoDeviceModel().queryBuilder()
					.where()
					.eq("game", idGame)
					.query();
		} catch (SQLException e) {
			System.err.println("Couldn't load the devices for the game : "
					+ this.toString());
			e.printStackTrace();
		}
		return devicesModel;
	}

	/**
	 * Adds or substract money to the total.
	 * 
	 * @param argent   How many money should me added or substracted.
	 * @param increase If the money should be added (true) or subtracted
	 *                 (false).
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

	public void setAppareil(DeviceModel deviceModel)
			throws MoneyException {
		for (int i = 0; i < devicesModel.size(); i++) {
			if (devicesModel.get(i).getCoordinates()
					.propertiesEquals(deviceModel.getCoordinates())) {
				devicesModel.set(i, deviceModel);
				break;
			}
		}
	}

	@Override
	public Displayer<Game> getDisplayer() {
		BorderPane root = new BorderPane();

		Label nom = new Label();
		nom.setUnderline(true);
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(this.getNom());
		nom.setWrapText(true);
		root.setTop(nom);

		// Changing the LocalDateTime of the game in a String representation
		// Instead of using toString(), i ues this method to have my perso
		// String
		String instant = (this.getLastView().getDayOfMonth() < 10)
				? "0" + this.getLastView().getDayOfMonth() + "/"
				: this.getLastView().getDayOfMonth() + "/";
		instant += (this.getLastView().getMonthValue() < 10)
				? "0" + this.getLastView().getMonthValue() + "/"
				: this.getLastView().getMonthValue() + "/";
		instant += (this.getLastView().getYear() < 10)
				? "0" + this.getLastView().getYear() + " "
				: this.getLastView().getYear() + " ";

		instant += (this.getLastView().getHour() < 10)
				? "0" + this.getLastView().getHour() + ":"
				: this.getLastView().getHour() + ":";
		instant += (this.getLastView().getMinute() < 10)
				? "0" + this.getLastView().getMinute() + ":"
				: this.getLastView().getMinute() + ":";
		instant += (this.getLastView().getSecond() < 10)
				? "0" + this.getLastView().getSecond()
				: this.getLastView().getSecond();

		Label infos = new Label();
		infos.setText("Dernière sauvegarde : " + instant
				+ "\nArgent en compte : "
				+ NumberFormat.getInstance(Locale.getDefault())
						.format(this.getArgent())
				+ " €");
		root.setLeft(infos);

		root.setPadding(new Insets(3, 3, 3, 3));
		return new Displayer<Game>(root, this);
	}

	/**
	 * 
	 * @return the id of this game
	 */
	public Long getIdPartie() {
		return idGame;
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
		return LocalDateTime.parse(lastSave);
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
		this.idGame = id;
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
		this.lastSave = lastView;
	}

	/**
	 * 
	 * @param lastView a parsable string to LocalDateTime object
	 */
	public void setLastView(LocalDateTime lastView) {
		this.lastSave = lastView.toString();
	}

	/**
	 * 
	 * @param tailleGrille the new grid-size
	 */
	public void setTailleGrille(int tailleGrille) {
		this.gridSize = tailleGrille;
	}
}
