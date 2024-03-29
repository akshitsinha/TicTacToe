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

package com.wimi.tictactoe.gui;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.wimi.tictactoe.App;
import com.wimi.tictactoe.builders.ButtonBuilder;
import com.wimi.tictactoe.builders.SliderBuilder;
import com.wimi.tictactoe.builders.TextBuilder;
import com.wimi.tictactoe.client.NoughtsAndCrosses;
import com.wimi.tictactoe.client.game.mechanics.AudioEngine;
import com.wimi.tictactoe.util.Console;
import com.wimi.tictactoe.util.Levels;
import com.wimi.tictactoe.util.Themes;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @Description Options gui screen where the game settings are configured.
 */
public class OptionsScreen {

    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 1366, 768);
    private final JFXToggleButton theme = new JFXToggleButton();
    private final JFXToggleButton SFX = new JFXToggleButton();

    public OptionsScreen() {
        Button goBack = new ButtonBuilder("Go back")
                .setPrefWidth(200)
                .setStyle("-jfx-button-type: RAISED; -fx-background-color: #5b455e; -fx-text-fill: #eabead;")
                .onMouseClick(event -> {
                    Console.log("User goes back to Home screen. Thru " + this.getClass().getSimpleName());
                    App.getStage().setScene(App.getScene());
                    App.getStage().setHeight(App.getStage().getHeight());
                    App.getStage().setWidth(App.getStage().getWidth());
                    if (NoughtsAndCrosses.getWriter().getSFX())
                        AudioEngine.playGlassClickSound();
                })
                .build();

        Text themeHeader = new TextBuilder("Theme")
                .setFont(Font.font("Arial", FontPosture.ITALIC, 24))
                .setColor(Color.SLATEGRAY)
                .build();
        theme.setPrefWidth(50);
        theme.setOnMouseClicked(event -> {
            Object THEME = NoughtsAndCrosses.getWriter().getJsonKey("theme");
            if (THEME.equals(Themes.LIGHT.toString())) { // Switch to Dark. (true)
                NoughtsAndCrosses.getWriter().setJsonKey("theme", Themes.DARK.toString());
                theme.setSelected(true);
                Console.log("Set theme JSON value to " + NoughtsAndCrosses.getWriter().getJsonKey("theme"));
            } else if (THEME.equals(Themes.DARK.toString())) { // Switch to Light. (false)
                NoughtsAndCrosses.getWriter().setJsonKey("theme", Themes.LIGHT.toString());
                theme.setSelected(false);
                Console.log("Set theme JSON value to " + NoughtsAndCrosses.getWriter().getJsonKey("theme"));
            } else
                Console.log("Error during changing theme JSON value. Current value: " + NoughtsAndCrosses.getWriter().getJsonKey("theme"));

            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playGlassClickSound();

            NoughtsAndCrosses.setSceneBackground(root);
            NoughtsAndCrosses.setSceneBackground(App.getRoot());
        });

        if (NoughtsAndCrosses.getWriter().getJsonKey("theme").equals(Themes.DARK.toString())) theme.setSelected(true);
        else if (NoughtsAndCrosses.getWriter().getJsonKey("theme").equals(Themes.LIGHT.toString()))
            theme.setSelected(false);

        HBox themeHBox = new HBox();
        themeHBox.setAlignment(Pos.CENTER);
        themeHBox.getChildren().addAll(themeHeader, theme);

        Text sfxHeader = new TextBuilder("SFX")
                .setFont(Font.font("Arial", FontPosture.ITALIC, 24))
                .setColor(Color.SLATEGRAY)
                .build();
        SFX.setPrefWidth(50);
        SFX.setOnMouseClicked(event -> {
            Object object = NoughtsAndCrosses.getWriter().getJsonKey("SFX");
            if (object.equals(true)) {
                NoughtsAndCrosses.getWriter().setJsonKey("SFX", false);
                SFX.setSelected(false);
                Console.log("Set SFX JSON value to " + NoughtsAndCrosses.getWriter().getJsonKey("SFX"));
            } else if (object.equals(false)) {
                NoughtsAndCrosses.getWriter().setJsonKey("SFX", true);
                SFX.setSelected(true);
                Console.log("Set SFX JSON value to " + NoughtsAndCrosses.getWriter().getJsonKey("SFX"));
            } else
                Console.log("Error during changing SFX JSON value. Current value: " + NoughtsAndCrosses.getWriter().getJsonKey("SFX"));

            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playGlassClickSound();
        });

        if (NoughtsAndCrosses.getWriter().getSFX()) SFX.setSelected(true);
        else SFX.setSelected(false);

        HBox sfxHBox = new HBox(20);
        sfxHBox.setAlignment(Pos.CENTER);
        sfxHBox.getChildren().addAll(sfxHeader, SFX);

        Text timeHeader = new TextBuilder("Max Time allowed in Timed Mode")
                .setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 24))
                .setColor(Color.SLATEGRAY)
                .build();
        JFXSlider timeSlider = new SliderBuilder(1, 15, (long) NoughtsAndCrosses.getWriter().getJsonKey("maxTime"))
                .setOrientation(Orientation.HORIZONTAL)
                .setWidth(400)
                .setCursor(Cursor.H_RESIZE)
                .setShowTickLabels(true)
                .build();
        timeSlider.setOnMouseReleased(event -> {
            NoughtsAndCrosses.getWriter().setJsonKey("maxTime", (int) Math.round(timeSlider.getValue()));
            Console.log("Set maxTime JSON value to " + NoughtsAndCrosses.getWriter().getJsonKey("maxTime") + " seconds.");
        });

        VBox maxTimeVBox = new VBox(20);
        maxTimeVBox.setAlignment(Pos.CENTER);
        maxTimeVBox.getChildren().addAll(timeSlider, timeHeader);

        Text difficultyHeader = new TextBuilder("Difficulty: ")
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 24))
                .setColor(Color.LIGHTGREY)
                .build();

        Label difficultyLabel = new Label(NoughtsAndCrosses.getWriter().getJsonKey("difficulty").toString());
        difficultyLabel.setFont(Font.font("Segoe UI", 24));

        if (NoughtsAndCrosses.getWriter().getJsonKey("difficulty").equals(Levels.EASY.toString()))
            difficultyLabel.setTextFill(Color.SPRINGGREEN);
        else if (NoughtsAndCrosses.getWriter().getJsonKey("difficulty").equals(Levels.INTERMEDIATE.toString()))
            difficultyLabel.setTextFill(Color.ORANGE);
        else if (NoughtsAndCrosses.getWriter().getJsonKey("difficulty").equals(Levels.IMPOSSIBLE.toString()))
            difficultyLabel.setTextFill(Color.RED);
        else difficultyLabel.setTextFill(Color.BLACK);

        HBox diffHBox = new HBox(5);
        diffHBox.getChildren().addAll(difficultyHeader, difficultyLabel);
        diffHBox.setAlignment(Pos.CENTER);

        JFXSlider difficultySlider = new SliderBuilder(1, 3, getDifficultyLevel())
                .setOrientation(Orientation.HORIZONTAL)
                .setWidth(300)
                .setCursor(Cursor.H_RESIZE)
                .setShowTickLabels(true)
                .build();

        difficultySlider.setOnMouseReleased(event -> setDifficultyLevel((int) Math.round(difficultySlider.getValue())));
        difficultySlider.valueProperty().addListener((observable, oldValue, newValue) -> updateDiffTextLabel(Math.round(newValue.doubleValue()), difficultyLabel));

        VBox difficultiesVBox = new VBox(5);
        difficultiesVBox.getChildren().addAll(difficultySlider, diffHBox);
        difficultiesVBox.setAlignment(Pos.CENTER);

        VBox rootVBox = new VBox(15);
        rootVBox.getChildren().addAll(themeHBox, sfxHBox, maxTimeVBox, difficultiesVBox);
        rootVBox.setAlignment(Pos.CENTER);
        root.setCenter(rootVBox);

        VBox textWrapper = new VBox(0);
        Text gameHeader = new TextBuilder("Tic Tac Toe")
                .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 36))
                .setColor(Color.SLATEGRAY)
                .build();
        textWrapper.getChildren().add(gameHeader);
        textWrapper.setAlignment(Pos.TOP_CENTER);
        textWrapper.setPadding(new Insets(25, 0, 0, 0));
        root.setTop(textWrapper);

        VBox buttonWrapper = new VBox(0);
        buttonWrapper.getChildren().add(goBack);
        buttonWrapper.setAlignment(Pos.BASELINE_CENTER);
        buttonWrapper.setPadding(new Insets(0, 0, 100, 0));
        root.setBottom(buttonWrapper);

        App.getStage().setHeight(App.getStage().getHeight());
        App.getStage().setWidth(App.getStage().getWidth());
        NoughtsAndCrosses.setSceneBackground(root);
        scene.getStylesheets().add(this.getClass().getResource("/assets/Slider.css").toExternalForm());
    }

    private void updateDiffTextLabel(double observableValue, Label label) {
        if (observableValue == 1) {
            label.setText("Easy");
            label.setTextFill(Color.SPRINGGREEN);
        } else if (observableValue == 2) {
            label.setText("Intermediate");
            label.setTextFill(Color.ORANGE);
        } else if (observableValue == 3) {
            label.setText("Impossible");
            label.setTextFill(Color.RED);
        } else {
            label.setText("ERROR:" + observableValue);
            label.setTextFill(Color.BLACK);
        }
    }

    private int getDifficultyLevel() {
        if (!NoughtsAndCrosses.getWriter().containsKey("difficulty"))
            Console.log("Could not find the 'difficulty' JSON key in the settings JSON");
        else if (NoughtsAndCrosses.getWriter().getJsonKey("difficulty").equals(Levels.EASY.toString())) return 1;
        else if (NoughtsAndCrosses.getWriter().getJsonKey("difficulty").equals(Levels.INTERMEDIATE.toString()))
            return 2;
        else if (NoughtsAndCrosses.getWriter().getJsonKey("difficulty").equals(Levels.IMPOSSIBLE.toString())) return 3;

        return 0;
    }

    private void setDifficultyLevel(int difficultyLevel) {
        switch (difficultyLevel) {
            case 1:
                NoughtsAndCrosses.getWriter().setJsonKey("difficulty", Levels.EASY.toString());
                break;
            case 2:
                NoughtsAndCrosses.getWriter().setJsonKey("difficulty", Levels.INTERMEDIATE.toString());
                break;
            case 3:
                NoughtsAndCrosses.getWriter().setJsonKey("difficulty", Levels.IMPOSSIBLE.toString());
                break;
            default:
                throw new IllegalStateException("Unknown difficulty level");
        }

        Console.log("Set 'difficulty' JSON value to " + NoughtsAndCrosses.getWriter().getJsonKey("difficulty"));
    }

    public Scene getScene() {
        return scene;
    }
}