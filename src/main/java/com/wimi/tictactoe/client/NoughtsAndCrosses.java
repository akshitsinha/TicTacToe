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

package com.wimi.tictactoe.client;

import com.wimi.tictactoe.client.game.writers.SettingsConfigurator;
import com.wimi.tictactoe.util.Themes;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class NoughtsAndCrosses {

    private static final SettingsConfigurator writer = new SettingsConfigurator();
    private static Background background = new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY));

    public static SettingsConfigurator getWriter() {
        return writer;
    }

    /**
     * Sets a theme of the scene.
     *
     * @param root The root of the scene to which the theme should be applied to.
     */
    public static void createSceneBackground(Pane root) {
        if (writer.getJsonKey("theme").equals(Themes.DARK.toString())) // Dark theme
            background = new Background(new BackgroundFill(Color.rgb(54, 57, 65), CornerRadii.EMPTY, Insets.EMPTY));
        else if (writer.getJsonKey("theme").equals(Themes.LIGHT.toString())) // Light theme
            background = new Background(new BackgroundFill(Color.rgb(250, 250, 250), CornerRadii.EMPTY, Insets.EMPTY));

        root.setBackground(background);
    }
}
