package com.martinheywang.view;

import com.martinheywang.model.devices.Device;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
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

	/**
	 * The Device used to build this view.
	 */
	private Device device;

	/**
	 * Builds a new DeviceView.
	 * 
	 * @param device
	 */
	public DeviceView(Device device) {
		this.device = device;

		// Refreshes to avoid duplicating code
		refresh();

		// ON HOVER
		this.setOnMouseEntered(event -> {
			this.setEffect(new Glow(hoverGlowAmount));
		});
		this.setOnMouseExited(event -> {
			this.setEffect(new Glow(defaultGlowAmount));
		});

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
		// Todo DeviceView on click behaviour

		// ON DRAG
		this.setOnDragDetected(event -> {
			Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();

			content.put(classFormat, device.getClass());
			content.putImage(getImage());

			db.setContent(content);

		});
		this.setOnDragOver(event -> {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		});
		this.setOnDragEntered(event -> {
			/* the drag-and-drop gesture entered the target */
			/* show to the user that it is an actual gesture target */
			this.setEffect(new Glow(hoverGlowAmount));

			event.consume();
		});
		this.setOnDragExited(event -> {
			/* the drag-and-drop gesture entered the target */
			/* show to the user that it is an actual gesture target */
			this.setEffect(new Glow(defaultGlowAmount));

			event.consume();
		});
		this.setOnDragDropped(event -> {
			final Dragboard db = event.getDragboard();

			if (event.getTransferMode().equals(TransferMode.COPY)) {
				if (db.hasContent(classFormat)) {
					@SuppressWarnings("unchecked")
					final Class<? extends Device> type = (Class<? extends Device>) db
							.getContent(classFormat);
					device.build(type);
				}
			}
			if (event.getTransferMode().equals(TransferMode.MOVE)) {
				// Todo : move devices
			}
		});
	}

	/**
	 * Refreshes this view by retrieving the eventually modified values of
	 * the registered device.
	 */
	public void refresh() {
		this.setImage(device.getView());
		this.setRotate(device.getDirection().getRotate());
		this.setOpacity(defaultOpacityValue);

	}

}
