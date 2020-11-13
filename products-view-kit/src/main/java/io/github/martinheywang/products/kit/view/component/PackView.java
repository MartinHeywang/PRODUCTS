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

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
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
 * The PackView class inherits {@link javafx.scene.Node} and displays a
 * {@link Pack}.
 * 
 * @author Martin Heywang
 */
public class PackView extends VBox {

	/**
	 * Creates a new PackView using the given Pack
	 * 
	 * @param data
	 *            the pack to display
	 */
	public PackView(Pack data) {
		this.setMinHeight(125d);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(5, 8, 5, 8));
		this.setSpacing(10d);

		this.getStylesheets().add(
				ViewUtils.class.getResource("/fxml/Label.css").toExternalForm());

		this.setDisplayed(data);
	}

	/**
	 * Changes the displayed pack
	 * 
	 * @param pack
	 *            the new pack
	 */
	public void setDisplayed(Pack pack) {
		this.getChildren().clear();

		final Label name = new Label(pack.getResource().getName());

		if (name.getText().length() > 15)
			name.setFont(new Font(10d));

		final ImageView view = new ImageView(new Image(pack.getResource().getURL()));

		final Label quantity = new Label(
				"Quantité: " + String.valueOf(NumberFormat
						.getInstance(Locale.getDefault()).format(pack.getQuantity())));
		final Pane fancy = new Pane();
		fancy.setPrefHeight(2d);

		this.setOnMouseEntered(event -> {
			fancy.setBackground(new Background(new BackgroundFill(
					Color.DODGERBLUE, null, null)));
			this.setBackground(new Background(new BackgroundFill(
					Color.web("282828"), new CornerRadii(3d), new Insets(0d))));
		});
		this.setOnMouseExited(event -> {
			fancy.setBackground(
					new Background(new BackgroundFill(null, null, null)));
			this.setBackground(
					new Background(new BackgroundFill(null, null, null)));
		});
		this.getChildren().addAll(name, view, quantity, fancy);
	}

}
