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
package io.github.martinheywang.products.model.bundle;

import java.util.ListResourceBundle;

/**
 * Bundle class that defines the price for upgrading the independent limit count.
 * It associates the key (the count limit) with the value (the price to go to the key value)
 * 
 * @author Martin Heywang
 */
public class MaxBuyerUpdate extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
	return new Object[][] { { "8", "15000" }, { "12", "40000" }, { "16", "100000" }, { "20", "150000" },
	    { "24", "500000" }, { "28", "1000000" }, { "32", "8500000" }, { "36", "14000000" },
	    { "40", "59000000" }, { "44", "150000000" }, { "48", "8000000000" }, { "52", "96000000000" },
	    { "56", "140000000000" }, { "60", "8700000000000" } };
    }
}
