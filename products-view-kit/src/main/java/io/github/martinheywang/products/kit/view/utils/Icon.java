package io.github.martinheywang.products.kit.view.utils;

import java.net.URL;

import io.github.martinheywang.products.kit.view.component.SVGImage;

/**
 * <p>
 * Icon is an enum that provides path to icon.
 * </p>
 * Sources :
 * <ul>
 * <li>https://material.io/resources/icons</li>
 * </ul>
 * 
 * @author Martin Heywang
 */
public enum Icon {

    /**
     * The build icon. (Example : icon for the build menu in the toolbar)
     */
    BUILD("build.svg"),

    /**
     * The close icon. (Example : close button of the stage)
     */
    CLOSE("close.svg"),

    /**
     * The create icon. (Example : plus sign as logo of the create option)
     */
    CREATE("create.svg"),

    /**
     * An up keyboard arrow (Example : on a laptop keyboard)
     */
    UP_KEYBOARD_ARROW("up_keyboard_arrow.svg"),

    /**
     * An right keyboard arrow (Example : on a laptop keyboard)
     */
    RIGHT_KEYBOARD_ARROW("right_keyboard_arrow.svg"),

    /**
     * An down keyboard arrow (Example : on a laptop keyboard)
     */
    DOWN_KEYBOARD_ARROW("down_keyboard_arrow.svg"),

    /**
     * An let keyboard arrow (Example : on a laptop keyboard)
     */
    LEFT_KEYBOARD_ARROW("left_keyboard_arrow.svg"),

    /**
     * A regular up arrow
     */
    UP_ARROW("up_arrow.svg"),

    /**
     * A right up arrow
     */
    RIGHT_ARROW("right_arrow.svg"),

    /**
     * A down up arrow
     */
    DOWN_ARROW("down_arrow.svg"),

    /**
     * A left up arrow
     */
    LEFT_ARROW("left_arrow.svg"),

    /**
     * The LOGO of the game (looks like a dollar sign)
     */
    LOGO("logo.svg"),

    /**
     * A maximize icon (Example : full screen)
     */
    MAXIMIZE("maximize.svg"),

    /**
     * A minimize icon (Example : reduce screen)
     */
    MINIMIZE("minimize.svg"),

    /**
     * The open icon (Example : the icon of the open option)
     */
    OPEN("open.svg"),

    /**
     * A point (or a dot).
     */
    POINT("point.svg"),

    /**
     * The reduce icon (Example : the reduce button of Windows)
     */
    REDUCE("reduce.svg"),
    /**
     * The help icon (A question mark in a circle).
     */
    HELP("question-mark.svg");

    private URL path;

    Icon(String path) {
        this.path = Icon.class.getResource("/icons/" + path);
    }

    /**
     * Returns the absolute path to the icon, as an 'java.net.URL'.
     * 
     * @return the path
     */
    public URL getPath() {
        return path;
    }

    /**
     * Creates a new {@link SVGImage} using the path to this icon.
     * 
     * @param side both width and height
     * @return the brand new created game.
     */
    public SVGImage createView(double side) {
        return new SVGImage(this.getPath(), side, side);
    }

    /**
     * Creates a new {@link SVGImage} using the given width and height, as specified
     * in {@link SVGImage#SVGImage(URL, double, double)}.
     * 
     * @param width  the width of the image
     * @param height the height of the image
     * @return the new svg image
     */
    public SVGImage createView(double width, double height) {
        return new SVGImage(this.getPath(), width, height);
    }
}