/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.database;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.device.DeviceModel;

/**
 * <p>
 * Class that manages connectivity to the database.
 * </p>
 * <p>
 * To create a {@link com.j256.ormlite.dao.Dao} connection to a table, 
 * use {@link #createDao(Class)} with a persistent class as an argument.
 * </p>
 * 
 * @author Martin Heywang
 */
public final class Database {

    private static ConnectionSource connection;

    private Database() {
    }

    /**
     * Sets up everything in order.
     * 
     * @throws SQLException
     */
    private static void setUp() throws SQLException {
	try {
	    connection = new JdbcConnectionSource("jdbc:sqlite:Products.db");

	    TableUtils.createTableIfNotExists(connection, Game.class);
	    TableUtils.createTableIfNotExists(connection, Pack.class);
	    TableUtils.createTableIfNotExists(connection, DeviceModel.class);
	    TableUtils.createTableIfNotExists(connection, Coordinate.class);

	} catch (final SQLException e) {
	    System.err.println(
		    "Ohh, something messed up in the initialization with the "
			    + "database...\nHere is the full message :\n\n\n");
	    e.printStackTrace();
	}
    }

    /**
     * Creates a fully-functionnal {@link com.j256.ormlite.dao.Dao} 
     * based on the given <strong>persistent</strong> class.
     * 
     * @param typeClass the persistent class
     * @param <Type> a persistent class
     * @return a functionnal dao.
     * @throws SQLException if an error with the database occured.
     */
    public static <Type> Dao<Type, Long> createDao(Class<Type> typeClass) throws SQLException {
	if (connection == null)
	    setUp();
	return DaoManager.createDao(connection, typeClass);
    }

}