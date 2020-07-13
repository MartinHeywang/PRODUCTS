package com.martinheywang.view.components;

import com.martinheywang.model.Pack;
import com.martinheywang.model.resources.DefaultResources;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayer;

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
		if (!res.equals(DefaultResources.NONE)) {

			for (Pack pack : displayed.getRecipe()) {
				Displayer<Pack> display = pack.getDisplayer();
				Recipe.setMargin(display, new Insets(0, 10, 0, 10));
				this.getChildren().add(display);
			}
		} else {
			Label unavailable = new Label("Aucun aperçu disponible.");
			this.getChildren().add(unavailable);
		}
	}

}
