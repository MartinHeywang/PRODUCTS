package com.martinheywang.products.view.component;

import java.lang.reflect.Field;

import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.resource.Craftable;
import com.martinheywang.products.model.resource.DefaultResource;
import com.martinheywang.products.model.resource.Resource;
import com.martinheywang.products.model.resource.Craftable.RemotePack;
import com.martinheywang.products.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RecipeView extends HBox {

	Resource displayed;

	public RecipeView(Resource displayed) {
		this.setMinHeight(125d);
		this.setAlignment(Pos.CENTER);

		setDisplayedResource(displayed);
	}

	public void setDisplayedResource(Resource res) {
		this.displayed = res;

		this.getChildren().clear();
		if (!res.equals(DefaultResource.NONE)) {

			try {
				String name = displayed.toString();
				Field displayedField = null;
				displayedField = displayed.getClass().getField(name);

				if (displayedField.isAnnotationPresent(Craftable.class)) {

					Craftable craftable = displayedField
							.getAnnotation(Craftable.class);
					RemotePack[] annotationValue = craftable.recipe();

					for (Pack pack : Pack.toPack(annotationValue)) {
						Displayer<Pack> display = pack.getDisplayer();
						RecipeView.setMargin(display, new Insets(0, 10, 0, 10));
						this.getChildren().add(display);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Label unavailable = new Label("Aucun aperçu disponible.");
			this.getChildren().add(unavailable);
		}
	}

}
