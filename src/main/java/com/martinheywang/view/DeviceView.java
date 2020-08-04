package com.martinheywang.view;

import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Floor;
import com.martinheywang.model.exceptions.EditException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;

/**
 * <p>
 * The DeviceView view is a children of
 * <code>javafx.scene.image.ImageView</code>. It provides an easy way
 * of showing a Device.
 * </p>
 * <p>
 * You can at any moment refresh this view by calling
 * {@link #refresh()}.
 * </p>
 * 
 * @author Heywang
 */
public final class DeviceView extends ImageView {

	// CONSTANT VALUES
	private static final double defaultOpacityValue = .7d;
	private static final double activeOpacityValue = .9d;
	private static final double defaultGlowAmount = .0d;
	private static final double hoverGlowAmount = .3d;

	static final DataFormat classFormat = new DataFormat(
			"class");
	static final DataFormat coordinateFormat = new DataFormat("coordinate");
	static final DataFormat levelFormat = new DataFormat("level");

	/**
	 * The Device used to build this view.
	 */
	private final Device device;

	/**
	 * Builds a new DeviceView.
	 * 
	 * @param device
	 */
	public DeviceView(Device device) {
		this.device = device;

		this.imageProperty().bind(device.getView());
		this.setOpacity(defaultOpacityValue);
		this.setRotate(device.getDirection().getRotate());

		// ON HOVER
		if (!device.getClass().equals(Floor.class)) {
			this.setOnMouseEntered(event -> {
				this.setEffect(new Glow(hoverGlowAmount));
			});
			this.setOnMouseExited(event -> {
				this.setEffect(new Glow(defaultGlowAmount));
			});
		}

		// ON ACTIVE (when the Device just act)
		this.device.activeProperty().addListener(
				(observableValue, oldValue, newValue) -> {
					if (newValue == true) {
						// Show the new active state with a smooth transition
						final Timeline in = new Timeline();
						in.getKeyFrames().addAll(
								new KeyFrame(Duration.ZERO,
										new KeyValue(this.opacityProperty(),
												defaultOpacityValue)),
								new KeyFrame(Duration.millis(400),
										new KeyValue(this.opacityProperty(),
												activeOpacityValue)));
						in.playFromStart();
					} else {
						// Unshow the active state
						final Timeline in = new Timeline();
						in.getKeyFrames().addAll(
								new KeyFrame(Duration.ZERO,
										new KeyValue(this.opacityProperty(),
												activeOpacityValue)),
								new KeyFrame(Duration.millis(400),
										new KeyValue(this.opacityProperty(),
												defaultOpacityValue)));
						in.playFromStart();
					}
				});

		// ON CLICK
		if (!device.getClass().equals(Floor.class)) {
			this.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.SECONDARY) {
					// Right click = turn.
					device.turn();

				} else if (event.getClickCount() >= 2) {
					// Double click = upgrade

					try {
						device.upgrade();
					} catch (final EditException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// ON DRAG
		if (!device.getClass().equals(Floor.class)) {
			this.setOnDragDetected(event -> {
				/*
				 * I used MOVE to delete devices. I use link to destroy devices, and COPY to
				 * build devices.
				 */
				final Dragboard db = this.startDragAndDrop(TransferMode.MOVE, TransferMode.LINK);
				final ClipboardContent content = new ClipboardContent();

				content.put(coordinateFormat, device.getPosition());
				content.put(levelFormat, device.getLevel());
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
					final Class<? extends Device> type = (Class<? extends Device>) db
					.getContent(classFormat);
					device.build(type);
					success = true;
				}
			} else if (event.getTransferMode().equals(TransferMode.LINK)) {
				device.swap();
				success = true;
			}

			event.setDropCompleted(success);

			event.consume();
		});
		this.setOnDragDone(event -> {
			// If the other target was a DeviceView
			try {
				if (event.getTarget().getClass().equals(DeviceView.class)) {
					if (event.getTransferMode().equals(TransferMode.LINK)) { // Quick review : LINK to swap devices
						device.swap();
					}
				}
			} catch (final NullPointerException e) {
				/* If an error occurs during the drag'n drop gesture, the event
				 * is not properly set, that means that the properties are not
				 * accesible and throws this error */
			}
		});
	}

}
