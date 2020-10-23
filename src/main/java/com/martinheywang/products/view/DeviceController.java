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

		this.view.setImage(data.getView().get());

		this.coordinate.setText(data.getPosition().toString());

		this.refreshTurnedView();
		this.refreshUpgradeBox();
		this.refreshLiteral();

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

			this.main.getChildren().add(0, special);
		}
		this.iterationView = new IterationReportView(this.device.getCurrentReport());
		final ImageView icon = new ImageView(new Image(Main.class.getResourceAsStream("/images/icons/info.png")));
		icon.setFitHeight(30d);
		icon.setFitWidth(30d);
		final TitledPane iterationContainer = new TitledPane("Informations utiles", this.iterationView);
		iterationContainer.setFont(new Font(16d));
		iterationContainer.setGraphic(icon);
		iterationContainer.setExpanded(false);
		iterationContainer.setAnimated(false);

		this.main.getChildren().addAll(iterationContainer);

	}

	@FXML
	public void upgrade() {
		try {
			this.device.upgrade();
			this.refreshUpgradeBox();
		} catch (final EditException e) {
			e.printStackTrace();
		}
	}

	private void refreshUpgradeBox(){
		try{
			final MoneyFormat formatter = MoneyFormat.getSingleton();
			this.destroyPrice.setText("+ "+formatter.format(this.device.getDeletePrice()));
			this.upgradePrice.setText("- "+formatter.format(this.device.getUpgradePrice()));

			final String upgradedURL = "/images" + this.device.getLevel().getNext().getURL()
					+ this.device.getClass().getSimpleName().toUpperCase() + ".png";
			final Image upgraded = new Image(this.getClass().getResourceAsStream(upgradedURL));
			this.upgradedView.setImage(upgraded);
			this.view.setImage(this.device.getView().get());

			this.refreshLiteral();
		}catch(final ArrayIndexOutOfBoundsException e){
			/* In most cases, this will be caused because of a non-existing devices_level_4 
				This means that the device is at level max.
			*/
			this.actions.getChildren().remove(this.upgradeBox);
		}
	}

	private void refreshLiteral(){
		final String type = this.device.getClass().getAnnotation(AccessibleName.class).value();
		this.literal.setText(type + " | " + this.device.getLevel().getLiteral() + " | " + this.device.getDirection().getLiteral());
	}

	@FXML
	public void turn() {
		this.device.turn();
		this.refreshTurnedView();
	}

	private void refreshTurnedView(){
		this.turnedView.setImage(this.device.getView().get());
		this.turnedView.setRotate(this.device.getDirection().getNext().getRotate());
		this.refreshLiteral();
	}

	@FXML
	public void destroy() {
		try {
			this.device.destroy();

			// Close the window
			// The device doesn't exist anymore
			final Stage window = (Stage) this.upgradeBox.getScene().getWindow();
			window.close();
		} catch (final EditException e) {
			e.printStackTrace();
		}
	}

}
