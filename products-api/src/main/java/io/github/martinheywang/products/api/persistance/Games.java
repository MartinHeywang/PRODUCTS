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
     * Calls {@link Request#countOf(Class)}, giving as argument the {@link Game}
     * class.
     * 
     * @return the count of persisted games
     */
    public synchronized int countOf() {
        return super.countOf(Game.class);
    }

    /**
     * Calls {@link Request#getAll(Class)}, giving as argument the @{link Game}
     * class.
     * 
     * @return all the persisted games.
     */
    public synchronized List<Game> getAll() {
        return this.getAll(Game.class);
    }

    /**
     * Calls {@link Request#getByID(Class, long)}, giving as argument the
     * {@link Game} class and the given id.
     * 
     * @param id the id to search for
     * @return a persisted game with the given id
     */
    public synchronized Game getByID(long id) {
        for (Game o : getAll(Game.class)) {
            if (o.getID() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Returns the most recent persisted game, the one with the most recent
     * {@link Game#getLastSave()}.
     * 
     * @return the most recent game
     */
    public synchronized Game getMostRecent() {
        final List<Game> games = getAll();
        games.sort(Comparator.comparing(Game::getLastSave).reversed());
        return games.get(0);
    }

    /**
     * Calls {@link Request#idExists(Class, long)}, giving as argument the
     * {@link Game} class and the given id.
     * 
     * @param id the id to search for
     * @return true if a persisted game was found, otherwise false
     */
    public synchronized boolean idExists(long id) {
        // the diamond operator isn't useless !
        return getByID(Game.class, id) == null ? false : true;
    }

    /**
     * Calls {@link Request#create(Persistable)}, giving as argument the given game.
     * 
     * @param game the game to persist
     */
    public synchronized void create(Game game) {
        super.create(game);
    }

    /**
     * Calls {@link Request#createIfNotExists(Persistable)}, giving as argument the
     * given game.
     * 
     * @param game the game to persist
     */
    public synchronized void createIfNotExists(Game game) {
        super.createIfNotExists(game);
    }

    /**
     * Calls {@link Request#createOrUpdate(Persistable)}, giving as argument the
     * given game.
     * 
     * @param game the game to either create or update.
     */
    public synchronized void createOrUpdate(Game game) {
        super.createOrUpdate(game);
    }

    /**
     * Calls {@link Request#update(Persistable)}, giving as argument the given game
     * 
     * @param game the game to update
     */
    public synchronized void update(Game game) {
        super.update(game);
    }

    /**
     * Calls {@link Request#delete(Persistable)}, giving as argument the given game.
     * 
     * @param game the game to delete
     */
    public synchronized void delete(Game game) {
        super.delete(game);
    }

    /**
     * Calls {@link Request#deleteIfExists(Persistable)}, giving as argument the
     * given game.
     * 
     * @param game the game to delete
     */
    public synchronized void deleteIfExists(Game game) {
        super.deleteIfExists(game);
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
