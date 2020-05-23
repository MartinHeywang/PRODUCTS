package com.martinheywang;

import java.io.IOException;
import java.sql.SQLException;

import com.martinheywang.model.Game;
import com.martinheywang.view.GameController;
import com.martinheywang.view.Home;
import com.martinheywang.view.Home2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static Stage stage;

	public static void main(String[] args) {
		System.setProperty("ormlite.logs.debug", "false");

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// Little stage paramatering
			stage = primaryStage;
			stage.setTitle("PRODUCTS.");
			stage.getIcons().add(
					new Image(getClass().getResourceAsStream("/Icone.png")));

			// Then one or the other
			if (Database.daoGame().queryForAll().size() == 0)
				initAccueil();
			else {
				initAccueil2();
			}
		} catch (SQLException e) {
			System.err.println(
					"Oh ! There is a mistake ! The games can't be loaded... "
							+ "Here is the full error message :\n");
			e.printStackTrace();
		}
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				System.exit(0);
			}
		});
		// Show the main stage
		stage.show();
	}

	/**
	 * Initialize the stage with the view Accueil.fxml, who corresponds to
	 * a the first start page (generally when no game can be found on the
	 * database).
	 * 
	 * @see Home
	 * @see Home#setMainApp(Main)
	 */
	public void initAccueil() {
		// Allow to load .fxml files
		FXMLLoader loader = new FXMLLoader();

		try {
			// Defining the file to search
			loader.setLocation(
					getClass().getResource("/Home.fxml"));
			// Loads the file
			BorderPane conteneurPrincipal = (BorderPane) loader.load();
			// Creating a new scene and add the loaded file
			Scene scene = new Scene(conteneurPrincipal);
			// Setting the scene to the main stage
			stage.setScene(scene);
			stage.setResizable(false);

			// Creating the controller of the scene
			Home controller = loader.getController();
			controller.setMainApp(this);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent e) {
					if (e.getCode() == KeyCode.ENTER) {
						controller.seConnecter();
					}
				}

			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Initialize the stage with the view Accueil2.fxml, who demands to
	 * load the registered game or to begin a new game. (when at least one
	 * game is registered)
	 * 
	 * @see Home2
	 * @see Home2#setMainApp(Main)
	 */
	public void initAccueil2() {
		// Allow to load .fxml files
		FXMLLoader loader = new FXMLLoader();

		try {
			// Which file do we want to load
			loader.setLocation(
					getClass().getResource("/Home2.fxml"));
			// Load the file, obviously
			BorderPane conteneurPrincipal = (BorderPane) loader.load();
			// Creating a new scene, in which we put the loaded file
			Scene scene = new Scene(conteneurPrincipal);
			// Setting the scene of th stage
			stage.setScene(scene);
			stage.setResizable(false);

			// Creating a controller for our scene
			Home2 controler = loader.getController();
			controler.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Initialize the stage with the view Jeu.fxml, who loads all the
	 * images and resources to do this game functionnal.
	 * 
	 * @param game the game to load
	 * 
	 * @see GameController
	 * @see GameController#load(Game)
	 * @see GameController#setMainApp(Main)
	 */
	public void initGame(Game game) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Game.fxml"));

			BorderPane Bp = (BorderPane) loader.load();
			Scene scene = new Scene(Bp);
			stage.setScene(scene);
			stage.setResizable(true);

			GameController controller = loader.getController();
			controller.setMainApp(this);
			controller.load(game);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						controller.getPartieEnCours().save();
					} catch (SQLException e) {
						System.out.println(e.getLocalizedMessage());

					}
					System.exit(0);
				}
			});
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}