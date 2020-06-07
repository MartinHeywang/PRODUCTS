package com.martinheywang.view;

import com.martinheywang.Main;

import javafx.scene.Cursor;
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
	 * @return the subject used to creates this Displayer
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
		Main.getMainStage().getScene().setCursor(Cursor.HAND);
	}

	private void mouseexit() {
		Main.getMainStage().getScene().setCursor(Cursor.DEFAULT);
	}

}
