package com.martin.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Dashboard extends VBox {

	private int second = 0;
	
	private Label labelSecond;

	public Dashboard() {
		labelSecond = new Label("Cet appareil a été construit il y a "+second+" seconde(s).");
		this.getChildren().add(labelSecond);
	}
	
	public void addSecond() {
		second++;
	}

}
