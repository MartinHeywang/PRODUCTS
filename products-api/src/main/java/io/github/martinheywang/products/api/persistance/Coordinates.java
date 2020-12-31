package io.github.martinheywang.products.api.persistance;

import io.github.martinheywang.products.api.model.Coordinate;

import java.util.List;

public final class Coordinates extends Request {
    private static Coordinates singleton = new Coordinates();

    private Coordinates() {
    }

    /**
     * Calls {@link Request#countOf(Class)}, giving as argument the
     * {@link Coordinate} class.
     * 
     * @return the count of persisted games
     */
    public synchronized int countOf() {
        return super.countOf(Coordinate.class);
    }

    /**
     * Calls {@link Request#getAll(Class)}, giving as argument the @{link
     * Coordinate} class.
     * 
     * @return all the persisted games.
     */
    public synchronized List<Coordinate> getAll() {
        return this.getAll(Coordinate.class);
    }

    /**
     * Calls {@link Request#getByID(Class, long)}, giving as argument the
     * {@link Coordinate} class and the given id.
     * 
     * @param id the id to search for
     * @return a persisted coord with the given id
     */
    public synchronized Coordinate getByID(long id) {
        for (Coordinate o : getAll(Coordinate.class)) {
            if (o.getID() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Either creates a new Coordinate, or fetch the exisiting one out of the
     * persistance system. If a coordinates with the given x and y values exists in
     * the persistance system, fetch it back. Otherwise, create a new
     * {@link Coordinate} and persists it. This ensures that the coordinate that you
     * manipulate has the same id as the persisted one.
     * 
     * @param x the x value
     * @param y the y value
     * @return the coordinate
     */
    public synchronized Coordinate forArgs(int x, int y) {
        createIfNotExists(new Coordinate(x, y));
        for(Coordinate coord : getAll()){
            if(coord.propertiesEquals(new Coordinate(x, y)).get()){
                return coord;
            }
        }
        throw new IllegalStateException("This method should have returned before.");
    }

    /**
     * Calls {@link Request#idExists(Class, long)}, giving as argument the
     * {@link Coordinate} class and the given id.
     * 
     * @param id the id to search for
     * @return true if a persisted coord was found, otherwise false
     */
    public synchronized boolean idExists(long id) {
        // the diamond operator isn't useless !
        return getByID(Coordinate.class, id) == null ? false : true;
    }

    /**
     * Calls {@link Request#create(Persistable)}, giving as argument the given
     * coord.
     * 
     * @param coord the coord to persist
     */
    public synchronized void create(Coordinate coord) {
        super.create(coord);
    }

    /**
     * Calls {@link Request#createIfNotExists(Persistable)}, giving as argument the
     * given coord.
     * 
     * @param coord the coord to persist
     */
    public synchronized void createIfNotExists(Coordinate coord) {
        super.createIfNotExists(coord);
    }

    /**
     * Calls {@link Request#createOrUpdate(Persistable)}, giving as argument the
     * given coord.
     * 
     * @param coord the coord to either create or update.
     */
    public synchronized void createOrUpdate(Coordinate coord) {
        super.createOrUpdate(coord);
    }

    /**
     * Calls {@link Request#update(Persistable)}, giving as argument the given coord
     * 
     * @param coord the coord to update
     */
    public synchronized void update(Coordinate coord) {
        super.update(coord);
    }

    /**
     * Calls {@link Request#delete(Persistable)}, giving as argument the given
     * coord.
     * 
     * @param coord the coord to delete
     */
    public synchronized void delete(Coordinate coord) {
        super.delete(coord);
    }

    /**
     * Calls {@link Request#deleteIfExists(Persistable)}, giving as argument the
     * given coord.
     * 
     * @param coord the coord to delete
     */
    public synchronized void deleteIfExists(Coordinate coord) {
        super.deleteIfExists(coord);
    }

    /**
     * Returns the singleton to use this class.
     * 
     * @return the singleton instance of this class
     */
    public static Coordinates getSingleton() {
        return singleton;
    }
}
