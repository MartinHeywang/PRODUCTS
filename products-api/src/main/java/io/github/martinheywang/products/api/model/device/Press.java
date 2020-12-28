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
import io.github.martinheywang.products.api.model.resource.DefaultResource;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack;
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.utils.ResourceUtils;

/**
 * <p>
 * A press is a {@link io.github.martinheywang.products.api.model.device.Device}
 * that presses the received resource, - if it's possible -. It it's not, the
 * action won't be successful and the assembly line will not give suite.
 * </p>
 * <p>
 * To know whether a resource is pressable or not, the furnace searches for an
 * {@link io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup}
 * targeting "press".
 * </p>
 */

@AccessibleName("Presse")
@Description("Le presse transforme les resources qui lui parviennent en plaques.")
@Prices(build = "2000", upgradeTo2 = "15000", upgradeTo3 = "500000", destroyAt1 = "1800", destroyAt2 = "13000", destroyAt3 = "450000")
@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("20")
public class Press extends Device {

	/**
	 * Creates a new Press.
	 * 
	 * @param model a device model, where the type may be null.
	 */
	public Press(DeviceModel model) {
		super(model);
	}

	@Override
	public final Action act(Pack resources) throws MoneyException {
		final Action action = new Action(this, resources);

		final Resource resource = resources.getResource();

		if (ResourceUtils.hasGroup(resource, "press")) {
			final AnnotationPackGroup group = ResourceUtils.getGroup(resource, "press");
			final AnnotationPack annotation = group.value()[0];
			Resource transformed;
			try {
				transformed = (Resource) annotation.clazz().getField(annotation.field()).get(null);
				final Coordinate output = this.template.getPointersFor(PointerType.EXIT).get(0);
				action.setOutput(output);
				action.setGivenPack(new Pack(transformed, resources.getQuantity()));
				action.markAsSuccessful();
				action.addCost(this.getActionCost());
			} catch (final Exception e) {
				e.printStackTrace();
				transformed = DefaultResource.NONE;
			}
		}

		return action;
	}
}