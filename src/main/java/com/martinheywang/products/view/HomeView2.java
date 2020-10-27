package com.martinheywang.products.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.database.Database;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class HomeView2 implements Initializable {

	Main main;

	@FXML
	private AnchorPane openPane, createPane;
	@FXML
	private HBox logoContainer;
	@FXML
	private VBox root, optionContainer;
	@FXML
	private Separator logoSeparator;
	@FXML
	private Label lead, helpLabel;

	final Pane growPane = new Pane();

	final ScrollPane gameScroll = new ScrollPane();
	final VBox gameContainer = new VBox();
	Button backButton = new Button("Retour");

	final TextField field = new TextField();

	private void create(String text) {
		try {
			final Game game = new Game(text);
			game.save();
			main.initGame(game);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resources) {
		backButton.setOnMouseClicked(event -> reloadJustOpened());
		field.setOnAction(event -> create(field.getText()));
		field.getStylesheets().add(Main.class.getResource("/fxml/TextField.css").toExternalForm());
		field.setFocusTraversable(false);
		field.setPromptText("\"Partie de Luc\"");
		VBox.setMargin(field, new Insets(2d, 0d, 5d, 0d));

		lead.setMinHeight(100d);
		VBox.setVgrow(growPane, Priority.ALWAYS);

		gameScroll.setFitToHeight(true);
		gameScroll.setFitToWidth(true);
		gameScroll.setContent(gameContainer);

		openPane.setStyle("-fx-background-radius: 3px");
		createPane.setStyle("-fx-background-radius: 3px");

		try {
			final List<Game> games = Database.createDao(Game.class).queryForAll();
			games.sort(Comparator.comparing(Game::getLastSave).reversed());
			for (final Game game : games) {
				final Displayer<Game> display = game.getDisplayer();
				display.setOnMouseClicked(event -> main.initGame(game));
				display.getStyleClass().add("game-open");
				gameContainer.getChildren().addAll(display);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <h1>setMainApp</h1>
	 * <p>
	 * Sets the object main
	 * </p>
	 * 
	 * @param main the object to set
	 */
	public void setMainApp(final Main main) {
		this.main = main;
	}

	private void fadeOut() {
		final Timeline tl = new Timeline();
		for (Node node : root.getChildrenUnmodifiable()) {
			tl.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, 
					new KeyValue(node.opacityProperty(), node.getOpacity())),
				new KeyFrame(Duration.millis(500), new KeyValue(node.opacityProperty(), 0d)));
		}
		tl.playFromStart();
	}

	private void fadeIn() {
		final Timeline tl = new Timeline();
		for (Node node : root.getChildrenUnmodifiable()) {
			tl.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, 
					new KeyValue(node.opacityProperty(), 0d)),
				new KeyFrame(Duration.millis(500), new KeyValue(node.opacityProperty(), 1d)));
		}
		tl.playFromStart();
	}

	@FXML
	private void goToOpen() {
		fadeOut();
		root.getChildren().removeAll(optionContainer, logoSeparator, logoContainer);
		root.getChildren().add(gameScroll);

		helpLabel.setText("Sélectionnez une partie à charger:");
		root.getChildren().addAll(growPane, backButton);
		fadeIn();
	}

	@FXML
	private void goToCreate() {
		fadeOut();
		root.getChildren().removeAll(optionContainer, logoContainer, logoSeparator);
		helpLabel.setText("Entrez le nom de la nouvelle partie, puis appuyez sur entrer: (Vous ne pourrez pas le changer!)");
		root.getChildren().addAll(field, growPane, backButton);
		fadeIn();
	}

	@FXML
	private void reloadJustOpened() {
		fadeOut();
		helpLabel.setText("Choisissez l'une des deux options suivantes:");
		root.getChildren().removeAll(gameScroll, backButton, field, growPane);
		root.getChildren().add(0, logoSeparator);
		root.getChildren().add(0, logoContainer);
		root.getChildren().add(optionContainer);
		fadeIn();
	}

}
