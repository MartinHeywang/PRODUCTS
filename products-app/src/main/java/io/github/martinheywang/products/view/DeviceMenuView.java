/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.view;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.martinheywang.products.Main;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.exception.EditException;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.view.component.IterationView;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <p>
 * The DeviceMenuView class is a controller class, in javafx terms. It controls
 * the menu of a device, when it's open. It can give the order to destroy the
 * device, upgrade it, turn it...
 * </p>
 * <p>
 * It has a section containing all the widgets that the device requested for.
 * </p>
 * 
 * @see io.github.martinheywang.products.api.model.device.Device
 * 
 * @author Martin Heywang
 */
public final class DeviceMenuView implements Initializable {

	/*
	 * View components below :
	 */
	@FXML
	private Parent root;

	@FXML
	private Label coordinate, literal, upgradePrice, destroyPrice;

	@FXML
	private ImageView view, upgradedView, turnedView, deleteView;

	@FXML
	private VBox main, actions, stats;

	@FXML
	private HBox upgradeBox;

	private IterationView iterationView;

	private Device device;

	private GameController gameManager;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.root.getStylesheets().add(ViewUtils.class.getResource("/css/General.css").toExternalForm());
		deleteView.setImage(new Image(getClass().getResourceAsStream("/images/dashboard/Destroy.png")));
	}

	/**
	 * Sets the displayed device.
	 * 
	 * @param data        the displayed device
	 * @param gameManager the game controller that controlls the game.
	 */
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
		this.iterationView = new IterationView(this.device.getCurrentIteration());
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

	/**
	 * Upgrades the displayed device and updates the view.
	 */
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
		this.literal.setText(type + " | " + this.device.getLevel().getAccessibleName() + " | "
				+ this.device.getDirection().getLiteral());
	}

	/**
	 * Turns the displayed device and updates the view.
	 */
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

	/**
	 * Destroys the displayed device and closes the window.
	 */
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
