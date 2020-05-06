package com.martinheywang.view;

import com.martinheywang.model.Coordinates;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class DeviceController {

	@FXML
	Label coordinates;
	@FXML
	ScrollPane scrollPane;

	public DeviceController() {
	}

	public void initialize() {
	}

	public void setMainApp(Coordinates xy, Dashboard dashboard) {
		coordinates.setText("X: " + xy.getX() + " Y: " + xy.getY());
		scrollPane.setContent(dashboard);
		dashboard.prefWidthProperty().bind(scrollPane.widthProperty());
		dashboard.prefHeightProperty().bind(scrollPane.heightProperty());
	}
}