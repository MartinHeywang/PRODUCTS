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
package io.github.martinheywang.products.api.model.resource;

import java.math.BigInteger;

/**
 * This enum defines a NONE resource. It actually has no effect to any device.
 * 
 * @author Martin Heywang
 */
public enum DefaultResource implements Resource {

    /**
     * Nothing. Does not vault anything, represents nothing.
     */
    NONE("None", 0, "None.png");

    private String name;
    private BigInteger price;
    private String url;

    DefaultResource(String name, long value, String file) {
        this.name = name;
        this.price = BigInteger.valueOf(value);
        this.url = DefaultResource.class.getResource("/images/resources/"+file).toExternalForm();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigInteger getPrice() {
        return this.price;
    }

    @Override
    public String getURL() {
        return this.url;
    }
}
