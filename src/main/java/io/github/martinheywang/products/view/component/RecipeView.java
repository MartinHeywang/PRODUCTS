package io.github.martinheywang.products.view.component;

import java.lang.reflect.Field;

import io.github.martinheywang.products.Main;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.resource.Craftable;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.Craftable.RemotePack;
import io.github.martinheywang.products.controller.ResourceManager;
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

	public RecipeView(Resource displayed) {
		this.setMinHeight(175d);
		this.setAlignment(Pos.CENTER);

		this.getStylesheets().add(
				Main.class.getResource("/fxml/Label.css").toExternalForm());

		this.setDisplayedResource(displayed);
	}

	/**
	 * Sets the new displayed resource.
	 * 
	 * @param res
	 *            the new resource
	 */
	public void setDisplayedResource(Resource res) {
		this.displayed = res;

		this.getChildren().clear();
		if (this.displayed.getField().isAnnotationPresent(Craftable.class))
			try {
				final Field displayedField = this.displayed.getField();

				final Craftable craftable = displayedField
						.getAnnotation(Craftable.class);
				final RemotePack[] annotationValue = craftable.recipe();

				for (final Pack pack : ResourceManager
						.toPack(annotationValue)) {
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
