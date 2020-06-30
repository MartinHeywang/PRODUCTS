package com.martinheywang.model.database;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.DeviceModel;

/**
 * Class that manages all about creating, and updating stuff in the
 * database.
 * 
 * @author Heywang
 * 
 * @see Database
 */
public final class Saver {

	private Saver() {

	}

	/**
	 * Defines whenever the cascade mode is activated or not.
	 */
	private static boolean cascadeActivated = true;

	/**
	 * Saves (creates or updates) the given game in the database.
	 * 
	 * @param game the game to save
	 * @throws SQLException if an error occurs when saving the
	 *                      object/creating the DAO.
	 */
	public final static void saveGame(Game game) throws SQLException {
		final Dao<Game, ?> dao = Database.createDao(Game.class);
		dao.createOrUpdate(game);
	}

	/**
	 * Saves (creates or updates) the given model in the database, and its
	 * associated game.
	 * 
	 * @param model the model to save
	 * @throws SQLException if an error occurs when saving the
	 *                      object/creating the DAO.
	 */
	public final static void saveDeviceModel(DeviceModel model)
			throws SQLException {
		final Dao<DeviceModel, ?> dao = Database.createDao(DeviceModel.class);
		dao.createOrUpdate(model);
		saveCoordinate(model.getCoordinates());

		if (cascadeActivated) {
			final Game associatedGame = model.getGame();
			saveGame(associatedGame);
		}
	}

	public final static <T> void replace(Class<T> table, T oldData, T newData)
			throws SQLException {
		final Dao<T, Long> dao = Database.createDao(table);
		dao.delete(oldData);
		dao.create(newData);
	}

	/**
	 * Saves (creates or updates) the given coords quietly.
	 * 
	 * @param coords the coords to save.
	 */
	public final static void saveCoordinate(Coordinate coords) {
		try {
			final Dao<Coordinate, Long> dao = Database
					.createDao(Coordinate.class);
			dao.createOrUpdate(coords);
		} catch (SQLException e) {
			// In most cases, it would be a constraint violation, so I don't
			// want to print the exception.
		}
	}

	/**
	 * Saves (creates or updates) the given pack in the database. It saves
	 * then its associated device's model, and the game.
	 * 
	 * @param pack
	 * @throws SQLException
	 */
	public final static void savePack(Pack pack) throws SQLException {
		final Dao<Pack, ?> dao = Database.createDao(Pack.class);
		dao.createOrUpdate(pack);

		if (cascadeActivated) {
			final Game associatedGame = pack.getDeviceModel().getGame();
			saveGame(associatedGame);
		}
	}

	/**
	 * Defines whenever the methods of this class practices cascades.<br>
	 * It means that when an object is saved, it saves the object and its
	 * parents.<br>
	 * <strong>Exemple</strong>: a model has an associated game. When
	 * cascade is activated, both model and game are saved. If it is not
	 * activated, only the model will be saved. The same stands for packs
	 * and games.
	 * 
	 * @param cascade the new value
	 */
	public final static void setCascadeActivated(boolean cascade) {
		cascadeActivated = cascade;
	}
}