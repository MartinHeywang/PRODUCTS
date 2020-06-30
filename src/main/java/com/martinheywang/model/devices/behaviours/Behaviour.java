package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public abstract class Behaviour {

	protected Level level;
	protected GameController controller;

	protected Device device;
	protected DeviceModel model;

	public Behaviour(Device device, GameController controller) {
		this.device = device;
		this.model = device.getModel();
		this.controller = controller;
		this.level = model.getLevel();
	}

	/**
	 * Do what the devices is meant to do when it recieves a Resource.
	 * 
	 * @param resource the resource to use by this device
	 * @throws MoneyException if money reaches 0 or less
	 */
	public abstract void action(Pack resource, Coordinate pointer)
			throws MoneyException;

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

	public Level getLevel() {
		return level;
	}

	public void upgrade() {
		final int newOrdinal = level.ordinal() + 1;
		if (newOrdinal > Level.values().length) {
			return;
		}
		level = Level.values()[newOrdinal];
	}

	public void setLevel(Level newLevel) {
		this.level = newLevel;
	}
}
