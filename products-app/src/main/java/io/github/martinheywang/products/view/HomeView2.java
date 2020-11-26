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
import io.github.martinheywang.products.kit.view.utils.Icons;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
	private Main main;

	// The actual root
	@FXML
	private Parent root;

	// The root seen by the user
	@FXML
	private VBox content;

	@FXML
	private VBox options;
	@FXML
	private Label title;
	@FXML
	private Label help;

	@FXML
	private HBox stageBar;

	ScrollPane gameScroll = new ScrollPane();
	final VBox gameContainer = new VBox();
	Button backButton = new Button();

	private void create(String text) {
		try {
			final Game game = new Game(text);
			Database.createDao(Game.class).create(game);
			this.main.initGame(game);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resources) {
		this.root.getStylesheets().addAll(ViewUtils.class.getResource("/css/General.css").toExternalForm());

		final SVGImage reduce = new SVGImage(Icons.asURL("reduce.svg"), 18, 18);
		reduce.getStyleClass().addAll("stageButtons");
		stageBar.getChildren().add(reduce);

		final SVGImage maximize = new SVGImage(Icons.asURL("maximize.svg"), 18, 18);
		maximize.getStyleClass().addAll("stageButtons");
		maximize.setDisable(true);
		stageBar.getChildren().add(maximize);

		final SVGImage close = new SVGImage(Icons.asURL("close.svg"), 18, 18);
		close.getStyleClass().addAll("stageButtons", "closeButton");
		stageBar.getChildren().add(close);

		close.setOnMouseClicked(event -> {
			Platform.exit();
		});
		reduce.setOnMouseClicked(event -> {
			((Stage) reduce.getScene().getWindow()).setIconified(true);
		});

		stageBar.setOnMousePressed(pressEvent -> {
			stageBar.setOnMouseDragged(dragEvent -> {
				final Stage primaryStage = (Stage) this.root.getScene().getWindow();
				primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});

		final SVGImage logo = new SVGImage(Icons.asURL("logo.svg"), 75d, 112.5d);
		this.content.getChildren().add(0, logo);

		final URL arrow = Icons.asURL("point.svg");

		final ComplexButton load = new ComplexButton("Ouvrir !", "Charger une partie déjà existante.", arrow);
		load.setOnMouseClicked(event -> goToOpen());
		this.options.getChildren().add(load);

		final ComplexButton create = new ComplexButton("Créer !", "Recommencer l'aventure dans une nouvelle partie.",
				arrow);
		create.setOnMouseClicked(event -> goToCreate());
		this.options.getChildren().add(create);

		final ComplexButton quit = new ComplexButton("Quitter...", "Fermer le jeu.", arrow);
		quit.setOnMouseClicked(close.getOnMouseClicked());
		this.options.getChildren().add(quit);

		this.backButton.setOnMouseClicked(event -> this.reloadJustOpened());
		this.backButton.setGraphic(new SVGImage(Icons.asURL("left_keyboard_arrow.svg"), 30d, 30d));
	}

	/**
	 * <p>
	 * Sets the main instance in order to re-call things.
	 * </p>
	 * 
	 * @param main the object to set
	 */
	public void setMainApp(final Main main) {
		this.main = main;

		final double translateYValue = 50d;

		this.root.getScene().getWindow().setOnShowing(event -> {
			for (Node children : this.content.getChildrenUnmodifiable()) {
				children.setTranslateY(translateYValue);
			}
		});
		this.root.getScene().getWindow().setOnShown(event -> {
			final Timeline tl = new Timeline();
			for (Node children : content.getChildrenUnmodifiable()) {
				tl.getKeyFrames()
						.addAll(new KeyFrame(Duration.millis(100d),
								new KeyValue(children.translateYProperty(), translateYValue),
								new KeyValue(children.opacityProperty(), 0d)),
								new KeyFrame(Duration.millis(700d), new KeyValue(children.translateYProperty(), 0d),
										new KeyValue(children.opacityProperty(), 1d)));
			}
			tl.play();
		});
	}

	private void fadeOut() {
		final Timeline tl = new Timeline();
		for (final Node node : this.content.getChildrenUnmodifiable())
			tl.getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(node.opacityProperty(), node.getOpacity())),
					new KeyFrame(Duration.millis(500), new KeyValue(node.opacityProperty(), 0d)));
		tl.playFromStart();
	}

	private void fadeIn() {
		final Timeline tl = new Timeline();
		for (final Node node : this.content.getChildrenUnmodifiable())
			tl.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(node.opacityProperty(), 0d)),
					new KeyFrame(Duration.millis(500), new KeyValue(node.opacityProperty(), 1d)));
		tl.playFromStart();
	}

	@FXML
	private void goToOpen() {
		this.fadeOut();

		this.content.getChildren().clear();
		this.help = new Label();
		this.help.getStyleClass().add("h5");
		this.help.setText("Quelle partie veux-tu charger ?");
		this.help.setGraphic(this.backButton);

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
					main.initGame(game);
				});
				this.gameContainer.getChildren().add(view);
			}
			this.gameContainer.setSpacing(10d);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.content.getChildren().addAll(this.help, this.gameScroll);

		this.fadeIn();
	}

	@FXML
	private void goToCreate() {
		this.fadeOut();

		this.content.getChildren().clear();
		this.content.setSpacing(15d);
		this.content.setAlignment(Pos.CENTER_LEFT);

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

		this.content.getChildren().addAll(backButton, help, field, precision, validate);

		this.fadeIn();
	}

	@FXML
	private void reloadJustOpened() {
		this.fadeOut();
		main.initAccueil2();
	}

	@FXML
	private void close() {
		((Stage) this.root.getScene().getWindow()).close();
	}

	@FXML
	private void selectNode(MouseEvent event) {
		final Button hovered = (Button) event.getSource();
		hovered.setGraphic(new SVGImage(Icons.asURL("right_keyboard_arrow.svg"), 18, 18));
	}

	@FXML
	private void unselectNode(MouseEvent event) {
		final Button hovered = (Button) event.getSource();
		hovered.setGraphic(null);
	}
}