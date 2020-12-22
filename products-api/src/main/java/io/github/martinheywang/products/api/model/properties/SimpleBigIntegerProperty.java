package io.github.martinheywang.products.api.model.properties;

import java.math.BigInteger;

import javafx.beans.property.SimpleObjectProperty;

/**
 * The SimpleBigIntegerProperty is nothing more than a
 * SimpleObjectProperty<BigInteger>. The only purpose of this class is to give a
 * name to this generic name, in order to create a custom persister for this
 * type.
 * 
 * @author Martin Heywang
 */
public final class SimpleBigIntegerProperty extends SimpleObjectProperty<BigInteger> {
    
    /**
     * Creates a new SimpleBigIntegerProperty using the {@link BigInteger#ZERO} value.
     */
    public SimpleBigIntegerProperty(){
        super(BigInteger.ZERO);
    }

    /**
     * Creates a new SimpleBigIntegerProperty using the given value.
     * 
     * @param value the value to initialize the property with
     */
    public SimpleBigIntegerProperty(BigInteger value){
        super(value);
    }
}