package com.martinheywang.model.mechanics;

import java.util.Collection;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.toolbox.ArrayList2D;

public final class DeviceManager {

	private final ArrayList2D<Device> devices = new ArrayList2D<>();
	private final GameManager gameManager;

	public DeviceManager(Collection<DeviceModel> devicesModel,
			GameManager gameManager) {
		for (DeviceModel deviceModel : devicesModel) {
			this.devices.add(deviceModel.instantiate(),
					deviceModel.getPosition().getX(),
					deviceModel.getPosition().getY());
		}
		this.gameManager = gameManager;
	}

	public ArrayList2D<Device> getDevices() {
		return devices;
	}

	public Device getDevice(Coordinate to) {
		return devices.get(to.getX(), to.getY());
	}

}
