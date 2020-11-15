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
 * This class is list resou
 */
public class GrilleUpdate extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
	return new Object[][] {
	    { "4", "10000" },
			{ "5", "50000" },
	    { "6", "100000" },
			{ "7", "500000" }, { "8", "1200000" }, { "9", "6000000" }, { "10", "10000000" },
			{ "11", "50000000" }, { "12", "500000000" }, { "13", "1000000000" },
			{ "14", "700000000000" },
	};

    }

}
