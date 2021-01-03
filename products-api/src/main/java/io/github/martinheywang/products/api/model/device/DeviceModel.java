/*
   Copyright 2021 Martin Heywang

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

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.Research;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.properties.SimpleClassProperty;
import io.github.martinheywang.products.api.model.properties.SimpleDirectionProperty;
import io.github.martinheywang.products.api.persistance.Persistable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <p>
 * A DeviceModel is tha part of a device that is persistable. It contains
 * informations like the level, the direction... - all the necessary to recreate
 * a real {@link Device device} -.
 * </p>
 * <p>
 * Some properties are specific to the device model class :
 * </p>
 * <h4>The type</h4>
 * <p>
 * The type of the device model is simply the {@link java.lang.Class} extending
 * {@link Devive} that defines the type, including what it does. When creating a
 * Device from this device model, a constructor of this class will be called.²
 * </p>
 * <h4>The packs</h4>
 * <p>
 * The {@link io.github.martinheywang.products.api.model.Pack} list is used when
 * generating the real device. The device type may register a pack in order to
 * get it the next time it instantiates. For example, the buyer register a pack
 * as its distributed resource, in order to fecth it back the next time the
 * player goes on the party.
 * </p>
 * <em>More informations in the {@link Device Device class}.</em>
 * 
 * @author Heywang
 */
public final class DeviceModel implements Persistable {

    private Long id;
    private SimpleClassProperty clazz;
    private SimpleDirectionProperty direction;
    private Game game;
    private Coordinate position;
    private Collection<Pack> packs;
    private Collection<Research> researches;

    DeviceModel() {
    }

    /**
     * Creates a new DeviceModel.
     * 
     * @param clazz     the class of the device
     * @param level     the level
     * @param direction the direction
     * @param game      the game
     * @param position  the position
     */
    public DeviceModel(Class<? extends Device> clazz, Direction direction, Game game, Coordinate position) {

        this.clazz = new SimpleClassProperty(clazz);
        this.direction = new SimpleDirectionProperty(direction);
        this.game = game;
        this.position = position;
        this.packs = new ArrayList<>();
        this.researches = new ArrayList<>();
    }

    /**
     * Instantiates this device model. Creates the associated device.
     * 
     * @return a device created from this model.
     */
    public Device instantiate() {
        try {
            @SuppressWarnings("unchecked")
            final Class<? extends Device> clazz = (Class<? extends Device>) this.clazz.get();
            final Device device = clazz.getConstructor(DeviceModel.class).newInstance(this);
            return device;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The class of the device model isn't equal to a simple {@link #getClass()}.
     * Instead, it is the class of the device that would be instantiated using
     * {@link #instantiate()}. You can consider this as the <em>the type</em> of the
     * device model.
     * 
     * @return the class property
     */
    public SimpleClassProperty classProperty() {
        return clazz;
    }

    /**
     * The direction is the rotation level of the device.
     * 
     * @return the direction property
     */
    public SimpleDirectionProperty directionProperty() {
        return direction;
    }

    @Override
    public long getID() {
        return this.id;
    }

    /**
     * @return the type of this Device object.
     */
    @SuppressWarnings("unchecked")
    public Class<? extends Device> getType() {
        return (Class<? extends Device>) this.clazz.get();
    }

    /**
     * @return the direction of this Device object.
     */
    public Direction getDirection() {
        return this.direction.get();
    }

    /**
     * @return the position of this Device object.
     */
    public Coordinate getPosition() {
        return this.position;
    }

    /**
     * @return the game of this Device object.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * @return the packs associated to this DeviceModel object
     */
    public Collection<Pack> getPacks(){
        return Collections.unmodifiableCollection(packs);
    }

    /**
     * The researches associated to this device model
     * 
     * @return the researches
     */
    public Collection<Research> getResearches(){
        return researches;
    }

    /**
     * @param clazz the new type
     */
    public void setType(Class<? extends Device> clazz) {
        this.clazz.set(clazz);
    }

    /**
     * @param newDirection the new direction
     */
    public void setDirection(Direction direction) {
        this.direction.set(direction);
    }

    @Override
    public void setID(long id) {
        this.id = id;
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
     * Returns true if the values in the fields (except the id) are the same in this
     * DeviceModel object and the given one.
     * 
     * @param other the DeviceModel to compare
     * @return true if the properties of the compared models are the same
     */
    public boolean propertiesEquals(DeviceModel other) {
        if (direction.get() == other.direction.get() && game == other.game
                && position.propertiesEquals(other.position).get()) {
            return true;
        }

        return false;
    }

}
