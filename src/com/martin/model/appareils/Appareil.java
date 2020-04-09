package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.martin.Main;
import com.martin.model.LocatedImage;
import com.martin.model.Stock;
import com.martin.model.appareils.comportement.Comportement;
import com.martin.model.appareils.comportement.Comportement_Aucun;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.AppareilsContrôle;
import com.martin.view.Dashboard;
import com.martin.view.JeuContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public abstract class Appareil extends ImageView {

	protected AppareilModel model;

	protected Comportement comportement = new Comportement_Aucun();

	protected JeuContrôle controller;

	protected List<Direction> entrances;
	protected List<Direction> exits;

	protected Dashboard dashboard = new Dashboard();

	private EventHandler<MouseEvent> onClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			try {
				// Charger et ouvrir la boîte de dialogue demandant l'action
				// à effectuer
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

				AppareilsContrôle controller = loader.getController();
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
	protected Appareil(AppareilModel model, JeuContrôle controller)
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
	public void action(Stock resATraiter) throws NegativeArgentException {
		// Todo : action method
	}

	// GETTERS, THEN SETTERS

	public AppareilModel getModel() {
		return model;
	}

	/**
	 * @return the comportement
	 */
	public Comportement getComportement() {
		return comportement;
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
	 * @param comportement the comportement to set
	 */
	public void setComportement(Comportement comportement) {
		this.comportement = comportement;
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
		Appareil.électricité = électricité;
	}
}
