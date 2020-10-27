package com.martinheywang.products.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.martinheywang.products.Main;
import com.martinheywang.products.controller.GameController;
import com.martinheywang.products.model.device.Device;
import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.exception.EditException;
import com.martinheywang.products.toolbox.MoneyFormat;
import com.martinheywang.products.view.component.IterationView;

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

public final class DeviceMenuView implements Initializable {

    /*
     * View components below :
     */

    @FXML
    private Label coordinate, literal, upgradePrice, destroyPrice;

    @FXML
    private ImageView view, upgradedView, turnedView;

    @FXML
    private VBox main, actions, stats;

    @FXML
    private HBox upgradeBox;

    private IterationView iterationView;

    private Device device;

    private GameController gameManager;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public void setContent(Device data, GameController gameManager) {
	this.device = data;
	this.gameManager = gameManager;

	this.view.setImage(new Image(data.getModel().getType().getResourceAsStream(data.getURL())));

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
	this.iterationView = new IterationView(this.device.getCurrentReport());
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
	    this.gameManager.upgrade(this.device);
	} catch (final EditException e) {
	    e.printStackTrace();
	}
	this.refreshUpgradeBox();
    }

    private void refreshUpgradeBox() {
	try {
	    final MoneyFormat formatter = MoneyFormat.getSingleton();
	    this.destroyPrice.setText("+ " + formatter.format(this.device.getDeletePrice()));
	    this.upgradePrice.setText("- " + formatter.format(this.device.getUpgradePrice()));

	    this.view.setImage(new Image(this.device.getModel().getType().getResourceAsStream(this.device.getURL())));
	    this.upgradedView.setImage(
		    new Image(this.device.getModel().getType().getResourceAsStream(this.device.getUpgradedURL())));
	    this.turnedView
		    .setImage(new Image(this.device.getModel().getType().getResourceAsStream(this.device.getURL())));

	    this.refreshLiteral();
	} catch (final ArrayIndexOutOfBoundsException e) {
	    /*
	     * In most cases, this will be caused because of a non-existing devices_level_4
	     * This means that the device is at level max.
	     */
	    this.actions.getChildren().remove(this.upgradeBox);
	}
    }

    private void refreshLiteral() {
	final String type = this.device.getClass().getAnnotation(AccessibleName.class).value();
	this.literal.setText(
		type + " | " + this.device.getLevel().getLiteral() + " | " + this.device.getDirection().getLiteral());
    }

    @FXML
    public void turn() {
	try {
	    this.gameManager.turn(this.device);
	} catch (final EditException e) {
	    e.printStackTrace();
	}
	this.refreshTurnedView();
    }

    private void refreshTurnedView() {
	this.turnedView.setImage(new Image(this.device.getModel().getType().getResourceAsStream(this.device.getURL())));
	this.turnedView.setRotate(this.device.getDirection().getNext().getRotate());
	this.refreshLiteral();
    }

    @FXML
    public void destroy() {
	try {
	    this.gameManager.destroy(this.device);

	    // Close the window
	    // The device doesn't exist anymore
	    final Stage window = (Stage) this.upgradeBox.getScene().getWindow();
	    window.close();
	} catch (final EditException e) {
	    e.printStackTrace();
	}
    }

}
