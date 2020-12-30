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

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.properties.SimpleBigIntegerProperty;
import io.github.martinheywang.products.api.model.properties.SimpleDateTimeProperty;
import io.github.martinheywang.products.api.persistent.Persistable;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>
 * Sort of Java FX Beans that represents a game in the software.
 * </p>
 * <p>
 * In PRODUCTS, the player can create games : this is the base class for it. It
 * contains a lot of informations about a game :
 * </p>
 * <ul>
 * <li>The name</li>
 * <li>The date of last save</li>
 * <li>The amount of money</li>
 * <li>The grid size</li>
 * <li>The grow amount (evolution of the money amount per second)</li>
 * <li>The delay applied to the game loop</li>
 * <li>The independent max count</li>
 * </ul>
 * <p>
 * Those are all informations that doesn't depends directly on other objects.
 * Even if the grid size depends on the count of devices, we doesn't need to
 * have the device to retrieve this kind of information.
 * </p>
 * <p>
 * This class is persistent.
 * </p>
 * 
 * @author Martin Heywang
 */
@DatabaseTable(tableName = "game")
public final class Game implements Persistable {

    @DatabaseField(columnName = "id", generatedId = true)
    private Long id;
    @DatabaseField
    private StringProperty name;
    @DatabaseField
    private SimpleDateTimeProperty lastSave;
    @DatabaseField
    private SimpleBigIntegerProperty money;

    @ForeignCollectionField
    private Collection<DeviceModel> models;

    @DatabaseField
    private IntegerProperty gridSize;
    @DatabaseField
    private SimpleBigIntegerProperty grow;
    @DatabaseField
    private IntegerProperty delay;
    @DatabaseField
    private IntegerProperty maxIndependent;

    /**
     * Creates an empty Game.
     */
    public Game() {
    }

    /**
     * Creates a new <i>game</i>.
     * 
     * @param name the name of the new game
     */
    public Game(String name) {
        this.name = new SimpleStringProperty(name);
        this.gridSize = new SimpleIntegerProperty(3);
        this.money = new SimpleBigIntegerProperty(new BigInteger("1250"));
        this.grow = new SimpleBigIntegerProperty(new BigInteger("0"));
        this.delay = new SimpleIntegerProperty(1000);
        this.maxIndependent = new SimpleIntegerProperty(4);
        this.lastSave = new SimpleDateTimeProperty(LocalDateTime.now());
        this.models = new ArrayList<>();
    }

    /**
     * The name of the game is the accessible name. This is the one defined by the
     * user, when creating it. It shows in the user interface.
     * 
     * @return the name property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * By 'last save' should we understand 'the date and time of the last save'.
     * This value is updated whenever the game is saved in the database.
     * 
     * @return the last save property
     */
    public SimpleDateTimeProperty lastSaveProperty() {
        return lastSave;
    }

    /**
     * The money is the amount of money that the player currently have.
     * 
     * @return the money property
     */
    public SimpleBigIntegerProperty moneyProperty() {
        return money;
    }

    /**
     * The grid size property is a value that contains the size of the grid of the
     * game. The grid is a square, so this is single value.
     * 
     * @return the grid size property
     */
    public IntegerProperty gridSizeProperty() {
        return gridSize;
    }

    /**
     * The grow property is a value that stores the evolution per second of the
     * money amount.
     * 
     * @return the grow property
     */
    public SimpleBigIntegerProperty growProperty() {
        return grow;
    }

    /**
     * The delay is the period that the game loop waits at the end of each
     * iteration.
     * 
     * @return the delay property
     */
    public IntegerProperty delayProperty() {
        return delay;
    }

    /**
     * The max independent is a value that limits the player from building two many
     * independents devices. It may evolve whenever the player do some research.
     * 
     * @return the max independent property
     */
    public IntegerProperty maxIndependentProperty() {
        return maxIndependent;
    }

    @Override
    public Long getID(){
        return id;
    }

    @Override
    public void setID(Long id){
        this.id = id;
    }

    /**
     * Returns the value of the {@link #nameProperty()}.
     * 
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the value of the {@link #lastSaveProperty()}.
     * 
     * @return the last save
     */
    public LocalDateTime getLastSave() {
        return lastSave.get();
    }

    /**
     * Returns the value of the {@link #moneyProperty()}.
     * 
     * @return the money
     */
    public BigInteger getMoney() {
        return money.get();
    }

    public Collection<DeviceModel> getModels(){
        return models;
    }

    /**
     * Returns the value of the {@link #gridSizeProperty()}.
     * 
     * @return the grid size
     */
    public Integer getGridSize() {
        return gridSize.get();
    }

    /**
     * Returns the value of the {@link #growProperty()}.
     * 
     * @return the grow
     */
    public BigInteger getGrow() {
        return grow.get();
    }

    /**
     * Returns the value of the {@link #delayProperty()}.
     * 
     * @return the delay
     */
    public Integer getDelay() {
        return delay.get();
    }

    /**
     * Returns the value of the {@link #maxIndependentProperty()}.
     * 
     * @return the max independent
     */
    public Integer getMaxIndependent() {
        return maxIndependent.get();
    }

    /**
     * Sets the value of the {@link #nameProperty()}.
     * 
     * @param newName the new name
     */
    public void setName(String newName) {
        name.set(newName);
    }

    /**
     * Sets the value of the {@link #lastSaveProperty()}.
     * 
     * @param newLastSave the new lastSave
     */
    public void setLastSave(LocalDateTime newLastSave) {
        lastSave.set(newLastSave);
    }

    /**
     * Sets the value of the {@link #moneyProperty()}.
     * 
     * @param newMoney the new money
     */
    public void setMoney(BigInteger newMoney) {
        money.set(newMoney);
    }

    /**
     * Sets the value of the {@link #gridSizeProperty()}.
     * 
     * @param newGridSize the new gridSize
     */
    public void setGridSize(Integer newGridSize) {
        gridSize.set(newGridSize);
    }

    /**
     * Sets the value of the {@link #growProperty()}.
     * 
     * @param newGrow the new grow
     */
    public void setGrow(BigInteger newGrow) {
        grow.set(newGrow);
    }

    /**
     * Sets the value of the {@link #delayProperty()}.
     * 
     * @param newDelay the new delay
     */
    public void setDelay(Integer newDelay) {
        delay.set(newDelay);
    }

    /**
     * Sets the value of the {@link #maxIndependentProperty()}.
     * 
     * @param newMaxIndependent the new maxIndependent
     */
    public void setMaxIndependent(Integer newMaxIndependent) {
        maxIndependent.set(newMaxIndependent);
    }
}
