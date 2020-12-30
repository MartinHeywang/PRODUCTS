package io.github.martinheywang.products.api.persistent;

import java.util.Collection;

/**
 * The DataManager is a singleton that manages how the persistent data is
 * fetched/created/updated/deleted. Any application should create an
 * implementation of this class, as long as it tries to persist resources. Once
 * created, the application should set the manager using
 * {@link #setDataManager(DataManager)}, this will set up the data manager and
 * use it. (Pass as argument the result of {@link #getSingleton()}.)
 */
public abstract class DataManager {

    private static DataManager instance;

    private DataManager() {

    }

    /**
     * Sets everything up that is needed. This method is called when the data
     * manager is set using {@link #setDataManager(DataManager)}.
     */
    protected abstract void setUp();

    /**
     * Returns the count of persisted object for the given persistable class.
     * 
     * @param clazz the class of the persistable objects
     * @return the number of persisted object for the given type
     */
    public abstract Long countOf(Class<? extends Persistable> clazz);

    /**
     * Returns all the persisted objects for the given persistable type.
     * 
     * @param <T>  a persistable type
     * @param type the type of the returned persisted object.
     * @return all the persisted objects
     */
    public abstract <T extends Persistable> Collection<? extends T> getAll(Class<T> type);

    /**
     * Returns the persisted object for the given type and the given id. May return
     * null, if none was found.
     * 
     * @param type the type of the persisted object
     * @param id   the id of the persisted object.
     * @return the persisted object if one was found, otherwise null
     */
    public abstract <T extends Persistable> Persistable getForID(Class<T> type, Long id);

    /**
     * Persist the given persistable object, giving it an id. If successful, this
     * ensures that the given object is persisted.
     * 
     * @param object the object to persist
     * @return true if the creation succeeded, otherwise false
     */
    public abstract boolean create(Persistable object);

    /**
     * Like {@link #create(Persistable)}, but checks before the object has an id and
     * if it already exists. If not, creates it; if yes, does nothing. If
     * successful, this ensures that the object is persisted.
     * 
     * @param object the object to persist
     */
    public abstract void createIfNotExists(Persistable object);

    /**
     * Does a mix between {@link #create(Persistable)} and
     * {@link #update(Persistable)}. If the id already exists, replaces the object,
     * if not, creates it. If successful, this ensures the given object is
     * persisted.
     * 
     * @param object the object to create/update
     */
    public abstract void createOrUpdate(Persistable object);

    /**
     * Replaces the persisted object with the same id by the one given as argument.
     * This doesn't ensure that the given object is persisted : if the id wasn't
     * found, this method does nothing.
     * 
     * @param object the object to update
     * @return true if an update was actually performed
     */
    public abstract boolean update(Persistable object);

    /**
     * Deletes the object with the same id as the object given as argument.
     * 
     * @param object the object to 'un'persist.
     * @return true if a deletion was actually performed
     */
    public abstract boolean delete(Persistable object);

    /**
     * Checks if the given id exists for the given type.
     * 
     * @param <T> the persistable type
     * @param type the persistable type
     * @param id the id to check
     * @return true if the id was found.
     */
    public abstract <T extends Persistable> boolean idExists(Class<T> type, Long id);

    /**
     * Returns the singleton instance of this class.
     * 
     * @return the singleton
     */
    public abstract DataManager getSingleton();

    /**
     * Returns the data manager being used currently
     * 
     * @return the data manager
     */
    public static final DataManager current() {
        return instance;
    }

    /**
     * Sets the DataManager that will be called whenever a request is made in a
     * request class. Sets it up.
     * 
     * @param manager the manager to use
     */
    public static final void setDataManager(DataManager manager) {
        instance = manager;
        manager.setUp();
    }

}