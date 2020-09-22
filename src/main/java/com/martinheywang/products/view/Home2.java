package com.martinheywang.products.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.database.Database;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Home2 implements Initializable {

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
	private Label helpLabel;

	final ScrollPane gameScroll = new ScrollPane();
	final VBox gameContainer = new VBox();
	Button backButton = new Button("Retour");

	@FXML
	private void create() {
		/*
		 * if (!field.getText().isEmpty()) { try { final Game game = new
		 * Game(field.getText()); game.save(); main.initGame(game); } catch (final
		 * Exception e) { e.printStackTrace(); } }
		 */
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resources) {
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

	}

	private void fadeIn() {

	}

	@FXML
	private void goToOpen() {
		root.getChildren().removeAll(optionContainer, logoSeparator, logoContainer);
		gameContainer.getChildren().clear();
		try {
			final List<Game> games = Database.createDao(Game.class).queryForAll();
			System.out.println(games);
			// Todo: Sort list
			for (final Game game : games) {
				final Displayer<Game> display = game.getDisplayer();
				display.setCursor(Cursor.HAND);
				display.setOnMouseClicked(event -> main.initGame(game));
				gameContainer.getChildren().addAll(display);

			}
			root.getChildren().add(gameScroll);


			helpLabel.setText("Sélectionnez une partie à charger:");
			root.getChildren().add(backButton);

			backButton.setOnMouseClicked(event -> reloadJustOpened());

			gameScroll.setFitToHeight(true);
			gameScroll.setFitToWidth(true);
			gameScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			gameScroll.setContent(gameContainer);
			VBox.setVgrow(gameScroll, Priority.ALWAYS);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void goToCreate(){

	}

	@FXML
	private void reloadJustOpened(){
		helpLabel.setText("Choisissez l'une des deux options suivantes:");
		root.getChildren().removeAll(gameScroll, backButton);
		root.getChildren().add(0, logoSeparator);
		root.getChildren().add(0, logoContainer);
		root.getChildren().add(optionContainer);
	}

}
