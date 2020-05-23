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
	 * Initialize the build dialog, where we can build devices on the
	 * ground at the cooresponding coordinates. Called automatically as a
	 * constructor.
	 */
	public void initialize() {
		for (int i = 0; i < Type.values().length - 1; i++) {
			Displayer<Type> display = Type.values()[i].getDisplayer();
			listeAppareils.getChildren().add(display);
			display.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dialog.setResult(display.getSubject());
					dialog.close();
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
