package io.github.martinheywang.products.view.component;

import java.util.HashMap;

import io.github.martinheywang.products.api.model.MenuType;
import io.github.martinheywang.products.kit.view.component.Menu;
import io.github.martinheywang.products.kit.view.utils.Icon;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The MenuView class is a view component that manages the different
 * {@link io.github.martinheywang.products.kit.view.component.Menu}.
 */
public class MenuView extends HBox {

    private final HashMap<MenuType, Menu> menus;

    private final VBox toolbar;

    public MenuView() {
        menus = new HashMap<>();

        toolbar = new VBox();
        toolbar.setPadding(new Insets(5d));
        this.getChildren().add(toolbar);
    }

    /**
     * Adds the given menu to the view. You can not add two menu with the same menu
     * type.
     * 
     * @param menu
     */
    public void addMenu(Menu menu, Icon icon) {
        menus.putIfAbsent(menu.getType(), menu);
        toolbar.getChildren().add(icon.createView(40d));
    }

}