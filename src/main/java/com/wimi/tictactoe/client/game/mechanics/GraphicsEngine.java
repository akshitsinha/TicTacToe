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

package com.wimi.tictactoe.client.game.mechanics;

import com.wimi.tictactoe.builders.TextBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

/**
 * @Description Creates the basic framework and builds the scene.
 * Basic graphics, transitions, Texts, etc.
 */
class GraphicsEngine {

    private final Text gameTitleBanner = new TextBuilder("Tic Tac Toe")
            .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 36))
            .setColor(Color.SLATEGRAY)
            .build();
    private final Text timeElapsedBanner = new TextBuilder("Time elapsed:")
            .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 38))
            .setColor(Color.DARKCYAN)
            .build();
    private final Text nextMoveBanner = new TextBuilder("Next move")
            .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 38))
            .setColor(Color.SPRINGGREEN)
            .build();

    /**
     * Creates the base framework of the game.
     */
    GraphicsEngine() {
        StackPane.setMargin(nextMoveBanner, new Insets(2, 0, 0, 5));
        StackPane.setAlignment(nextMoveBanner, Pos.TOP_RIGHT);
        StackPane.setMargin(gameTitleBanner, new Insets(20, 0, 0, 0));
        StackPane.setAlignment(gameTitleBanner, Pos.TOP_CENTER);
        StackPane.setMargin(timeElapsedBanner, new Insets(2, 0, 0, 5));
        StackPane.setAlignment(timeElapsedBanner, Pos.TOP_LEFT);
    }

    /**
     * Specifies the root to which the nodes are added.
     *
     * @param root The root of the pane to which the nodes should be added to.
     */
    void setRoot(StackPane root) {
        root.getChildren().addAll(nextMoveBanner, gameTitleBanner, timeElapsedBanner);
    }
}
