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

import io.github.martinheywang.products.api.model.exception.RequestException;

import java.util.List;

/**
 * <p>
 * The DataManager is a singleton that manages how the persistent data is
 * fetched/created/updated/deleted. Any application should create an
 * implementation of this class, as long as it tries to persist resources. Once
 * created, the application should set the manager using
 * {@link #setDataManager(DataManager)}, this will set up the data manager and
 * use it. (Pass as argument the result of {@link #getSingleton()}.)
 * </p>
 * <p>
 * When overriding this class, read carefully the javadoc to ensure that the
 * expected behaviour is performed. By the way, you may want to use the
 * <code>synchronized</code> keyword.
 * </p>
 */
public abstract class DataManager {

    private static DataManager instance;

    protected DataManager() {
    }

    /**
     * Sets everything up that is needed. This method is called when the data
     * manager is set using {@link #setDataManager(DataManager)}.
     * 
     * @throws Exception if the method couldn't execute as expected
     */
    protected abstract void setUp() throws Exception;

    /**
     * Returns all the persisted objects for the given persistable type.
     * 
     * @param <T>  a persistable type
     * @param type the type of the returned persisted object.
     * @return all the persisted objects
     * @throws RequestException if the data couldn't not be fetched properly
     */
    public abstract <T extends Persistable> List<T> getAll(Class<T> type) throws RequestException;

    /**
     * Persist the given persistable object, giving it an id. If a persisted object
     * with the same id is found, this method does nothing. If the given object has
     * already an id, this one may change during the process.
     * 
     * @param object the object to persist
     * @return true if the creation succeeded, otherwise false
     * @throws RequestException if the creation didn't happen properly
     */
    public abstract void create(Persistable object) throws RequestException;

    /**
     * Replaces the persisted object with the same id by the one given as argument.
     * This <strong>doesn't</strong> ensure that the given object is persisted : if
     * the object either has not got an id or this one wasn't found, this method
     * does nothing.
     * 
     * @param object the object to update
     * @return true if an update was actually performed
     * @throws RequestException if the update didn't happen properly
     */
    public abstract void update(Persistable object) throws RequestException;

    /**
     * Deletes the object with the same id as the object given as argument. The
     * object will not be persisted anymore, and its id will be null. If the given
     * object has not an id yet, throws an error
     * 
     * @param object the object to 'un'persist.
     * @return true if a deletion was actually performed
     * @throws RequestException if the deletion didn't happen properly
     */
    public abstract void delete(Persistable object) throws RequestException;

    /**
     * Returns the data manager being used
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
        try {
            manager.setUp();
        } catch (Exception e) {
            throw new IllegalStateException("The DataManager didn't set up properly.", e);
        }
    }

}