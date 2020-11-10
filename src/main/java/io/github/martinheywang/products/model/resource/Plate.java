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
package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.info.Prices;

/**
 * This enum defines all the Plate available by default in the game.
 * 
 * @author Martin Heywang
 */
@Extension(ordinal = 2)
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
    PLATE_OF_IRON("Plaque de fer", Prices.transformedPrice(), "/Plaque_De_Fer.png"),

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
    PLATE_OF_GOLD("Plaque d'or", Prices.transformedPrice(), "/Plaque_De_Or.png"),

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
    PLATE_OF_COPPER("Plaque de cuivre", Prices.transformedPrice(), "/Plaque_De_Cuivre.png"),

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
    PLATE_OF_SILVER("Plaque d'argent", Prices.transformedPrice(), "/Plaque_De_Argent.png"),
    
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
    PLATE_OF_ALUMINIUM("Plaque d'aluminium", Prices.transformedPrice(), "/Plaque_De_Aluminium.png");

    private String name;
    private BigInteger price;
    private String url;

    Plate(String name, BigInteger price, String url) {
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
