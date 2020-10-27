package com.martinheywang.products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;

import com.martinheywang.products.controller.DeviceController;
import com.martinheywang.products.controller.GameController;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.database.Database;
import com.martinheywang.products.model.device.Buyer;
import com.martinheywang.products.model.device.Constructor;
import com.martinheywang.products.model.device.Conveyor;
import com.martinheywang.products.model.device.Device;
import com.martinheywang.products.model.device.Furnace;
import com.martinheywang.products.model.device.LeftConveyor;
import com.martinheywang.products.model.device.MultiConveyor;
import com.martinheywang.products.model.device.Press;
import com.martinheywang.products.model.device.RightConveyor;
import com.martinheywang.products.model.device.Seller;
import com.martinheywang.products.model.device.WireDrawer;
import com.martinheywang.products.model.resource.DefaultResource;
import com.martinheywang.products.model.resource.Ingot;
import com.martinheywang.products.model.resource.Ore;
import com.martinheywang.products.model.resource.Plate;
import com.martinheywang.products.model.resource.Product;
import com.martinheywang.products.model.resource.Resource;
import com.martinheywang.products.model.resource.Wire;
import com.martinheywang.products.toolbox.Tools;
import com.martinheywang.products.view.GameView;
import com.martinheywang.products.view.HomeView;
import com.martinheywang.products.view.HomeView2;

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

	final List<Class<? extends Resource>> greetingsClasses = pluginManager.getExtensionClasses(Resource.class);
	for (final Class<? extends Resource> greeting : greetingsClasses)
	    Resource.register(greeting);

	final List<Class<? extends Device>> devicesClasses = pluginManager.getExtensionClasses(Device.class);
	for (final Class<? extends Device> greeting : devicesClasses)
	    DeviceController.registerType(greeting);

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
	    final FXMLLoader loader = Tools.prepareFXMLLoader("Home");

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
	    final FXMLLoader loader = Tools.prepareFXMLLoader("Home2");

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
     * @see GameView
     * @see GameView#load(Game)
     * @see GameView#setMainApp(Main)
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

	    final GameView controller = loader.getController();

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

	DeviceController.registerType(Buyer.class);
	DeviceController.registerType(Seller.class);
	DeviceController.registerType(Conveyor.class);
	DeviceController.registerType(LeftConveyor.class);
	DeviceController.registerType(RightConveyor.class);
	DeviceController.registerType(MultiConveyor.class);
	DeviceController.registerType(Furnace.class);
	DeviceController.registerType(Press.class);
	DeviceController.registerType(WireDrawer.class);
	DeviceController.registerType(Constructor.class);

	Resource.register(Ore.class);
	Resource.register(Plate.class);
	Resource.register(Ingot.class);
	Resource.register(Wire.class);
	Resource.register(Product.class);
	Resource.register(DefaultResource.class);

	launch(args);
    }
}