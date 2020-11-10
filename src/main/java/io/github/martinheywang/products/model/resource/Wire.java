package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.info.Prices;

/**
 * This enum defines all the wire that are available by default in the game.
 * 
 * @author Martin Heywang
 */
@Extension(ordinal = 2)
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
