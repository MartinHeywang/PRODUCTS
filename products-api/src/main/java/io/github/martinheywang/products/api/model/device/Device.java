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
import java.util.Arrays;
import java.util.List;

import org.pf4j.ExtensionPoint;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.action.Iteration;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.model.template.Template;
import io.github.martinheywang.products.api.model.template.TemplateModel;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import javafx.scene.Node;

/**
 * @author Martin Heywang
 */
public abstract class Device implements ExtensionPoint {
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
	 * Whether this device is overload or not.
	 */
	protected boolean overload = false;

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
	 * Returns the list of widgets of this device. A widget is a component displayed
	 * in the device menu.
	 * 
	 * @return a list of {@link javafx.scene.Node}, considered as widgets.
	 */
	public List<Node> getWidgets() {
		return Arrays.asList();
	}

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
	public void clearIteration() {
		this.iteration = new Iteration(this);
		this.setOverload(false);
	}

	/**
	 * (Re-)Generates the template of the device, based on the device type and the
	 * rotation.
	 */
	public void generateTemplate() {
		final TemplateModel templateModel = StaticDeviceDataRetriever.getDefaultTemplate(this.getClass());
		this.template = templateModel.create(this.getPosition(), this.getDirection());
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return this.template;
	}

	/**
	 * Shortcut to
	 * {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever}.
	 * 
	 * @return the action cost
	 */
	protected BigInteger getActionCost() {
		if (this.getClass().isAnnotationPresent(ActionCost.class))
			return new BigInteger(this.getClass().getAnnotation(ActionCost.class).value());

		return new BigInteger("5");
	}

	/**
	 * <p>
	 * Returns a prices modules that get you the information you need about the
	 * prices of this device type.
	 * </p>
	 * 
	 * <p>
	 * <em>Consider using {@link #getUpgradePrice()} and {@link #getDeletePrice()}
	 * when you want to access the upgrade and delete price of the device.</em>
	 * </p>
	 * 
	 * @return a prices modules
	 * @deprecated use
	 *             {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever#getPrices(Class)}
	 *             instead.
	 */
	@Deprecated
	protected PricesModule getPrices() {
		if (this.getClass().isAnnotationPresent(Prices.class)) {
			final Prices annotation = this.getClass().getAnnotation(Prices.class);
			return new PricesModule(annotation.build(), annotation.upgradeTo2(), annotation.upgradeTo3(),
					annotation.destroyAt1(), annotation.destroyAt2(), annotation.destroyAt3());
		}
		return new PricesModule("0", "0", "0", "0", "0", "0");
	}

	/**
	 * Shortcut of
	 * {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever#getEntriesCount(Class)}.
	 * 
	 * @return the entries count
	 */
	public int getEntriesCount() {
		return StaticDeviceDataRetriever.getEntriesCount(this.getClass());
	}

	/**
	 * Shortcut of
	 * {@link io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever#getExitsCount(Class)}.
	 * 
	 * @return the exits count
	 */
	public int getExitsCount() {
		return StaticDeviceDataRetriever.getExitsCount(this.getClass());
	}

	/**
	 * Returns how many times this device might act in a single iteration.
	 * 
	 * @return the max act count.
	 */
	public int getMaxActCount() {
		if (this.getEntriesCount() == 0)
			return this.getLevel().getValue();

		// entries count * the level value * two
		// that's actually pretty wide but it avoids cheated assembly lines.
		return this.getEntriesCount() * this.getLevel().getValue() * 2;
	}

	/**
	 * Shortcut for {@link DeviceModel#getURL()}.
	 * 
	 * @return an url as string
	 * @deprecated see {@link DeviceModel#getURL()} for explanation.
	 */
	@Deprecated
	public String getURL() {
		return this.model.getURL();
	}

	/**
	 * See {@link DeviceModel#getUpgradedURL()}.
	 * 
	 * @return an url as string
	 */
	public String getUpgradedURL() {
		return this.model.getUpgradedURL();
	}

	/**
	 * 
	 * @return the model (persistent data)
	 */
	public DeviceModel getModel() {
		return this.model;
	}

	/**
	 * 
	 * @return the level of this Device object.
	 */
	public Level getLevel() {
		return this.model.getLevel();
	}

	/**
	 * 
	 * @return the game of this Device object.
	 */
	public Game getGame() {
		return this.model.getGame();
	}

	/**
	 * 
	 * @return the direction of this Device object.
	 */
	public Direction getDirection() {
		return this.model.getDirection();
	}

	/**
	 * 
	 * @return the position of this Device object.
	 */
	public Coordinate getPosition() {
		return this.model.getPosition();
	}

	/**
	 * 
	 * @return whether this device is overload.
	 */
	public boolean isOverload() {
		return this.overload;
	}

	/**
	 * Returns the price (or gain) of deleting this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getDeletePrice() {
		final String key = this.getDeletePriceKey();
		return this.getPrices().getPriceFromKey(key);
	}

	/**
	 * Returns the price of upgrading this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getUpgradePrice() {
		final String key = this.getUpgradePriceKey();
		return this.getPrices().getPriceFromKey(key);
	}

	/**
	 * Returns the valid delete price key for this device.
	 * 
	 * @return a string
	 */
	private String getDeletePriceKey() {
		return this.getLevel().toString().toLowerCase() + "_delete";
	}

	/**
	 * Returns the valid upgarde price key for this device.
	 * 
	 * @return a string
	 */
	private String getUpgradePriceKey() {
		return this.getLevel().getNext().toString().toLowerCase() + "_build";
	}

	/**
	 * Returns the current IterationReport.
	 * 
	 * @return the current report.
	 */
	public Iteration getCurrentIteration() {
		return this.iteration;
	}

	/**
	 * @param value the new value
	 */
	public void setOverload(boolean value) {
		this.overload = value;
	}
}