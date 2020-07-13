package com.martinheywang.model;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.database.Deleter;
import com.martinheywang.model.database.Saver;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.toolbox.MoneyFormat;
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
	private BigInteger money;

	@DatabaseField
	private int gridSize;

	@DatabaseField(columnName = "grow")
	private BigInteger growPerSecond;

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
		this.money = BigInteger.valueOf(1250);
		this.growPerSecond = BigInteger.ZERO;

		save();
	}

	/**
	 * Saves this object int the database, with all its devices.
	 * 
	 * @see Database
	 * @throws SQLException if the saving process fails.
	 */
	public void save() throws SQLException {
		lastSave = LocalDateTime.now().toString();
		Saver.saveGame(this);
	}

	/**
	 * This method deletes this game and all its devices in the database.
	 * After invoking this method, all changes won't be saved. This object
	 * won't be useful.
	 * 
	 * @throws SQLException if the deletion process fails.
	 */
	public void delete() throws SQLException {
		Deleter.deleteGame(this);
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
			devicesModel = Database.createDao(DeviceModel.class).queryBuilder()
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
	public void addMoney(BigInteger amount) throws MoneyException {
		removeMoney(amount.negate());
	}

	/**
	 * Removes money to the game
	 * 
	 * @param amount how many
	 * @throws MoneyException if there aren't enough money to remove.
	 */
	public void removeMoney(BigInteger amount) throws MoneyException {
		if (money.compareTo(amount) == -1) {
			throw new MoneyException();
		} else {
			this.money = this.money.subtract(amount);
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
	public void setMoney(BigInteger amount) throws MoneyException {
		if (amount.compareTo(new BigInteger("0")) == -1) {
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

		final DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm");

		Label infos = new Label();
		infos.setText(
				"Dernière sauvegarde : " + this.getLastSave().format(formatter)
						+ "\nArgent en compte : "
						+ MoneyFormat.getSingleton().format(this.getMoney()));
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
	public BigInteger getMoney() {
		return money;
	}

	/**
	 * @return how much the money evolved between the last second
	 */
	public BigInteger getGrowPerSecond() {
		return growPerSecond;
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

	public void setGrowPerSecond(BigInteger difference) {
		this.growPerSecond = difference;
	}
}
