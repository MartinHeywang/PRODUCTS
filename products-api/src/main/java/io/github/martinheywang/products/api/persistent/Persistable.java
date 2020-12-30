package io.github.martinheywang.products.api.persistent;

/**
 * The Persistable interface is an interface that defines the implementing
 * classes as persistable, and so persistable in any format (database, json, ...).
 * The implementing classes must provide the management of an id; this is
 * necessary to recognize an object from another. A class marked with
 * Persistable might be managed by the implementation of the {@link DataManager}
 * provided by the local app.
 */
public abstract interface Persistable {

    /**
     * Returns the id of this object. The ID is use by the data manager to know if
     * this persistable exists. May be null if the object isn't persisted.
     * 
     * @return the id
     */
    Long getID();

    /**
     * Sets the id of this object. <strong>This method should not be called outside
     * of the DataManager or its implementations.</strong>
     * 
     * @param id the new id
     */
    void setID(Long id);
}