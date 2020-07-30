package com.martinheywang.model.mechanics;

import java.math.BigInteger;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Floor;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.view.GameController;

import javafx.scene.paint.Color;

/**
 * The GameManager is the class that manage, as the name said, the
 * game. It takes care of the devices and the game, launches the game
 * loop, for example. It also relays the data b/w the view and the
 * real model.
 * 
 * @see GameLoop
 * 
 * @author Heywang
 */
public final class GameManager {

	private final GameController gameController;
	private final Game game;
	private final DeviceManager deviceManager;

	private final Thread gameThread;
	private final GameLoop gameLoop;

	/**
	 * Builds a new GameManager and launches the game loop in auto mode.
	 * 
	 * @param gameController the game controller that allows it to modify
	 *                       the view
	 * @param game           the game that this manager will handle
	 */
	public GameManager(GameController gameController, Game game) {
		// GAME
		this.game = game;

		// DEVICE MANAGER
		this.deviceManager = new DeviceManager(game.getDevicesModel(), this);

		// GAME CONTROLLER -> the scene controller (view updates)
		this.gameController = gameController;
		this.gameController.toast("Bienvenue !",
				Color.DODGERBLUE, 10d);
		this.gameController.loadGame(this.deviceManager.getDevices(), game);

		// GAME LOOP STUFF
		gameLoop = new GameLoop();
		gameThread = new Thread(gameLoop);
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
			deviceManager.getDevice(to).act(resources);
		} catch (MoneyException e) {
			e.printStackTrace();
		}
	}

	public void performReplacement(DeviceModel model) {
		final Coordinate position = model.getPosition();
		deviceManager.replace(model.getType(), model.getLevel(),
				model.getDirection(), position);
		gameController.replaceDevice(deviceManager.getDevice(position));
	}

	public void build(Class<? extends Device> clazz, Coordinate position) {
		deviceManager.replace(clazz, Level.LEVEL_1, Direction.UP, position);
		gameController.replaceDevice(deviceManager.getDevice(position));
	}

	public void delete(Coordinate position) {
		deviceManager.replace(Floor.class, Level.LEVEL_1, Direction.UP,
				position);
		gameController.replaceDevice(deviceManager.getDevice(position));
	}

	public void turn(Coordinate position) {
		final Direction newDirection = deviceManager.getDevice(position)
				.getDirection().getNext();

		// Replace with the same device but with the new direction
		deviceManager.replace(deviceManager.getDevice(position).getClass(),
				deviceManager.getDevice(position).getLevel(), newDirection,
				position);
		gameController.replaceDevice(deviceManager.getDevice(position));
	}

	public void upgrade(Coordinate position) {
		final Level newLevel = deviceManager.getDevice(position).getLevel()
				.getNext();

		// Replace with the same device but with the new level
		deviceManager.replace(deviceManager.getDevice(position).getClass(),
				newLevel, deviceManager.getDevice(position).getDirection(),
				position);
		gameController.replaceDevice(deviceManager.getDevice(position));
	}

	/**
	 * Returns the amount of money currently available in the game.
	 * 
	 * @return the amount of money
	 */
	public BigInteger getMoney() {
		return game.getMoney();
	}

	/**
	 * 
	 * @return the managed game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Adds money to the game
	 * 
	 * @param value the amount to add
	 */
	public void addMoney(BigInteger value) {
		game.setMoney(game.getMoney().add(value));
	}

	/**
	 * Removes money to the game
	 * 
	 * @param value the amount to remove
	 */
	public void removeMoney(BigInteger value) {
		game.setMoney(game.getMoney().subtract(value));
	}

	/**
	 * Starts properly the game loop in auto mode, which means that it
	 * won't end until you stop it calling {@link #stop()}.
	 * 
	 * @see #step()
	 * @see #stop()
	 * @see GameLoop
	 */
	public void start() {
		gameLoop.setAutoMode(true);
		gameThread.start();
	}

	/**
	 * <p>
	 * Starts properly the game loop in manual mode, which means that it
	 * will stop automatically after one iteration.
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
		gameLoop.setAutoMode(false);
		gameThread.start();
	}

	/**
	 * Waits for the game loop to end it's iteration and stops it once
	 * it's done.
	 * 
	 * @see #start()
	 * @see #step()
	 * @see GameLoop
	 */
	public void stop() {
		gameLoop.setAutoMode(false);
	}

	/**
	 * The GameLoop is what is done by the second thread in background of
	 * the game. It calls the registered buyers to call them to action.
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
				for (int i = 0; i < Device.buyersLocations.size(); i++) {

					Coordinate pos = Device.buyersLocations.get(i);
					Device buyer = deviceManager.getDevices().get(pos.getX(),
							pos.getY());

					// Todo : active buyer
				}
			} while (autoMode);
		}

		public void setAutoMode(boolean mode) {
			this.autoMode = mode;
		}
	}
}