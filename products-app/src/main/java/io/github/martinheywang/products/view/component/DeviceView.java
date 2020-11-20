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

import java.io.IOException;

import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.exception.EditException;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.device.Floor;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import io.github.martinheywang.products.view.DeviceMenuView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
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
public final class DeviceView extends ImageView {

	// CONSTANT VALUES
	private static final double defaultOpacityValue = .7d;
	private static final double activeOpacityValue = .9d;
	private static final double defaultGlowAmount = .0d;
	private static final double hoverGlowAmount = .3d;

	/**
	 * A {@link javafx.scene.input.DataFormat} for class {@link java.lang.Class}
	 */
	public static final DataFormat classFormat = new DataFormat("class");

	/**
	 * A {@link javafx.scene.input.DataFormat} for class
	 * {@link io.github.martinheywang.products.api.model.Coordinate}.
	 */
	public static final DataFormat coordinateFormat = new DataFormat("coordinate");

	/**
	 * The Device used to build this view.
	 */
	private Device device;

	/**
	 * The current game manager (the controller)
	 */
	private final GameController gameManager;

	/**
	 * Builds a new DeviceView.
	 * 
	 * @param device      the device to display
	 * @param gameManager the game controller that manages the device.
	 */
	public DeviceView(Device device, GameController gameManager) {
		this.device = device;
		this.gameManager = gameManager;

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
		this.refreshEventHandling();
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
		final Image image = new Image(model.getType().getResourceAsStream(model.getURL()));
		this.setImage(image);
		this.setRotate(model.getDirection().getRotate());

		this.setOpacity(defaultOpacityValue);
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
	 * Refreshes the event handling behaviour (drag, click, ...etc.)
	 */
	private void refreshEventHandling() {
		// ON HOVER
		if (!this.device.getClass().equals(Floor.class)) {
			this.setOnMouseEntered(event -> {
				this.setEffect(new Glow(hoverGlowAmount));
			});
			this.setOnMouseExited(event -> {
				this.setEffect(new Glow(defaultGlowAmount));
			});
			this.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.SECONDARY)
					try {
						this.gameManager.turn(this.device);
					} catch (final EditException e) {
						e.printStackTrace();
					}
				else if (event.getButton() == MouseButton.PRIMARY)
					try {
						final FXMLLoader loader = ViewUtils
								.prepareFXMLLoader(this.getClass().getResource("/fxml/Device.fxml"));

						final Dialog<Void> dialog = new Dialog<>();
						final DialogPane root = loader.load();
						dialog.setDialogPane(root);

						final DeviceMenuView controller = loader.getController();
						controller.setContent(this.device, this.gameManager);

						dialog.show();
					} catch (final IOException e) {
						e.printStackTrace();
					}
			});

			// ON DRAG
			this.setOnDragDetected(event -> {
				/*
				 * I used MOVE to swap devices. I use LINK to destroy devices, and COPY to build
				 * devices.
				 */
				final Dragboard db = this.startDragAndDrop(TransferMode.MOVE, TransferMode.LINK);
				final ClipboardContent content = new ClipboardContent();

				content.put(coordinateFormat, this.device.getPosition());
				content.putImage(this.getImage());

				db.setContent(content);

			});
		}
		this.setOnDragOver(event -> {
			event.acceptTransferModes(TransferMode.COPY, TransferMode.LINK);
		});
		this.setOnDragEntered(event -> {
			/* the drag-and-drop gesture entered the target */
			/* show to the user that it is an actual gesture target */
			this.setEffect(new Glow(hoverGlowAmount));

		});
		this.setOnDragExited(event -> {
			/* the drag-and-drop gesture entered the target */
			/* show to the user that it is an actual gesture target */
			this.setEffect(new Glow(defaultGlowAmount));

		});
		this.setOnDragDropped(event -> {
			final Dragboard db = event.getDragboard();

			boolean success = false;

			if (event.getTransferMode().equals(TransferMode.COPY)) {
				if (db.hasContent(classFormat)) {
					@SuppressWarnings("unchecked")
					final Class<? extends Device> type = (Class<? extends Device>) db.getContent(classFormat);
					try {
						this.gameManager.build(type, this.device.getPosition());
					} catch (final MoneyException e) {
						e.printStackTrace();
					} catch (final EditException e) {
						e.printStackTrace();
					}
					success = true;
				}
			} else if (event.getTransferMode().equals(TransferMode.LINK)) {
				try {
					this.gameManager.swap(this.device.getPosition());
				} catch (final EditException e) {
					e.printStackTrace();
				}
				success = true;
			}

			event.setDropCompleted(success);

			event.consume();
		});

		this.setOnDragDone(event -> { // If the other target was a DeviceView
			try {
				if (event.getTarget().getClass().equals(DeviceView.class))
					if (event.getTransferMode().equals(TransferMode.LINK))
						try {
							this.gameManager.swap(this.device.getPosition());
						} catch (final EditException e) {
							e.printStackTrace();
						}
			} catch (final NullPointerException e) {
				/*
				 * If an error occurs during the drag'n drop gesture, the event is not properly
				 * set, that means that the properties are not accesible and throws this error
				 */
			}

		});

		if(this.device.getClass().equals(Floor.class)){
			this.setOnDragDetected(e -> {});
			this.setOnMouseClicked(e -> {});
			this.setOnMouseEntered(e -> {});
			this.setOnMouseExited(e -> {});
		}
	}
}