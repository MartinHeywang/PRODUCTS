package com.martinheywang.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.types.Type;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class FloorController implements Initializable {

	@FXML
	private Label coordinates;
	@FXML
	private VBox listeAppareils;

	private Dialog<Object> dialog;

	/**
	 * Initialize the build dialog, where we can build devices on the
	 * ground at the cooresponding coordinates. Called automatically as a
	 * constructor.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		for (int i = 0; i < Type.getReferences().size(); i++) {
			Displayer<Type> display = Type.getReferences().get(i)
					.getDisplayer();
			display.addHoverEffect();
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
	public void setMainApp(Coordinate xy, Dialog<Object> dialog) {
		coordinates.setText("X: " + xy.getX() + " Y: " + xy.getY());
		this.dialog = dialog;
	}
}
