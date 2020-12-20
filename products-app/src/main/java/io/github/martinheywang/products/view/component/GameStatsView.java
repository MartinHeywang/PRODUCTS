package io.github.martinheywang.products.view.component;

import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.view.component.LocatedProgressBar;
import io.github.martinheywang.products.kit.view.utils.Icon;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The GameStatsView is the top bar in the game view displaying the stats and
 * useful infos about the current game or the current session.
 * 
 * @author Martin Heywang
 */
public class GameStatsView extends HBox {

    private final Label gameName;
    private final Label gameMode;

    private final LocatedProgressBar progressBar;

    private GameController controller;

    public GameStatsView(GameController controller) {
        super(20d);
        this.controller = controller;
        this.gameName = new Label("Partie : "+controller.getGame().getName());
        this.gameMode = new Label("Mode : "+controller.getGame().getName());
        this.gameMode.setGraphic(controller.getGameMode().getIcon().createView(13d));

        this.progressBar = new LocatedProgressBar();
        this.progressBar.setProgress(.35d);
        HBox.setHgrow(progressBar, Priority.ALWAYS);

        defaultMode();

        this.setOnMouseClicked(event -> {
            processMode();
        });
    }

    /**
     * Changes to the default mode.
     */
    public void defaultMode() {
        // TODO code mthd
        this.getStyleClass().remove("dark-transparent");
        this.getStyleClass().add("cta");
        this.getChildren().addAll(this.gameName, this.gameMode);
    }

    /**
     * Changes to the build mode.
     */
    public void buildMode() {
        // TODO code mthd
    }

    /**
     * Changes to the process mode : switching view to the progress bar
     */
    public void processMode() {
        // TODO code mthd
        this.getStyleClass().add("dark-transparent");
        this.getStyleClass().remove("cta");
        this.getChildren().setAll(this.progressBar);
    }

}
