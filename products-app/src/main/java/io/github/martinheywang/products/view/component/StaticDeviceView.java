package io.github.martinheywang.products.kit.view.component;

import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.level.Level;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import io.github.martinheywang.products.api.utils.StaticDeviceDataRetriever;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A StaticDeviceView is a Javafx controller that displays static data about
 * devices. (name, prices, description).
 */
public class StaticDeviceView extends HBox {

    private Class<? extends Device> type;

    private VBox text;
    private Label name;
    private Label price;
    private SVGImage icon;

    private boolean collapsed = true;

    /**
     * Creates a new StaticDeviceView displaying the given device type.
     * 
     * @param clazz the (device) type to display.
     */
    public StaticDeviceView(Class<? extends Device> clazz) {
        this.setSpacing(5d);
        this.setDisplayed(clazz);
    }

    /**
     * Changes the type to display.
     * 
     * @param clazz the new type to display.
     */
    public void setDisplayed(Class<? extends Device> clazz) {
        this.type = clazz;
        this.text = new VBox();
        this.setBackground(ViewUtils.bgDefault);

        this.name = new Label(StaticDeviceDataRetriever.getAccessibleName(clazz));
        name.getStyleClass().add("h6");
        name.setWrapText(true);
        name.setMaxWidth(100d);
        this.icon = new SVGImage(StaticDeviceDataRetriever.getView(clazz), 50d, 50d);
        this.icon.setViewEffect(Level.LEVEL_1.getEffect());

        this.price = new Label();
        price.setText(MoneyFormat.getSingleton().format(StaticDeviceDataRetriever.getBuildPrice(clazz)));
        price.getStyleClass().add("precision");

        HBox.setHgrow(icon, Priority.NEVER);
        HBox.setHgrow(text, Priority.ALWAYS);
        this.text.setAlignment(Pos.TOP_LEFT);
        this.text.getChildren().addAll(name, price);
        this.text.setSpacing(10d);
        this.setPadding(new Insets(5d));
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(text, icon);
    }

    public void addHoverEffect() {
        this.setOnMouseEntered(event -> {
            this.setBackground(ViewUtils.bgHover);
            this.name.getStyleClass().add("full-white");
            this.price.getStyleClass().add("precision-light");
        });
        this.setOnMouseExited(event -> {
            this.setBackground(ViewUtils.bgDefault);
            this.name.getStyleClass().remove("full-white");
            this.price.getStyleClass().remove("precision-light");
        });
        this.setOnMouseClicked(event -> toggleCollapsed());
    }

    /**
     * Extends (if not currently) this view to show all informations about the
     * displayed device type.
     */
    public void extend() {
        this.text.getChildren().clear();

        this.price.setText("Coût : " + this.price.getText());

        final VBox infos = new VBox();

        final Label entries = new Label();
        entries.setText("Entrées : " + StaticDeviceDataRetriever.getEntriesCount(this.type).toString());
        final Label exits = new Label();
        exits.setText("Sorties : " + StaticDeviceDataRetriever.getExitsCount(this.type).toString());
        final Label actionCost = new Label();
        actionCost.setText("Coût d'une action : "
                + MoneyFormat.getSingleton().format(StaticDeviceDataRetriever.getActionCost(this.type)));
        final Label description = new Label();
        description.setWrapText(true);
        description.setText(StaticDeviceDataRetriever.getDescription(this.type));

        this.getChildren().remove(this.icon);
        infos.getChildren().addAll(entries, exits, actionCost);
        this.text.getChildren().addAll(this.name, this.icon, description, infos, this.price);

        this.icon.setImage(StaticDeviceDataRetriever.getView(this.type), 96d, 96d, 1d);
    }

    /**
     * Collapse (if not already collapsed) this view to reduce the height, showing
     * less informations.
     */
    public void collapse() {
        this.text.getChildren().clear();

        this.price.setText(MoneyFormat.getSingleton().format(StaticDeviceDataRetriever.getBuildPrice(this.type)));
        this.text.getChildren().addAll(this.name, this.price);

        this.icon.setImage(StaticDeviceDataRetriever.getView(this.type), 50d, 50d, 1d);
        this.getChildren().add(this.icon);
    }

    /**
     * Invoke {@link #extend()} if the view is collapsed, or invoke
     * {@link #collapse()} if the view is currently extended.
     */
    public void toggleCollapsed() {
        if (collapsed) {
            this.extend();
        } else {
            this.collapse();
        }
        this.collapsed = !this.collapsed;
    }

    /**
     * Returns the displayed (extending
     * {@link io.github.martinheywang.products.api.model.device.Device}) by this
     * view.
     * 
     * @return the device type
     */
    public Class<? extends Device> getDeviceType() {
        return type;
    }
}
