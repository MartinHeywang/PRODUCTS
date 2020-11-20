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
import io.github.martinheywang.products.kit.view.component.GameView;
import io.github.martinheywang.products.kit.view.component.SVGImage;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

	Main main;

	@FXML
	private AnchorPane openPane, createPane;
	@FXML
	private HBox logoContainer;
	@FXML
	private VBox root, content, optionContainer;
	@FXML
	private Separator logoSeparator;
	@FXML
	private Label lead, helpLabel;

	@FXML
	private HBox stageBar;

	// Stage buttons
	@FXML
	private Button reduceButton, maximizeButton, closeButton;

	final Pane growPane = new Pane();

	final ScrollPane gameScroll = new ScrollPane();
	final VBox gameContainer = new VBox();
	Button backButton = new Button("Retour");

	final TextField field = new TextField();

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
		this.root.getStylesheets().addAll(ViewUtils.class.getResource("/css/General.css").toExternalForm(),
				ViewUtils.class.getResource("/css/Home2.css").toExternalForm(),
				ViewUtils.class.getResource("/css/Label.css").toExternalForm(),
				ViewUtils.class.getResource("/css/Buttons.css").toExternalForm(),
				ViewUtils.class.getResource("/css/TextField.css").toExternalForm(),
				ViewUtils.class.getResource("/css/Stage.css").toExternalForm());

		closeButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Close.svg"), 20, 20));
		reduceButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Reduce.svg"), 20, 20));
		maximizeButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Maximize.svg"), 20, 20));

		closeButton.setOnMouseClicked(event -> {
			Platform.exit();
		});
		reduceButton.setOnMouseClicked(event -> {
			((Stage) reduceButton.getScene().getWindow()).setIconified(true);
		});
		maximizeButton.setOnMouseClicked(event -> {
			final Stage stage = ((Stage) reduceButton.getScene().getWindow());
			if(stage.isMaximized()){
				stage.setMaximized(false);
				maximizeButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Maximize.svg"), 20, 20));
			}else{
				stage.setMaximized(true);
				maximizeButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Minimize.svg"), 20, 20));
			}
		});

		stageBar.setOnMousePressed(pressEvent -> {
			stageBar.setOnMouseDragged(dragEvent -> {
				final Stage primaryStage = (Stage) this.root.getScene().getWindow();
				primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});

		this.logoContainer.getChildren().add(0, new SVGImage(getClass().getResource("/images/icons/logo.svg"), 50, 75));
		this.backButton.setOnMouseClicked(event -> this.reloadJustOpened());
		this.field.setOnAction(event -> this.create(this.field.getText()));
		this.field.setFocusTraversable(false);
		this.field.setPromptText("\"Partie de Luc\"");
		VBox.setMargin(this.field, new Insets(2d, 0d, 5d, 0d));

		this.lead.setMinHeight(100d);
		VBox.setVgrow(this.growPane, Priority.ALWAYS);

		this.gameScroll.setFitToHeight(true);
		this.gameScroll.setFitToWidth(true);
		this.gameScroll.setContent(this.gameContainer);

		this.openPane.setStyle("-fx-background-radius: 3px");
		this.createPane.setStyle("-fx-background-radius: 3px");

		try {
			final List<Game> games = Database.createDao(Game.class).queryForAll();
			games.sort(Comparator.comparing(Game::getLastSave).reversed());
			for (final Game game : games) {
				final GameView view = new GameView(game);
				view.setOnMouseClicked(event -> this.main.initGame(game));
				view.getStyleClass().add("game-open");
				this.gameContainer.getChildren().add(view);

			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
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
		maximizeButton.disableProperty()
				.bind(((Stage) maximizeButton.getScene().getWindow()).resizableProperty().not());
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
		this.content.getChildren().removeAll(this.optionContainer, this.logoSeparator, this.logoContainer);
		this.content.getChildren().add(this.gameScroll);

		this.helpLabel.setText("Sélectionnez une partie à charger:");
		this.content.getChildren().addAll(this.growPane, this.backButton);
		this.fadeIn();
	}

	@FXML
	private void goToCreate() {
		this.fadeOut();
		this.content.getChildren().removeAll(this.optionContainer, this.logoContainer, this.logoSeparator);
		this.helpLabel.setText(
				"Entrez le nom de la nouvelle partie, puis appuyez sur entrer: (Vous ne pourrez pas le changer!)");
		this.content.getChildren().addAll(this.field, this.growPane, this.backButton);
		this.fadeIn();
	}

	@FXML
	private void reloadJustOpened() {
		this.fadeOut();
		this.helpLabel.setText("Choisissez l'une des deux options suivantes:");
		this.content.getChildren().removeAll(this.gameScroll, this.backButton, this.field, this.growPane);
		this.content.getChildren().add(0, this.logoSeparator);
		this.content.getChildren().add(0, this.logoContainer);
		this.content.getChildren().add(this.optionContainer);
		this.fadeIn();
	}

}
