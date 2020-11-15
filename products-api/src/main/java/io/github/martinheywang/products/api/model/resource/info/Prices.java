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
package io.github.martinheywang.products.api.model.resource.info;

import java.math.BigInteger;

/**
 * <p>
 * Utility class that defines default sell prices for resources.
 * </p>
 */
public final class Prices {

	// This class is mostly used as an interface, with static methods returning
	// values.
	private Prices() {

	}

    /**
     * @return the default sell-price of raw resources.
     */
    public static BigInteger rawPrice() {
		return new BigInteger("75");
	}

    /**
     * 
     * @return the default sell-price of transformed (not crafted) resources.
     */
    public static BigInteger transformedPrice() {
		return new BigInteger("130");
	}
}
