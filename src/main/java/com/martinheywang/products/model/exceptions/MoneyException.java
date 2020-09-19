package com.martinheywang.products.model.exceptions;

@SuppressWarnings("serial")
public class MoneyException extends Exception {

	public MoneyException() {
		super("Attempting to substract too many money.");
	}

	public MoneyException(String string) {
		super(string);
	}

}
