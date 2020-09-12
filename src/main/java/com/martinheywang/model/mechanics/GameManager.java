package com.martinheywang.model.mechanics;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Floor;
import com.martinheywang.model.devices.annotations.Prices;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.view.GameController;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

/**
 * The GameManager is the class that manage, as the name said, the game. It
 * takes care of the devices and the game, launches the game loop, for example.
 * It also relays the data b/w the view and the real model.
 * 
 * @see GameLoop
 * 
 * @author Heywang
 */
public final class GameManager {

    private final int gameLoopDelay = 1000;
    private final GameController gameController;

    private final Game game;
    private final DeviceManager deviceManager;

    private final Thread gameThread;

    private final GameLoop gameLoop;

    private Coordinate toMove;

    /**
     * Builds a new GameManager and launches the game loop in auto mode.
     * 
     * @param gameController the game controller that allows it to modify the view
     * @param game           the game that this manager will handle
     */
    public GameManager(GameController gameController, Game game) {
	// Sets the dimension
	Coordinate.gridSize = game.getGridSize();

	// GAME
	this.game = game;

	// DEVICE MANAGER
	Collection<DeviceModel> devicesModels = null;
	try {
	    devicesModels = game.loadDevicesModel();
	} catch (final SQLException e) {
	    e.printStackTrace();
	}
	this.deviceManager = new DeviceManager(devicesModels, this, game);

	// GAME CONTROLLER -> the scene controller (view updates)
	this.gameController = gameController;
	this.gameController.setGameManager(this);
	this.gameController.toast("Bienvenue !", Color.DODGERBLUE, 10d);
	this.gameController.loadGame(this.deviceManager.getDevices(), game);

	// GAME LOOP STUFF
	this.gameLoop = new GameLoop();
	this.gameThread = new Thread(this.gameLoop);
    }

    /**
     * Performs an action at the given coordinate
     * 
     * @param from      the coordinate of the device requesting the action
     * @param to        the coordinate of the requested device
     * @param resources the resources to pass to the requested device.
     */
    public void performAction(Coordinate from, Coordinate to, Pack resources) {
	try {
	    this.deviceManager.getDevice(to).act(resources);
	} catch (final MoneyException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Builds a device of the given type at the given position.
     * 
     * @param clazz    the type of the device to build
     * @param position where
     * @throws MoneyException if we don't have enough money
     */
    public void build(Class<? extends Device> clazz, Coordinate position) throws MoneyException {
	final BigInteger actionPrice = new BigInteger(clazz.getAnnotation(Prices.class).build());

	if (this.game.getMoney().compareTo(actionPrice) == -1) {
	    throw new MoneyException("L'appareil n'a pas pu être construit");
	}
	removeMoney(actionPrice);

	this.deviceManager.replace(clazz, Level.LEVEL_1, Direction.UP, position);
	this.refreshViewAt(position);
    }

    /**
     * Destroys the Device at the given coordinate and replaces it by a floor.
     * 
     * @param position where
     * @param level    the level of the device to destroy - used to determine the
     *                 gain
     * @throws MoneyException if we don't have enough
     */
    public void destroy(Coordinate position, Level level) throws MoneyException {
	final Device device = this.deviceManager.getDevice(position);
	final Class<? extends Device> oldClass = device.getClass();
	if (oldClass.equals(Floor.class)) {
	    return;
	}

	BigInteger actionGain = new BigInteger("0");
	switch (level) {
	case LEVEL_1:
	    actionGain = new BigInteger(oldClass.getAnnotation(Prices.class).destroyAt1());
	    break;
	case LEVEL_2:
	    actionGain = new BigInteger(oldClass.getAnnotation(Prices.class).destroyAt2());
	    break;
	case LEVEL_3:
	    actionGain = new BigInteger(oldClass.getAnnotation(Prices.class).destroyAt3());
	    break;

	}

	addMoney(actionGain);

	// If it was an auto active device, remove it.
	Device.autoActiveDevices.remove(device);

	try {
	    final DeleteBuilder<Pack, Long> deleter = Database.createDao(Pack.class).deleteBuilder();
	    deleter.where().eq("model", device.getModel().getID());
	    deleter.delete();
	} catch (final SQLException e) {
	    e.printStackTrace();
	}

	// Update model and view
	this.deviceManager.replace(Floor.class, Level.LEVEL_1, Direction.UP, position);
	this.refreshViewAt(position);
    }

    /**
     * Swaps two devices. Registers the given coordinate if none was given before,
     * or performs the swap if one was already there.
     * 
     * @param position where
     */
    public void swap(Coordinate position) {
	if (this.toMove == null) {
	    this.toMove = position;
	    return;
	} else {
	    /* Perform swap (b/w toMove and position) */

	    // Get the devices
	    final Device first = this.deviceManager.getDevice(this.toMove);
	    final Device second = this.deviceManager.getDevice(position);

	    // Swap the coords of the devices
	    this.deviceManager.setDevice(first, position);
	    this.deviceManager.setDevice(second, this.toMove);

	    first.generateTemplate();
	    second.generateTemplate();

	    // Update the view
	    this.refreshViewAt(this.toMove);
	    this.refreshViewAt(position);

	    // Reset toMove
	    this.toMove = null;
	}
    }

    public boolean connectionExists(Coordinate from, Coordinate to) {
	return this.deviceManager.connectionExists(from, to);
    }

    /**
     * Refreshes the view of the device at the given coords.
     * 
     * @param position where
     */
    public void refreshViewAt(Coordinate position) {
	this.gameController.replaceDevice(this.deviceManager.getDevice(position));
    }

    /**
     * Returns the amount of money currently available in the game.
     * 
     * @return the amount of money
     */
    public BigInteger getMoney() {
	return this.game.getMoney();
    }

    /**
     * Adds money to the game
     * 
     * @param value the amount to add
     */
    public void addMoney(BigInteger value) throws MoneyException {
	// Removes the negation of the value (2 times minus equals plus)
	this.removeMoney(value.negate());
    }

    /**
     * Removes money to the game
     * 
     * @param value the amount to remove
     */
    public void removeMoney(BigInteger value) throws MoneyException {
	if (this.getMoney().compareTo(value) == -1) {
	    throw new MoneyException();
	}

	this.game.setMoney(this.game.getMoney().subtract(value));

	gameController.setMoney(getMoney());
    }

    public int getGridSize() {
	return game.getGridSize();
    }

    /**
     * Displays a toast in the top right corner of the game scene.
     * 
     * @param text       the text to display
     * @param background the background color of the toast (in seconds)
     * @param seconds    the duration of the toast
     */
    public void toast(String text, Color background, double seconds) {
	this.gameController.toast(text, background, seconds);
    }

    public void save() throws SQLException {
	final Alert info = new Alert(AlertType.INFORMATION);

	final Task<Void> saving = new Task<Void>() {
	    @Override
	    public Void call() {
		try {
		    game.save();
		    deviceManager.save();
		} catch (final SQLException e) {
		    e.printStackTrace();
		    Platform.runLater(() -> {
			info.close();
			final Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Oups...");
			alert.setHeaderText("Une erreur est survenue lors de la sauvegarde de la partie.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		    });
		}
		return null;
	    }
	};
	info.setTitle("Sauvegarde en cours");
	info.setHeaderText("La sauvegarde de la partie est en cours.");
	info.setContentText("Merci de ne pas fermer le jeu ni d'éteindre votre PC.");

	try {
	    final Thread thread = new Thread(saving);
	    info.show();
	    thread.run();
	    thread.join();
	    info.close();
	} catch (final InterruptedException e) {
	    // Something went user
	    // User may have turned off the computer
	    // But obviously we can't open a dialog
	    e.printStackTrace();
	}

    }

    /**
     * 
     * @return the id of the managed game
     */
    public Long getGameID() {
	return game.getID();
    }

    /**
     * Starts properly the game loop in auto mode, which means that it won't end
     * until you stop it calling {@link #stop()}.
     * 
     * @see #step()
     * @see #stop()
     * @see GameLoop
     */
    public void start() {
	this.gameLoop.setAutoMode(true);
	this.gameThread.start();
    }

    /**
     * <p>
     * Starts properly the game loop in manual mode, which means that it will stop
     * automatically after one iteration.
     * </p>
     * <p>
     * Will result the same as:
     * 
     * <pre>
     * <code>
     * gameManager.start();
     * gameManager.stop();
     * </code>
     * </pre>
     * 
     * where 'gameManager' is the current instance of the gameManager.
     * </p>
     * 
     * @see #start()
     * @see #stop()
     * @see GameLoop
     */
    public void step() {
	this.gameLoop.setAutoMode(false);
	this.gameThread.start();
    }

    /**
     * Waits for the game loop to end it's iteration and stops it once it's done.
     * 
     * @see #start()
     * @see #step()
     * @see GameLoop
     */
    public void stop() {
	this.gameLoop.setAutoMode(false);
    }

    /**
     * The GameLoop is what is done by the second thread in background of the game.
     * It calls the registered buyers to call them to action.
     * 
     * @see GameManager#start()
     * @see GameManager#step()
     * @see GameManager#stop()
     * 
     * @author Martin Heywang
     */
    class GameLoop implements Runnable {

	private volatile boolean autoMode = true;

	@Override
	public void run() {
	    do {
		for (final Device buyer : Device.autoActiveDevices) {
		    try {
			buyer.act(null);
		    } catch (final MoneyException e) {
			// We don't have enough money to perform the action
			// Todo : set the money label fill in the view to red
		    }
		}

		// Wait a little bit of course
		try {
		    Thread.sleep(GameManager.this.gameLoopDelay);
		} catch (final InterruptedException e) {
		    // Thread was interrupted
		    e.printStackTrace();
		}
	    } while (this.autoMode);
	}

	public void setAutoMode(boolean mode) {
	    this.autoMode = mode;
	}
    }
}