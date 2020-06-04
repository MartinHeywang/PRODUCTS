package com.martinheywang.view;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Game;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.Buyer;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.exceptions.MoneyException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameController implements Initializable {

	private Main main;

	@FXML
	private GridPane grid;
	@FXML
	private Label argentLabel;
	@FXML
	private Label report;
	@FXML
	private Button upgradeGridButton;
	@FXML
	private ProgressBar progression;

	private static final StringProperty reportProperty = new SimpleStringProperty();
	private static final LongProperty argentProperty = new SimpleLongProperty();

	private Thread t;
	private Game currentGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		report.textProperty().bind(reportProperty);
		report.setVisible(false);
		report.setTooltip(new Tooltip("Cliquez pour cacher..."));
		report.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				report.setVisible(false);
			}
		});
		argentProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
				argentLabel.setText(nf.format(newValue) + " €");

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
	 * Loads a game with all its informations and lauch the thread.
	 * 
	 * @param gameToLoad the game to load
	 */
	public void load(Game gameToLoad) throws SQLException {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() {
				currentGame = gameToLoad;

				refreshView();

				return null;
			}
		};

		Thread loading = new Thread(task);
		loading.start();

		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				t = new Thread(new GameLoop());
				t.start();
			}
		});

	}

	private void refreshView() {
		Platform.runLater(() -> toProcessView());
		Platform.runLater(() -> clearGrid());
		adjustDevicesModel();

		/*
		 * <!> Clear the registered buyers coords (when loading two differents
		 * game, some devices was called instaed of the buyers).
		 */
		Buyer.locations.clear();

		addDevices();
		Platform.runLater(() -> toPlayableView());
	}

	private void clearGrid() {
		grid.getChildren().clear();
	}

	private void addDevices() {
		List<DeviceModel> devicesModel = currentGame.getDevicesModel();

		progression.progressProperty()
				.set(0.0);

		int i = 1;
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

				i++;
				progression.progressProperty().set(
						(double) i / devicesModel.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			setReport("Failed to add devices to the grid"
					+ e.getLocalizedMessage(), Color.DARKRED);
		}
	}

	/**
	 * Adjust the devices in the devices model's list. Fill what is needed
	 * to match the grid-size.
	 */
	private void adjustDevicesModel() {

		final int size = currentGame.getGridSize();
		final List<DeviceModel> devicesModel = currentGame.getDevicesModel();

		if (devicesModel.size() < Math.pow(size, 2)) {
			try {
				for (int x = 0; x < size; x++) {
					for (int y = 0; y < size; y++) {
						final DeviceModel model = new DeviceModel(
								new Coordinates(x, y),
								currentGame);
						devicesModel.add(model);
						Database.daoDeviceModel().create(model);
					}
				}
				currentGame.save();
			} catch (SQLException e) {
				e.printStackTrace();
				setReport("Failed to add devices model in the database",
						Color.DARKRED);
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
		argentLabel.setVisible(false);
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
		argentLabel.setVisible(true);
		progression.setVisible(false);
		argentProperty.set(currentGame.getMoney());
		argentLabel.setText(NumberFormat.getInstance()
				.format(currentGame.getMoney()) + " €");
	}

	/**
	 * This method closes the game and set up the stage on the home scene,
	 * where we can chose which game we want to load.
	 * 
	 * @see Main#initAccueil2()
	 */
	@FXML
	public void returnToHome() throws SQLException {
		t.interrupt();
		currentGame.save();
		main.initAccueil2();
	}

	@FXML
	public void research() {
		// Todo : research frame
	}

	@FXML
	public void upgradeGrid() {
		try {
			ResourceBundle bundle = ResourceBundle
					.getBundle("com.martinheywang.model.bundles.GrilleUpdate");

			final Integer newSize = currentGame.getGridSize() + 1;
			final String bundleValue = bundle.getString(newSize.toString());

			removeMoney(Long.valueOf(bundleValue));

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
	public void addMoney(long amount) throws MoneyException {
		currentGame.addMoney(amount);
		refreshMoney();
	}

	/**
	 * Removes money to the game
	 * 
	 * @param amount how many
	 * @throws MoneyException if there aren't enough money
	 */
	public void removeMoney(long amount) throws MoneyException {
		currentGame.removeMoney(amount);
		refreshMoney();
	}

	/**
	 * Sets the money amount to the value given as parameter.
	 * 
	 * @param amount the value
	 */
	public void setArgent(long amount) throws MoneyException {
		currentGame.setMoney(amount);
		refreshMoney();
	}

	/**
	 * Refreshes the amount money displayed by the label and update the
	 * associated property.
	 */
	private void refreshMoney() {
		Platform.runLater(() -> argentProperty.set(currentGame.getMoney()));
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
	public long getMoney() {
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
			removeMoney(device.getModel().getType().getPrix());
		}
		final Coordinates coords = device.getModel().getCoordinates();
		Device oldDevice = findDevice(coords);
		grid.getChildren().remove(oldDevice);
		grid.add(device, coords.getX(), coords.getY());
	}

	/**
	 * Gets the cell content at the given coordinates.
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
	 * Shows the report if hided and sets the text to String in parameter.
	 * The must also indicates a JavaFX Color which will be the color of
	 * the border.
	 * 
	 * @param text        the text to show
	 * @param colorBorder the new color of the border
	 */
	public void setReport(String text, Color colorBorder) {
		reportProperty.set(text);
		report.getStyleClass().add("report");
		report.setStyle("-fx-border-color: " + String.format("#%02X%02X%02X",
				(int) (colorBorder.getRed() * 255),
				(int) (colorBorder.getGreen() * 255),
				(int) (colorBorder.getBlue() * 255)) + ";");
		report.setVisible(true);
	}

	private class GameLoop implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(750);
					for (int i = 0; i < Buyer.locations.size(); i++) {
						try {
							findDevice(Buyer.locations.get(i))
									.action(new Pack());
							argentLabel.setTextFill(Color.WHITE);
						} catch (MoneyException e) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									argentLabel.setTextFill(Color.DARKRED);
								}
							});

						}
					}
				}
			} catch (IllegalArgumentException | InterruptedException e) {
			}
		}

	}

}