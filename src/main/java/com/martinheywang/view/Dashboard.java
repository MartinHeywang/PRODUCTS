package com.martinheywang.view;

import java.io.FileNotFoundException;

import com.martinheywang.model.LocatedImage;
import com.martinheywang.model.devices.Device;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class Dashboard extends GridPane {

	public Dashboard(Device device) throws FileNotFoundException {

		HBox row0 = new HBox();

		ImageView view = new ImageView();
		view.setImage(new LocatedImage(
				device.getModel().getNiveau().getURL()
						+ device.getModel().getType().getURL()));
		row0.getChildren().add(view);

		VBox rotateBox = new VBox();
		Label rotateLabel = new Label("Tourner cet appareil de 90°");
		rotateBox.getChildren().add(rotateLabel);
		rotateBox.setStyle("-fx-border-color: cornflowerblue");

		HBox row2 = new HBox();
		VBox deleteBox = new VBox();
		Label deleteLabel = new Label("Détruire cet appareil : (!!!)");
		deleteBox.getChildren().add(deleteLabel);
		deleteBox.setStyle("-fx-border-color: darkred");

		VBox upgradeBox = new VBox();
		Label upgradeLabel = new Label("Améliorer l'appareil :");
		upgradeBox.getChildren().add(upgradeLabel);
		upgradeBox.setStyle("-fx-border-color: darkgreen");

		row2.getChildren().add(deleteBox);
		row2.getChildren().add(upgradeBox);
		HBox.setHgrow(deleteBox, Priority.ALWAYS);
		HBox.setHgrow(upgradeBox, Priority.ALWAYS);

		for (int i = 0; i < 3; i++) {
			RowConstraints row = new RowConstraints();
			row.setVgrow(Priority.ALWAYS);
			this.getRowConstraints().add(row);
		}
		for (int i = 0; i < 1; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setHgrow(Priority.ALWAYS);
			this.getColumnConstraints().add(col);
		}

		this.addRow(0, row0);
		this.addRow(1, rotateBox);
		this.addRow(2, row2);
		this.setGridLinesVisible(true);
	}

}
