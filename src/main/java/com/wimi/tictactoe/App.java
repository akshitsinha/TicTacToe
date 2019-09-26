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

package com.wimi.tictactoe;

import com.wimi.tictactoe.builders.ButtonBuilder;
import com.wimi.tictactoe.builders.TextBuilder;
import com.wimi.tictactoe.client.NoughtsAndCrosses;
import com.wimi.tictactoe.client.game.mechanics.AudioEngine;
import com.wimi.tictactoe.client.game.writers.DirectoryCreator;
import com.wimi.tictactoe.gui.ContinueScreen;
import com.wimi.tictactoe.gui.CreationScreen;
import com.wimi.tictactoe.gui.OptionsScreen;
import com.wimi.tictactoe.util.Console;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;

/**
 * Rudimentary version of Tic Tac Toe.
 *
 * @author Akshit Sinha
 */
public class App extends Application {

    private static final StackPane root = new StackPane();
    private static final Scene scene = new Scene(root, 1366, 768);
    private static Stage stage;
    private final Image appIcon = new Image(App.class.getResourceAsStream("/assets/images/ApplicationIcon.png"), 256, 256, true, false);
    private final DirectoryCreator directoryCreator = new DirectoryCreator();
    private final Instant startTime = Instant.now();
    private final Text gameBanner = new TextBuilder("Tic Tac Toe")
            .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 40))
            .setColor(Color.SLATEGRAY)
            .build();
    private final Button startButton = new ButtonBuilder("New Game")
            .setPrefWidth(200)
            .setCssScript("-jfx-button-type: RAISED; -fx-background-color: red; -fx-text-fill: white;")
            .build();
    private final Button continueGameButton = new ButtonBuilder("Continue game")
            .setPrefWidth(200)
            .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #760d84; -fx-text-fill: white;")
            .build();
    private final Button exitGameButton = new ButtonBuilder("Exit game")
            .setPrefWidth(200)
            .setCssScript("-jfx-button-type: RAISED; -fx-background-color: blue; -fx-text-fill: white;")
            .build();
    private final Button optionsGameButton = new ButtonBuilder("Options")
            .setPrefWidth(200)
            .setCssScript("-jfx-button-type: RAISED; -fx-background-color: #c20e3e; -fx-text-fill: white;")
            .build();

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    @Override
    public void init() {
        Console.log("Application started.");
        Console.log("Java Version: " + System.getProperty("java.version"));
        directoryCreator.createDirectories(); // Creates game directories.
        NoughtsAndCrosses.getWriter().init(); // Initializes game settings.
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setScene(getScene());
        stage.setTitle("Tic Tac Toe");
        stage.getIcons().add(appIcon);
        stage.setResizable(true);

        startButton.setOnAction(event -> {
            Console.log("User switches to a new game start screen.");
            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playClickSound();

            CreationScreen gameScreen = new CreationScreen();
            stage.setScene(gameScreen.getScene());
        });

        continueGameButton.setOnAction(event -> {
            Console.log("User switches to continue screen.");
            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playClickSound();

            ContinueScreen gameScreen = new ContinueScreen();
            stage.setScene(gameScreen.getScene());
        });

        optionsGameButton.setOnAction(event -> {
            Console.log("User switches to options screen.");
            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playClickSound();

            OptionsScreen gameScreen = new OptionsScreen();
            stage.setScene(gameScreen.getScene());
        });

        exitGameButton.setOnAction(event -> {
            if (NoughtsAndCrosses.getWriter().getSFX()) AudioEngine.playGlassClickSound();

            stage.close();
            if (!stage.isShowing()) {
                primaryStage.close();
                Console.log("Application terminated.");
            }
        });

        VBox vBox = new VBox(18);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(startButton, continueGameButton, optionsGameButton, exitGameButton);

        NoughtsAndCrosses.createSceneBackground(root);
        StackPane.setAlignment(gameBanner, Pos.TOP_CENTER);
        StackPane.setMargin(gameBanner, new Insets(20, 0, 0, 0));
        root.getChildren().addAll(vBox, gameBanner);

        stage.setMinHeight(700);
        stage.setMinWidth(1000);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Console.log("Application ran for " + Duration.between(startTime, Instant.now()).toMillis() + " milliseconds.");
        System.exit(0);
        super.stop();
    }
}
