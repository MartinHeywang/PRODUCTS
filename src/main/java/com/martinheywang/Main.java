package com.martinheywang;

import java.io.IOException;

import com.martinheywang.model.Game;
import com.martinheywang.model.devices.Buyer;
import com.martinheywang.model.devices.Conveyor;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Floor;
import com.martinheywang.model.devices.LeftConveyor;
import com.martinheywang.model.devices.RightConveyor;
import com.martinheywang.model.devices.Seller;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.resources.DefaultResource;
import com.martinheywang.model.resources.Ingot;
import com.martinheywang.model.resources.Ore;
import com.martinheywang.model.resources.Plate;
import com.martinheywang.model.resources.Product;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.model.resources.Wire;
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
 * The main class of the application. Launches and sets up everything.
 * 
 * @author Heywang
 *
 */
public final class Main extends Application {

	private static Stage stage;

	public static void main(String[] args) {
		System.setProperty("com.j256.ormlite.logger.level", "ERROR");

		/* <?> Registering resources means making the game able to use
		 * them. */
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

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("PRODUCTS.");
		stage.getIcons().add(
				new Image(getClass()
						.getResourceAsStream("/images/Icone.png")));

		initAccueil();

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
			stage.setMaxWidth(Double.MAX_VALUE);
			stage.setMaxHeight(Double.MAX_VALUE);
			stage.setMinWidth(550d);
			stage.setMinHeight(600d);

			GameController controller = loader.getController();

			GameManager manager = new GameManager(controller, game);
			manager.start();
		} catch (IOException e) {
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