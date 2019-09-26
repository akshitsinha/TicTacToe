/*
 * Copyright 2019 Akshit Sinha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
  This package contains builders for the JavaFX nodes.
 */
package com.wimi.tictactoe.builders;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@SuppressWarnings("unused")
public class ButtonBuilder {

    // Example usage: Button buttonName = new ButtonBuilder("Enter button name").setText("Button text").otherMethodsChain.build();

    // The JFX button to build for.
    private final JFXButton button;

    public ButtonBuilder(String text) {
        this.button = new JFXButton(text);
    }

    /**
     * Sets the CSS script to the button.
     *
     * @param css CSS script.
     */
    public ButtonBuilder setCssScript(String css) {
        this.button.setStyle(css);
        return this;
    }

    /**
     * Sets the translate X of the button
     *
     * @param x New value to set
     */
    public ButtonBuilder setLayoutX(double x) {
        button.setLayoutX(x);
        return this;
    }

    /**
     * Sets the translate Y of the button
     *
     * @param y New value to set
     */
    public ButtonBuilder setLayoutY(double y) {
        button.setLayoutY(y);
        return this;
    }

    /**
     * Sets the final width of the button
     *
     * @param width New value to set
     */
    public ButtonBuilder setPrefWidth(double width) {
        button.setPrefWidth(width);
        return this;
    }

    /**
     * Sets the final height of the button
     *
     * @param height New value to set
     */
    public ButtonBuilder setPrefHeight(double height) {
        button.setPrefHeight(height);
        return this;
    }

    /**
     * Sets the context menu for a node.
     *
     * @param contextMenu The context menu which can be accessed on right clicking the node.
     */
    public ButtonBuilder setContextMenu(ContextMenu contextMenu) {
        button.setContextMenu(contextMenu);
        return this;
    }

    /**
     * Sets the fill color of the button.
     *
     * @param r Red value of the color.
     * @param b Blue value of the color.
     * @param g Green value of the color.
     */
    public ButtonBuilder setFill(int r, int g, int b) {
        button.setStyle(String.format("-fx-background-color: rgb(%s, %s, %s);", r, g, b));
        return this;
    }

    /**
     * Sets the color of the text inside the button
     *
     * @param color New value to set
     */
    public ButtonBuilder setTextColor(Color color) {
        button.setTextFill(color);
        return this;
    }

    /**
     * Sets the font of the test inside the button
     *
     * @param font New value to set
     */
    public ButtonBuilder setFont(Font font) {
        button.setFont(font);
        return this;
    }

    /**
     * Sets if the button is disabled by default.
     *
     * @param state Sets disable state.
     */
    public ButtonBuilder setDisabled(boolean state) {
        button.setDisable(state);
        return this;
    }

    /**
     * Sets the action done when the button is clicked
     *
     * @param value New value to set
     */
    public ButtonBuilder onMouseClick(EventHandler<? super MouseEvent> value) {
        button.setOnMouseClicked(value);
        return this;
    }

    /**
     * Builds the button
     *
     * @return The built button.
     */
    public Button build() {
        return button;
    }
}
