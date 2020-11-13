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
import io.github.martinheywang.products.api.model.resource.info.Prices;

/**
 * This enum defines all the Plate available by default in the game.
 * 
 * @author Martin Heywang
 */
 
public enum Plate implements Resource {

    /**
     * <p>
     * Iron plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    IRON_PLATE("Plaque de fer", Prices.transformedPrice(), "Iron-Plate.png"),

    /**
     * <p>
     * Gold plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    GOLD_PLATE("Plaque d'or", Prices.transformedPrice(), "Gold-Plate.png"),

    /**
     * <p>
     * Copper plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    COPPER_PLATE("Plaque de cuivre", Prices.transformedPrice(), "Copper-Plate.png"),

    /**
     * <p>
     * Silver plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    SILVER_PLATE("Plaque d'argent", Prices.transformedPrice(), "Silver-Plate.png"),
    
    /**
     * <p>
     * Aluminium plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    ALUMINIUM_PLATE("Plaque d'aluminium", Prices.transformedPrice(), "Aluminium-Plate.png");

    private String name;
    private BigInteger price;
    private String url;

    Plate(String name, BigInteger price, String url) {
        this.name = name;
        this.price = price;
        this.url = Plate.class.getResource("/images/resources/Plate/").toExternalForm();
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
