package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.info.Prices;

/**
 * This enum defines all the ingots available by default in the game.
 * 
 * @author Martin Heywang
 */
@Extension(ordinal = 2)
public enum Ingot implements Resource {

    /**
     * <p>
     * Iron ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    IRON_INGOT("Lingot de fer", Prices.transformedPrice(), "/Lingot_De_Fer.png"),

    /**
     * <p>
     * Gold ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    GOLD_INGOT("Lingot d'or", Prices.transformedPrice(), "/Lingot_De_Or.png"),

    /**
     * <p>
     * Copper ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    COPPER_INGOT("Lingot de cuivre", Prices.transformedPrice(), "/Lingot_De_Cuivre.png"),

    /**
     * <p>
     * Silver ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    SILVER_INGOT("Lingot d'argent", Prices.transformedPrice(), "/Lingot_De_Argent.png"),

    /**
     * <p>
     * Aluminium ingot
     * </p>
     * <p>
     * Price : default price for transformed resources.
     * </p>
     * 
     * @see io.github.martinheywang.products.api.model.resource.info.Prices
     */
    ALUMINIUM_INGOT("Lingot d'aluminium", Prices.transformedPrice(), "/Lingot_De_Aluminium.png");

    private String name;
    private BigInteger price;
    private String url;

    Ingot(String name, BigInteger price, String url) {
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
