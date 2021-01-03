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

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * A conveyor is a simple
 * {@link io.github.martinheywang.products.api.model.device.Device} that takes
 * the resource and give it simply to the next device without changing it in any
 * form.
 */
 
@AccessibleName("Convoyeur")
@Description("Le convoyeur transporte les ressources d'u point A à un point B")
@Prices(build = "100", upgradeTo2 = "5000", upgradeTo3 = "100000", destroyAt1 = "100", destroyAt2 = "5000", destroyAt3 = "100000")
@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("5")
public class Conveyor extends Device {

	/**
	 * Creates a new Conveyor
	 * 
	 * @param model a device model, wherez the type may be null.
	 */
	public Conveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public final Action act(Pack resources) throws MoneyException {
		final Action action = new Action(this, resources);

		final Coordinate output = this.template.get().getPointersFor(PointerType.EXIT).get(0);
		action.setOutput(output);
		action.setGivenPack(resources);
		action.addCost(this.getActionCost());
		action.markAsSuccessful();

		return action;
	}

}
