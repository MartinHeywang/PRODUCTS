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
package io.github.martinheywang.products.api.model;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.persistance.Persistable;

import java.math.BigDecimal;
import java.math.BigInteger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data structure that defines a research in the game. A research is a list of
 * {@link ResearchKey research keys}, with a name and descriptions. Those are
 * mainly associated with a {@link DeviceModel}. These are used to upgrade
 * progressively a DeviceModel. If you knew the level system in the APIv1, this
 * class replaces it.
 * 
 * @since 2.0.0
 */
public class Research implements Persistable {

    private long id;
    private final StringProperty name;
    private final StringProperty shortDescription;
    private final StringProperty fullDescription;
    private final ObjectProperty<Number> property;
    private final ListProperty<ResearchKey> keys;

    /**
     * Creates a new {@link Research}.
     * 
     * @param name     the name of the property
     * @param property the property that will be incremented.
     */
    public Research(String name, ObjectProperty<Number> property) {
        this.name = new SimpleStringProperty(name);
        this.shortDescription = new SimpleStringProperty();
        this.fullDescription = new SimpleStringProperty();
        this.property = property;
        this.keys = new SimpleListProperty<>();
    }

    /**
     * Clears the list of keys, then adds them regularly. This means that each key
     * will be created with the same 'space' between them. For example, for args:
     * number = 3, increment = 10, price = 50, this method will create 3 keys. The
     * first one will have: newValue = 10, price = 50. The second key will have:
     * newValue = 20, price = 100.
     * 
     * @param number     the number of key to create
     * @param startValue the value to start with
     * @param increment  the increment value
     * @param startPrice the price to start with
     * @param price      the price
     */
    public void setKeysRegular(int number, int startValue, int increment, BigInteger startPrice, BigInteger price) {
        this.keys.clear();
        int lastValue = startValue;
        BigInteger lastPrice = startPrice;

        for (int i = 0; i < number; i++) {
            lastValue += increment;
            lastPrice = lastPrice.add(price);
            this.keys.add(new ResearchKey(lastPrice, lastValue));
        }
    }

    /**
     * Clears the current research keys, then fill it with the multiply way. Unlike
     * {@link #setKeysRegular(int, int, int, BigInteger, BigInteger)}, this method
     * multiplies the price instead of incrementing it.
     * 
     * @param number          the number of keys to create
     * @param startValue      the value to start with
     * @param increment       the increment value
     * @param startPrice      the price to start with
     * @param priceMultiplier the multiplier of the price
     */
    public void setKeysMultiply(int number, int startValue, int increment, BigInteger startPrice,
            BigDecimal priceMultiplier) {

        this.keys.clear();
        int lastValue = startValue;
        BigInteger lastPrice = startPrice;

        for (int i = 0; i < number; i++) {
            lastValue += increment;
            lastPrice = new BigDecimal(lastPrice).multiply(priceMultiplier).toBigInteger();
            this.keys.add(new ResearchKey(lastPrice, lastValue));
        }
    }

    /**
     * Replaces the current keys with the array given as argument
     * 
     * @param keys an array of keys
     */
    public void setCustomKeys(ResearchKey[] keys) {
        this.keys.setAll(keys);
    }

    /**
     * The name property wraps the name. The name is a short string that doesn't
     * need to describe anything. it may be funny.
     * 
     * @return the name property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * The short description is a short sentence that describe what is the effect of
     * the property.
     * 
     * @return the short description property
     */
    public StringProperty shortDescriptionProperty() {
        return shortDescription;
    }

    /**
     * The full description is a full paragraph that describes precisely what the
     * research does.
     * 
     * @return the full description property
     */
    public StringProperty fullDescriptionProperty() {
        return fullDescription;
    }

    /**
     * The main property is the property that will be set when a new research key is
     * set as completed.
     * 
     * @return the property
     */
    public ReadOnlyObjectProperty<Number> mainProperty() {
        return property;
    }

    /**
     * The keys property wraps a list of the keys.
     * 
     * @return the keys property
     */
    public ReadOnlyListProperty<ResearchKey> keysProperty() {
        return keys;
    }

    @Override
    public long getID() {
        return id;
    }

    /**
     * @return the name
     * @see #nameProperty()
     */
    public String getName() {
        return name.get();
    }

    /**
     * @return the short description
     * @see #shortDescriptionProperty()
     */
    public String getShortDescription() {
        return shortDescription.get();
    }

    /**
     * @return the full description
     * @see #fullDescriptionProperty()
     */
    public String getFullDescription() {
        return fullDescription.get();
    }

    @Override
    public void setID(long id) {
        this.id = id;
    }

    /**
     * Sets the name of this research
     * 
     * @param name the new name
     * @see #nameProperty()
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Sets the short description of this research.
     * 
     * @param desc the new short description
     * @see #shortDescription()
     */
    public void setShortDescription(String desc) {
        this.shortDescription.set(desc);
    }

    /**
     * Sets the full description of this research.
     * 
     * @param desc the new full description
     * @see #fullDescriptionProperty()
     */
    public void setFullDescription(String desc) {
        this.fullDescription.set(desc);
    }

    class ResearchKey {

        private final ObjectProperty<BigInteger> price;
        private final ObjectProperty<Integer> newValue;
        private final BooleanProperty completed;

        /**
         * Creates a new {@link ResearchKey}.
         * 
         * @param price    the price
         * @param newValue the new value
         */
        public ResearchKey(BigInteger price, int newValue) {
            this.price = new SimpleObjectProperty<>(price);
            this.newValue = new SimpleObjectProperty<>(newValue);
            this.completed = new SimpleBooleanProperty(false);
        }

        /**
         * The price property wraps the price that the player will need to spend when
         * completing this key.
         * 
         * @return the price property
         */
        public ReadOnlyObjectProperty<BigInteger> priceProperty() {
            return price;
        }

        /**
         * The new value property the new value. This value will be used when completing
         * the key.
         * 
         * @return the new value property
         */
        public ReadOnlyObjectProperty<Integer> newValueProperty() {
            return newValue;
        }

        /**
         * The completed property wraps the completed value. This value is set to true
         * whenever this key is marked as completed.
         * 
         * @return the completed property
         */
        public BooleanProperty completedProperty() {
            return completed;
        }

        /**
         * @return the price
         * @see #priceProperty()
         */
        public BigInteger getPrice() {
            return price.get();
        }

        /**
         * @return the new value
         * @see #newValueProperty()
         */
        public Number getNewValue() {
            return newValue.get();
        }

        /**
         * @return the completed value
         * @see #completedProperty()
         */
        public boolean isCompleted() {
            return completed.get();
        }

        /**
         * Sets whether this key is completed or not.
         * 
         * @param completed the new value
         */
        public void setCompleted(boolean completed) {
            this.completed.set(completed);
        }
    }
}
