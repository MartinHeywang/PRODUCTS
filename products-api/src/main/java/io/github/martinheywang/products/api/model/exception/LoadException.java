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
package io.github.martinheywang.products.api.model.exception;

import io.github.martinheywang.products.api.model.Game;

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
