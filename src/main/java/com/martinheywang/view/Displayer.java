package com.martinheywang.view;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Displayer<T> extends AnchorPane {

	T type;

	public Displayer(Node node, T type) {
		super(node);
		this.type = type;
		Displayer.setTopAnchor(node, 3d);
		Displayer.setRightAnchor(node, 3d);
		Displayer.setBottomAnchor(node, 3d);
		Displayer.setLeftAnchor(node, 3d);
	}

	public T getSubject() {
		return type;
	}

}
