package io.github.martinheywang.products.view.component;

import java.text.NumberFormat;
import java.util.Locale;

import io.github.martinheywang.products.Main;
import io.github.martinheywang.products.api.model.Pack;
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
				Main.class.getResource("/fxml/Label.css").toExternalForm());

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

		final ImageView view = new ImageView(new Image(this.getClass()
				.getResourceAsStream(pack.getResource().getURL())));
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
