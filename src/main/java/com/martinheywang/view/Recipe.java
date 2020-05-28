package com.martinheywang.view;

import com.martinheywang.model.Packing;
import com.martinheywang.model.Resource;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Recipe extends HBox {

	Resource displayed;

	public Recipe(Resource displayed) {
		this.setMinHeight(125d);
		this.setAlignment(Pos.CENTER);

		setDisplayedResource(displayed);
	}

	public void setDisplayedResource(Resource res) {
		this.displayed = res;

		this.getChildren().clear();
		if (!res.equals(Resource.NONE)) {

			for (Packing pack : displayed.getRecette()) {
				Displayer<Packing> display = pack.getDisplayer();
				Recipe.setMargin(display, new Insets(0, 10, 0, 10));
				this.getChildren().add(display);
			}
		} else {
			Label unavailable = new Label("Aucun aperçu disponible.");
			this.getChildren().add(unavailable);
		}
	}

}
