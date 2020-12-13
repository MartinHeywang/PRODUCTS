package io.github.martinheywang.products.kit.view.component;

import io.github.martinheywang.products.kit.view.utils.Icon;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class StageBar extends GridPane {

    protected static final double BUTTON_ICON_SIDE = 18d;
    protected final SVGImage closeIcon = Icon.CLOSE.createView(BUTTON_ICON_SIDE);
    protected final SVGImage maximizeIcon = Icon.MAXIMIZE.createView(BUTTON_ICON_SIDE);
    protected final SVGImage minimizeIcon = Icon.MINIMIZE.createView(BUTTON_ICON_SIDE);
    protected final SVGImage reduceIcon = Icon.REDUCE.createView(BUTTON_ICON_SIDE);

    protected final HBox title;

    protected final HBox buttons;
    protected final Button reduce;
    protected final Button maximize; // minimize too
    protected final Button close;

    public StageBar() {
        this.getStylesheets().add(ViewUtils.class.getResource("/css/Stage.css").toExternalForm());
        this.getStyleClass().add("stageBar");

        // 3 column
        for(int i = 0; i < 2; i++){
            final ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 / 2);
            this.getColumnConstraints().add(col);
        }

        // 1 row
        final RowConstraints row = new RowConstraints();
        row.setPercentHeight(100);
        this.getRowConstraints().addAll(row);

        this.title = new HBox();
        this.title.setAlignment(Pos.CENTER_LEFT);
        this.buttons = new HBox();
        this.reduce = new Button();
        this.reduce.setGraphic(reduceIcon);
        this.reduce.setOnAction(event -> {
            ((Stage) this.reduce.getScene().getWindow()).setIconified(true);
        });
        this.reduce.getStyleClass().add("stageButtons");
        this.maximize = new Button();
        this.maximize.setGraphic(maximizeIcon);
        this.maximize.setOnAction(event -> {
            final Stage stage = (Stage) this.reduce.getScene().getWindow();
            stage.setMaximized(!stage.isMaximized());

            if (stage.isMaximized()) {
                this.maximize.setGraphic(minimizeIcon);
            } else {
                this.maximize.setGraphic(maximizeIcon);
            }
        });
        this.maximize.getStyleClass().add("stageButtons");
        this.close = new Button();
        this.close.setGraphic(closeIcon);
        this.close.setOnAction(event -> {
            ((Stage) this.reduce.getScene().getWindow()).close();
        });
        this.close.getStyleClass().addAll("stageButtons", "closeButton");
        this.buttons.getChildren().addAll(this.reduce, this.maximize, this.close);
        this.buttons.setAlignment(Pos.CENTER_RIGHT);

        this.setOnMousePressed(pressEvent -> {
			this.setOnMouseDragged(dragEvent -> {
				final Stage primaryStage = (Stage) this.getScene().getWindow();
				primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			});
		});

        this.add(title, 0, 0);
        this.add(buttons, 1, 0);

        StageBar.setValignment(title, VPos.CENTER);
        StageBar.setHalignment(title, HPos.LEFT);
        StageBar.setValignment(buttons, VPos.CENTER);
        StageBar.setHalignment(buttons, HPos.RIGHT);
    }

    /**
     * Sets the text of the title of the stage. (Max length of all labels : 1/3 of the
     * stage width).
     * 
     * @param texts an array of Label
     */
    public void setTitle(Label... texts) {
        this.title.getChildren().clear();
        for(Label label : texts){
            label.getStyleClass().addAll("title");
            this.title.getChildren().add(label);
        }
    }

    /**
     * Disable the maximize/minimize button.
     */
    public void disableMaximizeButton(){
        this.maximize.setDisable(true);
    }
}
