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
import java.text.MessageFormat;
import java.util.List;

import io.github.martinheywang.products.api.database.Database;
import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.exception.DeviceException;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import io.github.martinheywang.products.model.GameMode;
import io.github.martinheywang.products.view.DeviceMenuView;
import io.github.martinheywang.products.view.GameRoot;
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
	private GameMode mode;

	private final DeviceController deviceController;
	private final MoneyController moneyController;
	private final LoopController loopController;

	private final GameRoot view;

	/**
	 * Builds a new GameManager and launches the game loop in auto mode.
	 * 
	 * @param gameController the view controller, in order to refresh the view
	 * @param game           the game that this manager will handle
	 */
	public GameController(Game game) {
		Coordinate.setGridSize(game.getGridSize());

		// The game
		this.game = game;
		this.mode = GameMode.DEFAULT;
		// The view
		this.view = new GameRoot(this);

		// The game loop controller
		this.loopController = new LoopController(this);

		// The device controller
		List<DeviceModel> models = null;
		try {
			models = Database.createDao(DeviceModel.class).queryForEq("game_id", game.getID());
		} catch (SQLException e) {
			toast("Erreur...", "Erreur de chargement des appareils.", Color.DARKRED, 20d);
		}
		this.deviceController = new DeviceController(this, models);

		// The money controller
		this.moneyController = new MoneyController(this, game.getMoney());
		try {
			final BigInteger offline = moneyController.calculateOfflineMoney(game.getLastSave());
			moneyController.addMoney(offline);
			toast("Cool !", MessageFormat.format("Vous avez gagné {0} pendant votre absence !",
					MoneyFormat.getSingleton().format(offline)), Color.BLUEVIOLET, 10d);
		} catch (MoneyException e) {
			toast("Erreur...", "Erreur lors du calcul de l'argent gagné !", Color.DARKRED, 20d);
		}

	}

	/**
	 * Returns the view. The view is the GUI that this controller tries to update
	 * whenever the model is changed.
	 * 
	 * @return the view
	 */
	public GameRoot getView() {
		return view;
	}

	// ====================================================================================
	// DEVICES - edits methods
	// ====================================================================================

	/**
	 * Builds a device at the given coordinate, using the current build class as the
	 * type. See {@link #setBuildClass(Class)} to learn more about the build class
	 * and how to set it.
	 * 
	 * @param position where to build
	 * 
	 * @see DeviceController#build(Coordinate)
	 */
	public void build(Coordinate position) {
		try {
			deviceController.build(position);
		} catch (DeviceException e) {
			System.out.println(e.getMessage());
			toast("Erreur...", e.getMessage(), Color.DARKORANGE, 7d);
		} catch (MoneyException e) {
			System.out.println(e.getMessage());
			toast("Pas assez d'argent...", e.getMessage(), Color.DARKORANGE, 7d);
		}
	}

	/**
	 * Destroys the given device.
	 * 
	 * @param device the device to destroy
	 * @see DeviceController#destroy(Coordinate)
	 */
	public void destroy(Coordinate position) {
		try {
			deviceController.destroy(position);
		} catch (DeviceException e) {
			toast("Erreur...", e.getMessage(), Color.DARKORANGE, 7d);
		} catch (MoneyException e) {
			toast("Pas assez d'argent...", e.getMessage(), Color.DARKORANGE, 7d);
		}
	}

	/**
	 * Swaps two devices. Registers the given coordinate if none was given before,
	 * or performs the swap if one was already there.
	 * 
	 * @param position where to swap
	 * @see DeviceController#swap(Coordinate)
	 */
	public void swap(Coordinate position) {
		try {
			deviceController.swap(position);
		} catch (DeviceException e) {
			toast("Erreur...", e.getMessage(), Color.DARKORANGE, 7d);
		}
	}

	/**
	 * Upgrade the device at the given position.
	 * 
	 * @param position where to upgrade.
	 * @see DeviceController#upgrade(Coordinate)
	 */
	public void upgrade(Coordinate position) {
		try {
			deviceController.upgrade(position);
		} catch (DeviceException e) {
			toast("Erreur...", e.getMessage(), Color.DARKORANGE, 7d);
		} catch (MoneyException e) {
			toast("Pas assez d'argent...", e.getMessage(), Color.DARKORANGE, 7d);
		}
	}

	/**
	 * Turns the device at the given position.
	 * 
	 * @param position where to turn
	 */
	public void turn(Coordinate position) {
		deviceController.turn(position);
	}

	// ====================================================================================
	// MONEY - add or remove
	// ====================================================================================

	/**
	 * <p>
	 * Checks if a connection exists between the two coordinates.
	 * </p>
	 * 
	 * <p>
	 * For more detailed information, see
	 * {@link io.github.martinheywang.products.controller.DeviceController#connectionExists(Device, Device)}.
	 * </p>
	 * 
	 * @param from the first coordinate
	 * @param to   the second coordinate
	 * @return if a connection exist between the two coordinates.
	 */
	public boolean connectionExists(Coordinate from, Coordinate to) {
		return this.deviceController.connectionExists(getDevice(from), getDevice(to));
	}

	void addMoney(BigInteger amount) throws MoneyException {
		this.moneyController.addMoney(amount);
	}

	void removeMoney(BigInteger amount) throws MoneyException {
		this.moneyController.removeMoney(amount);
	}

	// ====================================================================================
	// LOOP - start, stop...
	// ====================================================================================

	/**
	 * Starts the game loop.
	 * 
	 * @see LoopController#start()
	 */
	public void start() {
		this.loopController.start();
	}

	/**
	 * Stops the game loop.
	 * 
	 * @see LoopController#stop()
	 */
	public void stop() {
		this.loopController.stop();
	}

	// ====================================================================================
	// GAME - current state, save...
	// ====================================================================================

	/**
	 * @return the current game mode
	 */
	public GameMode getGameMode(){
		return mode;
	}
	
	/**
	 * Saves the managed game with its devices and everything.
	 */
	public void save() {
		final Task<Void> operation = new Task<Void>() {
			@Override
			public Void call() {
				try {
					game.setMaxIndependent(deviceController.getMaxIndependent());
					game.setDelay(loopController.getDelay());
					game.setGrow(moneyController.getAverageVariation());
					game.updateLastSave();
					Database.createDao(Game.class).createOrUpdate(game);
					deviceController.save();
				} catch (final SQLException e) {
					Platform.runLater(() -> {
						final Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Erreur...");
						alert.setHeaderText("Une erreur est survenue lors de la sauvegarde de la partie.");
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					});
				}
				return null;
			}
		};

		try {
			final Thread thread = new Thread(operation);
			thread.run();
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// ====================================================================================
	// VIEW - refresh, toasts...
	// ====================================================================================

	/**
	 * Displays a toast in the top right corner of the game scene.
	 * More information here : {@link GameRoot#toast(String, String, Color, double)}.
	 * 
	 * @param header       see link above
	 * @param content       see link above
	 * @param color see link above
	 * @param duration    see link above
	 */
	public void toast(String header, String content, Color color, double duration) {
		this.view.toast(header, content, color, duration);
	}

	/**
	 * Pulses the device view corresponding to the device passed as argument. "To
	 * pulse" a view means highlightning it for a short moment.
	 * 
	 * @param device the pos of the device to pulse
	 */
	public void pulsePos(Coordinate pos) {
		this.view.pulsePos(pos);
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
		} catch (Exception e) {
			toast("Problème...", "Erreur lors de l'ouverture du tableau de bord.", Color.DARKRED, 5d);
		}
	}

	/**
	 * See {@link GameRoot#refreshPos(Coordinate)}.
	 * 
	 * @param coord the location
	 */
	public void refreshPos(Coordinate coord) {
		this.view.refreshPos(coord);
	}

	/**
	 * Refreshes the money label.
	 */
	public void refreshMoney() {
		// Fixme : gameView.refreshMoney();
	}

	// ====================================================================================
	// GETTERS - retrieve data
	// ====================================================================================

	/**
	 * Returns the build class (the class used to as type whenever the
	 * {@link #build(Coordinate)} method is called).
	 * 
	 * @return the build class
	 */
	public Class<? extends Device> getBuildClass() {
		return deviceController.getBuildClass();
	}

	/**
	 * Sets the build class. The build class is the type of the device that will be
	 * created if the {@link #build(Coordinate)} method is invoked.
	 * 
	 * @param clazz the build class
	 */
	public void setBuildClass(Class<? extends Device> clazz) {
		deviceController.setBuildClass(clazz);
		if (deviceController.getBuildClass() != null) {
			view.buildMode();
		} else {
			view.defaultMode();
		}
	}

	/**
	 * Returns the device located at the given coordinate.
	 * 
	 * @param coord the coordinate
	 * @return the device
	 */
	public Device getDevice(Coordinate coord) {
		return this.deviceController.getDevice(coord);
	}

	/**
	 * Returns the side size of the game grid (where are all the devices view).
	 * 
	 * @return the grid-size
	 */
	public int getGridSize() {
		return this.game.getGridSize();
	}

	/**
	 * Returns the amount of money currently available in the game.
	 * 
	 * @return the amount of money
	 */
	public BigInteger getMoney() {
		return moneyController.currentAmount();
	}

	public Game getGame() {
		return game;
	}

	// Sub parts of the controller for organisation.
	DeviceController deviceController() {
		return this.deviceController;
	}

	MoneyController moneyController() {
		return this.moneyController;
	}

	LoopController loopController() {
		return this.loopController;
	}
}