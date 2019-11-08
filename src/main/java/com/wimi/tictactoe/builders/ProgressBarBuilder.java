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

import javafx.scene.control.ProgressBar;

@SuppressWarnings("unused")
public class ProgressBarBuilder {

    // Progress Bar to build for.
    private ProgressBar progressBar;

    private ProgressBarBuilder(double progress) {
        progressBar = new ProgressBar(progress);
    }

    /**
     * Initial progress bar.
     */
    public ProgressBarBuilder() {
        this(0);
    }

    /**
     * Sets the translate Y of the progress bar
     *
     * @param y New value to set
     */
    public ProgressBarBuilder setY(int y) {
        progressBar.setTranslateY(y);
        return this;
    }

    /**
     * Sets the final width of the progress bar
     *
     * @param width New value to set
     */
    public ProgressBarBuilder setWidth(int width) {
        progressBar.setPrefWidth(width);
        return this;
    }

    /**
     * Sets the final height of the progress bar
     *
     * @param height New value to set
     */
    public ProgressBarBuilder setHeight(int height) {
        progressBar.setMinHeight(height);
        progressBar.setPrefHeight(height);
        progressBar.setMaxHeight(height);
        return this;
    }

    /**
     * Builds the progress bar
     *
     * @return The built progress bar
     */
    public ProgressBar build() {
        return progressBar;
    }
}
