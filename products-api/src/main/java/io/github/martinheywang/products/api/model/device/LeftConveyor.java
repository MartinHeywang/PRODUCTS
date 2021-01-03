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

import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * A left-conveyor is a modified version of the {@link io.github.martinheywang.products.kit.device.Conveyor} that has a different
 * {@link io.github.martinheywang.products.api.model.template.Template}. It
 * takes an entry at the top, and an exit on the left.
 * 
 * @see io.github.martinheywang.products.api.model.device.Device
 */
 
@AccessibleName("Convoyeur à gauche")
@DefaultTemplate(top = PointerType.ENTRY, left = PointerType.EXIT)
public final class LeftConveyor extends Conveyor {

    /**
	 * Creates a new LeftConveyor.
	 * 
	 * @param model a device model, where the type may be null.
	 */
    public LeftConveyor(DeviceModel model) {
        super(model);
    }
}
