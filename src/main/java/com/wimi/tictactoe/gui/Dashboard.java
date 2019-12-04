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
import com.wimi.tictactoe.client.game.Structure;
import com.wimi.tictactoe.client.game.mechanics.AudioEngine;
import com.wimi.tictactoe.util.Console;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description Dashboard or a Statistics page for game progress and result.
 */
@SuppressWarnings("unchecked")
public class Dashboard extends Structure {

    private final TilePane root = new TilePane();
    private final Scene scene = new Scene(root, 1366, 768);

    public Dashboard(File file) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try (FileReader reader = new FileReader(file)) {
            jsonObject = (JSONObject) new JSONParser().parse(reader);
            jsonArray = (JSONArray) jsonObject.get("nodes");
            Console.log("Game status: " + jsonObject.get("nodes"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONArray timesOfX = new JSONArray();
        JSONArray timesOfO = new JSONArray();
        if (jsonObject.containsKey("timeX") && jsonObject.containsKey("timeO")) {
            timesOfX = (JSONArray) jsonObject.get("timeX");
            Console.log("Time taken by each move made by X is " + timesOfX);

            timesOfO = (JSONArray) jsonObject.get("timeO");
            Console.log("Time taken by each move made by O is " + timesOfO);
        } else Console.log("Time taken by each move is missing.");

        Text winnerBanner = new TextBuilder("Winner is ")
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 108))
                .setColor(Color.SEASHELL)
                .build();
        Text winnerText = new TextBuilder(getJsonWinnerHeader(jsonObject))
                .setFont(Font.font("Segoe UI", FontPosture.ITALIC, 90))
                .setColor(Color.KHAKI)
                .build();

        VBox winnerVBox = new VBox();
        winnerVBox.setPadding(new Insets(0, 0, 20, 60));
        winnerVBox.getChildren().addAll(winnerBanner, winnerText);

        Text elapsedTimeBanner = new TextBuilder("Total time played: " + String.format("%02d:%02d:%02d", (long) jsonObject.get("ElapsedTime") / 3600, ((long) jsonObject.get("ElapsedTime") % 3600) / 60, (long) jsonObject.get("ElapsedTime") % 60))
                .setColor(Color.BURLYWOOD)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 30))
                .build();
        Text opponentBanner = new TextBuilder("Opponent: " + jsonObject.get("opponent").toString().substring(0, 1).toUpperCase() + jsonObject.get("opponent").toString().substring(1))
                .setColor(Color.GREENYELLOW)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 30))
                .build();
        Text gameModeBanner = new TextBuilder("Game mode: " + getJsonGameModeHeader(jsonObject))
                .setColor(Color.GREENYELLOW)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 30))
                .build();
        Text totalTimeX = new TextBuilder("Total time taken by X: " + getTotalTime(timesOfX))
                .setColor(Color.LIGHTBLUE)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 30))
                .build();
        Text totalTimeO = new TextBuilder("Total time taken by O: " + getTotalTime(timesOfO))
                .setColor(Color.RED)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 30))
                .build();
        Button goBackButton = new ButtonBuilder("Go back to Home Screen")
                .setPrefHeight(250)
                .setPrefWidth(250)
                .setStyle("-jfx-button-type: RAISED; -fx-background-color: #32879c; -fx-text-fill: #eabead;")
                .setFont(Font.font("Arial", FontPosture.REGULAR, 18))
                .onMouseClick(event -> {
                    Console.log("User goes back to Home screen. Thru " + this.getClass().getSimpleName());
                    App.getStage().setScene(App.getScene());
                    App.getStage().setHeight(App.getStage().getHeight());
                    App.getStage().setWidth(App.getStage().getWidth());
                    if (NoughtsAndCrosses.getWriter().getSFX())
                        AudioEngine.playGlassClickSound();
                })
                .build();

        VBox statsVBox = new VBox(10);
        statsVBox.getChildren().addAll(elapsedTimeBanner, opponentBanner, gameModeBanner, totalTimeX, totalTimeO);

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(0, 0, 0, 25));
        hBox.getChildren().addAll(statsVBox, goBackButton);

        XYChart.Series<String, Number> seriesX = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesO = new XYChart.Series<>();
        seriesX.setName("Times of X");
        seriesO.setName("Times of O");

        for (int i = 0; i < timesOfX.size(); i++)
            seriesX.getData().add(new XYChart.Data<>("Move " + (i + 1), (double) timesOfX.get(i)));

        for (int i = 0; i < timesOfO.size(); i++)
            seriesO.getData().add(new XYChart.Data<>("Move " + (i + 1), (double) timesOfO.get(i)));

        Tile chartTile = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .title("Time taken by each player on each move")
                .chartType(Tile.ChartType.LINE)
                .smoothing(true)
                .borderColor(Color.PALEVIOLETRED)
                .tooltipTimeout(500)
                .tilesFxSeries(
                        new TilesFXSeries<>(seriesX, Tile.BLUE,
                                new LinearGradient(0, 0, 0, 1,
                                        true, CycleMethod.NO_CYCLE,
                                        new Stop(0, Tile.BLUE),
                                        new Stop(1, Color.TRANSPARENT))),
                        new TilesFXSeries<>(seriesO, Tile.RED,
                                new LinearGradient(0, 0, 0, 1,
                                        true, CycleMethod.NO_CYCLE,
                                        new Stop(0, Tile.LIGHT_RED),
                                        new Stop(1, Color.TRANSPARENT)))
                )
                .minSize(scene.getWidth() / 2, 500)
                .maxSize(scene.getWidth() / 2, 500)
                .padding(new Insets(10))
                .build();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 0, 0, 120));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        for (int i = 0; i < 9; i++) {
            gridPane.addColumn(i % 3, new ButtonBuilder(jsonArray.get(i).toString())
                    .setFont(Font.font("Arial", FontWeight.BOLD, 24))
                    .setSize(120, 120)
                    .setDisabled(true)
                    .setStyle("-jfx-button-type: RAISED; -fx-background-color: gold; -fx-text-fill: blue;")
                    .build());
        }

        if (!jsonObject.get("winner").equals("NONE")) {
            int[] winningCells = new int[3];
            if (checkForWin(jsonArray, winningCells))
                for (int i = 0; i < 3; i++) gridPane.getChildren().get(winningCells[i]).setOpacity(1);
        }

        root.setPrefRows(2);
        root.setPrefColumns(2);
        root.getChildren().addAll(gridPane, chartTile);
        root.getChildren().addAll(winnerVBox, hBox);
        NoughtsAndCrosses.setSceneBackground(root);
    }

    private String getJsonWinnerHeader(JSONObject object) {
        if (nameOfMove(object.get("winner")).equals("NA")) return "None! Draw!";
        else return (nameOfMove(object.get("winner")));
    }

    private String getJsonGameModeHeader(JSONObject object) {
        if (object.get("mode").equals("untimed")) return "Unlimited Time";
        else if (object.get("mode").equals("timed")) return "Timed";
        else return null;
    }

    /**
     * Returns the total time taken by a move.
     *
     * @param array The JSON Array to take the elements from.
     * @return Total time taken.
     */
    private String getTotalTime(JSONArray array) {
        double totalTime = 0; // In seconds upto one decimal place.
        for (Object o : array) totalTime += Double.parseDouble(o.toString());
        totalTime = round(totalTime);

        if (totalTime < 60) return Math.round(totalTime) + "s";
        else if (totalTime <= 3600) return round(totalTime / 60) + "m";
        else return ">= 1hr";
    }

    public Scene getScene() {
        return scene;
    }
}
