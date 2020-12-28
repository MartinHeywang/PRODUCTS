package io.github.martinheywang.products.kit.view.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The Icons class is a class that helps gettings icons in the game.
 */
public class Icons {

    /**
     * Can't instantiate type Icons, only for static use.
     */
    private Icons() {
    }

    /**
     * Gets the icon url from the file name. Is equivalent to:<br>
     * <code>
     * Icons.class.getResource("/icons/"+[the file name]);
     * </code>
     * 
     * @param fileName the icon file name with the extension
     * @return the url to the resource, may be null if the file can't be reached.
     */
    public static URL asURL(String fileName) {
        return Icons.class.getResource("/icons/" + fileName);
    }

    /**
     * Gets the icon from the file name. Is equivalent to:<br>
     * <code>
     * Icons.class.getResourceAsStream("/icons/"+[the file name]);
     * </code>
     * 
     * @param fileName the icon file name with the extension
     * @return the stream with the url to the resource, may be null if the resource
     *         couldn't be reached.
     */
    public static InputStream asStream(String fileName) {
        URL url = null;
        try {
            url = asURL(fileName);
            return new FileInputStream(url.toURI().getPath());
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't not find resource :\n" + url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}