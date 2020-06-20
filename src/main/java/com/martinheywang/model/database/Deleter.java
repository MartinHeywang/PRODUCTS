package com.martinheywang.model.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.DeviceModel;

/**
 * Class that manages all about deleting stuff in the database using
 * rules.
 * 
 * @author Heywang
 * 
 * @see Database
 */
public final class Deleter {

	private Deleter() {

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
	public final static void deleteGame(Game game) throws SQLException {
		final Dao<Game, Long> dao = Database.createDao(Game.class);
		dao.delete(game);

		if (cascadeActivated) {
			final List<DeviceModel> models = game.getDevicesModel();
			for (DeviceModel model : models) {
				deleteDeviceModel(model);
			}
		}
	}

	/**
	 * Saves (creates or updates) the given model in the database, and its
	 * associated game.
	 * 
	 * @param model the model to save
	 * @throws SQLException if an error occurs when saving the
	 *                      object/creating the DAO.
	 */
	public final static void deleteDeviceModel(DeviceModel model)
			throws SQLException {
		final Dao<DeviceModel, Long> dao = Database
				.createDao(DeviceModel.class);
		dao.delete(model);

		final Pack pack = Database.createDao(Pack.class)
				.queryForId(model.getID());
		if (pack != null) {
			deletePack(pack);
		}
	}

	/**
	 * Saves (creates or updates) the given pack in the database. It saves
	 * then its associated device's model, and the game.
	 * 
	 * @param pack
	 * @throws SQLException
	 */
	public final static void deletePack(Pack pack) throws SQLException {
		final Dao<Pack, Long> dao = Database.createDao(Pack.class);
		dao.delete(pack);
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
