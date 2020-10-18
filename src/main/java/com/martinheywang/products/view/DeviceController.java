package com.martinheywang.products.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.devices.Device;
import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.exceptions.EditException;
import com.martinheywang.products.toolbox.MoneyFormat;
import com.martinheywang.products.view.components.IterationReportView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class DeviceController implements Initializable {

	@FXML
	private Label coordinate, literal, upgradePrice, destroyPrice;

	@FXML
	private ImageView view, upgradedView, turnedView;

	@FXML
	private VBox main, actions, stats;

	@FXML
	private HBox upgradeBox;

	private IterationReportView iterationView;

	private Device device;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setContent(Device data) {
		this.device = data;

		view.setImage(data.getView().get());

		coordinate.setText(data.getPosition().toString());

		refreshTurnedView();
		refreshUpgradeBox();
		refreshLiteral();

		if (data.getWidgets().size() > 0) {
			final ImageView icon = new ImageView(new Image(Main.class.getResourceAsStream("/images/icons/edit.png")));
			icon.setFitWidth(30d);
			icon.setFitHeight(30d);

			final TitledPane special = new TitledPane();
			special.setText("Actions spéciales");
			special.setFont(new Font(16d));
			special.setGraphic(icon);

			final VBox widgetsBox = new VBox();
			widgetsBox.getChildren().addAll(data.getWidgets());

			special.setContent(widgetsBox);
			special.setAnimated(false);
			special.setExpanded(false);

			main.getChildren().add(0, special);
		}
		iterationView = new IterationReportView(device.getCurrentReport());
		ImageView icon = new ImageView(new Image(Main.class.getResourceAsStream("/images/icons/info.png")));
		icon.setFitHeight(30d);
		icon.setFitWidth(30d);
		TitledPane iterationContainer = new TitledPane("Informations utiles", iterationView);
		iterationContainer.setFont(new Font(16d));
		iterationContainer.setGraphic(icon);
		iterationContainer.setExpanded(false);
		iterationContainer.setAnimated(false);

		main.getChildren().addAll(iterationContainer);

	}

	@FXML
	public void upgrade() {
		try {
			device.upgrade();
			refreshUpgradeBox();
		} catch (final EditException e) {
			e.printStackTrace();
		}
	}

	private void refreshUpgradeBox(){
		try{
			final MoneyFormat formatter = MoneyFormat.getSingleton();
			destroyPrice.setText("+ "+formatter.format(this.device.getDeletePrice()));
			upgradePrice.setText("- "+formatter.format(this.device.getUpgradePrice()));

			final String upgradedURL = "/images" + this.device.getLevel().getNext().getURL()
					+ this.device.getClass().getSimpleName().toUpperCase() + ".png";
			final Image upgraded = new Image(getClass().getResourceAsStream(upgradedURL));
			upgradedView.setImage(upgraded);
			view.setImage(this.device.getView().get());

			refreshLiteral();
		}catch(ArrayIndexOutOfBoundsException e){
			/* In most cases, this will be caused because of a non-existing devices_level_4 
				This means that the device is at level max.
			*/
			actions.getChildren().remove(upgradeBox);
		}
	}

	private void refreshLiteral(){
		final String type = this.device.getClass().getAnnotation(AccessibleName.class).value();
		literal.setText(type + " | " + this.device.getLevel().getLiteral() + " | " + this.device.getDirection().getLiteral());
	}

	@FXML
	public void turn() {
		device.turn();
		refreshTurnedView();
	}

	private void refreshTurnedView(){
		turnedView.setImage(this.device.getView().get());
		turnedView.setRotate(this.device.getDirection().getNext().getRotate());
		refreshLiteral();
	}

	@FXML
	public void destroy() {
		try {
			device.destroy();

			// Close the window
			// The device doesn't exist anymore
			final Stage window = (Stage) upgradeBox.getScene().getWindow();
			window.close();
		} catch (final EditException e) {
			e.printStackTrace();
		}
	}

}
