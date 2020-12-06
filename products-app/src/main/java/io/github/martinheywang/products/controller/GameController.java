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

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.DeleteBuilder;

import io.github.martinheywang.products.api.database.Database;
import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.Independent;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.EditException;
import io.github.martinheywang.products.api.model.exception.LoadException;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import io.github.martinheywang.products.kit.device.Floor;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import io.github.martinheywang.products.view.DeviceMenuView;
import io.github.martinheywang.products.view.GameMenuView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;

/**
 * The GameManager is the class that manage, as the name said, the game. It
 * takes care of the devices and the game, launches the game loop, for example.
 * It also relays the data b/w the view and the real model.
 * 
 * @see GameLoop
 * 
 * @author Martin Heywang
 */
public final class GameController {

	private final Game game;

	/**
	 * The current delay applied between each iteration of the game loop.
	 */
	private int gameLoopDelay;
	private int maxBuyer;
	private final List<BigInteger> lastsGrow = new ArrayList<>();

	private DeviceController deviceController;
	private final GameMenuView gameView;

	private final GameLoop gameLoop;
	private final Thread gameThread;

	private Class<? extends Device> buildClazz = null;
	private Coordinate toMove;

	/**
	 * Builds a new GameManager and launches the game loop in auto mode.
	 * 
	 * @param gameController the game controller that allows it to modify the view
	 * @param game           the game that this manager will handle
	 */
	public GameController(GameMenuView gameController, Game game) {
		// Sets the dimension
		Coordinate.setGridSize(game.getGridSize());

		// GAME
		this.game = game;
		gameLoopDelay = game.getDelay();
		this.maxBuyer = game.getMaxBuyer();
		this.gameView = gameController;
		this.gameView.setGameManager(this);

		// DEVICE MANAGER
		try {
			this.deviceController = new DeviceController(game);
		} catch (final LoadException e) {
			gameController.toast(e.getMessage(), Color.DARKRED, 20d);
		}

		// OFFLINE MONEY
		try {
			final LocalDateTime lastSave = this.game.getLastSave();
			final LocalDateTime now = LocalDateTime.now();
			final BigInteger grow = this.game.getGrow();
			final long millis = ChronoUnit.MILLIS.between(lastSave, now);

			/*
			 * Amount won = grow amount * number of offline iterations Number of offline
			 * iterations = offline millis * gameLoopDelay / the divider
			 */
			final BigInteger offlineTotal = grow.multiply(BigInteger.valueOf(millis)).divide(BigInteger.valueOf(5))
					.divide(BigInteger.valueOf(gameLoopDelay));
			this.addMoney(offlineTotal);

			if (!offlineTotal.equals(BigInteger.ZERO))
				this.toast("Vous avez gagné durant votre absence:\n" + MoneyFormat.getSingleton().format(offlineTotal)
						+ ".", Color.DODGERBLUE, 20d);
		} catch (final MoneyException e) {
			e.printStackTrace();
		}

		// GAME CONTROLLER -> the scene controller (view updates)
		this.gameView.setGameManager(this);
		this.toast("Bienvenue !", Color.DODGERBLUE, 10d);
		this.gameView.loadGame(this.deviceController.getDevices(), game);

		// GAME LOOP STUFF
		this.gameLoop = new GameLoop();
		this.gameThread = new Thread(this.gameLoop);
	}

	/**
	 * Builds a device at the given coordinate, using the current build class. See
	 * {@link #setBuildClass(Class)} to learn more about the build class and how to
	 * set it.
	 * 
	 * @param position where
	 * @throws MoneyException if we don't have enough money
	 * @throws EditException  if the edit couldn't be performed.
	 */
	public void build(Coordinate position) throws MoneyException, EditException {
		if (this.buildClazz == null) {
			return;
		}
		final BigInteger actionPrice = new BigInteger(this.buildClazz.getAnnotation(Prices.class).build());

		if (this.game.getMoney().compareTo(actionPrice) == -1)
			throw new MoneyException(this.getMoney(), actionPrice);

		this.removeMoney(actionPrice);

		final Device newDevice = new DeviceModel(this.buildClazz, Level.LEVEL_1, Direction.UP, this.game, position)
				.instantiate();
		if (this.buildClazz.isAnnotationPresent(Independent.class)) {
			final boolean autoActive = this.buildClazz.getAnnotation(Independent.class).value();
			if (autoActive)
				this.deviceController.getIndependentDevices().add(newDevice);
		}

		this.deviceController.setDevice(newDevice, position);
		this.hardRefresh(position);
	}

	/**
	 * Destroys the given device.
	 * 
	 * @param device the device to destroy
	 * @throws EditException if an error occurs during the modification.
	 */
	public void destroy(Device device) throws EditException {
		final Class<? extends Device> oldClass = device.getClass();
		if (oldClass.equals(Floor.class))
			throw new EditException();

		final BigInteger actionGain = device.getDeletePrice();
		try {
			this.addMoney(actionGain);
		} catch (final MoneyException e) {
			// Adding money won't throw an error
		}

		// Update model and view
		this.deviceController.replace(Floor.class, Level.LEVEL_1, Direction.UP, device.getPosition());
		this.hardRefresh(device.getPosition());

		this.deviceController.removeIndependentDevice(device);

		try {
			if (device.getModel().getID() == null)
				return;
			final DeleteBuilder<Pack, Long> deleter = Database.createDao(Pack.class).deleteBuilder();
			deleter.where().eq("model", device.getModel().getID());
			deleter.delete();
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Swaps two devices. Registers the given coordinate if none was given before,
	 * or performs the swap if one was already there.
	 * 
	 * @param position where
	 * @throws EditException if the edit couldn't be performed.
	 */
	public void swap(Coordinate position) throws EditException {
		if (this.toMove == null) {
			this.toMove = position;
			return;
		} else {
			/* Perform swap (b/w toMove and position) */

			// Get the devices
			final Device first = this.deviceController.getDevice(this.toMove);
			final Device second = this.deviceController.getDevice(position);

			// Swap the coords of the devices
			this.deviceController.setDevice(first, position);
			this.deviceController.setDevice(second, this.toMove);

			first.generateTemplate();
			second.generateTemplate();

			// Update the view
			this.hardRefresh(this.toMove);
			this.hardRefresh(position);

			// Reset toMove
			this.toMove = null;
		}
	}

	/**
	 * Upgrades the given device.
	 * 
	 * @param device the device to upgrade
	 * @throws EditException if the edit couldn't be performed.
	 */
	public void upgrade(Device device) throws EditException {
		try {
			if (device.getLevel().equals(Level.LEVEL_3))
				throw new EditException();

			this.removeMoney(device.getUpgradePrice());

			device.getModel().setLevel(device.getLevel().getNext());
			this.lightRefresh(device.getPosition());
		} catch (final MoneyException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Turns the given device.
	 * 
	 * @param device the device to turn
	 * @throws EditException if the edit couldn't be performed.
	 */
	public void turn(Device device) throws EditException {
		device.getModel().setDirection(device.getDirection().getNext());
		device.generateTemplate();
		this.lightRefresh(device.getPosition());
	}

	/**
	 * See {@link GameMenuView#lightViewRefresh(Coordinate)}.
	 * 
	 * @param coord the location
	 */
	public void lightRefresh(Coordinate coord) {
		this.gameView.lightViewRefresh(coord);
	}

	/**
	 * See {@link GameMenuView#hardViewRefresh(Coordinate)}.
	 * 
	 * @param coord the location
	 */
	public void hardRefresh(Coordinate coord) {
		this.gameView.hardViewRefresh(coord);
	}

	/**
	 * <p>
	 * Checks if a connection exists between the two coordinates.
	 * </p>
	 * 
	 * <p>
	 * 
	 * For more detailed information, see
	 * {@link io.github.martinheywang.products.controller.DeviceController#connectionExists(Coordinate, Coordinate)}.
	 * </p>
	 * 
	 * @param from the first coordinate
	 * @param to   the second coordinate
	 * @return if a connection exist between the two coordinates.
	 */
	public boolean connectionExists(Coordinate from, Coordinate to) {
		return this.deviceController.connectionExists(from, to);
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
	 * @throws MoneyException if the money reaches 0 or less before the transaction.
	 */
	public void addMoney(BigInteger value) throws MoneyException {
		// Removes the negation of the value (2 times minus equals plus)
		this.removeMoney(value.negate());
	}

	/**
	 * Removes money to the game
	 * 
	 * @param value the amount to remove
	 * @throws MoneyException if the money amount reaches 0 or less.
	 */
	public void removeMoney(BigInteger value) throws MoneyException {
		if (this.getMoney().compareTo(value) == -1)
			throw new MoneyException(this.getMoney(), value);

		this.game.setMoney(this.game.getMoney().subtract(value));

		this.gameView.setMoney(this.getMoney());
	}

	/**
	 * 
	 * @return the grid-size
	 */
	public int getGridSize() {
		return this.game.getGridSize();
	}

	/**
	 * 
	 * @return the max buyer
	 */
	public int getMaxBuyer() {
		return this.maxBuyer;
	}

	/**
	 * Increase the max buyer value by 4
	 */
	public void addMaxBuyer() {
		this.maxBuyer += 4;
		this.gameView.loadGame(this.deviceController.getDevices(), this.game);
	}

	/**
	 * Returns the build class (the class used to as type whenever the
	 * {@link #build(Coordinate)} method is called).
	 * 
	 * @return the build class
	 */
	public Class<? extends Device> getBuildClazz() {
		return this.buildClazz;
	}

	/**
	 * Sets the build class. The build class is the type of the device that will be
	 * created if the {@link #build(Coordinate)} method is invoked.
	 * 
	 * @param clazz the build class
	 */
	public void setBuildClass(Class<? extends Device> clazz) {
		if (clazz == null || !StaticDeviceDataRetriever.isBuildable(clazz)) {
			clazz = null;
		}
		this.buildClazz = clazz;

		if(this.buildClazz != null){
			gameView.buildMode();
		}else{
			gameView.defaultMode();
		}
	}

	/**
	 * Displays a toast in the top right corner of the game scene.
	 * 
	 * @param text       the text to display
	 * @param background the background color of the toast (in seconds)
	 * @param seconds    the duration of the toast
	 */
	public void toast(String text, Color background, double seconds) {
		this.gameView.toast(text, background, seconds);
	}

	/**
	 * Open the dashboard of the device at the given coordinate.
	 * 
	 * @param coord the position of the device
	 */
	public void openDashboardOf(Coordinate coord) {
		try {
			final FXMLLoader loader = ViewUtils.prepareFXMLLoader(this.getClass().getResource("/fxml/Device.fxml"));

			final Dialog<Void> dialog = new Dialog<>();
			final DialogPane root = loader.load();
			dialog.setDialogPane(root);

			final DeviceMenuView controller = loader.getController();
			controller.setContent(getDevice(coord), this);
			dialog.show();
		} catch (IOException e) {
			toast("Erreur lors de l'ouverture du tableau de bord.", Color.DARKRED, 5d);
		}
	}

	/**
	 * Saves the managed game with its devices and everything.
	 */
	public void save() {
		final Alert info = new Alert(AlertType.INFORMATION);

		final Task<Void> saving = new Task<Void>() {
			@Override
			public Void call() {
				try {
					// Calculate average grow
					BigInteger average = BigInteger.ZERO;
					for (final BigInteger grow : lastsGrow)
						average = average.add(grow);
					average = average.divide(BigInteger.valueOf(lastsGrow.size()));

					game.setMaxBuyer(maxBuyer);
					game.setDelay(gameLoopDelay);
					game.setGrow(average);
					game.updateLastSave();
					Database.createDao(Game.class).createOrUpdate(game);
					deviceController.save();
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
	 * Upgrades the grid, increases its size by 1. (3x3 -> 4x4)
	 */
	public void upgradeGrid() {
		this.deviceController.upgradeGrid();
		Coordinate.setGridSize(this.deviceController.getDevices().size());
		this.game.upgradeGrid();

		this.gameView.loadGame(this.deviceController.getDevices(), this.game);
		this.toast("Grille améliorée !", Color.DODGERBLUE, 3d);
	}

	/**
	 * Decreases the {@link #gameLoopDelay} by 50 (50 milliseconds)
	 */
	public void decreaseGameLoopDelay() {
		gameLoopDelay -= 50;
		this.gameView.loadGame(this.deviceController.getDevices(), this.game);
	}

	/**
	 * 
	 * @return the id of the managed game
	 */
	public Long getGameID() {
		return this.game.getID();
	}

	/**
	 * @return the {@link #gameLoopDelay}
	 */
	public Integer getDelay() {
		return gameLoopDelay;
	}

	/**
	 * Starts the game loop in auto mode, which means that it won't end until you
	 * stop it calling {@link #stop()}.
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
	 * Starts the game loop in manual mode, which means that it will stop
	 * automatically after one iteration.
	 * </p>
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
	 * @see GameController#start()
	 * @see GameController#step()
	 * @see GameController#stop()
	 * 
	 * @author Martin Heywang
	 */
	class GameLoop implements Runnable {

		private volatile boolean autoMode = true;

		/**
		 * This value defines on how many iterations at maximum should be the grow value
		 * calculated.
		 */
		private final static int lastsGrowMaxSize = 30;

		@Override
		public void run() {
			do {
				final BigInteger amountBefore = game.getMoney();

				try {
					deviceController.clearIterations();
					for (final Device independent : deviceController.getIndependentDevices())
						for (int i = 0; i < independent.getLevel().getValue(); i++) {
							final List<Device> toPulse = new ArrayList<>();
							BigInteger cost = BigInteger.ZERO;

							// The independent device is a device at the beginning of the assembly line; it
							// doesn't any resources in order to work
							Action action = independent.act(null);
							while (action.isSuccessful()) {
								toPulse.add(action.getExecutor());
								cost = cost.add(action.getCost());
								action.getExecutor().getCurrentIteration().addAction(action);

								// The device did not give any valid pack.
								if (action.getGivenPack() == null)
									break;

								// The device did not give an output, or this output does not allow a connection
								// with the next device.
								if (action.getOutput() == null)
									break;
								if (!connectionExists(action.getPosition(), action.getOutput()))
									break;

								final Device next = getDevice(action.getOutput());

								// The next device is overload, it cannot do
								// anything
								if (!DeviceController.isActReady(next))
									break;

								// Find the next device in the assembly line and make it act
								action = next.act(action.getGivenPack());

							}

							for (final Device device : toPulse)
								gameView.pulseView(device.getPosition());
							removeMoney(cost);
						}
				} catch (final MoneyException e) {
					e.printStackTrace();
				}

				// Register evolution
				final BigInteger evolution = game.getMoney().subtract(amountBefore);
				lastsGrow.add(evolution);
				if (lastsGrow.size() > lastsGrowMaxSize)
					lastsGrow.remove(0);

				// Wait a little bit of course
				try {
					Thread.sleep(gameLoopDelay);
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

	/**
	 * Returns the device founded at the given coordinate.
	 * 
	 * @param coord the coordinate
	 * @return the device
	 */
	public Device getDevice(Coordinate coord) {
		return this.deviceController.getDevice(coord);
	}
}