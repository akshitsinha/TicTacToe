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

package com.wimi.tictactoe.client.game;

import com.wimi.tictactoe.App;
import com.wimi.tictactoe.builders.ButtonBuilder;
import com.wimi.tictactoe.builders.TextBuilder;
import com.wimi.tictactoe.client.NoughtsAndCrosses;
import com.wimi.tictactoe.client.game.mechanics.AudioEngine;
import com.wimi.tictactoe.util.Console;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

import java.io.File;
import java.io.FileReader;

/**
 * @Description Dashboard or a Statistics page for game progress and result.
 */
@SuppressWarnings("unchecked")
public class Dashboard {

    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 1366, 768);

    public Dashboard(File file) {
        JSONObject jsonObject = new JSONObject();
        try (FileReader reader = new FileReader(file)) {
            jsonObject = (JSONObject) new JSONParser().parse(reader);
            printNodes((JSONArray) jsonObject.get("nodes"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray timesOfX = new JSONArray();
        JSONArray timesOfO = new JSONArray();
        if (jsonObject.containsKey("timeX") && jsonObject.containsKey("timeO")) {
            timesOfX = (JSONArray) jsonObject.get("timeX");
            timesOfO = (JSONArray) jsonObject.get("timeO");
        } else Console.log("Times of each move are missing.");

        Text urbanName = new TextBuilder("Winner is " + Structure.nameOfMove(jsonObject.get("winner")) + ":")
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 36))
                .setColor(Color.rgb(167, 138, 167))
                .build(); // Urban name as in Nought or Cross.
        Text moveName = new TextBuilder(jsonObject.get("winner").toString())
                .setFont(Font.font("Segoe UI", FontWeight.BOLD, 36))
                .setColor(Color.rgb(167, 138, 167))
                .build(); // Actual move name like O and X.

        HBox winner = new HBox(20);
        winner.setAlignment(Pos.TOP_CENTER);
        winner.setPadding(new Insets(20, 0, 0, 0));
        winner.getChildren().addAll(urbanName, moveName);

        Text dashboardBanner = new TextBuilder("Dashboard")
                .setColor(Color.LAVENDER)
                .setCssScript("-fx-underline: true;")
                .setFont(Font.font("Segoe UI", FontWeight.BOLD, 44))
                .build();
        Text elapsedTimeBanner = new TextBuilder("Total elapsed time: " + String.format("%02d:%02d:%02d", (long) jsonObject.get("ElapsedTime") / 3600, ((long) jsonObject.get("ElapsedTime") % 3600) / 60, (long) jsonObject.get("ElapsedTime") % 60))
                .setColor(Color.LIGHTSLATEGRAY)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 36))
                .build();
        Text opponentBanner = new TextBuilder("Opponent: " + jsonObject.get("opponent").toString().substring(0, 1).toUpperCase() + jsonObject.get("opponent").toString().substring(1))
                .setColor(Color.GREENYELLOW)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 36))
                .build();
        Text gameModeBanner = new TextBuilder("Game mode: " + jsonObject.get("mode").toString().substring(0, 1).toUpperCase() + jsonObject.get("mode").toString().substring(1))
                .setColor(Color.GREENYELLOW)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 36))
                .build();
        Text totalTimeX = new TextBuilder("Total time taken by X: " + getTotalTime(timesOfX) + "s")
                .setColor(Color.GREENYELLOW)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 36))
                .build();
        Text totalTimeO = new TextBuilder("Total time taken by O: " + getTotalTime(timesOfO) + "s")
                .setColor(Color.BISQUE)
                .setFont(Font.font("Segoe UI", FontPosture.REGULAR, 36))
                .build();
        Button goBack = new ButtonBuilder("Go back to Home Screen")
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

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(dashboardBanner, winner, elapsedTimeBanner, opponentBanner, gameModeBanner, totalTimeX, totalTimeO, goBack);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        root.setLeft(vBox);

        XYChart.Series<String, Number> seriesX = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesO = new XYChart.Series<>();
        seriesX.setName("Times of X");
        seriesO.setName("Times of O");

        for (int i = 0; i < timesOfX.size(); i++) {
            seriesX.getData().add(new XYChart.Data<>("M" + (i + 1), (long) timesOfX.get(i)));
        }

        for (int i = 0; i < timesOfO.size(); i++) {
            seriesO.getData().add(new XYChart.Data<>("M" + (i + 1), (long) timesOfO.get(i)));
        }

        Tile chartTile = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .title("Time taken by each player on each move")
                .chartType(Tile.ChartType.AREA)
                .smoothing(true)
                .borderColor(Color.RED)
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
                .minSize(scene.getWidth() - 480, scene.getHeight())
                .maxSize(scene.getWidth() - 480, scene.getHeight())
                .padding(new Insets(10))
                .build();

        root.setRight(chartTile);
        NoughtsAndCrosses.createSceneBackground(root);
    }

    /**
     * Outputs the game nodes.
     *
     * @param jsonArray Array to get the nodes from.
     */
    private void printNodes(JSONArray jsonArray) {
        Console.log("Outputting the game nodes: ");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) System.out.println();
            System.out.print(jsonArray.get(i) + " ");
        }

        System.out.println();
    }

    /**
     * Returns the total time taken by a move.
     * Sum of all elements in a JSON array.
     *
     * @param array The JSON Array to take the elements from.
     * @return Total time.
     */
    private long getTotalTime(JSONArray array) {
        long totalTime = 0;
        for (Object o : array) {
            totalTime += Long.parseLong(o.toString());
        }

        return totalTime;
    }

    public Scene getScene() {
        return scene;
    }
}
