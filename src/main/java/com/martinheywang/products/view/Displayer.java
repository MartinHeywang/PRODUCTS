package com.martinheywang.products.view;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Displayer<T> extends AnchorPane {

	T type;

	/**
	 * Creates a new displayer.
	 * 
	 * @param node the first child of this root.
	 * @param type the subject use to build this Displayer.
	 */
	public Displayer(Node node, T type) {
		super(node);
		this.type = type;
		Displayer.setTopAnchor(node, 3d);
		Displayer.setRightAnchor(node, 3d);
		Displayer.setBottomAnchor(node, 3d);
		Displayer.setLeftAnchor(node, 3d);
	}

	/**
	 * 
	 * @return the subject used to create this Displayer
	 */
	public T getSubject() {
		return type;
	}

	/**
	 * Adds a nice mouse hover effect
	 */
	public void addHoverEffect() {
		this.setOnMouseEntered(event -> mouseover());
		this.setOnMouseExited(event -> mouseexit());
	}

	private void mouseover() {
		this.getStyleClass().add("hovered-element");
	}

	private void mouseexit() {
		this.getStyleClass().remove("hovered-element");
	}

}
