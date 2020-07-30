package com.martinheywang.model.devices;

import java.io.Serializable;

import com.martinheywang.model.ClassToID;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The DeviceModel class is a persistable class that stores all the
 * data about a Device.
 * 
 * @author Heywang
 */
public class DeviceModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570229030947566969L;

	private Long id;

	private StringProperty classIdProperty;
	private ObjectProperty<Level> levelProperty;
	private ObjectProperty<Direction> directionProperty;
	private ReadOnlyObjectProperty<Game> gameProperty;
	private ReadOnlyObjectProperty<Coordinate> positionProperty;

	public DeviceModel() {
	}

	public DeviceModel(Class<? extends Device> clazz, Level level,
			Direction direction, Game game, Coordinate position) {
		classIdProperty = new SimpleStringProperty(ClassToID.classToID(clazz));
		levelProperty = new SimpleObjectProperty<>(level);
		directionProperty = new SimpleObjectProperty<>(direction);
		gameProperty = new SimpleObjectProperty<>(game);
		positionProperty = new SimpleObjectProperty<>(position);
	}

	public Device instantiate() {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Device> clazz = (Class<? extends Device>) ClassToID
					.IDToClass(classIdProperty.get());
			final Device device = clazz
					.getConstructor(DeviceModel.class)
					.newInstance(this);
			return device;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// PROPERTIES GETTERs

	/**
	 * @return the type property
	 */
	public StringProperty classIdProperty() {
		return classIdProperty;
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
	public ReadOnlyObjectProperty<Coordinate> positionProperty() {
		return positionProperty;
	}

	/**
	 * @return the game property
	 */
	public ReadOnlyObjectProperty<Game> gameProperty() {
		return gameProperty;
	}

	// GETTERs

	public Long getID() {
		return id;
	}

	/**
	 * 
	 * @return the type of this Device object.
	 */
	public Class<? extends Device> getType() {
		@SuppressWarnings("unchecked")
		Class<? extends Device> clazz = (Class<? extends Device>) ClassToID
				.IDToClass(classIdProperty.get());
		return clazz;
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

	// SETTERs

	/**
	 * @param newType the new type
	 */
	public void setType(Class<? extends Device> clazz) {
		classIdProperty.set(ClassToID.classToID(clazz));
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

}
