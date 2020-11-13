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

import java.text.NumberFormat;
import java.util.Locale;

import io.github.martinheywang.products.api.model.resource.Resource;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The ResourceView class inherits {@link javafx.scene.Node} and displays a
 * {@link Resource}.
 * 
 * @author Martin Heywang
 */
public class ResourceView extends VBox {

	private Resource subject;

	/**
	 * Creates a new ResourceView using the given resource
	 * 
	 * @param data
	 *            the resource to display
	 */
	public ResourceView(Resource data) {
		this.setMinHeight(125d);
		this.setAlignment(Pos.CENTER);

		this.getStylesheets().add(
				ResourceView.class.getResource("/fxml/Label.css").toExternalForm());

		this.setDisplayed(data);
	}

	/**
	 * Sets the displayed resource.
	 * 
	 * @param resource
	 *            the new resource.
	 */
	public void setDisplayed(Resource resource) {
		this.subject = resource;
		this.getChildren().clear();

		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(3));

		final Label nom = new Label();
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(resource.getName());
		if (nom.getText().length() > 15)
			nom.setFont(new Font(10d));
		nom.setPrefHeight(20d);

		final ImageView image = new ImageView();
		image.setImage(new Image(resource.getURL()));

		final Label infos = new Label();
		infos.setAlignment(Pos.TOP_CENTER);
		infos.setText(
				String.valueOf(NumberFormat.getInstance(Locale.getDefault())
						.format(resource.getPrice())) + " €");

		final Pane fancy = new Pane();
		fancy.setPrefHeight(2d);

		this.setOnMouseEntered(event -> {
			fancy.setBackground(new Background(
					new BackgroundFill(Color.DODGERBLUE, null, null)));
			this.setBackground(new Background(new BackgroundFill(
					Color.web("282828"), new CornerRadii(3d), new Insets(0d))));
		});
		this.setOnMouseExited(event -> {
			fancy.setBackground(
					new Background(new BackgroundFill(null, null, null)));
			this.setBackground(
					new Background(new BackgroundFill(null, null, null)));
		});
		this.getChildren().addAll(nom, image, infos, fancy);
	}

	/**
	 * Returns the resource that has been used for this display.
	 * 
	 * @return the subject
	 */
	public Resource getResource() {
		return this.subject;
	}
}
