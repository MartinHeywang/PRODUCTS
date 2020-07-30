package com.martinheywang.model.mechanics;

import java.util.Collection;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;
import com.martinheywang.toolbox.ArrayList2D;

/**
 * <p>
 * The DeviceManager class is a final class that is built to manage
 * devices. It takes care only of the data bit and don't care of the
 * view.
 * </p>
 * 
 * @author Martin Heywang
 */
public final class DeviceManager {

	/**
	 * A bi-dimmensionnal array (a square) that stores the devices.
	 */
	private final ArrayList2D<Device> devices = new ArrayList2D<>();

	/**
	 * The current game manager
	 */
	private final GameManager gameManager;

	public DeviceManager(Collection<DeviceModel> devicesModel,
			GameManager gameManager) {
		this.gameManager = gameManager;
		for (DeviceModel deviceModel : devicesModel) {
			final Device device = deviceModel.instantiate();
			this.devices.add(device,
					deviceModel.getPosition().getX(),
					deviceModel.getPosition().getY());
			device.manageWith(gameManager);
		}
	}

	public ArrayList2D<Device> getDevices() {
		return devices;
	}

	public Device getDevice(Coordinate to) {
		return devices.get(to.getX(), to.getY());
	}

	public void replace(Class<? extends Device> clazz, Level level,
			Direction direction, Coordinate position) {
		// Create the new device
		Device newDevice = new DeviceModel(clazz, level, direction,
				gameManager.getGame(), position).instantiate();
		newDevice.manageWith(gameManager);

		// And replace it at the given coords
		devices.set(newDevice, position.getX(), position.getY());
	}

}
