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

import java.math.BigInteger;

import org.pf4j.ExtensionPoint;

/**
 * 
 * <p>
 * This interface represents a <em>Resource</em> that can be used by all the
 * devices. A Resource is anything that can be used by devices, tranformed into
 * other or used to build others. <strong> Only enums should implement this
 * interface.</strong>
 * </p>
 * <p>
 * Using the annotations, such as :
 * </p>
 * <ul>
 * <li>{@link Buyable}</li>
 * <li>{@link ToWire}</li>
 * <li>{@link ToPlate}</li>
 * <li>{@link ToIngot}</li>
 * <li>{@link Craftable}</li>
 * </ul>
 * <p>
 * You can add additionnal informations about a particular resource. In the
 * GitHub repo you will find a lot of example defining resources.
 * </p>
 * 
 * @author Martin Heywang
 *
 */
public interface Resource extends ExtensionPoint {

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
