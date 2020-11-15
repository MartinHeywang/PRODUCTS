package io.github.martinheywang.products.kit.view.utils;

import java.net.URL;

import javafx.fxml.FXMLLoader;

/**
 * The ViewUtils class contains a lot of utility methods about the view side of
 * the app.
 */
public class ViewUtils {

    private ViewUtils(){
    }

    /**
     * Prepares and returns an {@link javafx.fxml.FXMLLoader} from the given file.
     * 
     * @param url the url to the .fxml file that this FXMLLoader will be able to load.
     * @return a functionnal FXMLoader
     */
    public static FXMLLoader prepareFXMLLoader(URL url){
        final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		return loader;
    }
}