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
package io.github.martinheywang.products.kit.resource;

import java.math.BigInteger;

import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * This enum defines all the wire that are available by default in the game.
 * 
 * @author Martin Heywang
 */
public enum Wire implements Resource {

    /**
     * <p>
     * Iron wire
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    IRON_WIRE("Fil de fer", "150", "Iron-Wire.png"),

    /**
     * <p>
     * Gold wire
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    GOLD_WIRE("Fil d'or", "150", "Gold-Wire.png"),

    /**
     * <p>
     * Copper wire
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    COPPER_WIRE("Fil de cuivre", "150", "Copper-Wire.png"),

    /**
     * <p>
     * Silver wire
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    SILVER_WIRE("Fil d'argent", "150", "Silver-Wire.png"),

    /**
     * <p>
     * Aluminium wire
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    ALUMINIUM_WIRE("Fil d'aluminium", "150", "Aluminium-Wire.png");

    private String name;
    private BigInteger price;
    private String url;

    Wire(String name, String price, String file) {
        this.name = name;
        this.price = new BigInteger(price);
        this.url = Wire.class.getResource("/images/resources/Wire/"+file).toExternalForm();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigInteger getPrice() {
        return this.price;
    }

    @Override
    public String getURL() {
        return this.url;
    }

}
