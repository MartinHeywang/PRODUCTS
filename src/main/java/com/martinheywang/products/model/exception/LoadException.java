package com.martinheywang.products.model.exception;

import com.martinheywang.products.model.Game;

/**
 * A LoadException is throwed whenever a game fail loading.
 * 
 * @author Martin Heywang
 */
public class LoadException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5232654092900518709L;

    /**
     * Creates a LoadException.
     * 
     * @param game the game wanted to load
     */
    public LoadException(Game game) {
	super("Une erreur lors du chargement de la partie est survenue. (ID: " + game.getID() + ")");
    }

    /**
     * Creates a LoadException.
     * 
     * @param message a custom message
     * @param game    the game wanted to load
     */
    public LoadException(String message, Game game) {
	super(message + " (ID: " + game.getID() + ")");
    }

}
