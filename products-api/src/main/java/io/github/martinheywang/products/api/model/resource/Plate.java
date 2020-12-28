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
     */
    IRON_PLATE("Plaque de fer", "150", "Iron-Plate.png"),

    /**
     * <p>
     * Gold plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    GOLD_PLATE("Plaque d'or", "150", "Gold-Plate.png"),

    /**
     * <p>
     * Copper plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    COPPER_PLATE("Plaque de cuivre", "150", "Copper-Plate.png"),

    /**
     * <p>
     * Silver plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    SILVER_PLATE("Plaque d'argent", "150", "Silver-Plate.png"),
    
    /**
     * <p>
     * Aluminium plate
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     */
    ALUMINIUM_PLATE("Plaque d'aluminium", "150", "Aluminium-Plate.png");

    private String name;
    private BigInteger price;
    private String url;

    Plate(String name, String price, String file) {
        this.name = name;
        this.price = new BigInteger(price);
        this.url = Plate.class.getResource("/images/resources/Plate/"+file).toExternalForm();
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
