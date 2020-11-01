package io.github.martinheywang.products.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Iteration;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.Independent;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.LoadException;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.model.template.Template;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.toolbox.ArrayList2D;
import io.github.martinheywang.products.model.database.Database;
import io.github.martinheywang.products.model.device.Floor;

/**
 * <p>
 * The DeviceManager class is a final class that is built to manage devices. It
 * takes care only of the data bit and don't care of the view.
 * </p>
 * 
 * @author Martin Heywang
 */
public final class DeviceController {

	/**
	 * A list that contains all classes (types) that a device can be. It is
	 * filled at the beginning of the program using
	 * {@link #registerType(Class)}.
	 */
	public final static List<Class<? extends Device>> knownTypes = new ArrayList<>();

	/**
	 * A bi-dimmensionnal array (a square) that stores the devices.
	 */
	private final ArrayList2D<Device> devices = new ArrayList2D<>();

	/**
	 * This list is all about the auto active devices. It contains all the
	 * devices that can be triggered without giving any resources. For example,
	 * all the buyers are in here.
	 */
	private final List<Device> independentDevices = new ArrayList<>();

	/**
	 * The game that all the devices belong.
	 */
	private final Game game;

	private final Integer maxIndependent;

	/**
	 * Creates a new DeviceManager filled with the given collection of devices
	 * 
	 * @param devicesModel
	 *            the models of the devices
	 * @param gameManager
	 *            the current game manager
	 */
	public DeviceController(Game game) throws LoadException {
		this.game = game;
		this.maxIndependent = game.getMaxBuyer();

		// Fetching the devices right from the database.
		List<DeviceModel> devicesModels = null;
		try {
			devicesModels = Database.createDao(DeviceModel.class)
					.queryForEq("game_id", game.getID());
		} catch (final SQLException e) {
			e.printStackTrace();
			throw new LoadException(game);
		}

		// Filling the grid with the existing devices.
		for (final DeviceModel deviceModel : devicesModels) {
			final Device device = deviceModel.instantiate();
			this.devices.add(device, deviceModel.getPosition().getX(),
					deviceModel.getPosition().getY());

			// Check if this device is auto-active
			if (device.getClass().isAnnotationPresent(Independent.class)) {
				final boolean autoActive = device.getClass()
						.getAnnotation(Independent.class).value();
				if (autoActive)
					this.independentDevices.add(device);
			}
		}

		// Filling empty nodes of the grid with newly created floors.
		final int gridSize = game.getGridSize();
		for (int x = 0; x < gridSize; x++)
			for (int y = 0; y < gridSize; y++)
				if (this.devices.get(x, y) == null) {
					final Device device = new DeviceModel(Floor.class,
							Level.LEVEL_1, Direction.UP, game,
							new Coordinate(x, y)).instantiate();
					this.devices.add(device, x, y);
				}
	}

	/**
	 * Checks if a connection exists b/w the two coordinates, in other word, if
	 * the device located at the first coordinate can send resources to the
	 * device situated at the second coordinate. If such a thing is true, the
	 * reversed (e.g. <code>connectionExists(to, from)</code>) must be false, as
	 * it is not reciprocal.
	 * 
	 * @param from
	 *            the first coordinate (the sender)
	 * @param to
	 *            the second coordinate (the receiver)
	 * @return true, if the receiver can receive resources from the sender, else
	 *         false.
	 */
	public boolean connectionExists(Coordinate from, Coordinate to) {
		if (from.isInGrid() && to.isInGrid()) {
			final Template firstTemplate = this.devices
					.get(from.getX(), from.getY()).getTemplate();
			final Template secondTemplate = this.devices
					.get(to.getX(), to.getY()).getTemplate();

			for (final Coordinate output : firstTemplate
					.getPointersFor(PointerType.EXIT))
				if (output.propertiesEquals(to))
					for (final Coordinate input : secondTemplate
							.getPointersFor(PointerType.ENTRY))
						if (input.propertiesEquals(from))
							return true;
		}
		return false;
	}

	/**
	 * Returns the device at the given coordinate.
	 * 
	 * @param to
	 *            where
	 * @return the device
	 */
	public Device getDevice(Coordinate to) {
		return this.devices.get(to.getX(), to.getY());
	}

	/**
	 * Returns the devices
	 * 
	 * @return the bi-dimmensionnal array with the devices. (Please be careful
	 *         when using it.)
	 */
	public ArrayList2D<Device> getDevices() {
		return this.devices;
	}

	/**
	 * @return the independents devices
	 */
	public List<Device> getIndependentDevices() {
		return this.independentDevices;
	}

	public void addIndependentDevice(Device device) {
		if (device.getClass().isAnnotationPresent(Independent.class))
			return;

		this.independentDevices.add(device);
	}

	public void removeIndependentDevice(Device device) {
		this.independentDevices.remove(device);
	}

	/**
	 * 
	 * @return the count of indepedent devices currently built in the grid.
	 */
	public Integer getIndependentDevicesCount() {
		return this.independentDevices.size();
	}

	/**
	 * 
	 * @return the max independent value
	 */
	public Integer getMaxIndependent() {
		return this.maxIndependent;
	}

	/**
	 * <p>
	 * Upgrades the game grid-size
	 * </p>
	 * <p>
	 * Will create Floors around the bottom and right sides. For example, a grid
	 * of 3x3 device will become as big as a 4x4 grid.
	 * </p>
	 */
	public void upgradeGrid() {
		final int oldSize = this.devices.size();
		final int newSize = this.devices.size() + 1;
		for (int x = 0; x < newSize; x++)
			for (int y = 0; y < newSize; y++)
				if (x >= oldSize || y >= oldSize) {
					final Device device = new DeviceModel(Floor.class,
							Level.LEVEL_1, Direction.UP, this.game,
							new Coordinate(x, y)).instantiate();
					this.devices.add(device, x, y);
				}
	}

	/**
	 * Replaces the device at the given coordinate with a new Device using all
	 * the information and the game that this DeviceManager was initiliazed
	 * with.
	 * 
	 * @param clazz
	 *            the class of the new device
	 * @param level
	 *            the level
	 * @param direction
	 *            the direction
	 * @param position
	 *            the position (where to replace)
	 */
	public void replace(Class<? extends Device> clazz, Level level,
			Direction direction, Coordinate position) {
		// Create the new device
		final DeviceModel newModel = new DeviceModel(clazz, level, direction,
				this.game, position);
		final Device newDevice = newModel.instantiate();

		this.setDevice(newDevice, position);
	}

	/**
	 * Sets the given device to the given position
	 * 
	 * @param device
	 *            the device
	 * @param to
	 *            the position.
	 */
	public void setDevice(Device device, Coordinate to) {
		this.devices.set(device, to.getX(), to.getY());
		device.getModel().setPosition(to);
	}

	public void clearIterations() {
		final Collection<Device> collection = this.devices.toCollection();
		for (final Device device : collection)
			if (!device.getClass().equals(Floor.class))
				device.clearIteration();
	}

	/**
	 * Saves the loaded devices in the database.
	 * 
	 * @throws SQLException
	 *             if an error occurs with the database
	 */
	public void save() throws SQLException {

		final Dao<DeviceModel, Long> modelDao = Database
				.createDao(DeviceModel.class);
		final Dao<Pack, Long> packDao = Database.createDao(Pack.class);
		final Dao<Coordinate, Long> coordinateDao = Database
				.createDao(Coordinate.class);

		// DELETE DEVICE MODELS AND PACKS
		final List<DeviceModel> models = modelDao.queryForEq("game_id",
				this.game.getID());
		for (final DeviceModel model : models) {
			modelDao.delete(model);
			final DeleteBuilder<Pack, Long> packDeleteBuilder = packDao
					.deleteBuilder();
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
					.setPosition(coordinateDao.queryBuilder().where()
							.eq("x", current.getPosition().getX()).and()
							.eq("y", current.getPosition().getY())
							.queryForFirst());
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
	 * Register the given type as a known type of device.
	 * 
	 * @param type
	 *            the type to register
	 */
	public static void registerType(Class<? extends Device> type) {
		knownTypes.add(type);
	}

	/**
	 * Returns whether the given device is ready to act. It checks for example,
	 * if the cooldown is active or not.
	 */
	public static final boolean isActReady(Device device) {
		final Iteration iteration = device.getCurrentIteration();

		if (iteration.getActCount() < iteration.getDevice().getMaxActCount())
			return true;

		return false;
	}


}
