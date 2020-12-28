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

import java.math.BigInteger;

import org.pf4j.ExtensionPoint;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.action.Iteration;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.template.Template;
import io.github.martinheywang.products.api.model.template.TemplateModel;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;

/**
 * @author Martin Heywang
 */
public abstract class Device implements ExtensionPoint {

	// TODO verify fields
	/**
	 * The model is the part of the device that persists in the database.
	 * 
	 * @see DeviceModel
	 */
	protected final DeviceModel model;

	/**
	 * The template defines all the entries and exits of the device.
	 * 
	 * 
	 * @see io.github.martinheywang.products.api.model.template.Template
	 */
	protected Template template;

	/**
	 * The IterationReport contains a lot of information about what happens in the
	 * current iteration. A new instance of it is created each iteration.
	 */
	protected Iteration iteration = new Iteration(this);

	/**
	 * Creates a new Device.
	 * 
	 * @param model the model to build this device.
	 */
	public Device(DeviceModel model) {
		this.model = model;
		this.model.setType(this.getClass());

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
	 * Clears the current iteration by replacing it with a new one.
	 */
	public final void clearIteration() {
		this.iteration = new Iteration(this);
	}

	/**
	 * (Re-)Generates the template of the device, based on the device type and the
	 * rotation.
	 */
	public final void generateTemplate() {
		final TemplateModel templateModel = StaticDeviceDataRetriever.getDefaultTemplate(this.getClass());
		this.template = templateModel.create(this.getPosition(), this.getDirection());
	}

	/**
	 * @return the template
	 */
	public final Template getTemplate() {
		return this.template;
	}

	/**
	 * Shortcut to
	 * {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever}.
	 * 
	 * @return the action cost
	 */
	protected final BigInteger getActionCost() {
		if (this.getClass().isAnnotationPresent(ActionCost.class))
			return new BigInteger(this.getClass().getAnnotation(ActionCost.class).value());

		return new BigInteger("5");
	}

	/**
	 * Shortcut of
	 * {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever#getEntriesCount(Class)}.
	 * 
	 * @return the entries count
	 */
	public final int getEntriesCount() {
		return StaticDeviceDataRetriever.getEntriesCount(this.getClass());
	}

	/**
	 * Shortcut of
	 * {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever#getExitsCount(Class)}.
	 * 
	 * @return the exits count
	 */
	public final int getExitsCount() {
		return StaticDeviceDataRetriever.getExitsCount(this.getClass());
	}

	/**
	 * 
	 * @return the model (persistent data)
	 */
	public final DeviceModel getModel() {
		return this.model;
	}

	/**
	 * 
	 * @return the game of this Device object.
	 */
	public final Game getGame() {
		return this.model.getGame();
	}

	/**
	 * 
	 * @return the direction of this Device object.
	 */
	public final Direction getDirection() {
		return this.model.getDirection();
	}

	/**
	 * 
	 * @return the position of this Device object.
	 */
	public final Coordinate getPosition() {
		return this.model.getPosition();
	}

	/**
	 * Returns the current IterationReport.
	 * 
	 * @return the current report.
	 */
	public final Iteration getCurrentIteration() {
		return this.iteration;
	}
}