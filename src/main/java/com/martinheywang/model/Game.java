package com.martinheywang.model;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Floor;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;
import com.martinheywang.toolbox.MoneyFormat;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * <p>
 * The Game class represents a Game in PRODUCTS. In contains all the
 * data about them. This object may be stored in the database.
 * </p>
 * <p>
 * This class is using JavaFX Properties, you can at any time observe
 * one of them and apply changes in consequence.
 * </p>
 * <p>
 * It's near of a Java Bean:
 * <ul>
 * <li>It has a default constructor</li>
 * <li>It has a getter and setter for <em>almost</em> all properties
 * and values</li>
 * </ul>
 * </p>
 * <p>
 * It implements Displayable, that means that you can get at any time
 * a display (as a {@link javafx.scene.Node}) of it.
 * </p>
 * 
 * @author Martin Heywang
 */
public class Game implements Displayable<Game> {

	/**
	 * The ID of this game in the database. May be null.
	 */
	private Long gameId;

	/**
	 * The accesible name (shown in the UI) of this game
	 */
	private StringProperty nameProperty;

	/**
	 * As a Game can be saved in the database, we register the time of the
	 * last saving session.
	 */
	private ObjectProperty<LocalDateTime> lastSaveProperty;

	/**
	 * The money amount that "owns" this Game.
	 */
	private ObjectProperty<BigInteger> moneyProperty;

	/**
	 * The grid size of this game.
	 */
	private IntegerProperty gridSizeProperty;

	/**
	 * The grow of this game.
	 */
	private ObjectProperty<BigInteger> growProperty;

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
		this.nameProperty = new SimpleStringProperty(nom);
		this.gridSizeProperty = new SimpleIntegerProperty(3);
		this.moneyProperty = new SimpleObjectProperty<>(new BigInteger("1250"));
		this.growProperty = new SimpleObjectProperty<>(new BigInteger("0"));
	}

	/**
	 * Refreshes the list of devices model and return it then.
	 * 
	 * @return a list of all devices of this game
	 */
	public List<DeviceModel> getDevicesModel() {
		List<DeviceModel> devicesModel = new ArrayList<>();

		// Todo: load available devices
		for (int x = 0; x < getGridSize(); x++) {
			for (int y = 0; y < getGridSize(); y++) {
				devicesModel.add(new DeviceModel(Floor.class, Level.LEVEL_1,
						Direction.UP, this, new Coordinate(x, y)));
			}
		}
		return devicesModel;
	}

	/**
	 * May be null.
	 * 
	 * @return the id of this object in the database.
	 */
	public Long getID() {
		return gameId;
	}

	// PROPERTIES GETTER

	/**
	 * @return the property name of this Game object.
	 */
	public StringProperty nameProperty() {
		return nameProperty;
	}

	/**
	 * @return the property lastSave of this Game object.
	 */
	public ObjectProperty<LocalDateTime> lastSaveProperty() {
		return lastSaveProperty;
	}

	/**
	 * @return the property money of this Game object.
	 */
	public ObjectProperty<BigInteger> moneyProperty() {
		return moneyProperty;
	}

	/**
	 * @return the property gridSize of this Game object.
	 */
	public IntegerProperty gridSizeProperty() {
		return gridSizeProperty;
	}

	/**
	 * @return the property grow of this object.
	 */
	public ObjectProperty<BigInteger> growProperty() {
		return growProperty;
	}

	// GETTERS

	/**
	 * Returns the accesible name of this Game object.
	 * 
	 * @return the name of this game
	 */
	public String getName() {
		return nameProperty.get();
	}

	/**
	 * The timing of the last save of this Game object.
	 * 
	 * @return the date & time of the last save of this object (as
	 *         LocalDateTime, also known as JodaTime)
	 */
	public LocalDateTime getLastSave() {
		return lastSaveProperty.get();
	}

	/**
	 * Returns how money this game has.
	 * 
	 * @return the money amount
	 */
	public BigInteger getMoney() {
		return moneyProperty.get();
	}

	/**
	 * Returns the size of the grid.
	 * 
	 * @return the grid-size
	 */
	public Integer getGridSize() {
		return gridSizeProperty.get();
	}

	/**
	 * Returns how many this game generate each iterations of the game
	 * loop.
	 * 
	 * @return the grow
	 */
	public BigInteger getGrow() {
		return growProperty.get();
	}

	// SETTERS

	/**
	 * Sets the new accesible name of this Game object.
	 * 
	 * @param newName the new name
	 */
	public void setName(String newName) {
		nameProperty.set(newName);
	}

	/**
	 * Sets the new amount of money of this Game object
	 * 
	 * @param money the new money-amount
	 */
	public void setMoney(BigInteger money) {
		moneyProperty.set(money);
	}

	/**
	 * Sets the grow property
	 * 
	 * @param grow the new grow value
	 */
	public void setGrow(BigInteger grow) {
		growProperty.set(grow);
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
}
