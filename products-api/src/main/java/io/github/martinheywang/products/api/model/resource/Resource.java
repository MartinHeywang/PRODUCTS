/*
   Copyright 2020 Martin Heywang

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
package io.github.martinheywang.products.api.model.resource;

import io.github.martinheywang.products.api.persistent.Persistable;

import java.math.BigInteger;

import org.pf4j.ExtensionPoint;

/**
 * <p>
 * The Resource interface represents a resource in the game. You may call it
 * material or element.
 * </p>
 * <p>
 * The way resources are defined in the game is only in Java enumeration. Those
 * implements this interface and must give the a non-null value to all 3 of the
 * fields.
 * </p>
 * <p>
 * Those fields are :
 * </p>
 * <ul>
 * <li>The name : the name of the resource in the UI. Avoid names longer that 16
 * characters for display purpose.</li>
 * <li>The price : the value of this resource when it is selled</li>
 * <li>The url : the absolute path as a String to the image</li>
 * </ul>
 * <p>
 * Those informations are mandatory. But some others aren't : they are gave as
 * annotations. Two types of resources annotation exists : the tags and the pack
 * groups.
 * </p>
 * <p>
 * {@link io.github.martinheywang.products.api.model.resource.annotation.Tag}
 * defines a property and a value, and may also give a target class. Some
 * devices may be searching for particular property to get a value. The device
 * provider should say it explicitly if the device has a property, and the
 * expected value(s).
 * </p>
 * <p>
 * Then comes the pack groups. Those got two fields : the target, also and id
 * that a device will search, and the actual value : a list of
 * {@link io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack}.
 * Again, some device may search for a specific id to get the value.
 * </p>
 * 
 * @author Martin Heywang
 *
 */
public interface Resource extends ExtensionPoint, Persistable {

    /**
     * Returns the displayable name of the resource.
     * 
     * @return the name
     */
    public String getName();

    /**
     * Returns the value (the sell-price) of the resource.
     * 
     * @return the sell-price of the resource.
     */
    public BigInteger getPrice();

    /**
     * <p>
     * Returns a String representing a relative path to the view representing this
     * resource.
     * </p>
     * <p>
     * For example, the following expression :
     * <code>this.getClass().getResourceAsStream(this.getURL())</code> must not be
     * null, where the url is the value returned by the <code>getURL()</code>
     * method, and <code>this.getClass()</code> the class where the valeu is
     * defined.
     * </p>
     * 
     * @return the local url of the image
     */
    public String getURL();
}
