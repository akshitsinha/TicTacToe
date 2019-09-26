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

package com.wimi.tictactoe.client.game.writers;

import com.wimi.tictactoe.App;
import com.wimi.tictactoe.builders.ButtonBuilder;
import com.wimi.tictactoe.builders.TextBuilder;
import com.wimi.tictactoe.client.NoughtsAndCrosses;
import com.wimi.tictactoe.client.game.mechanics.GameEngine;
import com.wimi.tictactoe.util.Console;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description Configures the newly created game file. Contains the GUI Scene.
 */
@SuppressWarnings("unchecked")
public class GameConfigurator {

    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 1366, 768);
    private final JSONObject jsonObject = new JSONObject();

    public GameConfigurator(File file) {
        Console.log("Configuration of the new game file named " + file.getName().substring(0, file.getName().length() - 3) + " initiated.");

        VBox buttonWrapper = new VBox();
        Button cancelAndExit = new ButtonBuilder("Cancel and exit")
                .setTextColor(Color.RED)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #636989; -fx-text-fill: white;")
                .setPrefWidth(200)
                .onMouseClick(event -> exit(file))
                .build();
        buttonWrapper.getChildren().add(cancelAndExit);
        buttonWrapper.setAlignment(Pos.BOTTOM_CENTER);
        buttonWrapper.setPadding(new Insets(0, 0, 50, 0));
        root.setBottom(buttonWrapper);

        Button gameSelectHuman = new ButtonBuilder("Play against Human?") // R1 C1
                .setPrefWidth(200)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #760d84; -fx-text-fill: white;")
                .build();
        Button gameSelectComp = new ButtonBuilder("Play against computer?") // R1 C2
                .setPrefWidth(200)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #895e18; -fx-text-fill: white;")
                .build();

        gameSelectComp.setDisable(true);
        // Playing against computer is disabled at the moment but may be available in the future.

        Button timedGame = new ButtonBuilder("Timed Mode") // R2 C1
                .setPrefWidth(200)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #760d84; -fx-text-fill: white;")
                .setDisabled(true)
                .build();
        Button notTimedGame = new ButtonBuilder("No Time Limit Mode") // R2 C2
                .setPrefWidth(200)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #895e18; -fx-text-fill: white;")
                .setDisabled(true)
                .build();

        VBox typeSelection = new VBox(35);
        typeSelection.getChildren().addAll(gameSelectHuman, gameSelectComp);
        typeSelection.setAlignment(Pos.CENTER);

        VBox timedSelection = new VBox(35);
        timedSelection.getChildren().addAll(notTimedGame, timedGame);
        timedSelection.setAlignment(Pos.CENTER);
        timedSelection.setOpacity(0.1);

        root.setCenter(typeSelection);

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(10);
        fadeTransition.setToValue(0.1);
        fadeTransition.setDuration(Duration.millis(800));
        fadeTransition.setNode(typeSelection);
        fadeTransition.setOnFinished(event -> {
            root.getChildren().remove(typeSelection);
            root.setCenter(timedSelection);

            fadeTransition.setNode(timedSelection);
            fadeTransition.setFromValue(0.1);
            fadeTransition.setToValue(10);
            fadeTransition.play();
            fadeTransition.setOnFinished(null);
        });

        gameSelectHuman.setOnAction(event -> {
            fadeTransition.play();

            jsonObject.put("opponent", "human");
            gameSelectHuman.setDisable(true);
            gameSelectComp.setDisable(true);
            timedGame.setDisable(false);
            notTimedGame.setDisable(false);
        });

        gameSelectComp.setOnAction(event -> {
            fadeTransition.play();

            jsonObject.put("opponent", "computer");
            gameSelectHuman.setDisable(true);
            gameSelectComp.setDisable(true);
            timedGame.setDisable(false);
            notTimedGame.setDisable(false);
        });

        timedGame.setOnAction(event -> {
            jsonObject.put("mode", "timed");
            gameSelectHuman.setDisable(true);
            gameSelectComp.setDisable(true);
            App.getStage().setOnCloseRequest(null);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            GameEngine engine = new GameEngine(file);
            App.getStage().setScene(engine.getScene());
        });

        notTimedGame.setOnAction(event -> {
            jsonObject.put("mode", "untimed");
            gameSelectHuman.setDisable(true);
            gameSelectComp.setDisable(true);
            App.getStage().setOnCloseRequest(null);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            GameEngine engine = new GameEngine(file);
            App.getStage().setScene(engine.getScene());
        });

        VBox textWrapper = new VBox(0);
        Text gameHeader = new TextBuilder("Tic Tac Toe")
                .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 36))
                .setColor(Color.SLATEGRAY)
                .build();
        textWrapper.getChildren().add(gameHeader);
        textWrapper.setAlignment(Pos.TOP_CENTER);
        textWrapper.setPadding(new Insets(25, 0, 0, 0));
        root.setTop(textWrapper);

        App.getStage().setOnCloseRequest(event -> exit(file));
        NoughtsAndCrosses.createSceneBackground(root);
    }

    /**
     * Deletes a file.
     *
     * @param file The file to delete.
     */
    private void deleteFile(File file) {
        if (file.exists()) {
            boolean delete = file.delete();

            if (delete) Console.log("Deleted the file named " + file.getName());
            else Console.log("Unable to delete the file.");
        } else
            Console.log("The game file does not exist.");
    }

    /**
     * Exits the game initialization process.
     *
     * @param file File to exit and delete.
     */
    private void exit(File file) {
        Console.log("Exiting game initialization process and switching to the home screen.");
        jsonObject.clear();
        App.getStage().setScene(App.getScene());
        App.getStage().setOnCloseRequest(null);
        deleteFile(file);
    }

    public Scene getScene() {
        return scene;
    }
}
