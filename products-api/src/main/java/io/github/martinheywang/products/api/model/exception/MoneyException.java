package io.github.martinheywang.products.api.model.exception;

import java.math.BigInteger;

@SuppressWarnings("serial")
public class MoneyException extends Exception {

    public MoneyException() {
	super("Attempting to subtract too many money.");
    }

    public MoneyException(BigInteger beforeTransaction, BigInteger toSubtract) {
	super(String.format("Attempting to subtract too many money (%0 demandés, %1 disponibles)", toSubtract,
		beforeTransaction));
    }

}
