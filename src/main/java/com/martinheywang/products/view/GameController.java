package com.martinheywang.products.view;

import java.math.BigInteger;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.devices.Device;
import com.martinheywang.products.model.devices.DeviceModel;
import com.martinheywang.products.model.devices.Floor;
import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.Buildable;
import com.martinheywang.products.model.devices.annotations.Prices;
import com.martinheywang.products.model.direction.Direction;
import com.martinheywang.products.model.exceptions.MoneyException;
import com.martinheywang.products.model.level.Level;
import com.martinheywang.products.model.mechanics.GameManager;
import com.martinheywang.products.toolbox.ArrayList2D;
import com.martinheywang.products.toolbox.MoneyFormat;
import com.martinheywang.products.view.components.ZoomableScrollPane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * <h3>Controller class for <code>Game.fxml</code> file.</h3> Please make the
 * difference b/w the GameController and the {@link GameManager}.</strong>
 * 
 * @author Martin Heywang
 */
public class GameController implements Initializable {

	/**
	 * Is true if the options sidebar is shown
	 */
	private static boolean optionsSidebarShown = true;

	/**
	 * Instance of Main used to changed the view for example (return to home...)
	 */
	private GameManager gameManager;

	@FXML
	private AnchorPane main;

	private ZoomableScrollPane scrollpane;

	/**
	 * The main grid with all the devices
	 */
	private GridPane grid;
	/**
	 * The label showing the money
	 */
	@FXML
	private Label moneyLabel, upgradeGridInfo;

	/**
	 * The Progress Bar showing the progress on long treatment.
	 */
	@FXML
	private ProgressBar progression;

	/**
	 * The box displaying the sidebars. (For example: options, toasts...)
	 */
	@FXML
	private HBox sidebarsContainer;

	/**
	 * The list of all the toasts
	 * 
	 * @see GameController#toast(String, Color, double)
	 */
	@FXML
	private VBox toasts;

	/**
	 * The list showing the options (research, grid...)
	 */
	@FXML
	private AnchorPane options;

	/**
	 * The list showing the
	 */
	@FXML
	private VBox devicesBuild;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		grid = new GridPane();
		scrollpane = new ZoomableScrollPane(grid);
		this.main.getChildren().add(0, scrollpane);
		AnchorPane.setTopAnchor(scrollpane, 35d);
		AnchorPane.setRightAnchor(scrollpane, 0d);
		AnchorPane.setBottomAnchor(scrollpane, 0d);
		AnchorPane.setLeftAnchor(scrollpane, 3d);

		scrollpane.setFitToHeight(false);
		scrollpane.setFitToWidth(false);
		scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);

		this.scrollpane.setFocusTraversable(true);
		this.prepareToolbar();
	}

	public void loadGame(ArrayList2D<Device> devices, Game game) {
		this.toProcessView();

		this.grid.getChildren().clear();

		// LOAD DEVICES
		for (int x = 0; x < devices.size(); x++) {
			for (int y = 0; y < devices.size(); y++) {
				Device device = devices.get(x, y);
				// If a Device doesn't exists, create a new FLOOR
				if (device == null) {
					device = new DeviceModel(Floor.class, Level.LEVEL_1, Direction.UP, game, new Coordinate(x, y))
							.instantiate();
				}
				this.grid.add(new DeviceView(device), x, y);
			}
		}
		this.setMoney(game.getMoney());

		// Updating the upgrade grid zone (prices)
		try {
			final int currentSize = gameManager.getGridSize();
			final int nextSize = gameManager.getGridSize() + 1;
			final ResourceBundle bundle = ResourceBundle
					.getBundle("com.martinheywang.products.model.bundles.GrilleUpdate");
			final BigInteger nextCost = new BigInteger(bundle.getString(String.valueOf(nextSize)));
			upgradeGridInfo.setText("Votre grille actuelle est de " + currentSize + "x" + currentSize + ". Passer à "
					+ nextSize + "x" + nextSize + " vous coûte " + MoneyFormat.getSingleton().format(nextCost));
		} catch (MissingResourceException e) {
			upgradeGridInfo.setText("Vous avez atteint la taille maximale!");
		}

		this.toPlayableView();
	}

	public void setGameManager(GameManager gM) {
		this.gameManager = gM;
	}

	public void replaceDevice(Device device) {
		final int x = device.getPosition().getX();
		final int y = device.getPosition().getY();

		this.grid.add(new DeviceView(device), x, y);

		this.grid.getChildren().remove(this.findDeviceView(x, y));
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
			GameController.this.toasts.getChildren().add(toast);

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

			animation.setOnFinished((arg0) -> GameController.this.toasts.getChildren().remove(toast));
			animation.playFromStart();
		});

	}

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
		for (final Node node : this.grid.getChildrenUnmodifiable()) {
			if (node instanceof DeviceView) {
				if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
					return (DeviceView) node;
				}
			}
		}
		return null;
	}

	/**
	 * Prepares the toolbar at the end of a refresh
	 */
	private void prepareToolbar() {
		Platform.runLater(() -> {
			for (final Class<? extends Device> clazz : Device.subclasses) {
				if (clazz.isAnnotationPresent(Buildable.class)) {

					final HBox deviceUI = new HBox();
					deviceUI.getStyleClass().add("device-build-optn");
					/*
					 * The image of the device must be found in the resource folder, then in
					 * images/devices_level_1/<class_name_to_upper_case>.png
					 */
					final String url = clazz
							.getResource("/images/devices_level_1/" + clazz.getSimpleName().toUpperCase() + ".png")
							.toExternalForm();
					final ImageView view = new ImageView(url);
					view.setFitWidth(40d);
					view.setFitHeight(40d);

					// All subclasses of Device has this annotation, don't need
					// to test this.
					final String str = clazz.getAnnotation(AccessibleName.class).value();
					final Label label = new Label(str);
					label.setWrapText(true);

					deviceUI.setSpacing(10d);
					deviceUI.getChildren().addAll(view, label);
					deviceUI.setAlignment(Pos.CENTER_LEFT);
					deviceUI.setPadding(new Insets(2d, 5d, 2d, 5d));

					final BigInteger price = new BigInteger(clazz.getAnnotation(Prices.class).build());

					deviceUI.setOnMouseEntered(event -> {
						label.setText(MoneyFormat.getSingleton().format(price));
					});
					deviceUI.setOnMouseExited(event -> {
						label.setText(str);
					});

					// DRAG BEHAVIOUR
					deviceUI.setOnDragDetected(event -> {
						final Dragboard db = deviceUI.startDragAndDrop(TransferMode.COPY);
						final ClipboardContent content = new ClipboardContent();

						content.put(DeviceView.classFormat, clazz);
						content.putImage(view.getImage());

						db.setContent(content);

						event.consume();
					});

					deviceUI.getStyleClass().add("device-build-displayer");

					this.devicesBuild.getChildren().add(deviceUI);
				}
				this.devicesBuild.setSpacing(5d);

				this.options.setOnDragOver(event -> {
					event.acceptTransferModes(TransferMode.MOVE);
				});
				this.options.setOnDragDropped(event -> {
					if (event.getTransferMode().equals(TransferMode.MOVE)) {
						final Dragboard db = event.getDragboard();

						if (db.hasContent(DeviceView.coordinateFormat) && db.hasContent(DeviceView.levelFormat)) {

							final Coordinate coord = (Coordinate) db.getContent(DeviceView.coordinateFormat);
							final Level level = (Level) db.getContent(DeviceView.levelFormat);

							try {
								this.gameManager.destroy(coord, level);
							} catch (final MoneyException e) {
								e.printStackTrace();
							}

							event.setDropCompleted(true);
						}
					}
				});
			}
		});
	}

	/**
	 * Shows / Hide the options sidebar according ot its actual poisition
	 */
	@FXML
	private void showOrHideSidebar() {
		final Timeline transition = new Timeline();
		if (!optionsSidebarShown) {
			transition.getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(this.sidebarsContainer.translateXProperty(), 240d)),
					new KeyFrame(Duration.millis(250), new KeyValue(this.sidebarsContainer.translateXProperty(), 0d)));
			transition.playFromStart();

		} else {
			transition.getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(this.sidebarsContainer.translateXProperty(), 0d)),
					new KeyFrame(Duration.millis(250),
							new KeyValue(this.sidebarsContainer.translateXProperty(), 240d)));
			transition.playFromStart();
		}

		optionsSidebarShown = !optionsSidebarShown;
	}

	@FXML
	private void upgradeGrid() {
		final int newSize = gameManager.getGridSize() + 1;

		final ResourceBundle bundle = ResourceBundle.getBundle("com.martinheywang.products.model.bundles.GrilleUpdate");
		final BigInteger cost = new BigInteger(bundle.getString(String.valueOf(newSize)));
		try {
			gameManager.removeMoney(cost);
			gameManager.upgradeGrid();
		} catch (MoneyException e) {
			gameManager.toast(
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
		this.grid.setVisible(true);
		final Timeline fadeIn = new Timeline();
		fadeIn.getKeyFrames().addAll(new KeyFrame(Duration.millis(0d), new KeyValue(this.grid.opacityProperty(), 0d)),
				new KeyFrame(Duration.millis(250d), new KeyValue(this.grid.opacityProperty(), 1d)));
		fadeIn.playFromStart();
		this.moneyLabel.setVisible(true);
		this.progression.setVisible(false);
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
		this.moneyLabel.setVisible(false);
		this.progression.setVisible(true);
		this.progression.setProgress(0.0);
	}

}