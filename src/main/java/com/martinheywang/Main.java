package com.martinheywang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// Little stage paramatering
			stage = primaryStage;
			stage.setTitle("PRODUCTS.");
			stage.getIcons().add(new Image(
					new FileInputStream(
							new File("src/main/resources/images/Icone.png"))));

			// Then one or the other
			if (Database.daoGame().queryForAll().size() == 0)
				initAccueil();
			else {
				initAccueil2();
			}
		} catch (FileNotFoundException e) {
			System.err.println(
					"Oh ! There is a mistake ! The logo can't be loaded... "
							+ "Here is the full error message :\n");
			e.printStackTrace();
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
		// Permet de cherger des fichiers .fxml
		FXMLLoader loader = new FXMLLoader();
		// D�finit l'emplacement o� chercher

		try {
			loader.setLocation(
					getClass().getResource("/fxml/Home.fxml"));
			// Charge le fichier dans notre variable de contenu, comme pr�vu
			BorderPane conteneurPrincipal = (BorderPane) loader.load();
			// Fen�tre edans laquelle s'affiche notre contenu
			Scene scene = new Scene(conteneurPrincipal);
			// Montre � la fen�tre quel sc�ne utiliser
			stage.setScene(scene);
			stage.setResizable(false);

			// Le contr�leur de la fen�tre
			Home controller = loader.getController();
			controller.setMainApp(this);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent e) {
					if (e.getCode() == KeyCode.ENTER) { // Si on tape sur entr�e
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
		// Permet de charger des fichiers .fxml
		FXMLLoader loader = new FXMLLoader();

		try {
			// D�finit l'emplacement o� chercher le fichier
			loader.setLocation(
					getClass().getResource("/fxml/Home2.fxml"));
			// Charge le fichier dans notre variable de contenu, comme pr�vu
			BorderPane conteneurPrincipal = (BorderPane) loader.load();
			// Fen�tre dans laquelle s'affiche notre contenu
			Scene scene = new Scene(conteneurPrincipal);
			// Montre � la fen�tre quel stage utiliser, et donc son contenu
			stage.setScene(scene);
			stage.setResizable(false);

			// Le contr�leur de la fen�tre
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
	 * @see JeuContr�le
	 * @see JeuContr�le#load(Game)
	 * @see JeuContr�le#setMainApp(Main)
	 */
	public void initGame(Game game) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/Game.fxml"));

			BorderPane Bp = (BorderPane) loader.load();
			Scene scene = new Scene(Bp);
			stage.setScene(scene);

			stage.setResizable(true);

			GameController controller = loader.getController();
			controller.setMainApp(this);
			controller.load(game);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					try {
						controller.getPartieEnCours().save();
					} catch (SQLException e1) {
						System.out.println(e1.getLocalizedMessage());

					}
					System.exit(0);
				}
			});
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}