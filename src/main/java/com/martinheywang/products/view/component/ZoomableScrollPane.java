package com.martinheywang.products.view.component;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Thanks to
 * {@link https://stackoverflow.com/questions/39827911/javafx-8-scaling-zooming-scrollpane-relative-to-mouse-position
 * this SO question.}
 * 
 * @author Daniel Hári
 */
public class ZoomableScrollPane extends ScrollPane {
	private double scaleValue = 1d;
	private final double zoomIntensity = 0.02;
	private final Node target;
	private final Node zoomNode;

	public ZoomableScrollPane(Node target) {
		super();
		this.target = target;
		this.zoomNode = new Group(target);
		setContent(outerNode(zoomNode));

		setPannable(false);
		setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // and reset bars to never shown
		setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		setFitToHeight(true); // center
		setFitToWidth(true); // center

		updateScale();
	}

	private Node outerNode(Node node) {
		final Node outerNode = centeredNode(node);
		outerNode.setOnScroll(e -> {
			if (e.isControlDown()) {
				e.consume();
				onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
			}
		});
		return outerNode;
	}

	private Node centeredNode(Node node) {
		final VBox vBox = new VBox(node);
		vBox.setAlignment(Pos.CENTER);
		return vBox;
	}

	private void updateScale() {
		target.setScaleX(scaleValue);
		target.setScaleY(scaleValue);
	}

	private void onScroll(double wheelDelta, Point2D mousePoint) {
		final double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

		final Bounds innerBounds = zoomNode.getLayoutBounds();
		final Bounds viewportBounds = getViewportBounds();

		// calculate pixel offsets from [0, 1] range
		final double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
		final double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

		scaleValue = scaleValue * zoomFactor;
		updateScale();
		this.layout(); // refresh ScrollPane scroll positions & target bounds

		// convert target coordinates to zoomTarget coordinates
		final Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

		// calculate adjustment of scroll position (pixels)
		final Point2D adjustment = target.getLocalToParentTransform()
				.deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

		// convert back to [0, 1] range
		// (too large/small values are automatically corrected by ScrollPane)
		final Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
		this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
		this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
	}
}
