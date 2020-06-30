package com.martinheywang.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Direction;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.types.Type;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DeviceController implements Initializable {

	@FXML
	private Label coordinate;

	@FXML
	private ImageView view, upgradedView, turnedView;

	@FXML
	private Label literal, upgradePrice, destroyPrice;

	@FXML
	private VBox main, actions, stats;

	private final VBox specificActionsBox = new VBox();

	@FXML
	private HBox upgradeBox;

	private Device origin;
	private Dialog<?> scene;
	private List<Node> widgets;

	@Override
	public void initialize(URL url, ResourceBundle resources) {

	}

	public void setContent(Device device, Dialog<?> scene) {
		this.origin = device;
		this.scene = scene;

		// ACTIONS side
		coordinate.setText(device.getModel().getCoordinates().toString());
		// Coordinate label

		view.setImage(device.getImage());
		// The view

		turnedView.setImage(device.getImage());
		turnedView.setRotate(device.getRotate() + 90d);
		// The view turned 90 degrees

		final Type type = device.getModel().getType();
		final Level level = device.getModel().getLevel();
		final Direction direction = device.getModel().getDirection();

		upgradedView.setImage(new Image(getClass().getResourceAsStream(
				"/images" + level.getNext().getURL() + type.getURL())));
		// The view upgraded

		literal.setText(type.getName() + " | "
				+ level.getLiteral() + " | "
				+ direction.getLiteral());
		// Setting the literal at format Type | Level | Direction

		final String upgradePriceKey = level.getNext().toString().toLowerCase()
				+ "_build";
		upgradePrice.setText("- " + type.getPrices()
				.getPriceFromKey(upgradePriceKey).toString() + " €");
		// Adapting the prices for both destroy and upgrade
		final String deletePriceKey = level.toString().toLowerCase()
				+ "_delete";
		destroyPrice.setText("+ " + type.getPrices()
				.getPriceFromKey(deletePriceKey).toString() + " €");

		// Remove the upgarde option if the device is already at max
		if (level.equals(Level.LEVEL_3)) {
			actions.getChildren().remove(upgradeBox);
		}

		// CHARTS, STATS
		stats.getChildren().add(new Label(
				"Coût en électricité / seconde : " + Device.getElectricity()
						+ " €"));
	}

	private void refresh() {
		stats.getChildren().clear();

		setContent(origin, scene);
		addActions(widgets);
	}

	public void addActions(List<Node> widgets) {
		this.widgets = widgets;

		if (widgets.size() >= 1) {
			final TitledPane specificActions = new TitledPane();
			specificActions.setExpanded(true);
			specificActions.setCollapsible(false);
			specificActions.setText("Actions spéciales");
			specificActions.setFont(new Font(16d));

			final ImageView icon = new ImageView(new Image(
					getClass().getResourceAsStream("/images/icons/edit.png")));
			icon.setFitHeight(30d);
			icon.setFitWidth(30d);
			specificActions.setGraphic(icon);

			specificActionsBox.getChildren().addAll(widgets);

			specificActions.setContent(specificActionsBox);
			main.getChildren().add(0, new Separator());
			main.getChildren().add(0, specificActions);
		}
	}

	@FXML
	private void upgrade() {
		try {
			origin.upgrade();
			refresh();
		} catch (MoneyException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void turn() {
		origin.rotate();
		refresh();
	}

	@FXML
	private void delete() {
		try {
			origin.delete();
			scene.close();
		} catch (MoneyException e) {
			e.printStackTrace();
		}
	}

}