/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.kit.view.component;

import java.lang.reflect.Field;

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.resource.Craftable;
import io.github.martinheywang.products.api.model.resource.Craftable.RemotePack;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.utils.PackUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * The RecipeView class inherits {@link javafx.scene.Node} and displays the
 * recipe for a given {@link Resource}.
 * 
 * @author Martin Heywang
 */
public class RecipeView extends HBox {

	Resource displayed;

	/**
	 * Creates a new RecipeView.
	 * 
	 * @param displayed the displayed recipe
	 */
	public RecipeView(Resource displayed) {
		this.setMinHeight(175d);
		this.setAlignment(Pos.CENTER);

		this.getStylesheets().add(getClass().getResource("/fxml/Label.css").toExternalForm());

		this.setDisplayedResource(displayed);
	}

	/**
	 * Sets the new displayed resource.
	 * 
	 * @param res the new resource
	 */
	public void setDisplayedResource(Resource res) {
		this.displayed = res;

		this.getChildren().clear();
		if (this.displayed.getField().isAnnotationPresent(Craftable.class))
			try {
				final Field displayedField = this.displayed.getField();

				final Craftable craftable = displayedField.getAnnotation(Craftable.class);
				final RemotePack[] annotationValue = craftable.recipe();

				for (final Pack pack : PackUtils.toPack(annotationValue)) {
					final PackView view = new PackView(pack);
					RecipeView.setMargin(view, new Insets(0, 10, 0, 10));
					this.getChildren().add(view);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		else {
			final Label unavailable = new Label("Aucun aperçu disponible.");
			this.getChildren().add(unavailable);
		}
	}

}
