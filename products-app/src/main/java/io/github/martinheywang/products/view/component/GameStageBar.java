package io.github.martinheywang.products.view.component;

import io.github.martinheywang.products.kit.view.component.StageBar;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;

/**
 * This class is a view components that manages the custom stage bar of the
 * stage. It is the game one, as it contains more that the name of the stage and
 * the buttons : the money label.
 * 
 * @author Martin Heywang
 */
public final class GameStageBar extends StageBar {

    private Label money;
   
    /**
     * Creates a new GameStageBar.
     */
    public GameStageBar(){
        super();

        // 3 columns (replacing the 2 inherited ones)
        this.getColumnConstraints().clear();
        for(int i = 0; i < 3; i++){
            final ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 / 3);
            this.getColumnConstraints().add(col);
        }

        // 1 row inherited

        this.money = new Label("Something");

        this.getChildren().clear();
        this.add(this.title, 0, 0);
        this.add(this.money, 1, 0);
        this.add(this.buttons, 2, 0);

        StageBar.setValignment(this.money, VPos.CENTER);
        StageBar.setHalignment(this.money, HPos.CENTER);
    }

    /**
     * Sets the text of the money label.
     * 
     * @param newValue the new string
     */
    public void setMoney(String newValue){
        this.money.setText(newValue);
    }

}