package io.github.martinheywang.products.api.view.component;

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

public class Carousel extends HBox {

	private final List<Node> nodes = new ArrayList<Node>();

	private HBox images = new HBox();

	Node selection;

	public Carousel() {
		this.getStylesheets()
		.add(this.getClass().getResource("/fxml/Buttons.css").toString());

		final ImageView left_arrow = new ImageView(new Image(
				this.getClass()
				.getResourceAsStream("/images/icons/left_arrow.png")));
		final ImageView right_arrow = new ImageView(new Image(
				this.getClass()
				.getResourceAsStream("/images/icons/right_arrow.png")));

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

	private void nextElement() {
		if (this.nodes.size() < 3)
			try {
				throw new Exception(
						"The carousel cannot turn, there aren't enough elements in it");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		this.nodes.add(this.nodes.size() - 1, this.nodes.remove(0));
		this.refresh();
	}

	private void previousElement() {
		if (this.nodes.size() < 3)
			try {
				throw new Exception(
						"The carousel cannot turn, there aren't enough elements in it");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		this.nodes.add(0, this.nodes.remove(this.nodes.size() - 1));
		this.refresh();
	}

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
			this.images.getChildren().addAll(new Group(), this.nodes.get(0),
					new Group());
		// Else we show only three nodes (the last, the first then the second)
		else
			this.images.getChildren().addAll(this.nodes.get(this.nodes.size() - 1),
					this.nodes.get(0), this.nodes.get(1));

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

	public void addNodes(Node... nodes) {
		for (final Node node : nodes) {
			this.nodes.add(node);
			this.refresh();
		}
	}

	public void removeNode(Node node) {
		this.nodes.remove(node);
		this.refresh();
	}

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
	 * Sets the new EventHandler for the event CarouselEvent, in other
	 * words, what is done when the selection of the carousel is changed.
	 * 
	 * @param newHandler the new handler
	 */
	public void setOnSelectionChanged(EventHandler<CarouselEvent> newHandler) {
		this.addEventFilter(CarouselEvent.REFRESH, newHandler);
	}

	/**
	 * 
	 * @author Heywang
	 * 
	 *         A custom event that fires when the selection changed.
	 *
	 */
	public static class CarouselEvent extends Event {

		private static final long serialVersionUID = -4515993623404437149L;

		public static final EventType<CarouselEvent> REFRESH = new EventType<>(
				Event.ANY, "REFRESH");

		/**
		 * Informations that may need the event handler
		 */
		Node oldSelection;
		Node newSelection;

		public Node getOldSelection() {
			return this.oldSelection;
		}

		public Node getNewSelection() {
			return this.newSelection;
		}

		// Construtors
		// I don't need the EventType parameter, because the only valid arg is
		// the SELECTION_CHANGED, so I pass it directly
		public CarouselEvent() {
			super(REFRESH);
		}

		public CarouselEvent(Object source, EventTarget target) {
			super(source, target, REFRESH);

			this.oldSelection = ((HBox) target).getChildren().get(0);
			this.newSelection = ((HBox) target).getChildren().get(1);
		}

	}

}
