package com.martinheywang.model.devices;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.types.Type;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 * @author Martin Heywang
 */
public final class Device {

	/**
	 * The buyers locations references the location on the grid of all the
	 * buyers of the current game.
	 */
	public static List<Coordinate> buyersLocations = new ArrayList<>();

	private GameManager gameManager;

	private final ObjectProperty<Type> typeProperty;
	private final ObjectProperty<Direction> directionProperty;
	private final ObjectProperty<Level> levelProperty;
	private final ObjectProperty<Coordinate> positionProperty;
	private final ObjectProperty<Game> gameProperty;

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
	public Device(Type type, Direction direction, Level level,
			Coordinate position, Game game) {

		this.typeProperty = new SimpleObjectProperty<>(type);
		this.directionProperty = new SimpleObjectProperty<>(direction);
		this.levelProperty = new SimpleObjectProperty<>(level);
		this.positionProperty = new SimpleObjectProperty<>(position);
		this.gameProperty = new SimpleObjectProperty<>(game);
	}

	public void manageWith(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	/**
	 * Returns the valid delete price key for this device.
	 * 
	 * @return a string
	 */
	private String getDeletePriceKey() {
		return this.levelProperty.get().toString().toLowerCase() + "_delete";
	}

	/**
	 * Returns the valid upgarde price key for this device.
	 * 
	 * @return a string
	 */
	private String getUpgradePriceKey() {
		return this.levelProperty.get().getNext().toString().toLowerCase()
				+ "_build";
	}

	/**
	 * Returns the price (or gain) of deleting this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getDeletePrice() {
		final String key = getDeletePriceKey();
		return this.typeProperty.get().getPrices().getPriceFromKey(key);
	}

	/**
	 * Returns the price of upgrading this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getUpgradePrice() {
		final String key = getUpgradePriceKey();
		return this.typeProperty.get().getPrices().getPriceFromKey(key);
	}

	/**
	 * Generates a {@link Image} of this Device and returns it.
	 * 
	 * @return a view of this Device
	 */
	public ObjectProperty<Image> getView() {
		// Get the image from the type provider class
		return new SimpleObjectProperty<Image>(
				new Image(typeProperty.get().getClass()
						.getResourceAsStream("/images" +
								levelProperty.get().getURL()
								+ typeProperty.get().getURL())));
	}

	// PROPERTIES GETTERs

	/**
	 * @return the type property
	 */
	public ObjectProperty<Type> typeProperty() {
		return typeProperty;
	}

	/**
	 * @return the direction property
	 */
	public ObjectProperty<Direction> directionProperty() {
		return directionProperty;
	}

	/**
	 * @return the level property
	 */
	public ObjectProperty<Level> levelProperty() {
		return levelProperty;
	}

	/**
	 * @return the position property
	 */
	public ObjectProperty<Coordinate> positionProperty() {
		return positionProperty;
	}

	/**
	 * @return the game property
	 */
	public ObjectProperty<Game> gameProperty() {
		return gameProperty;
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
	 * @return the type of this Device object.
	 */
	public Type getType() {
		return typeProperty.get();
	}

	/**
	 * 
	 * @return the direction of this Device object.
	 */
	public Direction getDirection() {
		return directionProperty.get();
	}

	/**
	 * 
	 * @return the level of this Device object.
	 */
	public Level getLevel() {
		return levelProperty.get();
	}

	/**
	 * 
	 * @return the position of this Device object.
	 */
	public Coordinate getPosition() {
		return positionProperty.get();
	}

	/**
	 * 
	 * @return the game of this Device object.
	 */
	public Game getGame() {
		return gameProperty.get();
	}

	/**
	 * @return the value of the active property
	 */
	public boolean isActive() {
		return activeProperty.get();
	}

	// SETTERs

	/**
	 * @param newType the new type
	 */
	public void setType(Type newType) {
		typeProperty.set(newType);
	}

	/**
	 * @param newDirection the new direction
	 */
	public void setDirection(Direction newDirection) {
		directionProperty.set(newDirection);
	}

	/**
	 * @param newLevel the new level
	 */
	public void setLevel(Level newLevel) {
		levelProperty.set(newLevel);
	}

	/**
	 * @param newPosition the new position
	 */
	public void setPosition(Coordinate newPosition) {
		positionProperty.set(newPosition);
	}

	/**
	 * @param newGame the new game
	 */
	public void setGame(Game newGame) {
		gameProperty.set(newGame);
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