package io.github.martinheywang.products.api.persistance;

import io.github.martinheywang.products.api.model.Pack;

import java.util.List;

public final class Packs extends Request {
    
    private static Packs singleton = new Packs();

    private Packs() {
    }

    /**
     * Calls {@link Request#countOf(Class)}, giving as argument the
     * {@link Pack} class.
     * 
     * @return the count of persisted games
     */
    public synchronized int countOf() {
        return super.countOf(Pack.class);
    }

    /**
     * Calls {@link Request#getAll(Class)}, giving as argument the @{link
     * Pack} class.
     * 
     * @return all the persisted games.
     */
    public synchronized List<Pack> getAll() {
        return this.getAll(Pack.class);
    }

    /**
     * Calls {@link Request#getByID(Class, long)}, giving as argument the
     * {@link Pack} class and the given id.
     * 
     * @param id the id to search for
     * @return a persisted pack with the given id
     */
    public synchronized Pack getByID(long id) {
        for (Pack o : getAll(Pack.class)) {
            if (o.getID() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Calls {@link Request#idExists(Class, long)}, giving as argument the
     * {@link Pack} class and the given id.
     * 
     * @param id the id to search for
     * @return true if a persisted pack was found, otherwise false
     */
    public synchronized boolean idExists(long id) {
        // the diamond operator isn't useless !
        return getByID(Pack.class, id) == null ? false : true;
    }

    /**
     * Calls {@link Request#create(Persistable)}, giving as argument the given
     * pack.
     * 
     * @param pack the pack to persist
     */
    public synchronized void create(Pack pack) {
        super.create(pack);
    }

    /**
     * Calls {@link Request#createIfNotExists(Persistable)}, giving as argument the
     * given pack.
     * 
     * @param pack the pack to persist
     */
    public synchronized void createIfNotExists(Pack pack) {
        super.createIfNotExists(pack);
    }

    /**
     * Calls {@link Request#createOrUpdate(Persistable)}, giving as argument the
     * given pack.
     * 
     * @param pack the pack to either create or update.
     */
    public synchronized void createOrUpdate(Pack pack) {
        super.createOrUpdate(pack);
    }

    /**
     * Calls {@link Request#update(Persistable)}, giving as argument the given pack
     * 
     * @param pack the pack to update
     */
    public synchronized void update(Pack pack) {
        super.update(pack);
    }

    /**
     * Calls {@link Request#delete(Persistable)}, giving as argument the given
     * pack.
     * 
     * @param pack the pack to delete
     */
    public synchronized void delete(Pack pack) {
        super.delete(pack);
    }

    /**
     * Calls {@link Request#deleteIfExists(Persistable)}, giving as argument the
     * given pack.
     * 
     * @param pack the pack to delete
     */
    public synchronized void deleteIfExists(Pack pack) {
        super.deleteIfExists(pack);
    }

    /**
     * Returns the singleton to use this class.
     * 
     * @return the singleton instance of this class
     */
    public static Packs getSingleton() {
        return singleton;
    }
}
