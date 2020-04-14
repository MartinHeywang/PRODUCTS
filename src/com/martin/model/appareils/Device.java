package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.martin.Main;
import com.martin.model.LocatedImage;
import com.martin.model.Stock;
import com.martin.model.appareils.comportement.Behaviour;
import com.martin.model.appareils.comportement.None_;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.DeviceController;
import com.martin.view.Dashboard;
import com.martin.view.JeuContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public abstract class Device extends ImageView {

	protected DeviceModel model;

	protected Behaviour behaviour = new None_();

	protected JeuContrôle controller;

	protected List<Direction> entrances;
	protected List<Direction> exits;

	protected Dashboard dashboard = new Dashboard();

	private EventHandler<MouseEvent> onClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			try {
				// Loading and opening the dialog
				// Creating a fxml file loader
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(
						Main.class.getResource("view/Appareil.fxml"));

				Dialog<Type> dialog;
				DialogPane dialogPane;

				dialogPane = (DialogPane) loader.load();
				dialog = new Dialog<Type>();
				dialog.setTitle("Sélection d'appareil - PRODUCTS.");
				dialog.setDialogPane(dialogPane);
				dialog.initOwner(Main.stage);
				dialog.initModality(Modality.NONE);

				DeviceController controller = loader.getController();
				controller.setMainApp(model.getCoordonnees().getX(),
						model.getCoordonnees().getY(), dashboard);

				dialog.showAndWait();

			} catch (IOException e) {
				/*
				 * Si une erreur est survenue lors du chargement de la fenêtre,
				 * afficher le message plus la raison donnée par Java.
				 */
				System.err.println(
						"ERREUR dans Appareil.java entre les lignes 59 et 79 excluses."
								+ "Raison :\n" + e.getMessage());
			}
		}
	};

	public static int électricité = 5;

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
	protected Device(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(new LocatedImage(
				model.getNiveau().getURL() + model.getType().getURL()));

		this.model = model;
		this.controller = controller;

		this.setOnMouseClicked(onClicked);
	}

	/**
	 * <h1>action</h1>
	 * <p>
	 * This method do the action of the device. It calls the defined
	 * behaviour.
	 * </p>
	 * 
	 * @param resATraiter the resource who will be used by this device
	 */
	public void action(Stock resATraiter) throws MoneyException {
		// Todo : action method
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
	public JeuContrôle getController() {
		return controller;
	}

	/**
	 * @return the électricité
	 */
	public static int getÉlectricité() {
		return électricité;
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
	public void setController(JeuContrôle controller) {
		this.controller = controller;
	}

	/**
	 * @param électricité the électricité to set
	 */
	public static void setÉlectricité(int électricité) {
		Device.électricité = électricité;
	}
}
