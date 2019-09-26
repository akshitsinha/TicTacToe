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

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@SuppressWarnings("unused")
public class TextBuilder {

    // Example usage: Text text = new TextBuilder("Enter text to be displayed here").otherMethodsChain.build();

    // The JFX text to build for.
    private final Text text;

    public TextBuilder(String Text) {
        this.text = new Text(Text);
    }

    /**
     * Sets the X position of the text
     *
     * @param x New value to set
     */
    public TextBuilder setLayoutX(double x) {
        text.setLayoutX(x);
        return this;
    }

    /**
     * Sets the Y position of the text
     *
     * @param y New value to set
     */
    public TextBuilder setLayoutY(double y) {
        text.setLayoutY(y);
        return this;
    }

    /**
     * @param cssScript The CSS script applied to the text.
     * @return this
     */
    public TextBuilder setCssScript(String cssScript) {
        text.setStyle(cssScript);
        return this;
    }

    /**
     * Sets the text font
     *
     * @param font New value to set
     */
    public TextBuilder setFont(Font font) {
        text.setFont(font);
        return this;
    }

    /**
     * @param val When the text size reaches beyond val pixels will be wrapped to next line.
     */
    public TextBuilder setWrappingWidthProperty(double val) {
        text.wrappingWidthProperty().set(val);
        return this;
    }

    /**
     * Sets the color of the text
     *
     * @param color New value to set
     */
    public TextBuilder setColor(Paint color) {
        text.setFill(color);
        return this;
    }

    /**
     * Builds the text by setting all the properties
     *
     * @return The text after being modified
     */
    public Text build() {
        return text;
    }
}
