package com.martinheywang.model;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Bean representing a Game.<br>
 * Stored in the database in the <code>games</code> table. It also
 * contains a {@link List} of {@link DeviceModel} that you can get by
 * calling {@link Game#getDevicesModel()}.
 * 
 * 
 * @author Martin Heywang
 */
@DatabaseTable(tableName = "games")
public class Game implements Displayable<Game> {

	@DatabaseField(columnName = "id", generatedId = true)
	private Long gameId;

	@DatabaseField
	private String name;

	@DatabaseField
	private String lastSave;

	@DatabaseField(columnName = "money")
	private long money;

	@DatabaseField
	private int gridSize;

	private List<DeviceModel> devicesModel = new ArrayList<DeviceModel>();

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
		this.gridSize = 3;
		this.money = 1250;

		save();
	}

	/**
	 * Saves this object int the database, with all its devices.
	 * 
	 * @see Database
	 * @throws SQLException if the saving process fails.
	 */
	public void save() throws SQLException {
		this.lastSave = LocalDateTime.now().toString();
		Database.daoGame().createOrUpdate(this);

		for (DeviceModel model : devicesModel) {
			Database.daoDeviceModel().createOrUpdate(model);
		}
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
				.where().eq("partie", gameId).query();
		Database.daoDeviceModel().delete(list);
	}

	/**
	 * Refreshes the list of devices model and return it then.
	 * 
	 * @return a list of all devices of this game
	 */
	public List<DeviceModel> getDevicesModel() {
		return devicesModel;
	}

	/**
	 * Refreshes the devices model list.
	 */
	public void refreshDevicesModel() {
		try {
			devicesModel = Database.daoDeviceModel().queryBuilder()
					.where()
					.eq("game", gameId)
					.query();
		} catch (SQLException e) {
			System.err.println("Couldn't refresh the list : "
					+ this.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Adds money to the game.
	 * 
	 * @param amount how many
	 * @throws MoneyException if the amount after the transaction is under
	 *                        0.
	 */
	public void addMoney(long amount) throws MoneyException {
		this.money += amount;
	}

	/**
	 * Removes money to the game
	 * 
	 * @param amount how many
	 * @throws MoneyException if there aren't enough money to remove.
	 */
	public void removeMoney(long amount) throws MoneyException {
		if (money < amount) {
			throw new MoneyException();
		} else {
			this.money -= amount;
		}
	}

	/**
	 * Sets the money to the game. Should be used only if you want to
	 * reset the money. If it is not the case, prefer using
	 * {@link Game#addMoney(long)} or {@link Game#removeMoney(long)}.
	 * 
	 * @param amount the new amount
	 * @throws MoneyException if the given amoung is less than 0
	 */
	public void setMoney(long amount) throws MoneyException {
		if (amount < 0) {
			throw new MoneyException();
		} else {
			this.money = amount;
		}
	}

	/**
	 * Replaces a {@link DeviceModel} in the list of this game.
	 * 
	 * @param deviceModel
	 * @throws MoneyException
	 */
	public void setDeviceModel(DeviceModel deviceModel)
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
		nom.setText(this.getName());
		nom.setWrapText(true);
		root.setTop(nom);

		// Changing the LocalDateTime of the game in a String representation
		// Instead of using toString(), i ues this method to have my perso
		// String
		String instant = (this.getLastSave().getDayOfMonth() < 10)
				? "0" + this.getLastSave().getDayOfMonth() + "/"
				: this.getLastSave().getDayOfMonth() + "/";
		instant += (this.getLastSave().getMonthValue() < 10)
				? "0" + this.getLastSave().getMonthValue() + "/"
				: this.getLastSave().getMonthValue() + "/";
		instant += (this.getLastSave().getYear() < 10)
				? "0" + this.getLastSave().getYear() + " "
				: this.getLastSave().getYear() + " ";

		instant += (this.getLastSave().getHour() < 10)
				? "0" + this.getLastSave().getHour() + ":"
				: this.getLastSave().getHour() + ":";
		instant += (this.getLastSave().getMinute() < 10)
				? "0" + this.getLastSave().getMinute() + ":"
				: this.getLastSave().getMinute() + ":";
		instant += (this.getLastSave().getSecond() < 10)
				? "0" + this.getLastSave().getSecond()
				: this.getLastSave().getSecond();

		Label infos = new Label();
		infos.setText("Dernière sauvegarde : " + instant
				+ "\nArgent en compte : "
				+ NumberFormat.getInstance(Locale.getDefault())
						.format(this.getMoney())
				+ " €");
		root.setLeft(infos);

		root.setPadding(new Insets(3, 3, 3, 3));
		return new Displayer<Game>(root, this);
	}

	/**
	 * 
	 * @return the id of this game
	 */
	public Long getGameId() {
		return gameId;
	}

	/**
	 * 
	 * @return the name of the game
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the date of the latest save
	 */
	public LocalDateTime getLastSave() {
		return LocalDateTime.parse(lastSave);
	}

	/**
	 * 
	 * @return how many money the game counts
	 */
	public long getMoney() {
		return money;
	}

	/**
	 * 
	 * @return the grid-size
	 */
	public int getGridSize() {
		return gridSize;
	}

	/**
	 * 
	 * @param newName the new name
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * 
	 * @param lastView a parsable string to LocalDateTime object
	 */
	public void setLastSave(LocalDateTime lastView) {
		this.lastSave = lastView.toString();
	}

	/**
	 * 
	 * @param gridSize the new grid-size
	 */
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
}
