package io.github.martinheywang.products.api.persistance;

import io.github.martinheywang.products.api.model.device.DeviceModel;

import java.util.List;

public final class DeviceModels extends Request {

    private static DeviceModels singleton = new DeviceModels();

    private DeviceModels() {
    }

    /**
     * Calls {@link Request#countOf(Class)}, giving as argument the
     * {@link DeviceModel} class.
     * 
     * @return the count of persisted games
     */
    public synchronized int countOf() {
        return super.countOf(DeviceModel.class);
    }

    /**
     * Calls {@link Request#getAll(Class)}, giving as argument the @{link
     * DeviceModel} class.
     * 
     * @return all the persisted games.
     */
    public synchronized List<DeviceModel> getAll() {
        return this.getAll(DeviceModel.class);
    }

    /**
     * Calls {@link Request#getByID(Class, long)}, giving as argument the
     * {@link DeviceModel} class and the given id.
     * 
     * @param id the id to search for
     * @return a persisted model with the given id
     */
    public synchronized DeviceModel getByID(long id) {
        for (DeviceModel o : getAll(DeviceModel.class)) {
            if (o.getID() == id) {
                return o;
            }
        }
        return null;
    }

    /**
     * Calls {@link Request#idExists(Class, long)}, giving as argument the
     * {@link DeviceModel} class and the given id.
     * 
     * @param id the id to search for
     * @return true if a persisted model was found, otherwise false
     */
    public synchronized boolean idExists(long id) {
        // the diamond operator isn't useless !
        return getByID(DeviceModel.class, id) == null ? false : true;
    }

    /**
     * Calls {@link Request#create(Persistable)}, giving as argument the given
     * model.
     * 
     * @param model the model to persist
     */
    public synchronized void create(DeviceModel model) {
        super.create(model);
    }

    /**
     * Calls {@link Request#createIfNotExists(Persistable)}, giving as argument the
     * given model.
     * 
     * @param model the model to persist
     */
    public synchronized void createIfNotExists(DeviceModel model) {
        super.createIfNotExists(model);
    }

    /**
     * Calls {@link Request#createOrUpdate(Persistable)}, giving as argument the
     * given model.
     * 
     * @param model the model to either create or update.
     */
    public synchronized void createOrUpdate(DeviceModel model) {
        super.createOrUpdate(model);
    }

    /**
     * Calls {@link Request#update(Persistable)}, giving as argument the given model
     * 
     * @param model the model to update
     */
    public synchronized void update(DeviceModel model) {
        super.update(model);
    }

    /**
     * Calls {@link Request#delete(Persistable)}, giving as argument the given
     * model.
     * 
     * @param model the model to delete
     */
    public synchronized void delete(DeviceModel model) {
        super.delete(model);
    }

    /**
     * Calls {@link Request#deleteIfExists(Persistable)}, giving as argument the
     * given model.
     * 
     * @param model the model to delete
     */
    public synchronized void deleteIfExists(DeviceModel model) {
        super.deleteIfExists(model);
    }

    /**
     * Returns the singleton to use this class.
     * 
     * @return the singleton instance of this class
     */
    public static DeviceModels getSingleton() {
        return singleton;
    }
}
