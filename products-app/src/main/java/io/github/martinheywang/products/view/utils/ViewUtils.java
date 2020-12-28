package io.github.martinheywang.products.kit.view.utils;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * The ViewUtils class contains a lot of utility methods about the view side of
 * the app.
 */
public class ViewUtils {

    public static final Background bgDefault = new Background(
            new BackgroundFill(Color.web("transparent"), new CornerRadii(4d), null));
    public static final Background bgHover = new Background(
            new BackgroundFill(Color.web("rgba(0, 0, 0, .3)"), new CornerRadii(4d), null));

    private ViewUtils() {
    }

    /**
     * Prepares and returns an {@link javafx.fxml.FXMLLoader} from the given file.
     * 
     * @param url the url to the .fxml file that this FXMLLoader will be able to
     *            load.
     * @return a functionnal FXMLoader
     */
    public static FXMLLoader prepareFXMLLoader(URL url) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        return loader;
    }

    /**
     * Applies the game css to the root and all its nested children through
     * inheritance. Some animations are also added in the process.
     * 
     * @param root a node which will receive the styles.
     */
    public static void prepareScene(Parent root) {
        root.getStylesheets().add(ViewUtils.class.getResource("/css/General.css").toExternalForm());
    }

    /**
     * Adds the given style class to all childrens of the given parent. If nodes are
     * added after invoking this method, they won't be receive the style class.
     * 
     * @param parent     the container of the nodes
     * @param styleClass the style class to add
     */
    public static void addStyleClassToChildrens(Parent parent, String styleClass) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Removes the given style class to all childrens of the given parent. If nodes
     * are added after invoking this method, they won't be affected by this method.
     * 
     * @param parent     the container of the nodes
     * @param styleClass the style class to remove
     */
    public static void removeStyleClassToChildrens(Parent parent, String styleClass) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            node.getStyleClass().remove(styleClass);
        }
    }
}