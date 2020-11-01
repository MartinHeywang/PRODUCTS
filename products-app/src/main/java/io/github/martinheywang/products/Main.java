package io.github.martinheywang.products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;

import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.controller.DeviceController;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.controller.ResourceManager;
import io.github.martinheywang.products.model.database.Database;
import io.github.martinheywang.products.view.GameMenuView;
import io.github.martinheywang.products.view.HomeView;
import io.github.martinheywang.products.view.HomeView2;
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
		// PLUGINS STUFF
		final PluginManager pluginManager = new JarPluginManager();

		System.out.println("Registering resource enumeration:");
		final List<Class<? extends Resource>> resourceEnums = pluginManager.getExtensionClasses(Resource.class);
		for (final Class<? extends Resource> resourceEnum : resourceEnums) {
			ResourceManager.register(resourceEnum);
			System.out.println("| - - - " + resourceEnum);
		}

		System.out.println("Registering device type:");
		final List<Class<? extends Device>> devicesClasses = pluginManager.getExtensionClasses(Device.class);
		for (final Class<? extends Device> deviceClass : devicesClasses) {
			DeviceController.registerType(deviceClass);
			System.out.println("| - - - " + deviceClass);
		}

		System.out.println(
				"____________________________________________________________________________");

		stage = primaryStage;
		stage.setTitle("PRODUCTS.");
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/Icon.png")));

		try {
			// If at least one game is already registered
			if (Database.createDao(Game.class).countOf() == 0)
				// We launch the home of the first users
				this.initAccueil();
			else
				// Else we launch the select or create window.
				this.initAccueil2();
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
	 * @see HomeView
	 * @see HomeView#setMainApp(Main)
	 */
	public void initAccueil() {
		try {
			final FXMLLoader loader = prepareFXMLLoader("Home");

			final Parent root = loader.load();
			this.changeSceneTo(root);

			stage.setResizable(false);

			final HomeView controller = loader.getController();
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
	 * @see HomeView2
	 * @see HomeView2#setMainApp(Main)
	 */
	public void initAccueil2() {
		try {
			final FXMLLoader loader = prepareFXMLLoader("Home2");

			final Parent root = loader.load();
			this.changeSceneTo(root);

			stage.setResizable(true);
			stage.setMaxWidth(700d);
			stage.setMaxHeight(600d);
			stage.setMinWidth(500d);
			stage.setMinHeight(530d);

			final HomeView2 controller = loader.getController();
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
	 * @see GameMenuView
	 * @see GameMenuView#load(Game)
	 * @see GameMenuView#setMainApp(Main)
	 */
	public void initGame(Game game) {
		try {
			final FXMLLoader loader = prepareFXMLLoader("Game");

			final Parent root = loader.load();
			this.changeSceneTo(root);

			stage.setResizable(true);
			stage.setMaxWidth(Double.MAX_VALUE);
			stage.setMaxHeight(Double.MAX_VALUE);
			stage.setMinWidth(550d);
			stage.setMinHeight(600d);

			final GameMenuView controller = loader.getController();

			final GameController manager = new GameController(controller, game);
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

		launch(args);
	}

	/**
	 * Prepares a new {@link FXMLoader} with the given string as input. Do not
	 * specify the extension nor the directory, as long as this file is in a
	 * resource directory.<br>
	 * 
	 * <pre>
	 * <code>
	 * //The loaded file here is src/main/resources/fxml/Game.fxml
	 * FXMLLoader loader = prepareFXMLLoader("Game");
	 * 
	 * </code>
	 * </pre>
	 * 
	 * @param file the name of the file to load
	 * @return a new FXMLLoader
	 * @throws IOException if the file couldn't not be loaded.
	 */
	public static FXMLLoader prepareFXMLLoader(String file) throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fxml/" + file + ".fxml"));
		return loader;
	}
}