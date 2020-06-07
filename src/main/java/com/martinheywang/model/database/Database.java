package com.martinheywang.model.database;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.DeviceModel;

/**
 * Class that manages connectivity to the Database. See {@link Saver}
 * to know more about saving object in the database. This class should
 * be used to save objects in the database, instead of creating a Dao
 * by this class, because Saver respects saving conventions.
 * 
 * @see Saver
 * @see Deleter
 * 
 * @author Martin Heywang
 *
 */
public final class Database {

	private static ConnectionSource connection;

	private Database() {
	}

	/**
	 * Sets everything up to manages database connectivity.
	 * 
	 * @throws SQLException
	 */
	private static void setUp() throws SQLException {
		try {
			connection = new JdbcConnectionSource("jdbc:sqlite:Products.db");

			DataPersisterManager
					.registerDataPersisters(ResourcePersister.getInstance());

			TableUtils.createTableIfNotExists(connection, Pack.class);
			TableUtils.createTableIfNotExists(connection, DeviceModel.class);
			TableUtils.createTableIfNotExists(connection, Coordinates.class);
			TableUtils.createTableIfNotExists(connection, Game.class);
		} catch (SQLException e) {
			System.err.println(
					"Ohh, something messed up in the initialization with the "
							+ "database...\nHere is the full message :\n\n\n");
			e.printStackTrace();
		}
	}

	/**
	 * Creates an returns a DAO for the given Class.
	 * 
	 * @param typeClass the Type of the Dao.
	 * @return a new DAO
	 * @throws SQLException if the DAO couldn't be created.
	 */
	public static <Type> Dao<Type, Long> createDao(Class<Type> typeClass)
			throws SQLException {
		if (connection == null) {
			setUp();
		}
		return DaoManager.createDao(connection, typeClass);
	}

}