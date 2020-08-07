package com.martinheywang.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.martinheywang.Main;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.EditException;

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

    private Device device;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void setContent(Device data) {
	this.device = data;

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
	    special.setCollapsible(false);
	    special.setExpanded(true);

	    main.getChildren().add(0, special);
	}
    }

    @FXML
    public void upgrade() {
	try {
	    device.upgrade();
	} catch (final EditException e) {
	    e.printStackTrace();
	}
    }

    @FXML
    public void turn() {
	device.turn();
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
