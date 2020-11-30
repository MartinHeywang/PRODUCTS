package io.github.martinheywang.products.kit.view.component;

import java.net.URL;

import com.sun.webkit.WebPage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * The Icon view component allows to display an icon, as .svg. It also supports
 * a notification to trigger the user : he has something to check.
 * 
 * @author Martin Heywang
 */
public final class SVGImage extends StackPane {

    private URL pathToImage;

    private WebView view;

    private double width, height;

    private Effect viewEffect;

    /**
     * Builds a new image using the url to the svg. This svg should not have
     * <code>width</code> or <code>height</code> properties.
     * 
     * @param pathToImage the path to the SVG image
     */
    public SVGImage(URL pathToImage, double width, double height) {
        this.setImage(pathToImage, width, height);
    }

    /**
     * Adds a bubble on top of the icon in the upper right hand corner.
     * 
     * @param message the message in the bubble, put <code>null</code> to remove the
     *                notification. Should not be too long, according to the width
     *                of the icon, unless the notification won't be as beautiful as
     *                you meant it.
     */
    public void setNotification(String message, Paint background) {
        if (this.getChildren().size() > 1) {
            // Remove the old notification
            this.getChildren().remove(1);
        }
        if (message == null)
            return;

        final Label info = new Label(message);
        info.setFont(new Font("Segoe UI Bold", this.width / 5));
        info.setBackground(new Background(new BackgroundFill(background, new CornerRadii(3000), null)));
        info.setTextFill(Color.WHITE);
        info.setMinSize(this.width / 2.5d, this.height / 2.5d);
        info.setWrapText(true);
        info.setAlignment(Pos.CENTER);
        info.setPadding(new Insets(3));

        this.getChildren().add(info);
        StackPane.setAlignment(info, Pos.TOP_RIGHT);
        StackPane.setMargin(info, new Insets(5, 5, 0, 0));
    }

    /**
     * Returns the original URL of the image.
     * 
     * @return the url
     */
    public URL getURL() {
        return pathToImage;
    }

    /**
     * Sets the displayed icon.
     * 
     * <strong>Note : if an effect was set (using {@link #setViewEffect(Effect)}),
     * it will remain.</strong>
     * 
     * @param pathToImage the path to the SVG image.
     */
    public void setImage(URL pathToImage, double width, double height) {
        this.getChildren().clear();

        final WebView webView = new WebView();
        final WebEngine engine = webView.getEngine();

        this.pathToImage = pathToImage;
        this.view = webView;
        this.width = width;
        this.height = height;

        engine.load(pathToImage.toExternalForm());
        webView.setPrefSize(width, height);
        webView.setFocusTraversable(false);
        webView.setContextMenuEnabled(false);

        // Disable scroll too and doesn't affect the image
        webView.setDisable(true);

        webView.setEffect(this.viewEffect);

        // Here the @SupressWarnings avoids a discouraged access (but actually it
        // doesn't matter in that case)
        // Only the IDE shows this warning
        // Maven has nothing wrong with it
        // But it's annoying, so I prefer add it.
        @SuppressWarnings("restriction")
        final WebPage page = com.sun.javafx.webkit.Accessor.getPageFor(engine);
        page.setBackgroundColor(0);

        this.getChildren().add(webView);
    }

    /**
     * Adds an hover effect.
     */
    public void addHoverEffect() {
        this.setOnMouseEntered(event -> {
            view.setEffect(new ColorAdjust(0, 0, -0.4, 0));
        });
        this.setOnMouseExited(event -> {
            view.setEffect(null);
        });
    }

    /**
     * Adds an {@link javafx.scene.effect.Effect} on the icon (not the notification,
     * if it exists).
     * 
     * @param effect the effect to add.
     */
    public void setViewEffect(Effect effect) {
        view.setEffect(effect);
        this.viewEffect = effect;
    }
}