package com.martinheywang.view;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.devices.Type;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class FloorController {

	@FXML
	private Label coordinates; // Label for coordinates on top of the scene
	@FXML
	private VBox listeAppareils; // List of all the devices integrated to the
									// VBox

	private Dialog<Object> dialog;

	/**
	 * <h1>initialize</h1>
	 * <p>
	 * Initialize the build dialog, where we can build devices on the
	 * ground at the cooresponding coordinates. Called automatically as a
	 * constructor.
	 * </p>
	 */
	public void initialize() {
		for (int i = 0; i < Type.values().length - 1; i++) {
			// On ajoute les displayers des appareils dans la liste pr�vue � cet
			// effet
			listeAppareils.getChildren().add(new Displayer(Type.values()[i]));

			// On ajoute un listener du clic sur un displayer
			listeAppareils.getChildren().get(i)
					.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							try {
								dialog.setResult(((Displayer) event.getSource())
										.getTypeAppareil());
								dialog.close();
							} catch (Exception e) {
								System.out.println(
										"ERREUR lors de la construction d'un appareil. Raison : "
												+ e.getMessage());
							}
						}

					});
		}

	}

	/**
	 * 
	 * @param xy     the xy coordinates corresponding to a device.
	 * @param dialog the dialog who is displayed
	 */
	public void setMainApp(Coordinates xy, Dialog<Object> dialog) {
		coordinates.setText("X: " + xy.getX() + " Y: " + xy.getY());
		this.dialog = dialog;
	}
}
