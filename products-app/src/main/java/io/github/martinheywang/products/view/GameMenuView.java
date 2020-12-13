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
package io.github.martinheywang.products.view;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.utils.ArrayList2D;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.device.Floor;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import io.github.martinheywang.products.view.component.DeviceView;
import io.github.martinheywang.products.view.component.GameGrid;
import io.github.martinheywang.products.view.component.GameStageBar;
import io.github.martinheywang.products.view.component.LocatedScrollPane;
import io.github.martinheywang.products.view.component.MenuView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * The GameView class is the view controller of the file Game.fxml. It mapps and
 * updates the component as the
 * {@link io.github.martinheywang.products.controller.GameController} wants.
 * 
 * @author Martin Heywang
 */
public class GameMenuView implements Initializable {

	private GameController gameManager;

	// VIEW COMPONENTS ------------------------------------------------------
	@FXML
	private Parent root; // The root considered by javafx
	@FXML
	private VBox userRoot; // The 'root' seen by the user (the one with the shadow)

	private final GameStageBar stageBar = new GameStageBar();

	@FXML
	private VBox content; // The content of the stage (without the stage bar)
	@FXML
	private HBox mainView; // containing the grid, optionnal menu and toolbar
	@FXML
	private StackPane main; // containing grid and the toast container

	private LocatedScrollPane scrollpane;
	private GameGrid grid; // containing the devices view

	private MenuView menuView;

	private ProgressBar progression = new ProgressBar(); // the progress bar

	@FXML
	private VBox toasts;

	// -----------------------------------------------------------------------

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.root.getStylesheets().addAll(ViewUtils.class.getResource("/css/General.css").toExternalForm(),
				getClass().getResource("/css/Game.css").toExternalForm());

		this.grid = new GameGrid();
		this.scrollpane = new LocatedScrollPane(this.grid);
		this.scrollpane.getStyleClass().add("grid");
		this.main.getChildren().add(scrollpane);

		this.menuView = new MenuView();
		this.mainView.getChildren().add(menuView);

		this.userRoot.getChildren().add(0, stageBar);

		this.scrollpane.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
				event.consume();
			}
		});

		this.scrollpane.setFocusTraversable(true);
	}

	/**
	 * Loads the given game and the given devices into the view.
	 * 
	 * @param devices the devices to load in the grid-view
	 * @param game    the game to load
	 */
	public void loadGame(ArrayList2D<Device> devices, Game game) {
		this.toProcessView();

		this.grid.getChildren().clear();

		final Label products = new Label("PRODUCTS.");
		products.getStyleClass().addAll("logo", "h5");
		final Label gameName = new Label("- " + game.getName());
		gameName.getStyleClass().addAll("italic", "h6");
		this.stageBar.setTitle(products, gameName);

		// LOAD DEVICES
		for (int x = 0; x < devices.size(); x++) {
			for (int y = 0; y < devices.size(); y++) {
				Device device = devices.get(x, y);
				// If a device doesn't exists at the given coordinate, create a new FLOOR
				if (device == null)
					device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, game, new Coordinate(x, y))
							.instantiate();
				this.grid.add(new DeviceView(device, this.gameManager), x, y);
			}
		}

		this.toPlayableView();
	}

	/**
	 * Sets the game manager for the instance of the game controller
	 * 
	 * @param gM the game manager
	 */
	public void setGameManager(GameController gM) {
		this.gameManager = gM;
	}

	/**
	 * Performs a light-refresh on the device view located at the given coordinate.
	 * 
	 * @param coord the location
	 * 
	 * @see DeviceView#lightRefresh()
	 */
	public void lightDeviceViewRefreshAt(Coordinate coord) {
		final int x = coord.getX();
		final int y = coord.getY();

		final DeviceView view = this.findDeviceView(x, y);
		view.lightRefresh();
	}

	/**
	 * Performs a hard-refresh on the device view located at the given coordinate.
	 * 
	 * @param coord the location
	 * 
	 * @see DeviceView#hardRefresh()
	 */
	public void hardDeviceViewRefreshAt(Coordinate coord) {
		final int x = coord.getX();
		final int y = coord.getY();

		final DeviceView view = this.findDeviceView(x, y);
		view.setDevice(this.gameManager.getDevice(coord));
		view.hardRefresh();
	}

	/**
	 * Performs the pulse effect to the device view located at the given coordinate
	 * 
	 * @param coord the location
	 */
	public void pulseView(Coordinate coord) {
		final DeviceView view = this.findDeviceView(coord.getX(), coord.getY());
		view.pulse();
	}

	/**
	 * Displays a toast in the bottom-right corner of the view.
	 * 
	 * Default colors (you may want to use your own for a specific plugin).
	 * <ul>
	 * <li>INFO : javafx.scene.paint.Color.DODGERBLUE</li>
	 * <li>WARNING : javafx.scene.paint.Color.DARKORANGE</li>
	 * <li>ERROR : javafx.scene.paint.Color.DARKRED</li>
	 * </ul>
	 * But if you want to use your own color, make sure that it isn't too bright. As
	 * the text is white, it may cause some reading problems. Experiment with that,
	 * but test it before.<br>
	 * <br>
	 * 
	 * @param text       the text of the toast
	 * @param background the background-color of the toast
	 * @param duration   how long the toast will be displayed, <strong>in
	 *                   seconds</strong>
	 */
	public void toast(String text, Color background, double duration) {
		Platform.runLater(() -> {
			final Label toast = new Label(text);
			toast.getStyleClass().addAll("toast", "bold", "h6");
			GameMenuView.this.toasts.getChildren().add(toast);

			toast.setStyle("-fx-background-color: " + String.format("#%02X%02X%02X", (int) (background.getRed() * 255),
					(int) (background.getGreen() * 255), (int) (background.getBlue() * 255)) + ";");

			final Timeline animation = new Timeline();
			final double movementTime = 0.8d;
			animation.getKeyFrames().addAll(
					// Come in 1 second
					new KeyFrame(Duration.ZERO, new KeyValue(toast.translateXProperty(), -160.0)),
					new KeyFrame(Duration.ZERO, new KeyValue(toast.opacityProperty(), 0.0)),

					new KeyFrame(Duration.seconds(movementTime), new KeyValue(toast.translateXProperty(), 0.0)),
					new KeyFrame(Duration.seconds(movementTime), new KeyValue(toast.opacityProperty(), 1.0)),

					// And stay during the given duration
					new KeyFrame(Duration.seconds(duration - movementTime),
							new KeyValue(toast.translateXProperty(), 0.0)),
					new KeyFrame(Duration.seconds(duration - movementTime), new KeyValue(toast.opacityProperty(), 1.0)),

					// Leave in one second
					new KeyFrame(Duration.seconds(duration), new KeyValue(toast.translateXProperty(), -160.0)),
					new KeyFrame(Duration.seconds(duration), new KeyValue(toast.opacityProperty(), 0.0)));

			animation.setOnFinished((arg0) -> GameMenuView.this.toasts.getChildren().remove(toast));
			animation.playFromStart();
		});

	}

	/**
	 * Iterrates over the grid's children to find a DeviceView matching the given x
	 * and y position. If none is found, returns null.
	 * 
	 * @param x the x pos of the requested device view
	 * @param y the y pos of the requested device view
	 * @return the device view at the given position, if found.
	 */
	private DeviceView findDeviceView(int x, int y) {
		for (final Node node : this.grid.getChildrenUnmodifiable())
			if (node instanceof DeviceView)
				if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y)
					return (DeviceView) node;
		return null;
	}

	/**
	 * Changes the view mode to the default one.
	 */
	public void defaultMode() {
		for (DeviceView view : grid.getDeviceChildren()) {
			view.activateDefaultMode();
		}
	}

	/**
	 * Changes the view mode to the build mode.
	 */
	public void buildMode() {
		for (DeviceView view : grid.getDeviceChildren()) {
			view.activateBuildMode(StaticDeviceDataRetriever.getView(gameManager.getBuildClass()));
		}
	}

	/**
	 * Shows the grid with a nice opacity transition.<br>
	 * Should be executed on the JavaFx Application Thread (using
	 * {@link Platform#runLater(Runnable)}.
	 */
	private void toPlayableView() {
		this.main.getChildren().clear();
		this.grid.setVisible(true);
		final Timeline fadeIn = new Timeline();
		fadeIn.getKeyFrames().addAll(new KeyFrame(Duration.millis(0d), new KeyValue(this.grid.opacityProperty(), 0d)),
				new KeyFrame(Duration.millis(250d), new KeyValue(this.grid.opacityProperty(), 1d)));
		fadeIn.playFromStart();
		this.content.getChildren().remove(progression);
		this.main.getChildren().addAll(this.scrollpane);
	}

	/**
	 * Hides the grid with a nice opacity transition.<br>
	 * Should be executed on the JavaFx Application Thread (using
	 * {@link Platform#runLater(Runnable)}.
	 */
	private void toProcessView() {
		final Timeline fadeOut = new Timeline();
		fadeOut.getKeyFrames().addAll(new KeyFrame(Duration.millis(0d), new KeyValue(this.grid.opacityProperty(), 1d)),
				new KeyFrame(Duration.millis(250d), new KeyValue(this.grid.opacityProperty(), 0d)));
		fadeOut.playFromStart();
		this.grid.setVisible(false);
		this.progression.setProgress(.0d);
		this.content.getChildren().add(progression);
	}

}