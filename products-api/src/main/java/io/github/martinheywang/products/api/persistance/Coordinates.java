package io.github.martinheywang.products.api.persistance;

import io.github.martinheywang.products.api.model.Coordinate;

public final class Coordinates extends Request {
    private static Coordinates singleton = new Coordinates();

    private Coordinates() {
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
        for(Coordinate coord : getAll(Coordinate.class)){
            if(coord.propertiesEquals(new Coordinate(x, y)).get()){
                return coord;
            }
        }
        throw new IllegalStateException("This method should have returned before.");
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
