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

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.utils.ArrayList2D;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import io.github.martinheywang.products.controller.DeviceController;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.device.Floor;
import io.github.martinheywang.products.kit.view.component.SVGImage;
import io.github.martinheywang.products.kit.view.component.StaticDeviceView;
import io.github.martinheywang.products.kit.view.utils.Icons;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import io.github.martinheywang.products.model.bundle.GameLoopUpdate;
import io.github.martinheywang.products.model.bundle.GrilleUpdate;
import io.github.martinheywang.products.model.bundle.MaxBuyerUpdate;
import io.github.martinheywang.products.view.component.DeviceView;
import io.github.martinheywang.products.view.component.GameGrid;
import io.github.martinheywang.products.view.component.LocatedScrollPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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

	private String currentMenu = "none";

	// VIEW COMPONENTS ------------------------------------------------------
	@FXML
	private Parent root; // The root considered by javafx
	@FXML
	private VBox userRoot; // The 'root' seen by the user (the one who has the shadow)
	@FXML
	// The stage bar replacing the platform dependent one
	// (with buttons such as close, reduce...)
	private GridPane stageBar;

	@FXML
	private Label moneyLabel;
	@FXML
	private Label gameName;
	@FXML
	private Button closeButton, reduceButton, maximizeButton; // The stage buttons
	@FXML
	private VBox content; // The content of the stage (without the stage bar)

	@FXML
	private HBox mainView; // containing the grid, optionnal menu and toolbar
	@FXML
	private StackPane main; // containing grid and additionnal windows.

	private LocatedScrollPane scrollpane;
	private GameGrid grid; // containing the devices view

	private ProgressBar progression = new ProgressBar(); // the progress bar

	@FXML
	private VBox toolbar;
	
	private VBox buildMenu = new VBox();
	private VBox devices = new VBox();
	private TextField buildSearch = new TextField();

	@FXML
	private VBox toasts;

	// -----------------------------------------------------------------------

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.root.getStylesheets().addAll(ViewUtils.class.getResource("/css/General.css").toExternalForm(),
				getClass().getResource("/css/Game.css").toExternalForm());

		closeButton.setGraphic(new SVGImage(Icons.asURL("close.svg"), 20, 20));
		reduceButton.setGraphic(new SVGImage(Icons.asURL("reduce.svg"), 20, 20));
		maximizeButton.setGraphic(new SVGImage(Icons.asURL("maximize.svg"), 20, 20));

		closeButton.setOnMouseClicked(event -> {
			Platform.exit();
		});
		reduceButton.setOnMouseClicked(event -> {
			((Stage) reduceButton.getScene().getWindow()).setIconified(true);
		});
		maximizeButton.setOnMouseClicked(event -> {
			final Stage stage = ((Stage) reduceButton.getScene().getWindow());
			if (stage.isMaximized()) {
				userRoot.setPadding(new Insets(0, 10d, 10d, 0));
				maximizeButton.setGraphic(new SVGImage(Icons.asURL("maximize.svg"), 20, 20));
				stage.setMaximized(false);
			} else {
				maximizeButton.setGraphic(new SVGImage(Icons.asURL("minimize.svg"), 20, 20));
				userRoot.setPadding(new Insets(0));
				stage.setMaximized(true);
			}
		});

		stageBar.setOnMousePressed(pressEvent -> {
			stageBar.setOnMouseDragged(dragEvent -> {
				final Stage primaryStage = (Stage) this.root.getScene().getWindow();
				primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});

		this.grid = new GameGrid();
		this.scrollpane = new LocatedScrollPane(this.grid);
		this.scrollpane.setFitToHeight(false);
		this.scrollpane.setFitToWidth(false);
		this.scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		this.scrollpane.getStyleClass().add("grid");
		HBox.setHgrow(this.scrollpane, Priority.ALWAYS);
		this.scrollpane.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP)
				event.consume();
		});

		this.scrollpane.setFocusTraversable(true);
		this.prepareToolbar();
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
		this.gameName.setText("- " + game.getName());

		// LOAD DEVICES
		for (int x = 0; x < devices.size(); x++){
			for (int y = 0; y < devices.size(); y++) {
				Device device = devices.get(x, y);
				// If a d doesn't exists at the given coordinate, create a new FLOOR
				if (device == null)
					device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, game, new Coordinate(x, y))
							.instantiate();
				this.grid.add(new DeviceView(device, this.gameManager), x, y);
			}
		}
		this.setMoney(game.getMoney());

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
	public void lightViewRefresh(Coordinate coord) {
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
	public void hardViewRefresh(Coordinate coord) {
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
	 * Updates the money label view.
	 * 
	 * @param value the value to update to.
	 */
	public void setMoney(BigInteger value) {
		Platform.runLater(() -> {
			this.moneyLabel.setText(MoneyFormat.getSingleton().format(value));
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
	 * Prepares the toolbar at the end of a refresh
	 */
	private void prepareToolbar() {
		final SVGImage build = new SVGImage(Icons.asURL("build.svg"), 40d, 40d);
		build.addHoverEffect();

		prepareBuildMenu(this.buildMenu);

		build.setOnMouseClicked(event -> toggleBuildMenu());
		toolbar.getChildren().addAll(build);
	}

	private boolean closeMenu() {
		if (this.currentMenu != "none") {
			Node node = this.mainView.getChildren().get(1);
			this.mainView.getChildren().remove(node);
			node.setTranslateX(0d);
			this.currentMenu = "none";

			return true;
		}
		return false;
	}

	private void addMenu(VBox menu, String name) {
		this.mainView.getChildren().add(1, menu);
		this.currentMenu = name;
	}

	private void prepareBuildMenu(VBox menu) {
		menu.getStyleClass().add("menu");
		menu.setAlignment(Pos.TOP_CENTER);
		menu.setPadding(new Insets(10d));
		menu.setSpacing(15d);
		// Like a fixed size
		menu.setMinWidth(250d);
		menu.setMaxWidth(250d);

		final Label title = new Label("Construction");
		title.getStyleClass().add("h5");

		this.buildSearch = new TextField();
		buildSearch.setPromptText("Rechercher...");
		buildSearch.getStyleClass().addAll("graytone");

		final Label tutorial = new Label();
		tutorial.setText("Cliquez sur un type d'appareil ci-dessous pour obtenir plus d'informations. \n"
				+ "Faites un glisser-déposer vers la grille pour construire les appareils à "
				+ "l'emplacement désiré.\nEntrez du texte dans la barre de recherche " 
				+ "et appuyez sur entrer pour effectuer une recherche.");
		tutorial.setWrapText(true);
		tutorial.setMinHeight(120d);
		tutorial.getStyleClass().addAll("precision-light");

		final ScrollPane scroll = new ScrollPane();
		VBox.setVgrow(scroll, Priority.ALWAYS);
		scroll.getStyleClass().add("list");

		this.devices = new VBox();
		devices.setSpacing(10d);
		refreshAvailableDevices("");
		buildSearch.setOnAction(event -> {
			refreshAvailableDevices(buildSearch.getText());
		});
		scroll.setContent(devices);

		menu.getChildren().addAll(title, buildSearch, tutorial, scroll);
	}

	private void refreshAvailableDevices(String mustContain){
		devices.getChildren().clear();

			for (Class<? extends Device> clazz : DeviceController.knownTypes) {
				if (!StaticDeviceDataRetriever.isBuildable(clazz)) {
					continue;
				}
				final String name = StaticDeviceDataRetriever.getAccessibleName(clazz).toLowerCase();
				if (name.contains(this.buildSearch.getText().toLowerCase())) {
					final StaticDeviceView view = new StaticDeviceView(clazz);
					view.addHoverEffect();
					devices.getChildren().add(view);
				}
			}
			if(devices.getChildren().isEmpty()){
				final Label none = new Label("Aucun résultat.");
				devices.getChildren().add(none);
			}
	}

	private void toggleBuildMenu() {
		if (closeMenu()) {
			return;
		}
		addMenu(this.buildMenu, "build");

	}

	@FXML
	private void upgradeGrid() {
		final int newSize = this.gameManager.getGridSize() + 1;

		final ResourceBundle bundle = ResourceBundle.getBundle(GrilleUpdate.class.getCanonicalName());
		final BigInteger cost = new BigInteger(bundle.getString(String.valueOf(newSize)));
		try {
			this.gameManager.removeMoney(cost);
			this.gameManager.upgradeGrid();
		} catch (final MoneyException e) {
			this.gameManager.toast(
					"Vous n'avez pas assez d'argent! (" + MoneyFormat.getSingleton().format(cost) + " demandés)",
					Color.DARKORANGE, 4d);
		}

	}

	@FXML
	private void decreaseGameLoopDelay() {
		final int newDelay = this.gameManager.getDelay() - 50;

		final ResourceBundle bundle = ResourceBundle.getBundle(GameLoopUpdate.class.getCanonicalName());
		final BigInteger cost = new BigInteger(bundle.getString(String.valueOf(newDelay)));

		try {
			this.gameManager.removeMoney(cost);
			this.gameManager.decreaseGameLoopDelay();
		} catch (final MoneyException e) {
			this.gameManager.toast(
					"Vous n'avez pas assez d'argent! (" + MoneyFormat.getSingleton().format(cost) + " demandés)",
					Color.DARKORANGE, 4d);
		}
	}

	@FXML
	private void increaseMaxBuyer() {
		final int newMax = this.gameManager.getMaxBuyer() + 4;

		final ResourceBundle bundle = ResourceBundle.getBundle(MaxBuyerUpdate.class.getCanonicalName());
		final BigInteger cost = new BigInteger(bundle.getString(String.valueOf(newMax)));

		try {
			this.gameManager.removeMoney(cost);
			this.gameManager.addMaxBuyer();
		} catch (final MoneyException e) {
			this.gameManager.toast(
					"Vous n'avez pas assez d'argent! (" + MoneyFormat.getSingleton().format(cost) + " demandés)",
					Color.DARKORANGE, 4d);
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
		this.moneyLabel.setVisible(true);
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