package com.martinheywang.view;

import com.martinheywang.model.Pack;
import com.martinheywang.model.BaseResources;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Recipe extends HBox {

	BaseResources displayed;

	public Recipe(BaseResources displayed) {
		this.setMinHeight(125d);
		this.setAlignment(Pos.CENTER);

		setDisplayedResource(displayed);
	}

	public void setDisplayedResource(BaseResources res) {
		this.displayed = res;

		this.getChildren().clear();
		if (!res.equals(BaseResources.NONE)) {

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
