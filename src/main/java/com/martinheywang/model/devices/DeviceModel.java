package com.martinheywang.model.devices;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.types.BaseTypes;
import com.martinheywang.model.types.Type;

@DatabaseTable(tableName = "devicesModels")
public class DeviceModel implements Cloneable {

	/**
	 * The id in the database
	 */
	@DatabaseField(generatedId = true, columnName = "id")
	private Long idAppareilModel;

	/**
	 * An XY coordinates.
	 * 
	 * @see com.Coordinate.model.Coordinates
	 */
	@DatabaseField(columnName = "coordonnees", foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private Coordinate coordinates;

	/**
	 * The associated game
	 * 
	 * @see com.martin.model.Game
	 */
	@DatabaseField(columnName = "game", foreign = true, foreignColumnName = "id", uniqueCombo = true)
	private Game game;

	/**
	 * The type
	 * 
	 * @see com.martinheywang.model.types.BaseTypes.model.appareils.Type
	 */
	@DatabaseField
	private Type type;

	/**
	 * The level
	 * 
	 * @see com.martinheywang.model.level.martin.model.appareils.Level
	 */
	@DatabaseField
	private Level level;

	/**
	 * The direction (or orientation)
	 * 
	 * @see com.martin.model.appareils.Direction
	 */
	@DatabaseField
	private Direction direction;

	/**
	 * Creates a new AppareilModel object WITHOUT INITIALIZING ANYTHING.
	 * Use the others constructors to build this object properly. Used by
	 * ORMLite to fetch the data from the database.
	 */
	public DeviceModel() {
	}

	/**
	 * Creates a new AppareilModel with default values for type, niveau,
	 * and direction properties, respectively initializated to SOL,
	 * NIVEAU_1, and UP.
	 * 
	 * @param coordinates the coordinates
	 * @param game        the game
	 */
	public DeviceModel(Coordinate coordinates, Game game) {
		this.coordinates = coordinates;
		this.game = game;
		this.type = BaseTypes.FLOOR;
		this.level = Level.LEVEL_1;
		this.direction = Direction.UP;
	}

	/**
	 * Creates a new DeviceModel.
	 * 
	 * @param coordinates the coordinates
	 * @param game        the game
	 * @param type        the type
	 * @param level       the level
	 * @param direction   the direction
	 */
	public DeviceModel(Coordinate coordinates, Game game, Type type,
			Level level, Direction direction) {
		this.coordinates = coordinates;
		this.game = game;
		this.type = type;
		this.level = level;
		this.direction = direction;
	}

	/**
	 * @return the idAppareilModel
	 */
	public Long getID() {
		return idAppareilModel;
	}

	/**
	 * Sets the id
	 * 
	 * @param id the new id
	 */
	public void setID(Long id) {
		this.idAppareilModel = id;
	}

	/**
	 * @return the coordonnees
	 */
	public Coordinate getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordonnees to set
	 */
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the partie
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the partie to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	public Type typeProperty() {
		return type;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the niveau
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the niveau to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public DeviceModel clone() {
		return this.clone();
	}

}
