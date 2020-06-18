package com.martinheywang.view;

import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Buyer;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.exceptions.MoneyException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameController implements Initializable {

	private Main main;

	@FXML
	private GridPane grid;
	@FXML
	private Label moneyLabel;
	@FXML
	private VBox toasts;
	@FXML
	private ProgressBar progression;
	@FXML
	private AnchorPane sidebar;

	/*
	 * <!> Icons ImageView must be loaded in the Java code. The fxml are
	 * reading to a wrong location.
	 */
	@FXML
	private ImageView research_icon, grid_icon, edit_icon;

	private static final ObjectProperty<BigInteger> argentProperty = new SimpleObjectProperty<>();

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

				if (oldValue != null) {
					final BigInteger difference = newValue.subtract(oldValue);
					currentGame.setGrowPerSecond(difference);
				}
			};
		});
		grid.setFocusTraversable(true);

		/*
		 * Loading the icons
		 */
		research_icon.setImage(new Image(
				Main.class.getResourceAsStream("/icons/research.png")));
		grid_icon.setImage(new Image(
				Main.class.getResourceAsStream("/icons/grid_update.png")));
		edit_icon.setImage(new Image(
				Main.class.getResourceAsStream("/icons/add.png")));
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

		toast("La partie a été chargée !", Color.CORNFLOWERBLUE,
				10d);
	}

	/**
	 * Calculates and adds offline money according to the difference
	 * between the last save and the current time.
	 */
	private void addOfflineMoney() {
		try {
			final BigInteger grow = currentGame.getGrowPerSecond();
			final LocalDateTime lastSave = currentGame.getLastSave();
			final long offlineTime = lastSave.until(LocalDateTime.now(),
					ChronoUnit.SECONDS);
			BigInteger additionnalMoney = grow
					.multiply(BigInteger.valueOf(offlineTime));
			additionnalMoney = additionnalMoney.divide(new BigInteger("3"));

			addMoney(additionnalMoney);
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
				 * <!> Clear the registered buyers coords (when loading two
				 * differents game, some devices was called instead of the
				 * buyers).
				 */
				Buyer.locations.clear();

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
				final Device device = model.getType().getClasse()
						.getConstructor(DeviceModel.class,
								GameController.class)
						.newInstance(model, this);

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						grid.add(device,
								device.getModel().getCoordinates()
										.getX(),
								device.getModel().getCoordinates()
										.getY());
					}
				});

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
						if (findModel(new Coordinates(x, y)) == null) {
							final DeviceModel model = new DeviceModel(
									new Coordinates(x, y),
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
	 * Adds money to the game
	 * 
	 * @param amount how many
	 */
	public void addMoney(BigInteger amount) throws MoneyException {
		currentGame.addMoney(amount);
	}

	/**
	 * Removes money to the game
	 * 
	 * @param amount how many
	 * @throws MoneyException if there aren't enough money
	 */
	public void removeMoney(BigInteger amount) throws MoneyException {
		currentGame.removeMoney(amount);
	}

	/**
	 * Sets the money amount to the value given as parameter.
	 * 
	 * @param amount the value
	 */
	public void setMoney(BigInteger amount) throws MoneyException {
		currentGame.setMoney(amount);
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

	/**
	 * Replaces a device at the given coordinates of the new device.
	 * 
	 * @param device     the new device that replaces the old
	 * @param ignoreCost allow the user to avoid the cost of the operation
	 * @throws MoneyException in case the money is set, if the money
	 *                        amount is too low.
	 */
	public void setAppareil(Device device, boolean ignoreCost)
			throws MoneyException {
		currentGame.setDeviceModel(device.getModel());
		if (!ignoreCost) {
			removeMoney(
					BigInteger.valueOf(device.getModel().getType().getPrix()));
		}
		final Coordinates coords = device.getModel().getCoordinates();
		Device oldDevice = findDevice(coords);
		grid.getChildren().remove(oldDevice);
		grid.add(device, coords.getX(), coords.getY());
	}

	/**
	 * Gets the cell content of the grid at the given coordinates.
	 * 
	 * @param location the coords
	 * @return the node at the given coords
	 */
	public Device findDevice(Coordinates location) {
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
	public DeviceModel findModel(Coordinates location) {
		final int x = location.getX();
		final int y = location.getY();
		final List<DeviceModel> models = currentGame.getDevicesModel();

		try {
			final DeviceModel model = models.stream()
					.filter(m -> m.getCoordinates().getX() == x)
					.filter(m -> m.getCoordinates().getY() == y).findFirst()
					.get();
			return model;
		} catch (NoSuchElementException e) {

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
				toast.getStyleClass().addAll("toast", "bold");
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

	class GameLoop implements Runnable {

		private volatile boolean running = true;

		@Override
		public void run() {
			try {
				while (running) {
					refreshMoney();
					Thread.sleep(1000);
					for (int i = 0; i < Buyer.locations.size(); i++) {
						try {
							findDevice(Buyer.locations.get(i))
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