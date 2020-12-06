package io.github.martinheywang.products.kit.view.component;

import java.net.URL;

import com.sun.webkit.WebPage;

import javafx.scene.effect.Effect;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * The Icon view component allows to display an icon, as .svg. It also supports
 * a notification to trigger the user : he has something to check.
 * 
 * @author Martin Heywang
 */
public final class SVGImage extends StackPane {

    private static final double defaultOpacity = 1d;
    private static final double hoverOpacity = .7d;

    private WebView view;
    private URL pathToImage;

    private double width;
    private double height;
    private double zoom;

    private boolean hasHoverEffect = false;

    private Effect viewEffect;

    public SVGImage(){
        this.setImage(null, 0d, 0d, 1d);
    }
    /**
     * Builds a new image using the url to the svg. This svg should not have
     * <code>width</code> or <code>height</code> properties.
     * 
     * @param pathToImage the path to the SVG image
     */
    public SVGImage(URL pathToImage, double width, double height) {
        this.setImage(pathToImage, width, height, 1d);
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
    public void setImage(URL pathToImage, double width, double height, double zoom) {
        this.pathToImage = pathToImage;
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        refresh();
    }

    /**
     * Refreshes the displayed image (not the notification or whatver else).
     */
    public void refresh() {
        this.getChildren().clear();

        final WebView webView = new WebView();
        final WebEngine engine = webView.getEngine();

        this.view = webView;

        if(this.pathToImage != null){
            engine.load(pathToImage.toExternalForm());
        }else{
            engine.load(null);
        }
        webView.setPrefSize(width * zoom, height * zoom);
        webView.setFocusTraversable(false);
        webView.setContextMenuEnabled(false);
        webView.setDisable(true); // Disable scroll
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

        if (hasHoverEffect) {
            addHoverEffect();
        }
        if (viewEffect != null) {
            setViewEffect(viewEffect);
        }
    }

    /**
     * <p>
     * Applies a certain amount of zoom on the displayed image, without affecting
     * quality.
     * </p>
     * <ul>
     * <li>Less than 1 : zoom out</li>
     * <li>Equal to 1 : reset zoom (100%)</li>
     * <li>More than 1 : zoom in</li>
     * <li>Less or equal 0 : returns early</li>
     * </ul>
     * 
     * @param amount the amount of zoom
     */
    public void applyZoom(double amount) {
        if (amount <= 0) {
            return;
        }
        this.zoom = amount;
        this.view.setPrefSize(width * amount, height * amount);
    }

    /**
     * Adds an hover effect. (reduces opacity on hover)
     */
    public void addHoverEffect() {
        this.hasHoverEffect = true;
        this.setOnMouseEntered(event -> {
            view.setOpacity(hoverOpacity);
        });
        this.setOnMouseExited(event -> {
            view.setOpacity(defaultOpacity);
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