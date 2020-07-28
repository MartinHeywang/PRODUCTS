package com.martinheywang.model.devices;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.templates.Template;
import com.martinheywang.model.templates.TemplateModel;
import com.martinheywang.model.types.info.PricesModule;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 * @author Martin Heywang
 */
public abstract class Device {

	/**
	 * The buyers locations references the location on the grid of all the
	 * buyers of the current game.
	 */
	public static List<Coordinate> buyersLocations = new ArrayList<>();

	// The model is the persistent data of the device
	protected final DeviceModel model;

	// The next is created each time we load the game
	protected GameManager gameManager;
	protected Template template;

	private BooleanProperty activeProperty = new SimpleBooleanProperty(false);

	/**
	 * Creates a new Device.
	 * 
	 * @param type      the type
	 * @param direction the direction
	 * @param level     the level
	 * @param position  the position
	 * @param game      the game
	 */
	Device(DeviceModel model) {
		this.model = model;
	}

	/**
	 * Defines the GameManager that this Device object will give to its
	 * behaviour to perform actions.
	 * 
	 * @param gameManager
	 */
	public void manageWith(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public abstract void act(Pack resources) throws MoneyException;

	/**
	 * Returns the accesible name (shown in the UI) of this device.
	 * 
	 * @return the name
	 */
	public abstract String getAccesibleName();

	/**
	 * Returns the description of this device (shown in the UI).
	 * 
	 * @return the description
	 */
	public abstract String getDescription();

	/**
	 * Returns a Prices modules that defines the prices for each state of
	 * this device type.
	 * 
	 * @return the prices
	 */
	public abstract PricesModule getPrices();

	/**
	 * Returns as a BigInteger the cost of this device when it performs it
	 * actions properly.
	 * 
	 * @return the action cost
	 */
	public abstract BigInteger getActionCost();

	/**
	 * Returns the template mode used by this device.
	 * 
	 * @return the template model
	 */
	public abstract TemplateModel getTemplateModel();

	/**
	 * Returns a <code>javafx.scene.image.Image</code> that corresponds to
	 * a view of this device.
	 * 
	 * @return the view
	 */
	public abstract Image getView();

	public abstract boolean isBuildable();

	/**
	 * Returns the valid delete price key for this device.
	 * 
	 * @return a string
	 */
	private String getDeletePriceKey() {
		return this.levelProperty().get().toString().toLowerCase() + "_delete";
	}

	/**
	 * Returns the valid upgarde price key for this device.
	 * 
	 * @return a string
	 */
	private String getUpgradePriceKey() {
		return this.levelProperty().get().getNext().toString().toLowerCase()
				+ "_build";
	}

	/**
	 * Returns the price (or gain) of deleting this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getDeletePrice() {
		final String key = getDeletePriceKey();
		return this.getPrices().getPriceFromKey(key);
	}

	/**
	 * Returns the price of upgrading this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getUpgradePrice() {
		final String key = getUpgradePriceKey();
		return this.getPrices().getPriceFromKey(key);
	}

	// PROPERTIES GETTERs

	/**
	 * @return the direction property
	 */
	public ObjectProperty<Direction> directionProperty() {
		return model.directionProperty();
	}

	/**
	 * @return the level property
	 */
	public ObjectProperty<Level> levelProperty() {
		return model.levelProperty();
	}

	/**
	 * @return the position property
	 */
	public ReadOnlyObjectProperty<Coordinate> positionProperty() {
		return model.positionProperty();
	}

	/**
	 * @return the game property
	 */
	public ReadOnlyObjectProperty<Game> gameProperty() {
		return model.gameProperty();
	}

	/**
	 * @return the active property
	 */
	public BooleanProperty activeProperty() {
		return activeProperty;
	}

	// GETTERs

	/**
	 * 
	 * @return the direction of this Device object.
	 */
	public Direction getDirection() {
		return model.getDirection();
	}

	/**
	 * 
	 * @return the level of this Device object.
	 */
	public Level getLevel() {
		return model.getLevel();
	}

	/**
	 * 
	 * @return the position of this Device object.
	 */
	public Coordinate getPosition() {
		return model.getPosition();
	}

	/**
	 * 
	 * @return the game of this Device object.
	 */
	public Game getGame() {
		return model.getGame();
	}

	/**
	 * @return the value of the active property
	 */
	public boolean isActive() {
		return activeProperty.get();
	}

	// SETTERs

	/**
	 * @param newDirection the new direction
	 */
	public void setDirection(Direction newDirection) {
		model.setDirection(newDirection);
	}

	/**
	 * @param newLevel the new level
	 */
	public void setLevel(Level newLevel) {
		model.setLevel(newLevel);
	}

	/**
	 * Sets the value of the property isActive
	 * 
	 * @param active the new active value
	 */
	public void setActive(boolean active) {
		activeProperty.set(active);
	}

}