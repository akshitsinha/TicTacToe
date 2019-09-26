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

import com.wimi.tictactoe.App;
import com.wimi.tictactoe.builders.ButtonBuilder;
import com.wimi.tictactoe.builders.TextBuilder;
import com.wimi.tictactoe.client.NoughtsAndCrosses;
import com.wimi.tictactoe.client.game.mechanics.AudioEngine;
import com.wimi.tictactoe.client.game.writers.GameConfigurator;
import com.wimi.tictactoe.client.game.writers.GameCreator;
import com.wimi.tictactoe.util.Console;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description Gui screen for the creation of new games.
 */
public class CreationScreen {

    private final String directory = System.getenv("TEMP") + "//.tictactoe//saves//";
    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 1366, 768);
    private final TextField nameField = new TextField(); // Text Field which contains the name of the game.
    private final GameCreator assetsCreator = new GameCreator();
    private final Text notification = new TextBuilder("")
            .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 30))
            .setColor(Color.RED)
            .build();
    private final Button createGame = new ButtonBuilder("Create")
            .setPrefWidth(200)
            .setCssScript("-jfx-button-type: RAISED; -fx-background-color: gold; -fx-text-fill: blue;")
            .setTextColor(Color.BLUE)
            .onMouseClick(event -> {
                if (nameField.getText().isEmpty()) notificator("Name of the game cannot be empty!");
                else if (nameField.getText().contains(" ")) notificator("Name of the game cannot contain whitespaces!");
                else {
                    File file = new File(directory + nameField.getText() + ".nc");

                    if (file.exists()) notificator("A game with that name already exists.");
                    else if (nameField.getText().length() > 32) notificator("Name cannot be more than 32 characters.");
                    else {
                        assetsCreator.createGameAssets(nameField.getText());

                        if (file.exists()) {
                            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playClickSound();

                            GameConfigurator configurator = new GameConfigurator(file);
                            App.getStage().setScene(configurator.getScene());
                        } else {
                            Console.log("Could not create game file. Try again?");
                            notificator("Make sure the name has alphabets and numbers only.");
                        }
                    }
                }
            })
            .build();

    public CreationScreen() {
        Button goBack = new ButtonBuilder("Go back")
                .setPrefWidth(200)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #5b455e; -fx-text-fill: #eabead;")
                .onMouseClick(event -> {
                    Console.log("User goes back to Home screen. Thru " + this.getClass().getSimpleName());
                    App.getStage().setScene(App.getScene());
                    App.getStage().setHeight(App.getStage().getHeight());
                    App.getStage().setWidth(App.getStage().getWidth());
                    if (NoughtsAndCrosses.getWriter().getSFX())
                        AudioEngine.playGlassClickSound();
                })
                .build();
        Text gameBanner = new TextBuilder("Tic Tac Toe")
                .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 36))
                .setColor(Color.SLATEGRAY)
                .build();

        VBox rootVBox = new VBox(50);
        rootVBox.setAlignment(Pos.CENTER);
        rootVBox.getChildren().add(nameField);
        nameField.setMaxWidth(400);

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(createGame, goBack);
        rootVBox.getChildren().add(hBox); // HBox for other scene nodes.
        rootVBox.getChildren().add(notification); // Text for notifications
        root.setCenter(rootVBox);

        VBox textWrapper = new VBox(); // Using a wrapper because Text does not have a way to set alignment.
        textWrapper.getChildren().add(gameBanner);
        textWrapper.setAlignment(Pos.TOP_CENTER);
        textWrapper.setPadding(new Insets(50, 0, 0, 0));
        root.setTop(textWrapper);

        NoughtsAndCrosses.createSceneBackground(root);
        App.getStage().setHeight(App.getStage().getHeight());
        App.getStage().setWidth(App.getStage().getWidth());
    }

    /**
     * Notifies a text message for a few seconds.
     */
    private void notificator(String text) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        notification.setText(text);
        createGame.setDisable(true);
        nameField.setText("");

        executorService.schedule(() -> {
            createGame.setDisable(false);
            notification.setText("");
            executorService.shutdown();
        }, 1400, TimeUnit.MILLISECONDS);
    }

    public Scene getScene() {
        return scene;
    }
}
