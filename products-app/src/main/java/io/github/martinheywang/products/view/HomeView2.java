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
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import io.github.martinheywang.products.Main;
import io.github.martinheywang.products.api.database.Database;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.kit.view.component.ComplexButton;
import io.github.martinheywang.products.kit.view.component.GameView;
import io.github.martinheywang.products.kit.view.component.SVGImage;
import io.github.martinheywang.products.kit.view.utils.Icon;
import io.github.martinheywang.products.kit.view.utils.Icons;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The HomeView2 class is the controller class, in javafx terms, for Home2.fxml.
 * This view allow the user to choose between opening an existing game and
 * creating game.
 */
public class HomeView2 implements Initializable {

	// The instance of main is used to load the stage further (the game view for
	// example).
	private Main mainClass;

	private static boolean setUp = false;

	// The actual root
	@FXML
	private Parent root;

	// The root seen by the user
	@FXML
	private AnchorPane content;
	@FXML
	private VBox main;

	@FXML
	private VBox options;
	@FXML
	private Label title;
	@FXML
	private Label help;

	@FXML
	private HBox stageBar;

	private ScrollPane gameScroll = new ScrollPane();
	private final VBox gameContainer = new VBox();
	private Button backButton = new Button();

	private static final SVGImage reduce = Icon.REDUCE.createView(18d);
	private static final SVGImage maximize = Icon.MAXIMIZE.createView(18d);
	private static final SVGImage close = Icon.CLOSE.createView(18d);

	private static final SVGImage logo = Icon.LOGO.createView(75d, 112.5d);
	private static final AnchorPane logoContainer = new AnchorPane(logo);

	private void create(String text) {
		try {
			final Game game = new Game(text);
			Database.createDao(Game.class).create(game);
			this.mainClass.initGame(game);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	// The setup methods differs from the initialize by the fact that setUp() is
	// called once, and initialize() each time the fxml file is loaded.
	private void setUp() {
		reduce.getStyleClass().addAll("stageButtons");
		reduce.setOnMouseClicked(event -> {
			((Stage) reduce.getScene().getWindow()).setIconified(true);
		});

		maximize.getStyleClass().addAll("stageButtons");
		maximize.setDisable(true);

		close.getStyleClass().addAll("stageButtons", "closeButton");
		close.setOnMouseClicked(event -> {
			Platform.exit();
		});

		stageBar.setOnMousePressed(pressEvent -> {
			stageBar.setOnMouseDragged(dragEvent -> {
				final Stage primaryStage = (Stage) this.root.getScene().getWindow();
				primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});

		AnchorPane.setTopAnchor(logo, 0d);
		AnchorPane.setRightAnchor(logo, 0d);
		AnchorPane.setBottomAnchor(logo, 0d);
		AnchorPane.setLeftAnchor(logo, 0d);

		setUp = true;
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resources) {
		this.root.getStylesheets().addAll(ViewUtils.class.getResource("/css/General.css").toExternalForm());

		if (!setUp) {
			setUp();
		}

		this.backButton.setOnMouseClicked(event -> this.reloadJustOpened());
		this.backButton.setGraphic(Icon.LEFT_KEYBOARD_ARROW.createView(30));

		stageBar.getChildren().add(reduce);
		stageBar.getChildren().add(maximize);
		stageBar.getChildren().add(close);

		this.main.getChildren().setAll(logoContainer, title, help, options);

		final URL point = Icons.asURL("point.svg");

		final ComplexButton load = new ComplexButton("Ouvrir !", "Charger une partie déjà existante.", point);
		load.setOnMouseClicked(event -> goToOpen());
		this.options.getChildren().add(load);

		final ComplexButton create = new ComplexButton("Créer !", "Recommencer l'aventure dans une nouvelle partie.",
				point);
		create.setOnMouseClicked(event -> goToCreate());
		this.options.getChildren().add(create);

		final ComplexButton quit = new ComplexButton("Quitter...", "Fermer le jeu.", point);
		quit.setOnMouseClicked(close.getOnMouseClicked());
		this.options.getChildren().add(quit);
	}

	/**
	 * <p>
	 * Sets the instance of the main class.
	 * </p>
	 * 
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.mainClass = main;

		final double translateYValue = 50d;

		this.root.getScene().getWindow().setOnShowing(event -> {
			this.main.setTranslateY(translateYValue);
		});
		this.root.getScene().getWindow().setOnShown(event -> {
			final Timeline tl = new Timeline();
			tl.getKeyFrames()
					.addAll(new KeyFrame(Duration.ZERO, new KeyValue(this.main.translateYProperty(), translateYValue),
							new KeyValue(this.main.opacityProperty(), 0d)),
							new KeyFrame(Duration.millis(1500), new KeyValue(this.main.translateYProperty(), 0d),
									new KeyValue(this.main.opacityProperty(), 1d)));
			tl.play();
		});
	}

	private void fadeOut() {
		final Timeline tl = new Timeline();
		tl.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(main.opacityProperty(), 1d)),
				new KeyFrame(Duration.millis(500), new KeyValue(main.opacityProperty(), 0d)));
		tl.playFromStart();
	}

	private void fadeIn() {
		final Timeline tl = new Timeline();
		tl.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(main.opacityProperty(), 0d)),
				new KeyFrame(Duration.millis(500), new KeyValue(main.opacityProperty(), 1d)));
		tl.playFromStart();
	}

	@FXML
	private void goToOpen() {
		this.fadeOut();

		this.help = new Label();
		this.help.getStyleClass().add("h5");
		this.help.setText("Quelle partie veux-tu charger ?");

		this.gameScroll = new ScrollPane();
		this.gameScroll.getStyleClass().add("list");
		this.gameScroll.setFitToHeight(true);
		this.gameScroll.setFitToWidth(true);
		this.gameScroll.setContent(this.gameContainer);
		VBox.setVgrow(this.gameScroll, Priority.ALWAYS);

		try {
			final List<Game> games = Database.createDao(Game.class).queryForAll();
			games.sort(Comparator.comparing(Game::getLastSave).reversed());
			for (final Game game : games) {
				final GameView view = new GameView(game);
				view.allowAction("Lancer !", event -> {
					mainClass.initGame(game);
				});
				this.gameContainer.getChildren().add(view);
			}
			this.gameContainer.setSpacing(10d);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.main.getChildren().setAll(this.backButton, this.help, this.gameScroll);

		this.fadeIn();
	}

	@FXML
	private void goToCreate() {
		this.fadeOut();

		this.main.setSpacing(15d);
		this.main.setAlignment(Pos.CENTER_LEFT);

		this.help = new Label("Créez une nouvelle partie !");
		this.help.getStyleClass().add("h5");

		final TextField field = new TextField();
		field.setPromptText("Nom de la nouvelle partie");

		final Label precision = new Label("Appuyez sur entrer ou cliquez sur le bouton ci-dessous pour continuer.");
		precision.getStyleClass().add("precision");

		final Button validate = new Button("Créer !");
		validate.getStyleClass().add("fancy-border");

		validate.setOnAction(event -> create(field.getText()));
		field.setOnAction(validate.getOnAction());

		this.main.getChildren().setAll(backButton, help, field, precision, validate);

		this.fadeIn();
	}

	@FXML
	private void reloadJustOpened() {
		this.fadeOut();
		mainClass.initAccueil2();
	}

	@FXML
	private void close() {
		((Stage) this.root.getScene().getWindow()).close();
	}

	@FXML
	private void selectNode(MouseEvent event) {
		final Button hovered = (Button) event.getSource();
		hovered.setGraphic(new SVGImage(Icons.asURL("right_keyboard_point.svg"), 18, 18));
	}

	@FXML
	private void unselectNode(MouseEvent event) {
		final Button hovered = (Button) event.getSource();
		hovered.setGraphic(null);
	}
}