package com.martin.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Dashboard extends VBox {

	private int second = 0;
	
	private Label labelSecond;
	private Label labelAction;

	public Dashboard() {
		labelSecond = new Label();
		this.getChildren().add(labelSecond);
		
		labelAction= new Label();
		this.getChildren().add(labelAction);
	}
	
	/**
	 * <h1>addSecond</h1>
	 * <p>Adds a second to the count and sets the label</p>
	 */
	public void addSecond() {
		second++;
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				labelSecond.setText("Cet appareil a été construit il y a "+second+" seconde(s).");
			}
		});
	}

}
