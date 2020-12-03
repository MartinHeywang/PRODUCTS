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
package io.github.martinheywang.products.api.model.device;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.SerializableType;
import com.j256.ormlite.table.DatabaseTable;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.level.Level;

/**
 * <p>
 * The DeviceModel class is a persistable class that stores all the data about a
 * Device.
 * </p>
 * <p>
 * This class is able to instantiate a device from all its fields. To do a such
 * thing, call the {@link #instantiate()} method.
 * </p>
 * <p>
 * This way should be the only one, because you could break the game and create
 * a Device from a DeviceModel that register a different class. (:sad:)
 * </p>
 * 
 * @author Heywang
 */
@DatabaseTable
public class DeviceModel {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "class", persisterClass = SerializableType.class)
    private Class<? extends Device> clazz;

    @DatabaseField
    private Level level;
    @DatabaseField
    private Direction direction;
    @DatabaseField(foreign = true, foreignColumnName = "id", uniqueCombo = true)
    private Game game;
    @DatabaseField(foreign = true, foreignColumnName = "id", foreignAutoCreate = true, uniqueCombo = true)
    private Coordinate position;

    /**
     * Creates an empty device model.
     */
    public DeviceModel() {
    }

    /**
     * @param clazz     the class of the device
     * @param level     the level
     * @param direction the direction
     * @param game      the game
     * @param position  the position
     */
    public DeviceModel(Class<? extends Device> clazz, Level level, Direction direction, Game game,
            Coordinate position) {
        this.clazz = clazz;
        this.level = level;
        this.direction = direction;
        this.game = game;
        this.position = position;
    }

    /**
     * Instantiates this device model. Creates the associated device.
     * 
     * @return a device created from this model.
     */
    public Device instantiate() {
        try {
            final Device device = this.clazz.getConstructor(DeviceModel.class).newInstance(this);
            return device;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // GETTERs

    /**
     * @return the id, if it has got one.
     */
    public Long getID() {
        return this.id;
    }

    /**
     * Generates and retuns and id that corresponds to this object. P lease note
     * that this device model must have a persistent game and a persistent
     * coordinate in order to generate this id. This methods doesn't set the id, you
     * have to do it manually.
     * 
     * @return the generated id
     */
    public Long generateID() {
        return this.game.getID() * 10_000 + this.position.getID();
    }

    /**
     * 
     * @return the type of this Device object.
     */
    public Class<? extends Device> getType() {
        return this.clazz;
    }

    /**
     * 
     * @return the direction of this Device object.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * 
     * @return the level of this Device object.
     */
    public Level getLevel() {
        return this.level;
    }

    /**
     * 
     * @return the position of this Device object.
     */
    public Coordinate getPosition() {
        return this.position;
    }

    /**
     * 
     * @return the game of this Device object.
     */
    public Game getGame() {
        return this.game;
    }

    // SETTERs

    /**
     * 
     * @param id the new id
     */
    public void setID(Long id) {
        this.id = id;
    }

    /**
     * @param clazz the new type
     */
    public void setType(Class<? extends Device> clazz) {
        this.clazz = clazz;
    }

    /**
     * @param newDirection the new direction
     */
    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    /**
     * @param newLevel the new level
     */
    public void setLevel(Level newLevel) {
        this.level = newLevel;
    }

    /**
     * 
     * @param game the new game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * 
     * @param position the new position
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * @return the system path to the image file corresponding to the view of this
     *         model.
     * 
     * @deprecated use
     *             {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever#getView(Class)}
     *             (static) instead. Give as argument the result of {@link #getType()}.
     */
    @Deprecated
    public String getURL() {
        final String url = "/images" + this.getLevel().getURL() + this.getType().getSimpleName() + ".svg";
        return url;
    }

    /**
     * 
     * 
     * @return the system path to the image file corresponding to the upgraded view
     *         (level+1) of this model.
     */
    public String getUpgradedURL() {
        final String url = "/images" + this.getLevel().getNext().getURL() + this.getType().getSimpleName() + ".svg";
        return url;
    }

    @Override
    public String toString() {
        return "{" + this.clazz.getSimpleName() + ", " + this.level + ", " + this.direction + ", " + this.game + ", "
                + this.position + "}";
    }

    /**
     * Returns true if the values in the fields (except the id) are the same in this
     * DeviceModel object and the given one.
     * 
     * @param other the DeviceModel to compare
     * @return true if the properties of the compared models are the same
     */
    public boolean propertiesEquals(DeviceModel other) {
        if (this.clazz == other.clazz && this.direction == other.direction && this.level == other.level
                && this.game == other.game && this.position.propertiesEquals(other.position))
            return true;
        return false;
    }

}
