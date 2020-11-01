package io.github.martinheywang.products.view.component;

import java.text.NumberFormat;
import java.util.Locale;

import io.github.martinheywang.products.Main;
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
				Main.class.getResource("/fxml/Label.css").toExternalForm());

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
		image.setImage(new Image(
				this.getClass().getResourceAsStream(resource.getURL())));

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
