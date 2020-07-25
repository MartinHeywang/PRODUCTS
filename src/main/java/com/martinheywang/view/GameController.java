package com.martinheywang.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Game;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.types.BaseTypes;
import com.martinheywang.model.types.Type;
import com.martinheywang.toolbox.ArrayList2D;
import com.martinheywang.toolbox.MoneyFormat;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * <h3>Controller class for <code>Game.fxml</code> file.</h3> Please
 * make the difference b/w the GameController and the
 * {@link GameManager}.</strong>
 * 
 * @author Martin Heywang
 */
public class GameController implements Initializable {

	/**
	 * Instance of Main used to changed the view for example (return to
	 * home...)
	 */
	private Main main;

	@FXML
	private ScrollPane scrollpane;

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

	/**
	 * Is true if the options sidebar is shown
	 */
	private static boolean optionsSidebarShown = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		grid.setFocusTraversable(true);
		prepareToolbar();
	}

	/**
	 * This method sets which instance of main should be used.
	 * 
	 * @param main the instance of main
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}

	public void loadGame(ArrayList2D<Device> devices, Game game) {
		// LOAD DEVICES
		for (int x = 0; x < devices.size(); x++) {
			for (int y = 0; y < devices.size(); y++) {
				Device device = devices.get(x, y);
				// If a Device doesn't exists, create a new FLOOR
				if (device == null) {
					device = new Device(BaseTypes.FLOOR, Direction.UP,
							Level.LEVEL_1, new Coordinate(x, y), game);
				}
				grid.add(new DeviceView(device), x, y);
			}
		}

		toPlayableView();

		// LOAD GAME DATA
		game.moneyProperty().addListener((observable, oldValue, newValue) -> {
			moneyLabel.setText(MoneyFormat.getSingleton().format(newValue));
		});
		moneyLabel.setText(game.getMoney().toString());
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

					devicesBuild.getChildren().add(display);
				}
			}
		});
	}

	/**
	 * Displays a toast in the bottom-right corner of the view.
	 * 
	 * Default colors (you may want to use your own for a specific
	 * plugin).
	 * <ul>
	 * <li>INFO : javafx.scene.paint.Color.CORNFLOWERBLUE</li>
	 * <li>WARNING : javafx.scene.paint.Color.DARKORANGE</li>
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
										-160.0)),
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
										-160.0)),
						new KeyFrame(Duration.seconds(duration),
								new KeyValue(toast.opacityProperty(), 0.0)));

				animation.setOnFinished(
						(arg0) -> toasts.getChildren().remove(toast));
				animation.playFromStart();
			}
		});

	}

}