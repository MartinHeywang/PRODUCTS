package com.martin;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.martin.model.Coordinates;
import com.martin.model.Game;
import com.martin.model.Packing;
import com.martin.model.appareils.DeviceModel;

public final class Database {
	private static ConnectionSource connection;

	private Database() {
	}

	private static void setUp() {
		try {
			connection = new JdbcConnectionSource("jdbc:sqlite:Products.db");

			TableUtils.createTableIfNotExists(connection, DeviceModel.class);
			TableUtils.createTableIfNotExists(connection, Packing.class);
			TableUtils.createTableIfNotExists(connection, Coordinates.class);
			TableUtils.createTableIfNotExists(connection, Game.class);
		} catch (SQLException e) {
			System.err.println(
					"Ohh, something messed up in the initialization with the "
							+ "database...\nHere is the full message :\n\n\n");
			e.printStackTrace();
		}
	}

	public static Dao<DeviceModel, Long> daoDeviceModel() {
		Dao<DeviceModel, Long> dao;
		try {
			if (connection == null)
				setUp();

			dao = DaoManager.createDao(connection, DeviceModel.class);
			return dao;
		} catch (SQLException e) {
			System.err.println(
					"Couldn't not create a DAO for class DeviceModel.\nHere is th full error message :\n\n\n");
			e.printStackTrace();
			return null;
		}
	}

	public static Dao<Packing, Long> daoPacking() {
		Dao<Packing, Long> dao;
		try {
			if (connection == null)
				setUp();

			dao = DaoManager.createDao(connection, Packing.class);
			return dao;
		} catch (SQLException e) {
			System.err.println(
					"Couldn't not create a DAO for class DeviceModel.\nHere is th full error message :\n\n\n");
			e.printStackTrace();
			return null;
		}
	}

	public static Dao<Coordinates, Long> daoCoordinates() {
		Dao<Coordinates, Long> dao;
		try {
			if (connection == null)
				setUp();

			dao = DaoManager.createDao(connection, Coordinates.class);
			return dao;
		} catch (SQLException e) {
			System.err.println(
					"Couldn't not create a DAO for class DeviceModel.\nHere is th full error message :\n\n\n");
			e.printStackTrace();
			return null;
		}
	}

	public static Dao<Game, Long> daoGame() {
		Dao<Game, Long> dao;
		try {
			if (connection == null)
				setUp();

			dao = DaoManager.createDao(connection, Game.class);
			return dao;
		} catch (SQLException e) {
			System.err.println(
					"Couldn't not create a DAO for class DeviceModel.\nHere is th full error message :\n\n\n");
			e.printStackTrace();
			return null;
		}
	}

}