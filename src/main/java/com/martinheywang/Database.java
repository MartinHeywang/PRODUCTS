package com.martinheywang;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.DeviceModel;

public final class Database {
	private static ConnectionSource connection;

	private Database() {
	}

	private static void setUp() {
		try {
			connection = new JdbcConnectionSource("jdbc:sqlite:Products.db");

			TableUtils.createTableIfNotExists(connection, DeviceModel.class);
			TableUtils.createTableIfNotExists(connection, Pack.class);
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

	public static Dao<Pack, Long> daoPacking() {
		Dao<Pack, Long> dao;
		try {
			if (connection == null)
				setUp();

			dao = DaoManager.createDao(connection, Pack.class);
			return dao;
		} catch (SQLException e) {
			System.err.println(
					"Couldn't not create a DAO for class Packing.\nHere is th full error message :\n\n\n");
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
					"Couldn't not create a DAO for class Coordinates.\nHere is th full error message :\n\n\n");
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
					"Couldn't not create a DAO for class Game.\nHere is th full error message :\n\n\n");
			e.printStackTrace();
			return null;
		}
	}

}