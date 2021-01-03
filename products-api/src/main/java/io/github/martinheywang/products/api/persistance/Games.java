/*
   Copyright 2021 Martin Heywang

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
package io.github.martinheywang.products.api.persistance;

import io.github.martinheywang.products.api.model.Game;

import java.util.Comparator;
import java.util.List;

/**
 * The games adds addionnal requests to the {@link Request} class, thus being
 * specific to the game.
 */
public final class Games extends Request {

    private static Games singleton = new Games();

    private Games() {
    }

    /**
     * Returns the most recent persisted game, the one with the most recent
     * {@link Game#getLastSave()}.
     * 
     * @return the most recent game
     */
    public synchronized Game getMostRecent() {
        final List<Game> games = getAll(Game.class);
        games.sort(Comparator.comparing(Game::getLastSave).reversed());
        return games.get(0);
    }

    /**
     * Returns the singleton to use this class.
     * 
     * @return the singleton instance of this class
     */
    public static Games getSingleton() {
        return singleton;
    }
}
