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
package io.github.martinheywang.products.api.model;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * <p>
 * The Game class represents a Game in PRODUCTS. In contains all the data about
 * them. This object may be stored in the database.
 * </p>
 * <p>
 * This class is using JavaFX Properties, you can at any time observe one of
 * them and apply changes in consequence.
 * </p>
 * <p>
 * It's near of a Java Bean:
 * </p>
 * <ul>
 * <li>It has a default constructor</li>
 * <li>It has a getter and setter for <em>almost</em> all properties and
 * values</li>
 * </ul>
 * 
 * @author Martin Heywang
 */
@DatabaseTable(tableName = "game")
public class Game {

    /**
     * The ID of this game in the database. May be null.
     */
    @DatabaseField(columnName = "id", generatedId = true)
    private Long id;

    /**
     * The accesible name (shown in the UI) of this game
     */
    @DatabaseField
    private String name;

    /**
     * As a Game can be saved in the database, we register the time of the last
     * saving session.
     */
    @DatabaseField
    private String lastSave;

    /**
     * The money amount that "owns" this Game.
     */
    @DatabaseField
    private BigInteger money;

    /**
     * The grid size of this game.
     */
    @DatabaseField
    private Integer gridSize;

    /**
     * The grow of this game.
     */
    @DatabaseField
    private BigInteger grow;

    /**
     * The delay b/w each iterations of the game loop.
     */
    @DatabaseField(columnName = "delay")
    private Integer gameLoopDelay;

    /**
     * The maximum buildable buyers.
     */
    @DatabaseField
    private Integer maxIndependent;

    /**
     * Creates an empty Game;
     */
    public Game() {
    }

    /**
     * Creates a new <i>game</i>. Saves it directly in the database.
     * 
     * @param name the name of the new game
     * @throws SQLException if this object can't be registered in the database
     */
    public Game(String name) throws SQLException {
        this.name = name;
        this.gridSize = 3;
        this.money = new BigInteger("1250");
        this.grow = new BigInteger("0");
        this.gameLoopDelay = 1000;
        this.maxIndependent = 4;
        this.lastSave = LocalDateTime.now().toString();
    }

    // PROPERTIES GETTER

    /**
     * May be null.
     * 
     * @return the id of this object in the database.
     */
    public Long getID() {
        return this.id;
    }

    /**
     * Returns the accesible name of this Game object.
     * 
     * @return the name of this game
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the size of the grid.
     * 
     * @return the grid-size
     */
    public Integer getGridSize() {
        return this.gridSize;
    }

    /**
     * Returns how money this game has.
     * 
     * @return the money amount
     */
    public BigInteger getMoney() {
        return this.money;
    }

    // PROPERTIES GETTER

    /**
     * Returns how many this game generate each iterations of the game loop.
     * 
     * @return the grow
     */
    public BigInteger getGrow() {
        return this.grow;
    }

    /**
     * 
     * @return the game loop delay
     */
    public Integer getDelay() {
        return this.gameLoopDelay;
    }

    /**
     * The timing of the last save of this Game object.
     * 
     * @return the date and time of the last save of this object (as LocalDateTime,
     *         also known as JodaTime)
     */
    public LocalDateTime getLastSave() {
        return LocalDateTime.parse(this.lastSave);
    }

    /**
     * Returns the max number of buyer that the game can have at the same time on
     * the grid.
     * 
     * @return the max buyer amount
     * @deprecated use {@link #getMaxIndependent()} instead, changed name
     */
    @Deprecated
    public Integer getMaxBuyer() {
        return this.maxIndependent;
    }

    /**
     * Returns the max number of independent devices that the game can have at the
     * same time on the grid.
     * 
     * @return the max buyer amount
     */
    public Integer getMaxIndependent() {
        return this.maxIndependent;
    }

    /**
     * Sets the new accesible name of this Game object.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the new amount of money of this Game object
     * 
     * @param money the new money-amount
     */
    public void setMoney(BigInteger money) {
        this.money = money;
    }

    /**
     * Sets the last save to now. Use carefully.
     */
    public void updateLastSave() {
        this.lastSave = LocalDateTime.now().toString();
    }

    /**
     * Sets the grow property
     * 
     * @param grow the new grow value
     */
    public void setGrow(BigInteger grow) {
        this.grow = grow;
    }

    /**
     * Sets the delay property
     * 
     * @param delay the new delay value
     */
    public void setDelay(int delay) {
        this.gameLoopDelay = delay;
    }

    /**
     * Sets the new amount of max buyers.
     * 
     * @param newValue the new value
     * @deprecated use {@link #setMaxIndependent(int)} instead, changed name.
     */
    public void setMaxBuyer(int newValue) {
        this.maxIndependent = newValue;
    }

    /**
     * Sets the new amount of max independent devices.
     * 
     * @param newValue the new value
     */
    public void setMaxIndependent(int newValue) {
        this.maxIndependent = newValue;
    }

    @Override
    public String toString() {
        return "{" + this.name + ", " + this.money + "€ }";
    }

    /**
     * Increases the current grid size by one.
     */
    public void upgradeGrid() {
        this.gridSize++;
    }
}
