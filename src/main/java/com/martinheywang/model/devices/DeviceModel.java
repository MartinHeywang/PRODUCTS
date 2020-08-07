package com.martinheywang.model.devices;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.SerializableType;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;

/**
 * <p>
 * The DeviceModel class is a persistable class that stores all the
 * data about a Device.
 * </p>
 * <p>
 * This class is able to instantiate a device from all its fields. To
 * do a such thing, call the {@link #instantiate()} method.
 * </p>
 * <p>
 * This way should be the only one, because you could break the game
 * and create a Device from a DeviceModel that register a different
 * class. (:sad:)
 * </p>
 * 
 * @author Heywang
 */
@DatabaseTable
public class DeviceModel {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = "class", persisterClass = SerializableType.class)
    private Class<? extends Device> clazz;

    @DatabaseField
    private Level level;
    @DatabaseField
    private Direction direction;
    @DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
    private Game game;
    @DatabaseField(foreign = true, foreignColumnName = "id", foreignAutoCreate = true, uniqueCombo = true)
    private Coordinate position;

    public DeviceModel() {
    }

    /**
     * @param clazz     the class of the device
     * @param level     the level
     * @param direction the direction
     * @param game      the game
     * @param position  the position
     */
    public DeviceModel(Class<? extends Device> clazz, Level level,
	    Direction direction, Game game, Coordinate position) {
	this.clazz = clazz;
	this.level = level;
	this.direction = direction;
	this.game = game;

	// Get the position from the database and compare it if it already exists.
	try {
	    // 'X' and 'Y' columns are marked as a unique combo
	    this.position = Database.createDao(Coordinate.class).queryBuilder().where().eq("x", position.getX()).and()
		    .eq("y", position.getY()).queryForFirst();
	    // But in case it doesn't exists, assign the one given as arg.
	    if (this.position == null)
		this.position = position;
	} catch (final SQLException e) {
	    e.printStackTrace();
	}
    }

    public Device instantiate() {
	try {
	    final Device device = this.clazz
		    .getConstructor(DeviceModel.class)
		    .newInstance(this);
	    return device;
	} catch (final Exception e) {
	    e.printStackTrace();
	}
	return null;
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
	return clazz;
    }

    /**
     * 
     * @return the direction of this Device object.
     */
    public Direction getDirection() {
	return direction;
    }

    /**
     * 
     * @return the level of this Device object.
     */
    public Level getLevel() {
	return level;
    }

    /**
     * 
     * @return the position of this Device object.
     */
    public Coordinate getPosition() {
	return position;
    }

    /**
     * 
     * @return the game of this Device object.
     */
    public Game getGame() {
	return game;
    }

    // SETTERs

    /**
     * @param newType the new type
     */
    public void setType(Class<? extends Device> clazz) {
	this.clazz = clazz;
    }

    /**
     * @param newDirection the new direction
     */
    public void setDirection(Direction newDirection) {
	this.direction = newDirection;
    }

    /**
     * @param newLevel the new level
     */
    public void setLevel(Level newLevel) {
	this.level = newLevel;
    }

    /**
     * 
     * @param game the new game
     */
    public void setGame(Game game) {
	this.game = game;
    }

    /**
     * 
     * @param position the new position
     */
    public void setPosition(Coordinate position) {
	this.position = position;
    }

    @Override
    public String toString() {
	return "{" + clazz.getSimpleName() + ", " + level + ", " + direction + ", " + game + ", " + position + ", ";
    }

}
