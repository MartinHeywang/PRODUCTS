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
package io.github.martinheywang.products.api.model.resource;

import java.math.BigInteger;

/**
 * This enum defines all the ingots available by default in the game.
 * 
 * @author Martin Heywang
 */
 
public enum Ingot implements Resource {

    /**
     * <p>
     * Iron ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    IRON_INGOT("Lingot de fer", "130", "Iron-Ingot.png"),

    /**
     * <p>
     * Gold ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    GOLD_INGOT("Lingot d'or", "130", "Gold-Ingot.png"),

    /**
     * <p>
     * Copper ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    COPPER_INGOT("Lingot de cuivre","130", "Copper-Ingot.png"),

    /**
     * <p>
     * Silver ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    SILVER_INGOT("Lingot d'argent", "130", "Silver-Ingot.png"),

    /**
     * <p>
     * Aluminium ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    ALUMINIUM_INGOT("Lingot d'aluminium", "130", "Aluminium-Ingot.png");

    private String name;
    private BigInteger price;
    private String url;

    Ingot(String name, String price, String file) {
        this.name = name;
        this.price = new BigInteger(price);
        this.url = Ingot.class.getResource("/images/resources/Ingot/"+file).toExternalForm();
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
