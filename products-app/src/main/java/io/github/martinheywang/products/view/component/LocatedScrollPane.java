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

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * <p>
 * A scrollable, pannable and zoomable pane in JavaFX. It is used in the grid
 * devices view.
 * </p>
 * 
 * @author Daniel Hári, MartinHeywang
 * 
 * @see javafx.scene.control.ScrollPane
 */
public class LocatedScrollPane extends ScrollPane {

	private double scaleValue = 1d;
	private final double zoomIntensity = .1d;

	private final GameGrid target;
	private final Node zoomNode;

	/**
	 * Creates a new LocatedScrollPane.
	 * 
	 * @param target the content children
	 */
	public LocatedScrollPane(GameGrid target) {
		super();
		
		this.target = target;
		this.zoomNode = new Group(target);
		this.applyCss();
		setContent(outerNode(zoomNode));

		setPannable(true);
		setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		setFitToHeight(false);
		setFitToWidth(false);

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
		target.setZoom(scaleValue);
		// target.setScaleX(scaleValue);
		// target.setScaleY(scaleValue);
	}

	private void onScroll(double wheelDelta, Point2D mousePoint) {
		final double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

		scaleValue = scaleValue * zoomFactor;
		updateScale();
		this.layout(); // refresh ScrollPane scroll positions & target bounds

		/*
		 * final Bounds innerBounds = zoomNode.getLayoutBounds(); final Bounds
		 * viewportBounds = getViewportBounds(); final double valX = this.getHvalue() *
		 * (innerBounds.getWidth() - viewportBounds.getWidth()); final double valY =
		 * this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());
		 * final Point2D posInZoomTarget = new Point2D(mousePoint.getX() * 1 /
		 * (scaleValue), mousePoint.getY() * 1 / (scaleValue));
		 * 
		 * final Point2D adjustment = target.getLocalToParentTransform()
		 * .deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));
		 * System.out.println(posInZoomTarget);
		 * System.out.println(posInZoomTarget.multiply(zoomFactor - 1));
		 * 
		 * final Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
		 * this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() -
		 * viewportBounds.getWidth())); this.setVvalue((valY + adjustment.getY()) /
		 * (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
		 */
	}
}