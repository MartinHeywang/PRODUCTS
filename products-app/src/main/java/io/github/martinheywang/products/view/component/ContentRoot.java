/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.kit.view.controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The ContentRoot class is an abstract class that is the base for the content
 * in the window. It may be put directly in the scene. The root is this object,
 * the {@link #userRoot} the direct children of the root, and {@link #content}
 * the actual content given in the constructor.
 */
public class ContentRoot extends StackPane {

    /**
     * The user root is different as the root because the user root is the direct
     * and only children of the actual root (this object basically). This is the one
     * that has the shadow on it.
     */
    protected AnchorPane userRoot;

    /**
     * The content is the node given in the constructor, basically all that the user
     * can see. It is the direct and only children of the user root.
     */
    protected Node content;

    /**
     * Creates a new ContentRoot object.
     * 
     * @param content the content of this 'root'
     */
    public ContentRoot() {
        super();

        this.userRoot = new AnchorPane();
        this.userRoot.getStyleClass().add("userRoot");

        this.getChildren().add(userRoot);
        this.getStyleClass().add("root");
    }

    /**
     * Sets the content node
     * 
     * @param content
     */
    public final void setContent(Node content) {
        this.content = content;
        this.userRoot.getChildren().add(content);

        AnchorPane.setTopAnchor(this.content, 0d);
        AnchorPane.setRightAnchor(this.content, 0d);
        AnchorPane.setBottomAnchor(this.content, 0d);
        AnchorPane.setLeftAnchor(this.content, 0d);

        try {

        } catch (ClassCastException | NullPointerException e) {
            // The tried behaviour casts the window to a Stage
            // Only stage can me maximized/resized/iconified
            // This occurs whenever the window isn't a stage or this node isn't in a window
            System.out.println("Note: the ContentRoot isn't in a Stage, please consider changing it.");
        }
    }

    /**
     * Returns the {@link #userRoot}.
     * 
     * @return the user root.
     */
    public final Parent getUserRoot() {
        return userRoot;
    }

    /**
     * Returns the {@link #content}, given in the constructor.
     * 
     * @return the content
     */
    public final Node getContentNode() {
        return content;
    }

    /**
     * The auto padding feature adjusts the padding the user root, in order to show
     * the shadow when the stage is in default size, or to hide it when the stage is
     * maximized. This feature is only allowed when this ContentRoot is already in a
     * {@link Stage} (nested in the Scene).
     */
    public final void autoPadding() {
        ((Stage) this.getScene().getWindow()).maximizedProperty().addListener((obs, newVal, oldVal) -> {
            // somehow value is inversed... this isn't a mistake
            if (newVal == true) {
                userRoot.setPadding(new Insets(10d));
            } else {
                userRoot.setPadding(new Insets(0d));
            }
        });
    }
}