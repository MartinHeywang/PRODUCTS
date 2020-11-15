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
 * Bundle class that defines the prices for upgrading the game loop : making it faster.
 * 
 * @author Martin Heywang
 */
public class GameLoopUpdate extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
	return new Object[][] { { "950", "22000" }, { "900", "50000" }, { "850", "100000" }, { "800", "500000" },
	    { "750", "1200000" }, { "700", "2000000" }, { "650", "10000000" }, { "600", "40000000" },
	    { "550", "100000000" } };

    }
}
