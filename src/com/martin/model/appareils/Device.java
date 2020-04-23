package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.martin.Main;
import com.martin.model.Coordinates;
import com.martin.model.LocatedImage;
import com.martin.model.Stock;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.comportement.Behaviour;
import com.martin.model.appareils.comportement.None_;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.Dashboard;
import com.martin.view.DeviceController;
import com.martin.view.JeuContr�le;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

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
	 * of the Play class in JeuContr�le.
	 * 
	 * @see JeuContr�le
	 * 
	 * @see Behaviour
	 */
	protected Behaviour behaviour = new None_();

	/**
	 * The controller is useful when you want to change somme data about
	 * the game.
	 * 
	 * @see JeuContr�le
	 */
	protected JeuContr�le controller;

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
	 * The dashboard is an optional function. It is a pane included in the
	 * dialog of the device that can give a lot of informations about the
	 * object.
	 */
	protected Dashboard dashboard = new Dashboard();

	private EventHandler<MouseEvent> onClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			try {
				// Loading and opening the dialog
				// Creating a fxml file loader
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(
						Main.class.getResource("view/Device.fxml"));

				Dialog<Type> dialog;
				DialogPane dialogPane;

				dialogPane = (DialogPane) loader.load();
				dialog = new Dialog<Type>();
				dialog.setTitle("S�lection d'appareil - PRODUCTS.");
				dialog.setDialogPane(dialogPane);
				dialog.initOwner(Main.stage);
				dialog.initModality(Modality.NONE);

				DeviceController controller = loader.getController();
				controller.setMainApp(model.getCoordinates().getX(),
						model.getCoordinates().getY(), dashboard);

				dialog.showAndWait();

			} catch (IOException e) {
				/*
				 * Si une erreur est survenue lors du chargement de la fen�tre,
				 * afficher le message plus la raison donn�e par Java.
				 */
				System.err.println(
						"ERREUR dans Appareil.java entre les lignes 59 et 79 excluses."
								+ "Raison :\n" + e.getMessage());
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
	protected Device(DeviceModel model, JeuContr�le controller)
			throws FileNotFoundException {
		super(new LocatedImage(
				model.getNiveau().getURL() + model.getType().getURL()));

		this.model = model;
		this.controller = controller;

		this.setRotate(model.getDirection().getRotate());

		this.setOnMouseClicked(onClicked);
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
	public void action(Stock resATraiter) throws MoneyException {
		for (Coordinates xy : template.getPointersFor(PointerTypes.EXIT)) {
			if (xy.isInGrid(controller.getPartieEnCours().getTailleGrille())) {
				final Device pointedDevice = controller.findDevice(xy);
				for (Coordinates enter : pointedDevice.getTemplate()
						.getPointersFor(PointerTypes.ENTRY)) {
					if (enter.getX() == model.getCoordinates().getX() &&
							enter.getY() == model.getCoordinates().getY())
						System.out.println("Connection available.");
					behaviour.action(resATraiter,
							template.getPointersFor(PointerTypes.EXIT).get(0));
				}
			}
		}
	}

	// GETTERS, THEN SETTERS

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
	public JeuContr�le getController() {
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
	 * @return the �lectricit�
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
	public void setController(JeuContr�le controller) {
		this.controller = controller;
	}

	/**
	 * @param �lectricit� the �lectricit� to set
	 */
	public static void setElectricity(int electricity) {
		Device.electricity = electricity;
	}
}
