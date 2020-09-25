package com.martinheywang.products.model.mechanics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.database.Database;
import com.martinheywang.products.model.devices.Device;
import com.martinheywang.products.model.devices.DeviceModel;
import com.martinheywang.products.model.devices.Floor;
import com.martinheywang.products.model.direction.Direction;
import com.martinheywang.products.model.level.Level;
import com.martinheywang.products.model.templates.Template;
import com.martinheywang.products.model.templates.Template.PointerTypes;
import com.martinheywang.products.toolbox.ArrayList2D;

/**
 * <p>
 * The DeviceManager class is a final class that is built to manage devices. It
 * takes care only of the data bit and don't care of the view.
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
     * A copy of the field 'devices', except that it doesn't updates whenever a
     * change is made to the first one. Used for database purposes.
     */
    private final ArrayList2D<Device> lockedDevices = new ArrayList2D<>();

    /**
     * The current game manager
     */
    private final GameManager gameManager;

    /*
     * The current game
     */
    private final Game game;

    /**
     * Creates a new DeviceManager filled with the given collection of devices
     * 
     * @param devicesModel the models of the devices
     * @param gameManager  the current game manager
     */
    public DeviceManager(Collection<DeviceModel> devicesModel, GameManager gameManager, Game game) {
        this.gameManager = gameManager;
        this.game = game;

        for (final DeviceModel deviceModel : devicesModel) {
            final Device device = deviceModel.instantiate();
            this.devices.add(device, deviceModel.getPosition().getX(), deviceModel.getPosition().getY());
            this.lockedDevices.add(device, deviceModel.getPosition().getX(), deviceModel.getPosition().getY());
            device.manageWith(gameManager);
        }

        final int gridSize = gameManager.getGridSize();
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (this.devices.get(x, y) == null) {
                    final Device device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, game,
                            new Coordinate(x, y)).instantiate();
                    this.devices.add(device, x, y);
                    this.lockedDevices.add(device, x, y);
                    device.manageWith(gameManager);
                }
            }
        }
    }

    /**
     * Checks if a connection exists b/w the two coordinates, in other word, if the
     * device located at the first coordinate can send resources to the device
     * situated at the second coordinate. If such a thing is true, the reversed
     * (e.g. <code>connectionExists(to, from)</code>) may be false.
     * 
     * @param from the first coordinate (the sender)
     * @param to   the second coordinate (the receiver)
     * @return true, if the receiver can receive resources from the sender, else
     *         false.
     */
    public boolean connectionExists(Coordinate from, Coordinate to) {
        if (from.isInGrid() && to.isInGrid()) {
            final Template firstTemplate = this.devices.get(from.getX(), from.getY()).getTemplate();
            final Template secondTemplate = this.devices.get(to.getX(), to.getY()).getTemplate();

            /*
             * Weird script I know but it just checks if a connection exists b/w the two
             * templates.
             */
            for (final Coordinate output : firstTemplate.getPointersFor(PointerTypes.EXIT)) {
                if (output.propertiesEquals(to)) {
                    for (final Coordinate input : secondTemplate.getPointersFor(PointerTypes.ENTRY)) {
                        if (input.propertiesEquals(from)) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Returns the device at the given coordinate.
     * 
     * @param to where
     * @return the device
     */
    public Device getDevice(Coordinate to) {
        return this.devices.get(to.getX(), to.getY());
    }

    /**
     * Returns the devices
     * 
     * @return the bi-dimmensionnal array with the devices. (Please be careful when
     *         using it.)
     */
    public ArrayList2D<Device> getDevices() {
        return this.devices;
    }

    public List<DeviceModel> getModels() {
        final List<DeviceModel> models = new ArrayList<>();

        for (final Device device : devices.toCollection()) {
            models.add(device.getModel());
        }

        return models;
    }

    public void upgradeGrid() {
        final int oldSize = devices.size();
        final int newSize = devices.size() + 1;
        for (int x = 0; x < newSize; x++) {
            for (int y = 0; y < newSize; y++) {
                if (x >= oldSize || y >= oldSize) {
                    final Device device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, game,
                            new Coordinate(x, y)).instantiate();
                    device.manageWith(gameManager);
                    devices.add(device, x, y);
                }
            }
        }
    }

    /**
     * Replaces the device at the given coordinate with a new Device using all the
     * information and the game that this DeviceManager was initiliazed with.
     * 
     * @param clazz     the class of the new device
     * @param level     the level
     * @param direction the direction
     * @param position  the position (where to replace)
     */
    public void replace(Class<? extends Device> clazz, Level level, Direction direction, Coordinate position) {
        final Game game = getDevice(new Coordinate(0, 0)).getGame();

        // Create the new device
        final DeviceModel newModel = new DeviceModel(clazz, level, direction, game, position);
        newModel.setID(newModel.generateID());
        final Device newDevice = newModel.instantiate();
        newDevice.manageWith(this.gameManager);

        // And replace it at the given coords
        this.devices.set(newDevice, position.getX(), position.getY());
    }

    /**
     * Sets the given device to the given position
     * 
     * @param device the device
     * @param to     the position.
     */
    public void setDevice(Device device, Coordinate to) {
        this.devices.set(device, to.getX(), to.getY());
        device.getModel().setPosition(to);
    }

    /**
     * Saves the devices in the database using the same id as before.
     * 
     * @throws SQLException if an error occurs with the database
     */
    public void save() throws SQLException {

        final Dao<DeviceModel, Long> modelDao = Database.createDao(DeviceModel.class);

        /*
         * Attempt 1:
         * 
         * Delete all old devices of the game. Set the same id as before on the current
         * devices. Persist all the devices
         */

        final DeleteBuilder<DeviceModel, Long> deleter = modelDao.deleteBuilder();

        deleter.where().eq("game_id", gameManager.getGameID());
        deleter.delete();

        for (int x = 0; x < devices.size(); x++) {
            for (int y = 0; y < devices.size(); y++) {
                final DeviceModel current = devices.get(x, y).getModel();

                final Long id = current.generateID();
                current.setID(id);

                modelDao.create(current);

            }
        }

    }

}
