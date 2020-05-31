package com.martinheywang.model.devices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Behaviour;
import com.martinheywang.model.devices.behaviours.None_;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.Dashboard;
import com.martinheywang.view.DeviceController;
import com.martinheywang.view.GameController;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * The <em>Device</em> is an abstract class that all the devices
 * extends. In the JavaFX scene, it is an ImageView. In the game, it
 * corresponds to each square in the main GridPane.
 * 
 * @author Martin Heywang
 */
public abstract class Device extends ImageView {

	/**
	 * The DeviceModel that contains all the data specified about this
	 * device. Persistent object.
	 * 
	 * @see DeviceModel
	 */
	protected DeviceModel model;

	/**
	 * The Behaviour is basically his behaviour that runs each iterations
	 * of the Play class in GameController.
	 * 
	 * @see GameController
	 * 
	 * @see Behaviour
	 */
	protected Behaviour behaviour = new None_();

	/**
	 * The controller is useful when you want to change somme data about
	 * the game.
	 * 
	 * @see GameController
	 */
	protected GameController controller;

	/**
	 * The template is a data object who gives all the pointers of this
	 * device, and can indicates which type of connection it is.<br>
	 * <br>
	 * It is good practice to create first a private static TemplateModel
	 * :<br>
	 * <br>
	 * <code>
	 * private static TemplateModel templateModel = new TemplateModel(PointerTypes.NONE, ...);
	 * </code> <br>
	 * <br>
	 * Four values of type PointerTypes must be given to the contructor :
	 * four PointerTypes values that indicates which type of connection it
	 * is, respectively for the top, then right, then bottom, and to
	 * finish left. <br>
	 * This way you can easily create the template, invoking the
	 * <code>createTemplate</code> method in the constructor, like this
	 * :<br>
	 * <br>
	 * <code>
	 * template = templateModel.createTemplate(model.getCoordinates(), model.getDirection());
	 * </code> <br>
	 * where <em>model</em> is the current model of the device.
	 * 
	 * <em>Here is an example with the conveyor :</em> <blockquote><code>
	 * 
	 * // As a field<br>
	 * private static TemplateModel templateModel = new TemplateModel(<br>
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,<br>
			PointerTypes.NONE);<br><br>
				
				
		// Later in the constructor<br>
		template = templateModel.createTemplate(model.getCoordinates(),
			model.getDirection());
			
	 * </code></blockquote>
	 * 
	 * @see Template
	 */
	protected Template template;

	/**
	 * This Timeline field represents the animation is invoked when his
	 * action is invoked. When overriding <code>Device.action</code>,
	 * don't forget do play this animation.
	 */
	protected Timeline timeline;

	/**
	 * The dashboard is an optional function. It is a pane included in the
	 * dialog of the device that can give a lot of informations about the
	 * object.
	 */
	protected Dashboard dashboard;

	private EventHandler<MouseEvent> onClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				try {
					// Loading and opening the dialog
					// Creating a fxml file loader
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(
							getClass().getResource("/Device.fxml"));

					Dialog<Type> dialog;
					DialogPane dialogPane;

					dialogPane = (DialogPane) loader.load();
					dialog = new Dialog<Type>();
					dialog.setTitle("Sélection d'appareil - PRODUCTS.");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.stage);
					dialog.initModality(Modality.NONE);

					DeviceController controller = loader.getController();
					controller.setMainApp(model.getCoordinates(), dashboard);

					dialog.showAndWait();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (event.getButton().equals(MouseButton.SECONDARY)) {
				rotate();
			}
		}
	};

	public static int electricity = 5;

	/**
	 * Creates a new device.
	 * 
	 * @param xy         the coordinates of this device
	 * @param type       the kind of the device
	 * @param direction  the rotate of this device
	 * @param niveau     the level of this device
	 * @param controller the game controller
	 * 
	 */
	protected Device(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super();
		this.setImage(new Image(
				getClass().getResourceAsStream(model.getNiveau().getURL()
						+ model.getType().getURL())));

		// Defs
		this.model = model;
		this.controller = controller;

		// Defs visual effect
		this.setRotate(model.getDirection().getRotate());
		this.setOpacity(0.7);

		// Def Timeline
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0),
				new KeyValue(this.opacityProperty(), 1)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(750),
				new KeyValue(this.opacityProperty(), 1)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
				new KeyValue(this.opacityProperty(), 0.7)));

		// Def onClick
		this.setOnMouseClicked(onClicked);

		// Def dashboard
		dashboard = new Dashboard(this);
	}

	/**
	 * This method represents the behaviour of this device. When called,
	 * it calls the defined behaviour.<br>
	 * <strong>Note :</strong> since most of the device has no longer more
	 * than 1 exit, this method is like a <em>shorcut</em> for <em>simple
	 * devices</em>.It only works if the devices has 1 exit, neither less
	 * or more. In those cases, this method <strong>must be</strong>
	 * overriden to make the device work properly.
	 * 
	 * @param resATraiter the resource who will be used by this device
	 */
	public void action(Pack resATraiter) throws MoneyException {
		for (Coordinates xy : template.getPointersFor(PointerTypes.EXIT)) {
			if (xy.isInGrid(controller.getPartieEnCours().getTailleGrille())) {
				final Device pointedDevice = controller.findDevice(xy);
				for (Coordinates enter : pointedDevice.getTemplate()
						.getPointersFor(PointerTypes.ENTRY)) {
					if (enter.getX() == model.getCoordinates().getX() &&
							enter.getY() == model.getCoordinates().getY()) {
						timeline.playFromStart();
						behaviour.action(resATraiter,
								template.getPointersFor(PointerTypes.EXIT)
										.get(0));
					}
				}
			}
		}
	}

	public void upgrade() {
		// Define how the devices should upgarde themself + javadoc
	}

	public void rotate() {

		// Sets the new direction
		model.setDirection(model.getDirection().getNext());
		// Sets the visual effect
		this.setRotate(model.getDirection().getRotate());
		// Sets the template (such a module that describes the entry and the
		// exit
		this.setTemplate(
				this.getTemplateModel().createTemplate(model.getCoordinates(),
						model.getDirection()));
		// Save it in the database
		try {
			Database.daoDeviceModel().update(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		// Define how the devices should destroy themself + javadoc
	}

	protected abstract TemplateModel getTemplateModel();

	public DeviceModel getModel() {
		return model;
	}

	/**
	 * @return the comportement
	 */
	public Behaviour getComportement() {
		return behaviour;
	}

	/**
	 * @return the controller
	 */
	public GameController getController() {
		return controller;
	}

	/**
	 * 
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @return the electricity
	 */
	public static int getElectricity() {
		return electricity;
	}

	/**
	 * @param behaviour the comportement to set
	 */
	public void setComportement(Behaviour behaviour) {
		this.behaviour = behaviour;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(GameController controller) {
		this.controller = controller;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * @param electricity the electriciy to set
	 */
	public static void setElectricity(int electricity) {
		Device.electricity = electricity;
	}
}
