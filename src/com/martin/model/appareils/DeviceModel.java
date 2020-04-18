package com.martin.model.appareils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.model.Coordinates;
import com.martin.model.Game;

@DatabaseTable(tableName = "devicesModels")
public class DeviceModel {

	/**
	 * The id in the database
	 */
	@DatabaseField(generatedId = true, columnName = "id")
	private Long idAppareilModel;

	/**
	 * An XY coordinates.
	 * 
	 * @see com.martin.model.Coordinates
	 */
	@DatabaseField(columnName = "coordonnees", foreign = true, foreignAutoCreate = true, foreignColumnName = "id", uniqueCombo = true)
	private Coordinates coordinates;

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
	 * @see com.martin.model.appareils.Type
	 */
	@DatabaseField
	private Type type;

	/**
	 * The level
	 * 
	 * @see com.martin.model.appareils.Level
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
	 * Hibernate to fetch the data from the database.
	 */
	public DeviceModel() {
	}

	/**
	 * Creates a new AppareilModel with default values for type, niveau,
	 * and direction properties, respectively initializated to SOL,
	 * NIVEAU_1, and UP. Saves automatically the new object in the
	 * database.
	 * 
	 * @param coordinates the coordinates
	 * @param game        the game
	 */
	public DeviceModel(Coordinates coordinates, Game game) {
		this.coordinates = coordinates;
		this.game = game;
		this.type = Type.SOL;
		this.level = Level.NIVEAU_1;
		this.direction = Direction.UP;
	}

	/**
	 * Creates a new AppareilModel. Saves automatically the new object in
	 * the database.
	 * 
	 * @param coordinates the coordinates
	 * @param game        the game
	 * @param type        the type
	 * @param level       the level
	 * @param direction   the direction
	 */
	public DeviceModel(Coordinates coordinates, Game game, Type type,
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
	public Long getIdAppareilModel() {
		return idAppareilModel;
	}

	/**
	 * @param idAppareilModel the idAppareilModel to set
	 */
	public void setIdAppareilModel(Long idAppareilModel) {
		this.idAppareilModel = idAppareilModel;
	}

	/**
	 * @return the coordonnees
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordonnees to set
	 */
	public void setCoordinates(Coordinates coordinates) {
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
	public Level getNiveau() {
		return level;
	}

	/**
	 * @param level the niveau to set
	 */
	public void setNiveau(Level level) {
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

}
