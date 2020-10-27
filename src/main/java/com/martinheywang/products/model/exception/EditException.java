package com.martinheywang.products.model.exception;

public class EditException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EditException() {
	super("The requested edit could not be performed.");
    }

}
