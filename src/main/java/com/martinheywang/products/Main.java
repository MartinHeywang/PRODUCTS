package com.martinheywang.products;

import java.io.IOException;
import java.sql.SQLException;

import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.database.Database;
import com.martinheywang.products.model.devices.Buyer;
import com.martinheywang.products.model.devices.Constructor;
import com.martinheywang.products.model.devices.Conveyor;
import com.martinheywang.products.model.devices.Device;
import com.martinheywang.products.model.devices.Floor;
import com.martinheywang.products.model.devices.Furnace;
import com.martinheywang.products.model.devices.LeftConveyor;
import com.martinheywang.products.model.devices.MultiConveyor;
import com.martinheywang.products.model.devices.Press;
import com.martinheywang.products.model.devices.RightConveyor;
import com.martinheywang.products.model.devices.Seller;
import com.martinheywang.products.model.devices.WireDrawer;
import com.martinheywang.products.model.mechanics.GameManager;
import com.martinheywang.products.model.resources.DefaultResource;
import com.martinheywang.products.model.resources.Ingot;
import com.martinheywang.products.model.resources.Ore;
import com.martinheywang.products.model.resources.Plate;
import com.martinheywang.products.model.resources.Product;
import com.martinheywang.products.model.resources.Resource;
import com.martinheywang.products.model.resources.Wire;
import com.martinheywang.products.toolbox.Tools;
import com.martinheywang.products.view.GameController;
import com.martinheywang.products.view.Home;
import com.martinheywang.products.view.Home2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main class of the application. Launches and sets up everything.
 * 
 * @author Heywang
 *
 */
public final class Main extends Application {

	private static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("PRODUCTS.");
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/Icon.png")));

		
		try {
			// If at least one game is already registered
			if (Database.createDao(Game.class).countOf() == 0) {
				// We launch the home of the first users
				this.initAccueil();
			} else {
				// Else we launch the select or create window.
				this.initAccueil2();
			}
		} catch (final SQLException e) {
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
	 * Initialize the stage with the view Accueil.fxml, who corresponds to a the
	 * first start page (generally when no game can be found on the database).
	 * 
	 * @see Home
	 * @see Home#setMainApp(Main)
	 */
	public void initAccueil() {
		try {
			final FXMLLoader loader = Tools.prepareFXMLLoader("Home");

			final Parent root = loader.load();
			this.changeSceneTo(root);

			stage.setResizable(false);

			final Home controller = loader.getController();
			controller.setMainApp(this);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Initialize the stage with the view Accueil2.fxml, who demands to load the
	 * registered game or to begin a new game. (when at least one game is
	 * registered)
	 * 
	 * @see Home2
	 * @see Home2#setMainApp(Main)
	 */
	public void initAccueil2() {
		try {
			final FXMLLoader loader = Tools.prepareFXMLLoader("Home2");

			final Parent root = loader.load();
			this.changeSceneTo(root);

			stage.setResizable(true);
			stage.setMaxWidth(700d);
			stage.setMaxHeight(600d);
			stage.setMinWidth(500d);
			stage.setMinHeight(530d);

			final Home2 controller = loader.getController();
			controller.setMainApp(this);

		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Initialize the stage with the view Jeu.fxml, who loads all the images and
	 * resources to do this game functionnal.
	 * 
	 * @param game the game to load
	 * 
	 * @see GameController
	 * @see GameController#load(Game)
	 * @see GameController#setMainApp(Main)
	 */
	public void initGame(Game game) {
		try {
			final FXMLLoader loader = Tools.prepareFXMLLoader("Game");

			final Parent root = loader.load();
			this.changeSceneTo(root);

			stage.setResizable(true);
			stage.setMaxWidth(Double.MAX_VALUE);
			stage.setMaxHeight(Double.MAX_VALUE);
			stage.setMinWidth(550d);
			stage.setMinHeight(600d);

			final GameController controller = loader.getController();

			final GameManager manager = new GameManager(controller, game);
			manager.start();

			stage.setOnCloseRequest(event -> {
				try {
					manager.save();
				} catch (final SQLException e) {
					e.printStackTrace();
					final Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Partie non sauvegardée.");
					alert.setHeaderText("Une erreur est survenue lors de la sauvegarde de cette partie.");
					alert.setContentText(e.getMessage());
				}
				System.exit(0);
			});
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes the whole scene to the parent node given as input.
	 * 
	 * @param node the new scene's root
	 */
	private void changeSceneTo(Parent node) {
		final Scene scene = new Scene(node);
		stage.setScene(scene);
	}

	/**
	 * 
	 * @return the main window
	 */
	public static Stage getMainStage() {
		return stage;
	}

	public static void main(String[] args) {
		System.setProperty("com.j256.ormlite.logger.level", "ERROR");

		/*
		 * <?> Registering resources means making the game able to use them.
		 */
		Resource.register(DefaultResource.class);
		Resource.register(Ore.class);
		Resource.register(Plate.class);
		Resource.register(Wire.class);
		Resource.register(Ingot.class);
		Resource.register(Product.class);

		Device.registerType(Buyer.class);
		Device.registerType(Floor.class);
		Device.registerType(Seller.class);
		Device.registerType(Conveyor.class);
		Device.registerType(RightConveyor.class);
		Device.registerType(LeftConveyor.class);
		Device.registerType(Furnace.class);
		Device.registerType(Press.class);
		Device.registerType(WireDrawer.class);
		Device.registerType(Constructor.class);
		Device.registerType(MultiConveyor.class);

		launch(args);
	}
}