package com.martinheywang;

import java.io.IOException;
import java.sql.SQLException;

import com.martinheywang.model.Game;
import com.martinheywang.model.database.Database;
import com.martinheywang.toolbox.Tools;
import com.martinheywang.view.GameController;
import com.martinheywang.view.Home;
import com.martinheywang.view.Home2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main class of the application. Launches and setting up
 * everything.
 * 
 * @author Heywang
 *
 */
public final class Main extends Application {

	private static Stage stage;

	public static void main(String[] args) {

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
			if (Database.createDao(Game.class).queryForAll().size() == 0)
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
		try {
			FXMLLoader loader = Tools.prepareFXMLLoader("Home");

			Parent root = loader.load();
			changeSceneTo(root);

			stage.setResizable(false);

			Home controller = loader.getController();
			controller.setMainApp(this);
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
		try {
			FXMLLoader loader = Tools.prepareFXMLLoader("Home2");

			Parent root = loader.load();
			changeSceneTo(root);

			stage.setResizable(true);
			stage.setMaxWidth(700d);
			stage.setMaxHeight(600d);
			stage.setMinWidth(500d);
			stage.setMinHeight(530d);

			Home2 controller = loader.getController();
			controller.setMainApp(this);

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
			FXMLLoader loader = Tools.prepareFXMLLoader("Game");

			Parent root = loader.load();
			changeSceneTo(root);

			stage.setResizable(true);

			GameController controller = loader.getController();
			controller.setMainApp(this);
			controller.load(game);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						controller.saveGame();
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

	/**
	 * Changes the whole scene to the parent node given as input.
	 * 
	 * @param node the new scene's root
	 */
	private void changeSceneTo(Parent node) {
		Scene scene = new Scene(node);
		stage.setScene(scene);
	}

	/**
	 * 
	 * @return the main window
	 */
	public static Stage getMainStage() {
		return stage;
	}
}