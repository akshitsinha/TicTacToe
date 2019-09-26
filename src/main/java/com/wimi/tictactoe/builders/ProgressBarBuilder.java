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
