package io.github.martinheywang.products.model;

import io.github.martinheywang.products.kit.view.utils.Icon;

/**
 * The GameMode is a value which is independent of the game. It isn't persistant
 * and changes along the session. It changes the behaviour of certain UI
 * element.
 */
public enum GameMode {

    /**
     * The default (view & open windows only)
     */
    DEFAULT("Par défaut", Icon.EYE),

    /**
     * The build mode (build device on click)
     */
    BUILD("Construction", Icon.BUILD),

    /**
     * The upgrade mode (upgrade device on click)
     */
    UPGRADE("Amélioration", Icon.UPGRADE),

    /**
     * The destroy mode (destroy device on click)
     */
    DESTROY("Destruction", Icon.DESTROY);

    private String name;
    private Icon icon;

    GameMode(String name, Icon icon) {
        this.name = name;
        this.icon = icon;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the icon
     */
    public Icon getIcon() {
        return icon;
    }
}