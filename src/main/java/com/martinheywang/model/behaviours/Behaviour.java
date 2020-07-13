package com.martinheywang.model.behaviours;

import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Template;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.view.GameController;

import javafx.scene.Node;

public abstract class Behaviour {

	protected GameController controller;
	protected Level level;

	protected Device device;
	protected DeviceModel model;

	protected Template template;

	/**
	 * Builds a new behaviour
	 * 
	 * @param device     the associated device
	 * @param controller the current game controller
	 * @param args       additionnal args (read the documentation)
	 */
	public Behaviour(Device device, GameController controller) {
		this.device = device;

		this.model = device.getModel();
		this.template = device.getTemplate();

		this.controller = controller;
		this.level = model.getLevel();
	}

	/**
	 * Do what the devices is meant to do when it recieves a Resource.
	 * 
	 * @param resource the resource to use by this device
	 * @throws MoneyException if money reaches 0 or less
	 */
	public abstract void action(Pack resource)
			throws MoneyException;

	/**
	 * Returns a list of widgets that this behaviour needs. Those will be
	 * displayed in a separate part on the dashboard of the device.
	 * 
	 * @return the widgets of the device
	 */
	public abstract List<Node> getWidgets();

	/**
	 * 
	 * @return the bounded device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * 
	 * @return the model of the bounded device
	 */
	public DeviceModel getModel() {
		return model;
	}

	/**
	 * 
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * 
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * 
	 * @param newLevel the new level
	 */
	public void setLevel(Level newLevel) {
		this.level = newLevel;
	}

	/**
	 * 
	 * @param template the new template
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}
}
