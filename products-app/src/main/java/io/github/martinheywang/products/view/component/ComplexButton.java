package io.github.martinheywang.products.kit.view.component;

import java.net.URL;

import io.github.martinheywang.products.kit.view.utils.Icon;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ComplexButton extends HBox {

    private VBox text = new VBox();
    private Label titleLabel = new Label();
    private Label descLabel = new Label();

    private Region graphic;

    private static final SVGImage selectionIcon = Icon.RIGHT_KEYBOARD_ARROW.createView(30d);

    /**
     * Creates a new ComplexButton.
     * 
     * @param title       the title ot the button
     * @param description the description of the button
     * @param icon        the url path to the icon
     */
    public ComplexButton(String title, String description, URL icon) {
        this.graphic = getGraphic(icon);
        this.getChildren().add(this.graphic);

        this.text.getChildren().clear();
        HBox.setHgrow(text, Priority.ALWAYS);

        this.titleLabel = new Label(title);
        titleLabel.getStyleClass().addAll("h6");
        this.descLabel = new Label(description);
        descLabel.getStyleClass().add("precision");

        text.getChildren().addAll(titleLabel, descLabel);
        text.setAlignment(Pos.CENTER_LEFT);
        text.setSpacing(5d);
        this.setPadding(new Insets(10d));
        this.setMinHeight(50d);
        this.setBackground(ViewUtils.bgDefault);
        this.getChildren().add(text);

        this.setOnMouseEntered(event -> {
            titleLabel.getStyleClass().add("full-white");
            descLabel.getStyleClass().add("precision-light");
            this.getChildren().set(0, selectionIcon);
            this.setBackground(ViewUtils.bgHover);
        });
        this.setOnMouseExited(event -> {
            titleLabel.getStyleClass().remove("full-white");
            descLabel.getStyleClass().remove("precision-light");
            this.getChildren().set(0, this.graphic);
            this.setBackground(ViewUtils.bgDefault);
        });
    }

    /**
     * Returns a node according to the url. This url must be an SVG to be valid.
     * 
     * @param url the path to the icon
     * @return the node
     */
    private Region getGraphic(URL url) {
        final Pane pane = new Pane();
        pane.setMinSize(30d, 30d);
        if (url == null) {
            return pane;
        } else if (url.getPath().endsWith(".svg")) {
            return new SVGImage(url, 30d, 30d);
        }
        return pane;
    }

}