package com.martinheywang.model.mechanics;

import java.util.Collection;

import com.martinheywang.model.devices.Device;
import com.martinheywang.toolbox.ArrayList2D;

public final class DeviceManager {

	private final ArrayList2D<Device> devices = new ArrayList2D<>();
	private final GameManager gameManager;

	public DeviceManager(Collection<Device> devices, GameManager gameManager) {
		for (Device device : devices) {
			this.devices.add(device, device.getPosition().getX(),
					device.getPosition().getY());
		}
		this.gameManager = gameManager;
	}

	public ArrayList2D<Device> getDevices() {
		return devices;
	}

}
