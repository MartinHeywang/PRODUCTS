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
*/package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

 

import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.info.Prices;

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
    IRON_WIRE("Fil de fer", Prices.transformedPrice(), "/Fil_De_Fer.png"),

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
    GOLD_WIRE("Fil d'or", Prices.transformedPrice(), "/Fil_De_Or.png"),

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
    COPPER_WIRE("Fil de cuivre", Prices.transformedPrice(), "/Fil_De_Cuivre.png"),

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
    SILVER_WIRE("Fil d'argent", Prices.transformedPrice(), "/Fil_De_Argent.png"),

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
    ALUMINIUM_WIRE("Fil d'aluminium", Prices.transformedPrice(), "/Fil_De_Aluminium.png");

    // Diamonds wire obviously doesn't exist.

    private String name;
    private BigInteger price;
    private String url;

    Wire(String name, BigInteger price, String url) {
        this.name = name;
        this.price = price;
        this.url = "/images/resources" + url;
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
