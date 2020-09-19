package com.martinheywang.products.model.exceptions;

public class EditException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditException() {
		super("The requested edit could not be performed.");
	}

	public EditException(String message) {
		super(message);
	}

}
