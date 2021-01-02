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
