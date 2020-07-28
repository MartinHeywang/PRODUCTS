package com.martinheywang.model.behaviours;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.templates.Template;
import com.martinheywang.model.templates.TemplateModel;

import javafx.scene.Node;

/**
 * <p>
 * A Behaviour is basically what a Device does each iterations of the
 * game loop. The AbstractBehaviour is the parent class of all
 * behaviours.
 * </p>
 * <p>
 * When overriding this class and unfortunately, the children class
 * must have a single constructor
 * <code>__init__(Device, GameManager)</code> that will be used when
 * creating the behaviour of a device. Instead, you can add
 * additionnal features using fields, getter and setters that your
 * plugin can invoke using casting.
 * </p>
 * 
 * @author Martin Heywang
 */
public abstract class AbstractBehaviour {

	protected Level level;
	protected Device device;

	protected Template template;

	protected GameManager gameManager;

	public AbstractBehaviour(Device device, GameManager gameManager) {
		this.device = device;
		this.level = device.getLevel();
		this.gameManager = gameManager;

		generateTemplate();

		device.directionProperty().addListener(observable -> {
			generateTemplate();
		});
	}

	/**
	 * Recreates the template
	 */
	private final void generateTemplate() {
		final TemplateModel templateModel = device.getTemplateModel();
		// Re-create the new template using the position
		this.template = templateModel.create(device.getPosition());
	}

	/**
	 * Returns the position of this behaviour. Basically, where this
	 * behaviour is applied or where is located the Device using this
	 * specific behaviour.
	 * 
	 * @return the position
	 */
	protected final Coordinate getPosition() {
		return device.getPosition();
	}

	/**
	 * This method is the most important one of a behaviour. It does what
	 * this behaviour is meant to do, whatever this is.
	 * 
	 * @param resource the resource(s) given by the previous Device.
	 * @throws MoneyException if not enough is available to perform this
	 *                        action.
	 */
	public abstract void act(Pack resource) throws MoneyException;

	/**
	 * <p>
	 * A widget is a <code>javafx.scene.Node</code> that will be displayed
	 * on the dashboard (shown on click) of the Device.
	 * </p>
	 * <p>
	 * This methods returns all of them.
	 * </p>
	 * 
	 * @return the widgets.
	 */
	public List<Node> getWidgets() {
		return Arrays.asList();
	}

	public BigInteger getActionCost() {
		return new BigInteger("5");
	}

}
