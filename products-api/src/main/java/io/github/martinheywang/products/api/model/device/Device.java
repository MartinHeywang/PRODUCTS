package io.github.martinheywang.products.api.model.device;

import java.math.BigInteger;
import java.util.List;

import org.pf4j.ExtensionPoint;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.action.Iteration;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.model.template.Template;
import io.github.martinheywang.products.api.model.template.TemplateCreator;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import javafx.scene.Node;

/**
 * @author Martin Heywang
 */
@AccessibleName
@Description
@Prices(build = "0", upgradeTo2 = "0", upgradeTo3 = "0", destroyAt1 = "0", destroyAt2 = "0", destroyAt3 = "0")
@DefaultTemplate
@ActionCost
public abstract class Device implements ExtensionPoint {
	/**
	 * The model is the part of the device that persists in the database.
	 * 
	 * @see com.martinheywang.products.model.device.DeviceModel
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
	 * @param type      the type
	 * @param direction the direction
	 * @param level     the level
	 * @param position  the position
	 * @param game      the game
	 */
	public Device(DeviceModel model) {
		this.model = model;

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
	public void clearIteration() {
		this.iteration = new Iteration(this);
		this.setOverload(false);
	}

	/**
	 * Generates the template of the device
	 */
	public void generateTemplate() {
		final DefaultTemplate annotation = this.getClass().getAnnotation(DefaultTemplate.class);
		this.template = TemplateCreator.getSingleton().setTop(annotation.top()).setRight(annotation.right())
				.setBottom(annotation.bottom()).setLeft(annotation.left()).getModel()
				.create(this.getPosition(), this.getDirection());
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return this.template;
	}

	/**
	 * <p>
	 * Returns the action cost of this device, how many money it spend each time it
	 * is called.
	 * </p>
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
	 */
	protected PricesModule getPrices() {
		if (this.getClass().isAnnotationPresent(Prices.class)) {
			final Prices annotation = this.getClass().getAnnotation(Prices.class);
			return new PricesModule(annotation.build(), annotation.upgradeTo2(), annotation.upgradeTo3(),
					annotation.destroyAt1(), annotation.destroyAt2(), annotation.destroyAt3());
		}
		return new PricesModule("0", "0", "0", "0", "0", "0");
	}

	/**
	 * 
	 * @return the number of entries that this device has
	 */
	public int getEntriesCount() {
		return this.template.getPointersFor(PointerType.ENTRY).size();
	}

	/**
	 * 
	 * @return the number of exits that this device has.
	 */
	public int getExitsCount() {
		return this.template.getPointersFor(PointerType.EXIT).size();
	}

	/**
	 * Returns how many times this device might act in a single iteration.
	 * 
	 * @return
	 */
	public int getMaxActCount() {
		if (this.getEntriesCount() == 0)
			return this.getLevel().getValue();

		// entries count * the level value * two
		// that's actually pretty wide but it avoids cheated assembly lines.
		return this.getEntriesCount() * this.getLevel().getValue() * 2;
	}

	public abstract List<Node> getWidgets();

	/**
	 * See {@link DeviceModel#getURL()}.
	 * 
	 * @return an url as string
	 */
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