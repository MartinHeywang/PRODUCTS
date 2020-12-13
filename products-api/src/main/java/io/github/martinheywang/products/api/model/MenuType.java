package io.github.martinheywang.products.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The MenuType is a type that the menu may have.
 * 
 * @author Martin Heywang
 */
public class MenuType {

    private static final HashMap<String, MenuType> instances = new HashMap<>();

    private String name;

    private MenuType(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the type.
     * 
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * As the constructor of this class is private, this methods allows to create a
     * MenuType. Note that you can't create two menu types with the exact same name.
     * 
     * @param name the name of the menu
     * @return the new type, or null if type already exists.
     */
    public static MenuType create(String name) {
        if (instances.containsKey(name)) {
            return null;
        }
        final MenuType type = new MenuType(name);
        instances.put(name, type);
        return type;
    }
}
