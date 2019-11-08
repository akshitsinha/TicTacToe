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

package com.wimi.tictactoe.builders;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;

@SuppressWarnings("unused")
public class SliderBuilder {

    // The JFX slider to build for.
    private JFXSlider slider;

    public SliderBuilder(double min, double max, double defaultValue) {
        this.slider = new JFXSlider(min, max, defaultValue);
    }

    /**
     * Sets the translate X of the slider
     *
     * @param x New value to set
     */
    public SliderBuilder setLayoutX(int x) {
        slider.setLayoutX(x);
        return this;
    }

    /**
     * Sets the translate Y of the slider
     *
     * @param y New value to set
     */
    public SliderBuilder setLayoutY(int y) {
        slider.setLayoutY(y);
        return this;
    }

    /**
     * Sets the value of the slider.
     *
     * @param value The value to set for the Slider.
     */
    public SliderBuilder setValue(double value) {
        slider.setValue(value);
        return this;
    }

    /**
     * Set whether to show the labels of the ticks on the slider.
     */
    public SliderBuilder setShowTickLabels(boolean val) {
        slider.setShowTickLabels(val);
        return this;
    }

    /**
     * Select type of cursor when hovering over the slider.
     */
    public SliderBuilder setCursor(Cursor cursor) {
        slider.setCursor(cursor);
        return this;
    }

    /**
     * Sets the final width of the slider
     *
     * @param width New value to set
     */
    public SliderBuilder setWidth(int width) {
        slider.setMinWidth(width);
        slider.setPrefWidth(width);
        slider.setMaxWidth(width);
        return this;
    }

    /**
     * Sets the final height of the slider
     *
     * @param height New value to set
     */
    public SliderBuilder setHeight(int height) {
        slider.setMinHeight(height);
        slider.setPrefHeight(height);
        slider.setMaxHeight(height);
        return this;
    }

    /**
     * @param orientation Orientation of the slider.
     * @return this
     */
    public SliderBuilder setOrientation(Orientation orientation) {
        slider.setOrientation(orientation);
        return this;
    }

    /**
     * Sets the color of the slider
     *
     * @param r Red value of the color
     * @param g Green value of the color
     * @param b Blue value of the color
     */
    public SliderBuilder setColor(int r, int g, int b) {
        slider.setStyle(String.format("-fx-background-color: rgb(%s, %s, %s)", r, g, b));
        return this;
    }

    /**
     * Builds the slider
     *
     * @return The built slider
     */
    public JFXSlider build() {
        return slider;
    }
}
