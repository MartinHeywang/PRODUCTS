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
package io.github.martinheywang.products.view.component;

import java.net.URL;

import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.device.Floor;
import io.github.martinheywang.products.kit.view.component.SVGImage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * <p>
 * The DeviceView class is a final class that extends
 * <code>javafx.scene.image.ImageView</code>, which represents each square on
 * the main devices grid.
 * </p>
 * <p>
 * It represents a {@link Device} and shows a different image according to the
 * view of the device, based on:
 * </p>
 * <ul>
 * <li>The type of the device (e.g. buyer, conveyor)</li>
 * <li>The level of the device (1, 2, or 3 actually)</li>
 * <li>The direction of the device which defines how rotated this view is</li>
 * </ul>
 * <p>
 * This view doesn't refresh automatically. You can call either
 * {@link #lightRefresh()} or {@link #hardRefresh()} to refresh the view,
 * depending on your need. You will find more informations about it in the
 * javadoc of these methods.
 * </p>
 * <p>
 * Otherwise, this class reacts to a bunch of events, such as click, or drag.
 * </p>
 * 
 * @author Martin Heywang
 */
public final class DeviceView extends StackPane {

	// ---------- CONSTANT VALUES -----------------------------------------------
	private static final double defaultOpacityValue = 1d;
	private static final double activeOpacityValue = 1d;
	private static final double defaultGlowAmount = 0d;
	private static final double hoverGlowAmount = .2d;

	// ---------- DATA ----------------------------------------------------------
	/**
	 * The Device used to build this view.
	 */
	private Device device;

	/**
	 * The current game manager (the controller)
	 */
	private final GameController gameManager;

	private double side = 100d; // both width and height
	private double zoom = 1d;

	// ---------- VIEW ----------------------------------------------------------
	private SVGImage view;

	private SVGImage buildReflect;

	/**
	 * Builds a new DeviceView.
	 * 
	 * @param device      the device to display
	 * @param gameManager the game controller that manages the device.
	 */
	public DeviceView(Device device, GameController gameManager) {
		this.device = device;
		this.gameManager = gameManager;

		this.view = new SVGImage();
		this.buildReflect = new SVGImage();

		this.hardRefresh();
	}

	/**
	 * Refreshes this view 'lightly'. It re-generatates the view. Useful in case the
	 * level or the direction changed.
	 */
	public void lightRefresh() {
		this.generateView(this.device.getModel());
	}

	/**
	 * <p>
	 * The hard refresh calls first the light refresh, then refreshes the event
	 * handling behaviour. It is much more heavier than the light refresh, so
	 * consider calling the light refresh when it is sufficient.
	 * </p>
	 * <p>
	 * For example, the hard refresh is useful in case the type of the displayed
	 * device has changed. (It may change the event handling behaviour.)
	 * </p>
	 */
	public void hardRefresh() {
		this.lightRefresh();
	}

	/**
	 * Sets the displayed device
	 * 
	 * @param device the new device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * Generates and sets this Device view according to the given model.
	 * 
	 * @param model the model to display
	 */
	private void generateView(DeviceModel model) {
		this.getChildren().clear();

		this.view.setImage(StaticDeviceDataRetriever.getView(model.getType()), this.side, this.side, this.zoom);
		this.view.setRotate(model.getDirection().getRotate());
		this.view.setOpacity(defaultOpacityValue);
		this.view.setViewEffect(model.getLevel().getEffect());

		this.getChildren().addAll(view);
	}

	/**
	 * A pulsed device view means that the device is currently active. It increases
	 * suddenly the opacity and decreases slowly to the normal opacity value.
	 */
	public void pulse() {
		final Timeline tl = new Timeline();
		tl.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(this.opacityProperty(), defaultOpacityValue)),
				new KeyFrame(Duration.millis(10), new KeyValue(this.opacityProperty(), activeOpacityValue)),
				new KeyFrame(Duration.millis(800), new KeyValue(this.opacityProperty(), defaultOpacityValue)));
		tl.playFromStart();
	}

	/**
	 * <p>
	 * Activates the build mode.
	 * </p>
	 * <p>
	 * The build mode is a mode where the view has different reactions to certain
	 * event. On hover, the view displays a semi-transparent view of the currently
	 * selected. On click, the view builds a device at its coordinate.
	 * </p>
	 * 
	 * @param reflectURL the url of the reflect
	 */
	public void activateBuildMode(URL reflectURL) {
		this.buildReflect.setImage(reflectURL, this.side, this.side, this.zoom);
		this.buildReflect.setViewEffect(Level.LEVEL_1.getEffect());
		this.buildReflect.setOpacity(.5d);

		this.setOnMouseEntered(new BuildEnteredBehaviour());
		this.setOnMouseExited(new BuildExitedBehaviour());
		this.setOnMouseClicked(new BuildClickBehaviour());
	}

	/**
	 * <p>
	 * Activates the default mode (or disable all)
	 * </p>
	 * <p>
	 * On hover, the device view glows a bit. On click, the view tries to open the
	 * dashboard of the device.
	 */
	public void activateDefaultMode() {
		this.setOnMouseEntered(new DefaultEnteredBehaviour());
		this.setOnMouseExited(new DefaultExitedBehaviour());
		this.setOnMouseClicked(new DefaultClickBehaviour());
	}

	/**
	 * Sets the zoom amount to the given value and refreshes the view.
	 * 
	 * @param value the new amount of zoom
	 */
	public void applyZoom(double value) {
		this.zoom = value;
		lightRefresh();
	}

	private class DefaultClickBehaviour implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.SECONDARY) {
				gameManager.turn(device.getPosition());
			} else if (event.getButton() == MouseButton.PRIMARY) {
				gameManager.openDashboardOf(device.getPosition());
			}
		}

	}

	private class BuildClickBehaviour implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			gameManager.build(device.getPosition());
		}
	}

	private class DefaultEnteredBehaviour implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (device instanceof Floor) {
				return;
			}
			setEffect(new Glow(hoverGlowAmount));
		}

	}

	private class BuildEnteredBehaviour implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			if (!(device instanceof Floor)) {
				return;
			}
			buildReflect.applyZoom(zoom);
			getChildren().add(buildReflect);
		}
	}

	private class DefaultExitedBehaviour implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			setEffect(new Glow(defaultGlowAmount));
		}
	}

	private class BuildExitedBehaviour implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			getChildren().remove(buildReflect);
		}
	}
}