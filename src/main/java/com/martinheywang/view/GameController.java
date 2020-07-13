package com.martinheywang.view;

import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.exceptions.EditException;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.types.BaseTypes;
import com.martinheywang.model.types.Type;
import com.martinheywang.toolbox.MoneyFormat;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameController implements Initializable {

	/**
	 * Instance of Main used to changed the view for example (return to
	 * home...)
	 */
	private Main main;

	/**
	 * The main grid with all the devices
	 */
	@FXML
	private GridPane grid;

	/**
	 * The label showing the money
	 */
	@FXML
	private Label moneyLabel;
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

	private static final ObjectProperty<BigInteger> argentProperty = new SimpleObjectProperty<>();

	/**
	 * Is true if the options sidebar is shown
	 */
	private static boolean optionsSidebarShown = true;

	private Thread gameLoopThread;
	private GameLoop gameLoop;
	private Game currentGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		argentProperty.addListener(new ChangeListener<BigInteger>() {
			@Override
			public void changed(
					ObservableValue<? extends BigInteger> observable,
					BigInteger oldValue, BigInteger newValue) {
				Platform.runLater(
						() -> moneyLabel.setText(MoneyFormat.getSingleton()
								.format(newValue)));
			};
		});
		grid.setFocusTraversable(true);
	}

	/**
	 * This method sets which instance of main should be used.
	 * 
	 * @param main the instance of main
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Loads a game with all its informations and launch the thread.
	 * 
	 * @param gameToLoad the game to load
	 */
	public void load(Game gameToLoad) throws SQLException {
		currentGame = gameToLoad;

		refreshView();

		addOfflineMoney();
		prepareToolbar();

		toast("Bienvenue dans la partie : " + currentGame.getName(),
				Color.CORNFLOWERBLUE,
				15d);
	}

	/**
	 * Calculates and adds offline money according to the difference
	 * between the last save and the current time.
	 */
	private void addOfflineMoney() {
		try {
			final BigInteger grow = currentGame.getGrowPerSecond() == null
					? currentGame.getGrowPerSecond()
					: new BigInteger("0");
			final LocalDateTime lastSave = currentGame.getLastSave();
			final long offlineTime = lastSave.until(LocalDateTime.now(),
					ChronoUnit.SECONDS);
			BigInteger additionnalMoney = grow
					.multiply(BigInteger.valueOf(offlineTime));
			additionnalMoney = additionnalMoney.divide(new BigInteger("5"));

			addMoney(additionnalMoney);

			setMoney(new BigInteger("10000"));
		} catch (MoneyException e) {
			System.err.print("Couldn't add offline money");
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the grid
	 */
	private void refreshView() {

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				Platform.runLater(() -> toProcessView());
				currentGame.refreshDevicesModel();
				adjustDevicesModel();
				Platform.runLater(() -> clearGrid());

				/*
				 * <!> The locations lists all the coords that points a buyer.
				 * This list must be cleared when loading the game, so that only
				 * byers of the current game are called.
				 */
				Device.buyersLocations.clear();

				addDevices();
				Platform.runLater(() -> toPlayableView());

				return null;
			}
		};

		final Thread refreshing = new Thread(task);
		task.setOnRunning(event -> stopGameLoop());
		task.setOnSucceeded(event -> startGameLoop());
		task.setOnFailed(event -> toast("La vue n'a pas pu être rafraîchie...",
				Color.DARKRED, 7d));
		refreshing.start();
	}

	/**
	 * Clears the devices from the grid
	 */
	private void clearGrid() {
		grid.getChildren().clear();
	}

	/**
	 * Adds the devices from the current game in the grid
	 */
	private void addDevices() {
		List<DeviceModel> devicesModel = currentGame.getDevicesModel();

		progression.progressProperty().set(0.0);

		int progress = 1;
		try {
			for (DeviceModel model : devicesModel) {
				final Device device = new Device(
						new DeviceModel(model.getCoordinates(), currentGame),
						this);

				final int x = device.getModel().getCoordinates().getX();
				final int y = device.getModel().getCoordinates().getY();

				Platform.runLater(() -> grid.add(device, x, y));

				if (model.getType().equals(BaseTypes.FLOOR)) {
					device.setOnDragOver(event -> {
						if (event.getGestureSource() != device &&
								event.getDragboard().hasString()) {
							event.acceptTransferModes(
									TransferMode.COPY);
						}
					});

					device.setOnDragEntered(event -> {
						if (event.getGestureSource() != device &&
								event.getDragboard().hasString()) {
							device.setEffect(new Glow(0.4d));
						}

						event.consume();
					});
					device.setOnDragExited(event -> {
						device.setEffect(new Glow(0d));
					});
					device.setOnDragDropped(event -> {

						Dragboard db = event.getDragboard();
						boolean success = false;

						if (db.hasString()) {
							final Type type = Type.valueOf(db.getString());
							try {
								this.build(type, model.getCoordinates(), false);
							} catch (MoneyException e) {
								toast("Vous n'avez pas assez d'argent !",
										Color.DARKORANGE, 3d);
								e.printStackTrace();
							} catch (EditException e) {
								toast("L'action n'a pas pu être réalisé",
										Color.DARKRED, 5d);
								e.printStackTrace();
							}

							success = true;
						}

						event.setDropCompleted(success);

						event.consume();
					});
				}

				/*
				 * <?> Updating the progress bar
				 */
				progress++;
				progression.progressProperty().set(
						(double) progress / devicesModel.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			toast("Failed to add devices to the grid"
					+ e.getLocalizedMessage(), Color.DARKRED, 5d);
		}
	}

	/**
	 * Adjust the devices in the devices model's list. Fill what is needed
	 * to match the grid-size.
	 */
	private void adjustDevicesModel() {

		final int size = currentGame.getGridSize();
		final List<DeviceModel> devicesModel = currentGame.getDevicesModel();

		int progress = 0;
		if (devicesModel.size() < Math.pow(size, 2)) {
			try {
				for (int x = 0; x < size; x++) {
					for (int y = 0; y < size; y++) {
						if (findModel(new Coordinate(x, y)) == null) {
							final DeviceModel model = new DeviceModel(
									new Coordinate(x, y),
									currentGame);
							devicesModel.add(model);
						}

						/*
						 * <?> Updating the progress bar
						 */
						progress++;
						progression.progressProperty().set(
								progress / Math.pow(size, 2));
					}
				}
				currentGame.save();
			} catch (SQLException e) {
				e.printStackTrace();
				toast("Failed to add devices model in the database",
						Color.DARKRED, 5d);
			}
		}
	}

	/**
	 * Hides the grid with a nice opacity transition.<br>
	 * Should be executed on the JavaFx Application Thread (using
	 * {@link Platform#runLater(Runnable)}.
	 */
	private void toProcessView() {
		Timeline fadeOut = new Timeline();
		fadeOut.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(0d),
						new KeyValue(grid.opacityProperty(), 1d)),
				new KeyFrame(Duration.millis(250d),
						new KeyValue(grid.opacityProperty(), 0d)));
		fadeOut.playFromStart();
		grid.setVisible(false);
		moneyLabel.setVisible(false);
		progression.setVisible(true);
		progression.setProgress(0.0);
	}

	/**
	 * Shows the grid with a nice opacity transition.<br>
	 * Should be executed on the JavaFx Application Thread (using
	 * {@link Platform#runLater(Runnable)}.
	 */
	private void toPlayableView() {
		grid.setVisible(true);
		Timeline fadeIn = new Timeline();
		fadeIn.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(0d),
						new KeyValue(
								grid.opacityProperty(),
								0d)),
				new KeyFrame(Duration.millis(250d),
						new KeyValue(
								grid.opacityProperty(),
								1d)));
		fadeIn.playFromStart();
		moneyLabel.setVisible(true);
		progression.setVisible(false);
		refreshMoney();
	}

	/**
	 * Stops properly the game loop
	 * 
	 * @see GameLoop
	 */
	public void stopGameLoop() {
		try {
			if (gameLoopThread != null) {
				gameLoop.terminate();
				gameLoopThread.join();
			}
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Starts the game loop properly
	 * 
	 * @see GameLoop
	 */
	public void startGameLoop() {
		gameLoop = new GameLoop();
		gameLoopThread = new Thread(gameLoop);
		gameLoopThread.start();
	}

	/**
	 * This method closes the game and set up the stage on the home scene,
	 * where we can chose which game we want to load.
	 * 
	 * @see Main#initAccueil2()
	 */
	@FXML
	public void returnToHome() throws SQLException {
		stopGameLoop();
		currentGame.save();
		main.initAccueil2();
	}

	@FXML
	public void research() {
		// Todo : research frame
		refreshView();
	}

	@FXML
	public void upgradeGrid() {
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.martinheywang.model.bundles.GrilleUpdate");

			final Integer newSize = currentGame.getGridSize() + 1;
			final String bundleValue = bundle.getString(newSize.toString());

			removeMoney(new BigInteger(bundleValue));

			currentGame.setGridSize(newSize);
			refreshView();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Shows / Hide the options sidebar according ot its actual poisition
	 */
	@FXML
	private void showOrHideSidebar() {
		final Timeline transition = new Timeline();
		if (!optionsSidebarShown) {
			transition.getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO,
							new KeyValue(sidebarsContainer.translateXProperty(),
									240d)),
					new KeyFrame(Duration.millis(250),
							new KeyValue(sidebarsContainer.translateXProperty(),
									0d)));
			transition.playFromStart();

		} else {
			transition.getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO,
							new KeyValue(sidebarsContainer.translateXProperty(),
									0d)),
					new KeyFrame(Duration.millis(250),
							new KeyValue(sidebarsContainer.translateXProperty(),
									240d)));
			transition.playFromStart();
		}

		optionsSidebarShown = !optionsSidebarShown;
	}

	/**
	 * Prepares the toolbar at the end of a refresh
	 */
	private void prepareToolbar() {
		Platform.runLater(() -> {
			for (Type type : Type.getReferences()) {
				if (!type.equals(BaseTypes.FLOOR)) {
					Displayer<Type> display = type.getDisplayer();
					display.getStyleClass().add("selectable");
					display.addHoverEffect();
					display.setFocusTraversable(true);

					display.setOnDragDetected(event -> {
						Dragboard db = display
								.startDragAndDrop(TransferMode.COPY);

						ClipboardContent content = new ClipboardContent();
						content.putString(display.getSubject().toString());
						content.putImage(new Image(
								getClass()
										.getResourceAsStream(
												"/images"
														+ Level.LEVEL_1.getURL()
														+ display.getSubject()
																.getURL())));

						db.setContent(content);

						event.consume();
					});

					devicesBuild.getChildren().add(display);
				}
			}
		});
	}

	/**
	 * Build a device of the given Type at the given Coordinate.
	 * 
	 * @param type       the type of the device to build
	 * @param coords     the coordinate where to build the device
	 * @param ignoreCost if the cost of the operation should be ignored
	 * @throws MoneyException if there isn't enough money (only if
	 *                        ignoreCost is set to false)
	 * @throws EditException  if an error occurs during the operation
	 */
	public void build(Type type, Coordinate coords, boolean ignoreCost)
			throws MoneyException, EditException {

		try {
			final Device device = findDevice(coords);

			// Throwing possible exceptions before doing anything
			if (!device.getModel().getType().equals(BaseTypes.FLOOR)) {
				throw new EditException(
						"A device can only be built on a floor.");
			}
			if (type.equals(BaseTypes.FLOOR)) {
				throw new EditException(
						"A floor cannot be build. Use the delete method instead.");
			}
			if (!coords
					.isInGrid(currentGame.getGridSize())) {
				throw new EditException(
						"The given coordinate is out of the grid.");
			}

			if (!ignoreCost) {
				removeMoney(type.getPrices().getLevel1Build());
			}

			device.setData(type, Level.LEVEL_1, Direction.UP);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete the device founded at the given coords.
	 * 
	 * @param coords     the coords of the device to destroy
	 * @param ignoreGain if the gain should be ignored
	 * @throws MoneyException (only if ignoreGain is false) if an error
	 *                        with the money occurs
	 * @throws EditException  if the action couldn't not be performed
	 */
	public void delete(Coordinate coords, boolean ignoreGain)
			throws MoneyException, EditException {

		final Device device = findDevice(coords);

		if (!coords
				.isInGrid(currentGame.getGridSize())) {
			throw new EditException(
					"The given coordinate is out of the grid.");
		}
		if (device.getModel().getType().equals(BaseTypes.FLOOR)) {
			throw new EditException("A floor can not be destroyed.");
		}

		if (!ignoreGain) {
			addMoney(device.getDeletePrice());
		}

		device.setData(BaseTypes.FLOOR, Level.LEVEL_1, Direction.UP);
	}

	public void turn(Coordinate coords) throws EditException {

	}

	public void upgrade(Coordinate coords, boolean ignoreCost)
			throws MoneyException, EditException {

	}

	/**
	 * Gets the cell content of the grid at the given coordinates.
	 * 
	 * @param location the coords
	 * @return the node at the given coords
	 */
	public Device findDevice(Coordinate location) {
		final int x = location.getX();
		final int y = location.getY();

		for (Node node : grid.getChildren()) {
			if (GridPane.getColumnIndex(node) == x
					&& GridPane.getRowIndex(node) == y) {
				if (node instanceof Device) {
					return (Device) node;
				}
			}
		}
		return null;
	}

	/**
	 * Finds in the devices' model list a device with the given x and y
	 * coordinates. If none is found, returns null.
	 * 
	 * @param location
	 * @return a deviceModel
	 */
	public DeviceModel findModel(Coordinate location) {
		final List<DeviceModel> models = currentGame.getDevicesModel();

		for (DeviceModel model : models) {
			if (model.getCoordinates().propertiesEquals(location)) {
				return model;
			}
		}
		return null;
	}

	/**
	 * Displays a toast in the bottom-right corner of the view.
	 * 
	 * Default colors (you may want to use your own for a specific
	 * plugin).
	 * <ul>
	 * <li>INFO : javafx.scene.paint.Color.CORNFLOWERBLUE</li>
	 * <li>ERROR : javafx.scene.paint.Color.DARKRED</li>
	 * </ul>
	 * But if you want to use your own color, make sure that it isn't too
	 * bright. As the text is white, it may cause some reading problems.
	 * Experiment with that, but test it before.<br>
	 * <br>
	 * 
	 * @param text       the text of the toast
	 * @param background the background-color of the toast
	 * @param duration   how long the toast will be displayed, <strong>in
	 *                   seconds</strong>
	 */
	public void toast(String text, Color background, double duration) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				final Label toast = new Label(text);
				toast.getStyleClass().addAll("toast", "bold", "h6");
				toasts.getChildren().add(toast);

				toast.setStyle(
						"-fx-background-color: "
								+ String.format("#%02X%02X%02X",
										(int) (background.getRed() * 255),
										(int) (background.getGreen() * 255),
										(int) (background.getBlue() * 255))
								+ ";");

				final Timeline animation = new Timeline();
				animation.getKeyFrames().addAll(
						// Come in 1 second
						new KeyFrame(Duration.ZERO,
								new KeyValue(toast.translateXProperty(),
										160.0)),
						new KeyFrame(Duration.ZERO,
								new KeyValue(toast.opacityProperty(), 0.0)),

						new KeyFrame(Duration.seconds(1),
								new KeyValue(toast.translateXProperty(), 0.0)),
						new KeyFrame(Duration.seconds(1),
								new KeyValue(toast.opacityProperty(), 1.0)),

						// And stay during the given duration
						new KeyFrame(Duration.seconds(duration - 1),
								new KeyValue(toast.translateXProperty(), 0.0)),
						new KeyFrame(Duration.seconds(duration - 1),
								new KeyValue(toast.opacityProperty(), 1.0)),

						// Leave in one second
						new KeyFrame(Duration.seconds(duration),
								new KeyValue(toast.translateXProperty(),
										160.0)),
						new KeyFrame(Duration.seconds(duration),
								new KeyValue(toast.opacityProperty(), 0.0)));

				animation.setOnFinished(
						(arg0) -> toasts.getChildren().remove(toast));
				animation.playFromStart();
			}
		});

	}

	private void calculateDifference(BigInteger oldValue, BigInteger newValue) {
		if (oldValue != null) {
			final BigInteger difference = newValue.subtract(oldValue);
			currentGame.setGrowPerSecond(difference);
		}
	}

	/**
	 * Adds money to the game
	 * 
	 * @param amount how many
	 */
	public void addMoney(BigInteger amount) throws MoneyException {
		currentGame.addMoney(amount);
		refreshMoney();
	}

	/**
	 * Removes money to the game
	 * 
	 * @param amount how many
	 * @throws MoneyException if there aren't enough money
	 */
	public void removeMoney(BigInteger amount) throws MoneyException {
		currentGame.removeMoney(amount);
		refreshMoney();
	}

	/**
	 * Sets the money amount to the value given as parameter.
	 * 
	 * @param amount the value
	 */
	public void setMoney(BigInteger amount) throws MoneyException {
		currentGame.setMoney(amount);
		refreshMoney();
	}

	/**
	 * Refreshes the amount money displayed by the label and update the
	 * associated property.
	 */
	private void refreshMoney() {
		argentProperty.set(getMoney());

	}

	/**
	 * 
	 * @return the money
	 */
	public int getGridSize() {
		return currentGame.getGridSize();
	}

	/**
	 * 
	 * @return the amount of money of the game
	 */
	public BigInteger getMoney() {
		return currentGame.getMoney();
	}

	/**
	 * Saves the game
	 * 
	 * @throws SQLException if an error occurs when saving the game
	 */
	public void saveGame() throws SQLException {
		currentGame.save();
	}

	class GameLoop implements Runnable {

		private volatile boolean running = true;

		@Override
		public void run() {
			try {
				while (running) {
					Thread.sleep(1000);
					refreshMoney();
					final BigInteger moneyBefore = currentGame.getMoney();
					for (int i = 0; i < Device.buyersLocations.size(); i++) {
						try {
							findDevice(Device.buyersLocations.get(i))
									.action(new Pack());
							moneyLabel.setTextFill(Color.WHITE);
						} catch (MoneyException e) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									moneyLabel.setTextFill(Color.DARKRED);
								}
							});

						}
						final BigInteger moneyAfter = currentGame.getMoney();
						calculateDifference(moneyBefore, moneyAfter);
					}
				}
			} catch (IllegalArgumentException | InterruptedException e) {
				running = false;
			}
		}

		public void terminate() {
			this.running = false;
		}

	}

}