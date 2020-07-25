package com.martinheywang.model.mechanics;

import java.util.Collection;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.types.BaseTypes;
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
		this.deviceManager = new DeviceManager(game.getDevices(), this);
		Collection<Device> devices = deviceManager.getDevices().toCollection();
		for (Device device : devices) {
			// Assign the current game manager to all devices
			device.manageWith(this);
		}

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

					// Check if the type is a buyer, as expected
					if (buyer.getType().equals(BaseTypes.BUYER)) {
						// Todo: active device
					}
				}
			} while (autoMode);
		}

		public void setAutoMode(boolean mode) {
			this.autoMode = mode;
		}
	}
}