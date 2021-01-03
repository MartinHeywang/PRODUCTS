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
package io.github.martinheywang.products.api.model.device;

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.exception.MoneyException;

/**
 * A floor is a {@link io.github.martinheywang.products.api.model.device.Device}
 * that isn't buildable, can't receive resource in any form and won't perform
 * any action.
 */
@AccessibleName("Sol")
@ActionCost("0")
public final class Floor extends Device {

    /**
     * Creates a new Floor.
     * 
     * @param model a device model, where the type may be null.
     */
    public Floor(DeviceModel model) {
        super(model);
    }

    @Override
    public Action act(Pack resources) throws MoneyException {
        /*
         * The floor is a special device that does obviously nothing. But it doesn't
         * have any entry or exit, so it cannot be triggered by anything. To avoid some
         * NullPointerException, we'll just return an empty Action.
         */
        return new Action(this, resources);
    }

}
