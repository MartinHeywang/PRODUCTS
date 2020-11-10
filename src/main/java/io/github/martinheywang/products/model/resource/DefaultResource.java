package io.github.martinheywang.products.model.resource;

import java.math.BigInteger;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * This enum defines a NONE resource. It actually has no effect to any
 * device.
 * 
 * @author Martin Heywang
 */
@Extension
public enum DefaultResource implements Resource {

    /**
     * NONE is the default like a null value. It doesn't represents anything. It has no proper value.
     */
    NONE("None", 0, "/None.png");

    private String name;
    private BigInteger price;
    private String url;

    DefaultResource(String name, long value, String url) {
	this.name = name;
	this.price = BigInteger.valueOf(value);
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
