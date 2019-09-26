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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

@SuppressWarnings("unused")
public class ImageBuilder {

    // The image to build for.
    private Image image;

    @SuppressWarnings("ConstantConditions")
    private ImageView imageView = new ImageView(image);

    public ImageBuilder(String path) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            this.image = new Image(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView.setPreserveRatio(true);
    }

    /**
     * Sets the X position of the image
     *
     * @param x New value to set
     */
    public ImageBuilder setX(int x) {
        imageView.setX(x);
        return this;
    }

    /**
     * Sets the Y position of the image
     *
     * @param y New value to set
     */
    public ImageBuilder setY(int y) {
        imageView.setY(y);
        return this;
    }

    /**
     * Sets the image width
     *
     * @param width New value to set
     */
    public ImageBuilder setWidth(int width) {
        imageView.setFitWidth(width);
        return this;
    }

    /**
     * Sets the image height
     *
     * @param height New value to set
     */
    public ImageBuilder setHeight(int height) {
        imageView.setFitHeight(height);
        return this;
    }

    /**
     * Returns the built ImageView
     *
     * @return The modified ImageView
     */
    public ImageView build() {
        return imageView;
    }
}
