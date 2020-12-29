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
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.template.Template;
import io.github.martinheywang.products.api.model.template.TemplateModel;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;

import org.pf4j.ExtensionPoint;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * Base class for all devices. A Device is an unit (represented as a square in
 * the game) that deals with {@link Resource resources}. These enters the device
 * with a certain entry and leaves the device after some sort of transformation
 * using another side of the device. The {@link Template} is the data structures
 * that manages where resources can either enter or leave. Any device generates
 * an {@link Action} when its triggered : it allows to get a trace of what the
 * device do.
 */
public abstract class Device implements ExtensionPoint {

	/**
	 * The model is the part of the device that persists in the database.
	 */
	protected final ObjectProperty<DeviceModel> model;

	/**
	 * The template defines all the entries and exits of the device.
	 */
	protected final ObjectProperty<Template> template;

	/**
	 * All the actions done by this Device object.
	 */
	protected final ListProperty<Action> actions;

	/**
	 * Creates a new Device.
	 * 
	 * @param model the model to build this device.
	 */
	public Device(DeviceModel model) {
		this.model = new SimpleObjectProperty<>(model);
		this.model.get().setType(this.getClass());

		this.template = new SimpleObjectProperty<>();
		this.actions = new SimpleListProperty<>(FXCollections.observableArrayList());

		this.generateTemplate();
	}

	/**
	 * <p>
	 * Do whatever the device is meant to do when it is called by the previous
	 * device in the assembly line. In most cases, you should call the next device
	 * to keep the idea of an assembly line.
	 * </p>
	 * 
	 * <p>
	 * <strong>Note:</strong> If the device is registered in the auto active device
	 * list, the 'resources' is always equal to null, so it the action musn't depend
	 * on it.
	 * </p>
	 * 
	 * @param resources the resource given by the previous device
	 * @return the report of the action
	 * @throws MoneyException whenever the game doesn't have enough money (don't
	 *                        forget to check that)
	 */
	public abstract Action act(Pack resources) throws MoneyException;

	/**
	 * This inheritable method does not nothing by default. You don't need everytime
	 * to inherit it. But in case your device uses packs, in this method you should
	 * save these packs to make sure they are stored in the database.
	 */
	public void saveElements() {
	}

	/**
	 * (Re-)Generates the template of the device, based on the device type and the
	 * rotation.
	 */
	public final void generateTemplate() {
		final TemplateModel templateModel = StaticDeviceDataRetriever.getDefaultTemplate(this.getClass());
		this.template.set(templateModel.create(this.getPosition(), this.getDirection()));
	}

	/**
	 * The model is the part of the device that is persistant.
	 * 
	 * @return the model property
	 */
	public ReadOnlyObjectProperty<DeviceModel> modelProperty() {
		return model;
	}

	/**
	 * The template indicates where resources can enter and leave the device after
	 * treatment.
	 * 
	 * @return the template property
	 */
	public ReadOnlyObjectProperty<Template> templateProperty() {
		return template;
	}

	/**
	 * All the actions done by the device.
	 * 
	 * @return the actions
	 */
	public ReadOnlyListProperty<Action> actionsProperty() {
		return actions;
	}

	/**
	 * @return the model
	 * @see #modelProperty()
	 */
	public DeviceModel getModel() {
		return model.get();
	}

	/**
	 * @return the position
	 * @see DeviceModel#getPosition()
	 */
	public Coordinate getPosition() {
		return model.get().getPosition();
	}

	/**
	 * @return the direction
	 * @see DeviceModel#getDirection()
	 */
	public Direction getDirection() {
		return model.get().getDirection();
	}

	/**
	 * @return the template
	 * @see #templateProperty()
	 */
	public Template getTemplate() {
		return template.get();
	}
}