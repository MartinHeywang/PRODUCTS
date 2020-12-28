package io.github.martinheywang.products.kit.view.component;

import io.github.martinheywang.products.api.model.MenuType;
import io.github.martinheywang.products.kit.view.utils.Icon;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * A Menu in PRODUCTS. is a sidebar that shows/hides complentary informations
 * are enable/disable options. It has a name, an help zone, and the content.
 */
public final class Menu extends VBox {

    private MenuType type;
    private String help;
    
    private Label title;
    private SVGImage helpIcon;
    private Text helpText;
    private Node content;

    /**
     * Creates a new Menu.
     * 
     * @param type the type of the menu.
     * @param help the help of the menu (displayed in a popup).
     * @param content the content displayed in the menu.
     */
    public Menu(MenuType type, String help, Node content){
        this.type = type;
        this.help = help;
        this.content = content;

        this.setPadding(new Insets(5d));

        this.helpIcon = Icon.HELP.createView(15d);

        this.title = new Label(type.getName());
        this.title.getStyleClass().add("h4");
        this.title.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        this.title.setGraphic(this.helpIcon);

        this.getChildren().addAll(this.title, this.content);
    }

    /**
     * Returns the type of the menu.
     * 
     * @return the type of the menu
     */
    public MenuType getType(){
        return type;
    }

    /**
     * Returns the help of the menu, displayed in a popup.
     * 
     * @return the help of the menu
     */
    public String getHelp(){
        return help;
    }

    /**
     * The content of the menu, the root node.
     * 
     * @return the content
     */
    public Node getContent(){
        return content;
    }
}