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

/**
 * The Persistable interface is an interface that defines the implementing
 * classes as persistable, and so persistable in any format (database, json,
 * ...). The implementing classes must provide the management of an id; this is
 * necessary to recognize an object from another. A class marked with
 * Persistable might be managed by the implementation of the {@link DataManager}
 * provided by the local app.
 */
public abstract interface Persistable {

    /**
     * Returns the id of this object. The ID is use by the data manager to know if
     * this persistable exists. Vault 0 (default value for primitive type int) when
     * the object isn't persisted.
     * 
     * @return the id
     */
    long getID();

    /**
     * Sets the id of this object. <strong>This method should not be called outside
     * of the DataManager or its implementations.</strong>
     * 
     * @param id the new id
     */
    void setID(long id);
}