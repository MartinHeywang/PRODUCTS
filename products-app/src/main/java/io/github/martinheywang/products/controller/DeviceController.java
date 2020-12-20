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
package io.github.martinheywang.products.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import io.github.martinheywang.products.api.database.Database;
import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Iteration;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.Independent;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.DeviceException;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.utils.ArrayList2D;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import io.github.martinheywang.products.kit.device.Floor;

/**
 * <p>
 * The DeviceController class is a sub-part of the {@link GameController} that
 * targets the management of the devices.
 * </p>
 * <p>
 * It allows to {@link #build(Coordinate)}, {@link #upgrade(Coordinate)},
 * {@link #turn(Coordinate)}, {@link #destroy(Coordinate)} devices easily. And
 * you check connections and ability to send resources with
 * {@link #connectionExists(Device, Device)}.
 * </p>
 * 
 * @author Martin Heywang
 */
public final class DeviceController {

	/**
	 * The 'knownTypes' is a list containing all the types that a device can have in
	 * the game.
	 */
	public final static List<Class<? extends Device>> knownTypes = new ArrayList<>();

	private final GameController controller;
	private final Game game;

	private final ArrayList2D<Device> devices = new ArrayList2D<>();
	private final List<DeviceModel> lockedModels;
	private final List<Device> independentDevices = new ArrayList<>();

	private Integer maxIndependent;

	private Class<? extends Device> buildClass;
	private Coordinate swapTempo;

	/**
	 * Creates a new DeviceController filled with the given collection of devices
	 * 
	 * @param controller the game controller
	 * @param models     the devices models to manage
	 */
	public DeviceController(GameController controller, List<DeviceModel> models) {
		this.controller = controller;
		this.lockedModels = models;
		this.game = controller.getGame();
		this.maxIndependent = game.getMaxIndependent();

		// Filling the grid with the existing devices.
		for (final DeviceModel deviceModel : models) {
			final Device device = deviceModel.instantiate();
			this.devices.add(device, deviceModel.getPosition().getX(), deviceModel.getPosition().getY());

			// Check if this device is independent
			if (device.getClass().isAnnotationPresent(Independent.class)) {
				final boolean autoActive = device.getClass().getAnnotation(Independent.class).value();
				if (autoActive) {
					this.independentDevices.add(device);
				}
			}
		}

		// Filling empty nodes of the grid with newly created floors.
		final int gridSize = game.getGridSize();
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				if (this.devices.get(x, y) == null) {
					final Device device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, game,
							new Coordinate(x, y)).instantiate();
					this.devices.add(device, x, y);
				}
			}
		}
	}

	/**
	 * Sets the build class, used when calling the {@link #build(Coordinate)}.
	 * 
	 * @param clazz the new build clazz
	 */
	void setBuildClass(Class<? extends Device> clazz) {
		if (clazz == null || !StaticDeviceDataRetriever.isBuildable(clazz)) {
			clazz = null;
		}
		this.buildClass = clazz;
	}

	/**
	 * Returns the build class, used when calling the {@link #build(Coordinate)}.
	 * 
	 * @return the build class
	 */
	Class<? extends Device> getBuildClass() {
		return this.buildClass;
	}

	/**
	 * Builds a device at the given coordinate of the type defined in
	 * {@link #buildClass}.
	 * 
	 * @param position where to build, the coordinate of the new device
	 * @throws DeviceException if there are too many independent devices.
	 * @throws MoneyException  if the player doesn't have enough money.
	 */
	public void build(Coordinate position) throws DeviceException, MoneyException {
		if (this.buildClass == null || !StaticDeviceDataRetriever.isBuildable(this.buildClass)) {
			throw new DeviceException("Aucun type valide n'a été sélectionné pour la construction.");
		}
		final Device oldDevice = getDevice(position);
		if(!(oldDevice instanceof Floor)){
			throw new DeviceException("Vous ne pouvez construire des appareils que sur des sols.");
		}
		final BigInteger cost = StaticDeviceDataRetriever.getBuildPrice(this.buildClass);
		controller.removeMoney(cost);

		final Device device = new DeviceModel(this.buildClass, Level.LEVEL_1, Direction.UP, controller.getGame(),
				position).instantiate();

		setDevice(device, position);
		controller.refreshPos(position);
	}

	/**
	 * Destroys the device at the given position.
	 * 
	 * @param position where to destroy
	 * @throws DeviceException if there are no devices built at the given position
	 */
	public void destroy(Coordinate position) throws DeviceException, MoneyException {
		final Device oldDevice = getDevice(position);
		if (oldDevice instanceof Floor) {
			throw new DeviceException("Impossible de détruire un appareil inexistant !");
		}

		final BigInteger gain = oldDevice.getDeletePrice();
		controller.addMoney(gain);

		final Device device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, controller.getGame(), position)
				.instantiate();

		// removes the device from the independent
		// (no need to check if it is actually one)
		independentDevices.remove(oldDevice);

		setDevice(device, position);
		controller.refreshPos(position);
	}

	/**
	 * <p>
	 * Swaps the <strong>two</strong> device using the coordinate.
	 * </p>
	 * <p>
	 * If this method is called for the first time, it just registered the
	 * destination. But if it was already called (the tempo destination is not
	 * <code>null</code>), if performs the swap.
	 * 
	 * @param position where to exchange
	 * @throws DeviceException if an error occurs exchanging the devices.
	 */
	public void swap(Coordinate position) throws DeviceException {
		if (swapTempo == null) {
			swapTempo = position;
		} else {
			final Device first = getDevice(swapTempo);
			final Device second = getDevice(position);

			setDevice(first, position);
			setDevice(second, swapTempo);

			first.generateTemplate();
			second.generateTemplate();

			controller.refreshPos(swapTempo);
			controller.refreshPos(position);

			swapTempo = null;
		}
	}

	/**
	 * Upgrades the device located at the given position.
	 * 
	 * @param position where to upgrade
	 * @throws DeviceException if the device has already reached the max level
	 * @throws MoneyException  if the player doesn't have enough money
	 */
	public void upgrade(Coordinate position) throws DeviceException, MoneyException {
		final Device device = getDevice(position);
		final Level currentLevel = device.getModel().getLevel();

		controller.removeMoney(device.getUpgradePrice());

		if (currentLevel.equals(Level.LEVEL_3)) { // already max level ?
			throw new DeviceException("L'appareil a déjà atteint son niveau maximum.");
		}
		device.getModel().setLevel(currentLevel.getNext());

		controller.refreshPos(position);
	}

	/**
	 * Turns the device located at the given coordinate.
	 * 
	 * @param position where to turn
	 */
	public void turn(Coordinate position) {
		final Device device = getDevice(position);
		device.getModel().setDirection(device.getModel().getDirection().getNext());

		controller.refreshPos(position);
	}

	private void setDevice(Device device, Coordinate position) throws DeviceException {
		if (devices.contains(device)) {
			throw new DeviceException("La grille contient déjà cet appareil.");
		}
		// If the old device was independent, remove it from the list
		final Device oldDevice = getDevice(position);
		if (StaticDeviceDataRetriever.isIndependent(oldDevice.getClass())) {
			independentDevices.remove(device);
		}
		// And add it if the new one is independent
		final boolean isIndependent = StaticDeviceDataRetriever.isIndependent(device.getClass());
		if (isIndependent) {
			final int independentCount = getIndependentDevices().size();

			// By the way, check if the independent devices count is already too high.
			if (independentCount == maxIndependent) {
				throw new DeviceException("Trop d'appareil indépendents sont déjà présents sur la grille.");
			}
			independentDevices.add(device);
		}

		devices.set(device, position.getX(), position.getY());

		device.getModel().setPosition(position);
		device.generateTemplate();
	}

	/**
	 * Cleans up the current iteration in order to begin a new one.
	 */
	public void clearIterations() {
		final Collection<Device> collection = this.devices.toCollection();
		for (final Device device : collection)
			if (!device.getClass().equals(Floor.class))
				device.clearIteration();
	}

	/**
	 * Checks if the first device, the sender, is able to send resources to the
	 * second device, the receiver. If such a thing is true, the inverse must be
	 * false.
	 * 
	 * @param sender   the first device, the one that tries to send resources.
	 * @param receiver the second device, the one that may receive resources.
	 * @return true if a connection exists, else false.
	 */
	public boolean connectionExists(Device sender, Device receiver) {
		// First, check if the device are next one to another
		if (!sender.getPosition().isNearFrom(receiver.getPosition())) {
			return false;
		}

		// Second, look in the templates.
		for (Coordinate exits : sender.getTemplate().getPointersFor(PointerType.EXIT)) {
			if (exits.propertiesEquals(receiver.getPosition())) {
				for (Coordinate entry : receiver.getTemplate().getPointersFor(PointerType.ENTRY)) {
					if (entry.propertiesEquals(sender.getPosition())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Returns whether the given device is ready to act. It checks for example, if
	 * the cooldown is active or not.
	 * 
	 * @param device the device to test
	 * @return the result of the test, whether it is act-ready.
	 */
	public static final boolean isActReady(Device device) {
		final Iteration iteration = device.getCurrentIteration();

		if (iteration.getActCount() < iteration.getDevice().getMaxActCount())
			return true;

		return false;
	}

	/**
	 * Saves the loaded devices in the database.
	 * 
	 * @throws SQLException if an error occurs with the database
	 */
	public void save() throws SQLException {

		final Dao<DeviceModel, Long> modelDao = Database.createDao(DeviceModel.class);
		final Dao<Pack, Long> packDao = Database.createDao(Pack.class);
		final Dao<Coordinate, Long> coordinateDao = Database.createDao(Coordinate.class);

		// DELETE DEVICE MODELS AND PACKS
		final List<DeviceModel> models = modelDao.queryForEq("game_id", this.game.getID());
		for (final DeviceModel model : models) {
			modelDao.delete(model);
			final DeleteBuilder<Pack, Long> packDeleteBuilder = packDao.deleteBuilder();
			packDeleteBuilder.where().eq("model", model.getID());
			packDeleteBuilder.delete();
		}

		for (int x = 0; x < this.devices.size(); x++)
			for (int y = 0; y < this.devices.size(); y++) {
				final Device current = this.devices.get(x, y);
				final DeviceModel model = current.getModel();
				final Coordinate position = model.getPosition();

				if (!current.getClass().equals(Floor.class)) {
					// 'X' and 'Y' columns are marked as a unique combo
					current.getModel()
							.setPosition(coordinateDao.queryBuilder().where().eq("x", current.getPosition().getX())
									.and().eq("y", current.getPosition().getY()).queryForFirst());
					// But in case it doesn't exists, assign the one given as
					// arg.
					if (current.getPosition() == null) {
						model.setPosition(position);
						coordinateDao.create(model.getPosition());
					}

					final Long id = current.getModel().generateID();
					current.getModel().setID(id);
					current.saveElements();

					modelDao.create(current.getModel());
				}
			}

	}

	/**
	 * Returns the device located at the given coordinate.
	 * 
	 * @return the device
	 */
	public Device getDevice(Coordinate position) {
		return devices.get(position.getX(), position.getY());
	}

	/**
	 * Returns the model of the device located at the given coordinate.
	 * 
	 * @return the model
	 */
	public DeviceModel getModel(Coordinate position) {
		return getDevice(position).getModel();
	}

	/**
	 * Returns the current models of the devices. It changes everytime an update is
	 * made.
	 * 
	 * @return the current devices models.
	 */
	public Collection<DeviceModel> getModels() {
		final List<DeviceModel> list = new ArrayList<>();
		final Collection<Device> collection = devices.toCollection();

		for (Device device : collection) {
			list.add(device.getModel());
		}

		return Collections.unmodifiableList(list);
	}

	/**
	 * Returns the collection of all the devices currently in the grid.
	 * 
	 * @return the devices.
	 */
	public Collection<Device> getDevices() {
		return Collections.unmodifiableCollection(devices.toCollection());
	}

	/**
	 * Returns the devices, as {@link #getDevices()}, but as the grid, an
	 * {@link ArrayList2D}.
	 * 
	 * @return the grid of devices.
	 */
	ArrayList2D<Device> getDevices2D() {
		return devices;
	}

	/**
	 * Returns the models given in the constructor. They are not updating when
	 * changes are made.
	 * 
	 * @return the models in the constructor.
	 */
	public Collection<DeviceModel> getModelsUnmodifiable() {
		return Collections.unmodifiableCollection(lockedModels);
	}

	/**
	 * Returns the devices given in the constructor, or the devices ucreated using
	 * the models given in the constructor.
	 * 
	 * @return the devices.
	 */
	public Collection<Device> getDevicesUnmodifiable() {
		return Collections.unmodifiableCollection(devices.toCollection());
	}

	/**
	 * Returns the collection of all independent devices, marked with
	 * {@link io.github.martinheywang.products.api.model.device.annotation.Independent}
	 * annotation.
	 * 
	 * @return the independent devices.
	 */
	public Collection<Device> getIndependentDevices() {
		return Collections.unmodifiableCollection(independentDevices);
	}

	/**
	 * Returns the number corresponding to the maximum count of independent devices
	 * on the grid at the same time.
	 * 
	 * @return the maximum count of independent devices.
	 */
	public Integer getMaxIndependent() {
		return maxIndependent;
	}

	/**
	 * Register the given type as a known type of device.
	 * 
	 * @param type the type to register
	 */
	public static void registerType(Class<? extends Device> type) {
		knownTypes.add(type);
		System.out.println("| - - - " + type.getCanonicalName());
	}

}