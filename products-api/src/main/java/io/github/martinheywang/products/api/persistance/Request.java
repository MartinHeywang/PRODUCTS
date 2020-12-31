package io.github.martinheywang.products.api.persistance;

import io.github.martinheywang.products.api.model.exception.RequestException;

import java.util.Arrays;
import java.util.List;

abstract class Request {

    /**
     * Counts and returns the number of persisted object of the given type. Throws
     * an {@link IllegalArgumentException} if a problem occured.
     * 
     * @param clazz the class of the persisted objects to count
     * @return the total count
     */
    protected synchronized int countOf(Class<? extends Persistable> clazz) {
        try {
            return DataManager.current().getAll(clazz).size();
        } catch (RequestException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns the persisted objects of the given type, as a {@link List}. Throws an
     * {@link IllegalArgumentException} if a problem occured.
     * 
     * @param clazz the class of the persisted objects
     * @return the objects
     */
    protected synchronized final <T extends Persistable> List<T> getAll(Class<T> clazz) {
        try {
            return DataManager.current().getAll(clazz);
        } catch (RequestException e) {
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    /**
     * <p>
     * Returns the object with the given id and the given persistable type. Returns
     * null if none was found. If, somehow, the same id is found multiple times,
     * then returns the first one. (This last case should not happen.)
     * </p>
     * <p>
     * This means that :
     * </p>
     * 
     * <pre>
     * <code>
     * Class<? extends Persistable> clazz = Game.class;
     * long id = 5;
     * Persistable persisted = Games.getSingleton().getByID(clazz, id);
     * 
     * // If persisted is not null, then
     * persisted.getClass() == clazz // true
     * persisted.getID() == id // true
     * </code>
     * </pre>
     * 
     * @param clazz a persistable type
     * @param id    the id to search for
     * @return an object with the id, if found.
     */
    protected synchronized Persistable getByID(Class<? extends Persistable> clazz, long id) {
        for (Persistable o : getAll(clazz)) {
            if (o.getID() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Checks if one (or more) persisted object of the given type has the given id.
     * If such a thing is true, then the result of {@link #getByID(Class, long)}
     * must not be null.
     * 
     * @param clazz a persistable class
     * @param id    the id to search for
     * @return true if the id was found, otherwise false
     */
    protected synchronized boolean idExists(Class<? extends Persistable> clazz, long id) {
        // the diamond operator isn't useless !
        return getByID(clazz, id) == null ? false : true;
    }

    /**
     * <p>
     * Persists the persistable object. This means that the system will be able to
     * find it, even after a JVM reboot. If the given object already exists, then
     * throws an {@link IllegalArgumentException}. The algorithm that checks if the
     * object already exists is based only on the id of the object : if this last
     * isn't null and exists in the persistance system, then the given object won't
     * be persisted.
     * </p>
     * <p>
     * Note that when fetching back the object, they (the fetched one and the one
     * given as argument) won't be same, even though the value will be the same.
     * That's because, persisted objects are re-created when fetching them back.
     * You may consider them like twins.
     * </p>
     * 
     * @param object the object to persist
     */
    protected synchronized final void create(Persistable object) {
        try {
            DataManager.current().create(object);
        } catch (RequestException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Like {@link #create(Persistable)}, but won't throw an error if the object
     * already exists : this method checks that everything is ok before. If the
     * object already exists, then does nothing.
     * 
     * @param object the object to persist
     */
    protected synchronized void createIfNotExists(Persistable object) {
        if (object.getID() == null) {
            create(object);
        } else if (!idExists(object.getClass(), object.getID())) {
            create(object);
        }
    }

    /**
     * This method is useful when the devloper ("You!") if you want to persist an
     * object, but don't know if it already exists. In fact, it checks the
     * conditions like {@link #createIfNotExists(Persistable)}, but instead of doing
     * nothing if the object already exists, it will {@link #update(Persistable)}
     * the object.
     * 
     * @param object the object to either create or update
     */
    protected synchronized void createOrUpdate(Persistable object) {
        if (object.getID() == null) {
            create(object);
        } else if (!idExists(object.getClass(), object.getID())) {
            create(object);
        } else {
            update(object);
        }
    }

    /**
     * Updates the given persisted object. It the object doesn't exists in the
     * persistance system throws an {@link IllegalArgumentException}. See
     * {@link #create(Persistable)} the program detects whether an object already
     * exists or not. If you can't know whether an object already exists or not at a
     * certain state of the program, consider using
     * {@link #createOrUpdate(Persistable)} to ensure that the given object will
     * exist. In other word, replaces the persisted object of the same class and the
     * same id with the one given as argument.
     * 
     * @param object the object to update
     */
    protected synchronized void update(Persistable object) {
        try {
            DataManager.current().update(object);
        } catch (RequestException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Delete the given object from the persistance system. This means that the
     * object won't exits anymore outside the JVM. This one won't be able to get it
     * once again. Throws an {@link IllegalArgumentException} if the given object
     * isn't currently persisted.
     * 
     * @param object the object to delete
     */
    protected synchronized void delete(Persistable object) {
        try {
            DataManager.current().delete(object);
        } catch (RequestException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Like {@link #delete(Persistable)}, but won't throw any exception as if checks
     * before if everything is ok. This method is comparable to
     * {@link #createIfNotExists(Persistable)}, but for creating objects.
     * 
     * @param object the object to delete
     */
    protected synchronized void deleteIfExists(Persistable object) {
        if (object.getID() != null && idExists(object.getClass(), object.getID())) {
            delete(object);
        }
    }
}
