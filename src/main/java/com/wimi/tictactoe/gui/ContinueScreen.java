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
import com.wimi.tictactoe.client.game.mechanics.GameEngine;
import com.wimi.tictactoe.util.Console;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description Continue gui screen where user can switch to a previously saved game.
 */
public class ContinueScreen {

    private final String directory = System.getenv("TEMP") + "\\.tictactoe\\saves\\";
    private final ArrayList<ContextMenu> contextList = new ArrayList<>();
    private final ArrayList<Button> gameButtonsList = new ArrayList<>();
    private final ArrayList<MenuItem> menuJoinsList = new ArrayList<>();
    private final ArrayList<MenuItem> menuRenameList = new ArrayList<>();
    private final ArrayList<MenuItem> menuDeleteList = new ArrayList<>();
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private final VBox scrollVBox = new VBox(5);
    private final Stage popupStage = new Stage();
    private final Pane popupRoot = new Pane();
    private final Scene popupScene = new Scene(popupRoot, 500, 300);
    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 1366, 768);
    private File[] currentFiles;

    public ContinueScreen() {
        refreshExistingGames(); // Get the existing games in the saves directory.

        // Setup the popup window.
        popupStage.setScene(popupScene);
        popupStage.setTitle("Confirm changes?");
        popupStage.setResizable(false);
        Image confirmationIcon = new Image(App.class.getResourceAsStream("/assets/images/confirmationIcon.png"), 256, 256, false, true);
        popupStage.getIcons().add(confirmationIcon);
        popupStage.focusedProperty().addListener(this::focusChanged);

        Button goBack = new ButtonBuilder("Go back")
                .setPrefHeight(40)
                .setPrefWidth(250)
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
        Button deleteAll = new ButtonBuilder("Delete all")
                .setPrefHeight(40)
                .setPrefWidth(250)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: gold; -fx-text-fill: blue;")
                .onMouseClick(event -> {
                    if (currentFiles.length == 0) Console.log("No game files to delete.");
                    for (File currentFile : currentFiles) {
                        if (currentFile.getAbsolutePath().endsWith(".nc"))
                            deleteGameFile(currentFile);
                    }

                    refreshExistingGames();
                })
                .build();

        Text gameBanner = new TextBuilder("Tic Tac Toe")
                .setColor(Color.SLATEGREY)
                .setFont(Font.font("Arial", FontPosture.ITALIC, 36))
                .build();

        ScrollPane scrollPane = new ScrollPane(scrollVBox);
        scrollPane.setPannable(true);
        scrollPane.setMaxSize(600, 400);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVmax(Integer.MAX_VALUE);
        root.setCenter(scrollPane);

        HBox hBox = new HBox(25);
        hBox.getChildren().addAll(goBack, deleteAll);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 0, 150, 0));
        root.setBottom(hBox);

        VBox titleWrapper = new VBox(); // Using a wrapper because Text does not have a way to set alignment.
        titleWrapper.getChildren().add(gameBanner);
        titleWrapper.setAlignment(Pos.TOP_CENTER);
        titleWrapper.setPadding(new Insets(40, 0, 0, 0));
        root.setTop(titleWrapper);

        scene.getStylesheets().add(this.getClass().getResource("/assets/Background.css").toExternalForm());
        App.getStage().setHeight(App.getStage().getHeight());
        App.getStage().setWidth(App.getStage().getWidth());
        NoughtsAndCrosses.createSceneBackground(root);
    }

    private void refreshExistingGames() {
        currentFiles = new File(directory).listFiles();

        gameButtonsList.clear();
        menuJoinsList.clear();
        menuRenameList.clear();
        menuDeleteList.clear();
        contextList.clear();
        scrollVBox.getChildren().clear();

        Console.log("Scanning for existing game files");

        if (currentFiles != null) {
            for (int i = 0; i < currentFiles.length; i++) {
                if (currentFiles[i].getAbsolutePath().endsWith(".nc")) {
                    Console.log("Found a game file named " + currentFiles[i].getName());
                    int finalI = i;

                    menuJoinsList.add(new MenuItem("Join game"));
                    menuRenameList.add(new MenuItem("Rename game"));
                    menuDeleteList.add(new MenuItem("Delete game"));
                    contextList.add(new ContextMenu());

                    menuJoinsList.get(i).setOnAction(event -> {
                        GameEngine gameEngine = new GameEngine(currentFiles[finalI]);
                        App.getStage().setScene(gameEngine.getScene());
                    });
                    menuRenameList.get(i).setOnAction(event -> showRenameConfirmationPopup(currentFiles[finalI]));
                    menuDeleteList.get(i).setOnAction(event -> showDeleteConfirmationPopup(currentFiles[finalI]));

                    contextList.get(i).getItems().addAll(menuJoinsList.get(i), menuRenameList.get(i), menuDeleteList.get(i));
                    gameButtonsList.add(new ButtonBuilder(currentFiles[i].getName().substring(0, currentFiles[i].getName().length() - 3))
                            .setContextMenu(contextList.get(finalI))
                            .setPrefHeight(40)
                            .setPrefWidth(580)
                            .setFill(128, 203, 196)
                            .onMouseClick(mouseEvent -> {
                                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                                    GameEngine gameEngine = new GameEngine(currentFiles[finalI]);
                                    App.getStage().setScene(gameEngine.getScene());
                                }
                            })
                            .build());
                } else
                    Console.log("Warning: Found a strange file which does not have the usual game file extension at the saves directory.");
            }

            if (currentFiles.length == 0) Console.log("No game files found at the given path at " + directory);
            else scrollVBox.getChildren().addAll(this.gameButtonsList);
        }
    }

    /**
     * Shows the delete confirmation popup.
     *
     * @param file The file selected for deletion.
     */
    private void showDeleteConfirmationPopup(File file) {
        Text commandMessage = new TextBuilder("Are you sure you want to delete " + file.getName())
                .setLayoutX(popupScene.getWidth() / 2 - 180)
                .setLayoutY(popupScene.getHeight() / 2 - 50)
                .setFont(Font.font("Arial", FontPosture.REGULAR, 18))
                .build();
        Button confirmationButton = new ButtonBuilder("Confirm")
                .setPrefWidth(100)
                .setLayoutX(popupScene.getWidth() / 2 - 150)
                .setLayoutY(popupScene.getHeight() / 2 + 50)
                .onMouseClick(event -> {
                    deleteGameFile(file);
                    popupStage.close();
                    refreshExistingGames();
                })
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: gold; -fx-text-fill: green;")
                .build();
        Button cancellationButton = new ButtonBuilder("Cancel")
                .setPrefWidth(100)
                .setLayoutX(popupScene.getWidth() / 2 + 50)
                .setLayoutY(popupScene.getHeight() / 2 + 50)
                .onMouseClick(event -> popupStage.close())
                .setTextColor(Color.RED)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: aqua; -fx-text-fill: red;")
                .build();

        popupRoot.getChildren().add(commandMessage);
        popupRoot.getChildren().addAll(confirmationButton, cancellationButton);

        popupStage.show();
    }

    private void showRenameConfirmationPopup(File file) {
        TextField renamedTextField = new TextField();
        renamedTextField.setLayoutX(popupScene.getWidth() / 2 - 100);
        renamedTextField.setLayoutY(popupScene.getHeight() / 2 - 25);
        renamedTextField.setPrefWidth(200);

        Text commandMessage = new TextBuilder("What do you want to rename this file to?")
                .setLayoutX(popupScene.getWidth() / 2 - 180)
                .setLayoutY(popupScene.getHeight() / 2 - 50)
                .setFont(Font.font("Arial", FontPosture.REGULAR, 18))
                .build();
        Text notificationText = new TextBuilder("")
                .setLayoutX(popupScene.getWidth() / 2 - 160)
                .setLayoutY(popupScene.getHeight() / 2 + 40)
                .setFont(Font.font("Arial", FontPosture.REGULAR, 20))
                .setColor(Color.RED)
                .build();
        Button confirmationButton = new ButtonBuilder("Confirm")
                .setPrefWidth(100)
                .setLayoutX(popupScene.getWidth() / 2 - 150)
                .setLayoutY(popupScene.getHeight() / 2 + 50)
                .onMouseClick(event -> {
                    if (renamedTextField.getText().isEmpty()) {
                        notificationText.setText("Name of the game cannot be empty.");
                        service.schedule(() -> notificationText.setText(""), 1024, TimeUnit.MILLISECONDS);
                    } else {
                        renameGameFile(file, renamedTextField.getText());
                        refreshExistingGames();
                        popupStage.close();
                    }
                })
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: gold; -fx-text-fill: green;")
                .build();
        Button cancellationButton = new ButtonBuilder("Cancel")
                .setPrefWidth(100)
                .setLayoutX(popupScene.getWidth() / 2 + 50)
                .setLayoutY(popupScene.getHeight() / 2 + 50)
                .onMouseClick(event -> popupStage.close())
                .setTextColor(Color.RED)
                .setCssScript("-jfx-button-type: RAISED; -fx-background-color: aqua; -fx-text-fill: red;")
                .build();

        popupRoot.getChildren().addAll(commandMessage, notificationText); // Texts
        popupRoot.getChildren().addAll(confirmationButton, cancellationButton, renamedTextField); // Buttons and other necessary text fields.

        popupStage.show();
    }

    /**
     * Deletes a file.
     *
     * @param file The file to delete.
     */
    private void deleteGameFile(File file) {
        if (file.exists()) {
            boolean delete = file.delete();

            if (delete) {
                Console.log("Deleted the file named " + file.getName());
            } else
                Console.log("Could not delete the existing game file.");
        } else
            Console.log("Th game file does not exist.");
    }

    /**
     * Renames a file.
     *
     * @param file            The file to perform rename operation on.
     * @param renamedFileName The name to which the file should be renamed to.
     */
    private void renameGameFile(File file, String renamedFileName) {
        File renamedFile = new File(directory + renamedFileName + ".nc");

        if (file.exists() && !renamedFile.exists()) {
            Console.log("Renaming " + file.getName());
            boolean rename = file.renameTo(renamedFile);
            if (rename)
                Console.log("File renamed to " + renamedFileName);
        } else if (!file.exists())
            Console.log("No such game file exists with the given name");
        else if (renamedFile.exists())
            Console.log("A file named " + renamedFileName + " already exists at the given path");
    }

    /**
     * @Description If the popup window is hidden or minimized, the window is closed automatically without any confirmation.
     */
    @SuppressWarnings("unused")
    private void focusChanged(ObservableValue<? extends Boolean> property, Boolean wasFocused, Boolean isFocused) {
        if (wasFocused && !isFocused) {
            popupStage.close();
            popupRoot.getChildren().clear();
        }
    }

    public Scene getScene() {
        return scene;
    }
}