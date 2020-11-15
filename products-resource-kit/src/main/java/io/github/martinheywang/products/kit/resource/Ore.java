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
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack;
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup;
import io.github.martinheywang.products.api.model.resource.annotation.Tag;

/**
 * This class register all the ores that can be found in the game. Each of them
 * is marked with some annotation (visible in the JavaDoc), that defines their
 * default behaviours.
 * 
 * @author Martin Heywang
 */

public enum Ore implements Resource {

     /**
      * <p>
      * Iron
      * </p>
      * <p>
      * Price : default price for raw resources.
      * </p>
      */
     @Tag(property = "buyable", value = "true")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Ingot.class, field = "IRON_INGOT"), target = "furnace")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Plate.class, field = "IRON_PLATE"), target = "press")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Wire.class, field = "IRON_WIRE"), target = "wire_drawer")
     IRON("Fer", "75", "Iron.png"),

     /**
      * <p>
      * Gold
      * </p>
      * <p>
      * Price : default price for raw resources.
      * </p>
      */
     @Tag(property = "buyable", value = "true")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Ingot.class, field = "GOLD_INGOT"), target = "furnace")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Plate.class, field = "GOLD_PLATE"), target = "press")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Wire.class, field = "GOLD_WIRE"), target = "wire_drawer")
     GOLD("Or", "75", "Gold.png"),

     /**
      * <p>
      * Copper
      * </p>
      * <p>
      * Price : default price for raw resources.
      * </p>
      */
     @Tag(property = "buyable", value = "true")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Ingot.class, field = "COPPER_INGOT"), target = "furnace")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Plate.class, field = "COPPER_PLATE"), target = "press")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Wire.class, field = "COPPER_WIRE"), target = "wire_drawer")
     COPPER("Cuivre", "75", "Copper.png"),

     /**
      * <p>
      * Silver
      * </p>
      * <p>
      * Price : default price for raw resources.
      * </p>
      */
     @Tag(property = "buyable", value = "true")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Ingot.class, field = "SILVER_INGOT"), target = "furnace")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Plate.class, field = "SILVER_PLATE"), target = "press")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Wire.class, field = "SILVER_WIRE"), target = "wire_drawer")
     SILVER("Argent", "75", "Silver.png"),

     /**
      * <p>
      * Aluminium
      * </p>
      * <p>
      * Price : default price for raw resources.
      * </p>
      */
     @Tag(property = "buyable", value = "true")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Ingot.class, field = "ALUMINIUM_INGOT"), target = "furnace")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Plate.class, field = "ALUMINIUM_PLATE"), target = "press")
     @AnnotationPackGroup(value = @AnnotationPack(clazz = Wire.class, field = "ALUMINIUM_WIRE"), target = "wire_drawer")
     ALUMINIUM("Aluminium", "75", "Aluminium.png"),

     /**
      * <p>
      * Diamond
      * </p>
      * <p>
      * Price : default price for raw resources.
      * </p>
      * 
      * <strong>Note : cannot be transformed in plate, ingot and wire.</strong>
      * 
      */
     @Tag(property = "buyable", value = "true")
     DIAMOND("Diamant", "75", "Diamond.png");

     private String name;
     private BigInteger price;
     private String url;

     Ore(String name, String price, String file) {
          this.name = name;
          this.price = new BigInteger(price);
          this.url = Ore.class.getResource("/images/resources/Ore/" + file).toExternalForm();
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
