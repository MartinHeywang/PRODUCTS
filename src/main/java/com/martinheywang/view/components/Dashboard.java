package com.martinheywang.view.components;

import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.toolbox.LocatedHashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Dashboard extends VBox {

	public Dashboard(Device device) {

		// Default styling for nodes
		final String border = "-fx-border-color: white";

		HBox title = new HBox();
		ImageView view = new ImageView(device.getImage());
		view.setScaleX(0.8);
		view.setScaleY(0.8);

		Label titleLabel = new Label("Tableau de bord :");
		title.getChildren().add(view);
		title.getChildren().add(titleLabel);
		title.setStyle(border);
		title.setMinHeight(200);
		title.setAlignment(Pos.CENTER_LEFT);

		LocatedHashMap<Integer, String> rowsContent = new LocatedHashMap<>();
		rowsContent.put(0, "/Upgrade.png");
		rowsContent.put(0, "Améliorer l'appareil");
		rowsContent.put(0,
				"L'appareil réalisera une action de plus toutes les secondes.");
		rowsContent.put(0, "- 50 K€");

		rowsContent.put(1, "/Rotate.png");
		rowsContent.put(1, "Tourner l'appareil");
		rowsContent.put(1,
				"Tourner l'appareil de 90 degrès dans les sens des aiguilles d'une montre.");
		rowsContent.put(1, "Gratuit");

		rowsContent.put(2, "/Sell.png");
		rowsContent.put(2, "Vendre l'appareil");
		rowsContent.put(2,
				"Détruire l'appareil et le vendre.");
		rowsContent.put(2, "+ 7500 €");

		this.getChildren().add(title);

		for (int i = 0; i < rowsContent.size(); i++) {

			HBox rowBox = new HBox();
			rowBox.setMinHeight(120d);
			ImageView rowView = new ImageView(new Image(
					getClass().getResourceAsStream(rowsContent.get(i, 0))));
			rowView.setScaleX(0.5);
			rowView.setScaleY(0.5);
			rowView.setFitWidth(100d);
			rowView.setFitHeight(100d);

			HBox rowTextBox = new HBox();
			VBox rowLeftTextBox = new VBox();
			Label rowLabel = new Label(rowsContent.get(i, 1));
			rowLabel.setFont(new Font(21d));
			Label rowDescLabel = new Label(
					rowsContent.get(i, 2));
			rowDescLabel.setWrapText(true);
			rowDescLabel.setFont(new Font(16d));
			Label rowPriceLabel = new Label(rowsContent.get(i, 3));
			rowPriceLabel.setFont(new Font(18d));

			rowTextBox.getChildren().add(rowLeftTextBox);
			rowLeftTextBox.getChildren().add(rowLabel);
			rowLeftTextBox.getChildren().add(rowDescLabel);
			rowTextBox.getChildren().add(rowPriceLabel);
			rowBox.getChildren().add(rowView);
			rowBox.getChildren().add(rowTextBox);

			rowTextBox.setAlignment(Pos.CENTER_RIGHT);
			rowLeftTextBox.setAlignment(Pos.CENTER_LEFT);
			rowBox.setAlignment(Pos.CENTER_LEFT);
			rowLeftTextBox.setPrefWidth(275d);

			rowBox.setOnMouseEntered(
					(e) -> rowBox.setCursor(Cursor.HAND));

			this.getChildren().add(rowBox);
		}

		this.getChildren().get(1).setOnMouseClicked((event) -> {
			try {
				device.upgrade();
			} catch (MoneyException e) {
				e.printStackTrace();
			}
		});
		this.getChildren().get(2).setOnMouseClicked((event) -> device.rotate());
		this.getChildren().get(3).setOnMouseClicked((event) -> {
			try {
				device.delete();
			} catch (MoneyException e) {
				e.printStackTrace();
			}
		});

		// And a little bit of space !
		this.setSpacing(7d);
		this.setPadding(new Insets(3, 5, 3, 5));
	}

	/**
	 * This methods adds the given Node in the dashboard between the
	 * general decription and the options (upgarde, rotate, delete).
	 * Defines your styles (font-size, color...), but the background is
	 * already defined. However, you need to define the Event(s)
	 * Listener(s) You can always add another Node where you want with the
	 * method add(Integer index, Node node).
	 * 
	 * @param node the node to add
	 */
	public void addNode(Node node) {
		this.getChildren().add(1, node);
	}

}
