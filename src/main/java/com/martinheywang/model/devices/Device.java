package com.martinheywang.model.devices;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.ClassToID;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.ActionCost;
import com.martinheywang.model.devices.annotations.DefaultTemplate;
import com.martinheywang.model.devices.annotations.Description;
import com.martinheywang.model.devices.annotations.Prices;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.templates.Template;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 * @author Martin Heywang
 */
@AccessibleName
@Description
@Prices(build = "0", upgradeTo2 = "0", upgradeTo3 = "0", destroyAt1 = "0", destroyAt2 = "0", destroyAt3 = "0")
@DefaultTemplate
@ActionCost
public abstract class Device {

	/**
	 * The buyers locations references the location on the grid of all the
	 * buyers of the current game.
	 */
	public static List<Coordinate> buyersLocations = new ArrayList<>();
	public static List<Class<? extends Device>> subclasses = new ArrayList<>();

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

	/**
	 * Do whatever the device is meant to do when it is called by the
	 * previous device in the assembly line. In most cases, you should
	 * call the next device to keep the idea of an assembly line.
	 * 
	 * @param resources the resource given by the previous device
	 * @throws MoneyException whenever the game doesn't have enough money
	 *                        (don't forget to check that)
	 */
	public abstract void act(Pack resources) throws MoneyException;

	/**
	 * Parses the value of the @ActionCost annotation into a BigInteger,
	 * or return 5 if such an annotation isn't present.
	 * 
	 * @return the action cost
	 */
	protected BigInteger getActionCost() {
		if (this.getClass().isAnnotationPresent(ActionCost.class)) {
			return new BigInteger(
					getClass().getAnnotation(ActionCost.class).value());
		}
		return new BigInteger("5");
	}

	protected PricesModule getPrices() {
		if (this.getClass().isAnnotationPresent(Prices.class)) {
			Prices annotation = getClass().getAnnotation(Prices.class);
			return new PricesModule(annotation.build(),
					annotation.upgradeTo2(), annotation.upgradeTo3(),
					annotation.destroyAt1(), annotation.destroyAt2(),
					annotation.destroyAt3());
		}
		return new PricesModule("0", "0", "0", "0", "0", "0");
	}

	public Image getView() {
		final String url = "/images/devices_level_1/"
				+ getClass().getSimpleName().toUpperCase()
				+ ".png";
		return new Image(url);
	}

	/*
	 * The following methods (about keys) creates and returns kays to find
	 * the appropriate prices in the #getPrices().
	 */
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
	 * @return the model (persistent data)
	 */
	public DeviceModel getModel() {
		return model;
	}

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

	public void setType(Class<? extends Device> clazz) {
		gameManager.build(clazz, getPosition());
	}

	/**
	 * Turns the device in the next direction
	 */
	public void turn() {
		gameManager.turn(getPosition());
	}

	/**
	 * Upgrades the device to next level, if one exists
	 */
	public void upgrade() {
		gameManager.upgrade(getPosition());
	}

	/**
	 * Sets the value of the property isActive
	 * 
	 * @param active the new active value
	 */
	public void setActive(boolean active) {
		activeProperty.set(active);
	}

	public static void registerType(Class<? extends Device> clazz) {
		if (!subclasses.contains(clazz)) {
			subclasses.add(clazz);
			ClassToID.register(clazz);
		}
	}

}