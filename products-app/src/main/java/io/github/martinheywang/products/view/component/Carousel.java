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
package io.github.martinheywang.products.kit.view.component;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * <p>
 * The carousel is a UI component that may be used as a selector between
 * different {@link Node}. It is also knows as a slider in the web domain. You
 * can put different nodes. The user will be able to navigate between them.
 * </p>
 * <p>
 * You can handle a {@link CarouselEvent} each time the user changes the
 * selection.
 * </p>
 */
public class Carousel extends HBox {

	/**
	 * All the nodes contained in the carousel.
	 */
	private final List<Node> nodes = new ArrayList<Node>();

	/**
	 * The box containing the three nodes displayed.
	 */
	private HBox images = new HBox();

	/**
	 * The current selected node. (the middle-one)
	 */
	Node selection;

	/**
	 * Creates an empty carousel without any nodes in it. You may want them by
	 * calling {@link #addNodes(Node...)}.
	 */
	public Carousel() {
		this.getStylesheets().add(this.getClass().getResource("/css/Buttons.css").toExternalForm());

		final ImageView left_arrow = new ImageView(
				new Image(this.getClass().getResourceAsStream("/images/icons/left_arrow.png")));
		final ImageView right_arrow = new ImageView(
				new Image(this.getClass().getResourceAsStream("/images/icons/right_arrow.png")));

		final Button previous = new Button();
		previous.setGraphic(left_arrow);
		previous.getStyleClass().add("as-icon");
		final Button next = new Button();
		next.getStyleClass().add("as-icon");
		next.setGraphic(right_arrow);

		previous.setOnMouseClicked((event) -> this.previousElement());
		next.setOnMouseClicked((event) -> this.nextElement());

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(previous, this.images, next);
		this.setMinHeight(150d);
		Carousel.setHgrow(this.images, Priority.ALWAYS);
	}

	/**
	 * Switch to the next element, the one on the right.
	 */
	private void nextElement() {
		if (this.nodes.size() < 3)
			try {
				throw new Exception("The carousel cannot turn, there aren't enough elements in it");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		this.nodes.add(this.nodes.size() - 1, this.nodes.remove(0));
		this.refresh();
	}

	/**
	 * Switch to the previous element, the one on the left.
	 */
	private void previousElement() {
		if (this.nodes.size() < 3)
			try {
				throw new Exception("The carousel cannot turn, there aren't enough elements in it");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		this.nodes.add(0, this.nodes.remove(this.nodes.size() - 1));
		this.refresh();
	}

	/**
	 * Refreshes the carousel and all its children.
	 */
	private void refresh() {
		// If the container is null, instantiate a new Node
		if (this.images == null)
			this.images = new HBox();
		this.images.setAlignment(Pos.CENTER);
		Carousel.setHgrow(this.images, Priority.ALWAYS);

		// Clear all children of the container
		this.images.getChildren().clear();
		// Return if there are no Node
		if (this.nodes.size() == 0)
			return;
		// If there are less than three, we just show the first node
		else if (this.nodes.size() < 3)
			this.images.getChildren().addAll(new Group(), this.nodes.get(0), new Group());
		// Else we show only three nodes (the last, the first then the second)
		else
			this.images.getChildren().addAll(this.nodes.get(this.nodes.size() - 1), this.nodes.get(0),
					this.nodes.get(1));

		// We give the children nice effects
		final double scaledown = .7d;

		this.images.getChildren().get(0).setScaleX(scaledown);
		this.images.getChildren().get(0).setScaleY(scaledown);
		this.images.getChildren().get(0).setOnMouseClicked(event -> {
			this.previousElement();
		});
		this.images.getChildren().get(1).setScaleX(1d);
		this.images.getChildren().get(1).setScaleY(1d);
		this.images.getChildren().get(1).setOnMouseClicked(event -> {
		});
		this.images.getChildren().get(2).setScaleX(scaledown);
		this.images.getChildren().get(2).setScaleY(scaledown);
		this.images.getChildren().get(2).setOnMouseClicked(event -> {
			this.nextElement();
		});

		this.selection = this.images.getChildren().get(1);

		// And we fire a Refresh event
		this.fireEvent(new CarouselEvent(this, this.images));
	}

	/**
	 * Add the given node(s) to the carousel.
	 * 
	 * @param nodes the nodes to add.
	 */
	public void addNodes(Node... nodes) {
		for (final Node node : nodes) {
			this.nodes.add(node);
			this.refresh();
		}
	}

	/**
	 * Remove nodes from the carousel.
	 * 
	 * @param node the node to remove
	 */
	public void removeNode(Node node) {
		this.nodes.remove(node);
		this.refresh();
	}

	/**
	 * Deletes all the nodes from the carousel. You may not want this to happen
	 * under the user's eyes.
	 */
	public void removeAll() {
		this.nodes.clear();
		refresh();
	}

	/**
	 * Set the selection to a specific node, if it exists in the carousel.
	 * 
	 * @param node the node to switch to.
	 */
	public void setSelection(Node node) {
		// Check if the given node is in the list
		if (this.nodes.contains(node)) {
			final int selectionIndex = this.nodes.indexOf(node);
			for (int i = 0; i < selectionIndex; i++)
				this.nodes.add(this.nodes.remove(0));
			this.refresh();
		}
	}

	/**
	 * Sets the new EventHandler for the event CarouselEvent, in other words, what
	 * is done when the selection of the carousel is changed.
	 * 
	 * @param newHandler the new handler
	 */
	public void setOnSelectionChanged(EventHandler<CarouselEvent> newHandler) {
		this.addEventFilter(CarouselEvent.REFRESH, newHandler);
	}

	/**
	 * @author Heywang
	 * 
	 *         A custom event that fires when the selection changed.
	 *
	 */
	public static class CarouselEvent extends Event {

		private static final long serialVersionUID = -4515993623404437149L;

		/**
		 * The Event type of this class.
		 */
		public static final EventType<CarouselEvent> REFRESH = new EventType<>(Event.ANY, "REFRESH");

		/**
		 * The left element displayed in the carousel.
		 */
		Node leftElement;

		/**
		 * The new selected element.
		 */
		Node newSelection;

		/**
		 * The right element displayed in the carousel.
		 */
		Node rightElement;

		/**
		 * Returns the new element situated on the left. 
		 * 
		 * @return the left element.
		 */
		public Node getLeftElement() {
			return this.leftElement;
		}

		/**
		 * Returns the newly selected element.
		 * 
		 * @return the currently selected node.
		 */
		public Node getNewSelection() {
			return this.newSelection;
		}

		/**
		 * Returns the new element situated on the right.
		 * 
		 * @return the right element.
		 */
		public Node getRightElement(){
			return this.rightElement;
		}

		// Construtors
		// I don't need the EventType parameter, because the only valid arg is
		// the SELECTION_CHANGED, so I pass it directly
		/**
		 * Creates a new CarouselEvent
		 */
		public CarouselEvent() {
			super(REFRESH);
		}

		/**
		 * Creates a new CarouselEvent
		 * 
		 * @param source the source
		 * @param target the target
		 */
		public CarouselEvent(Object source, EventTarget target) {
			super(source, target, REFRESH);

			this.leftElement = ((HBox) target).getChildren().get(0);
			this.newSelection = ((HBox) target).getChildren().get(1);
			this.rightElement = ((HBox) target).getChildren().get(2);
		}

	}

}
