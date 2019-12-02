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

import javafx.scene.control.Button;
import org.json.simple.JSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Description Contains the basic methods used for running a Tic Tac Toe game and utils.
 */
public class Structure {

    /**
     * Checks if there is a win in the game.
     *
     * @param nodes Game Matrix to check for a win.
     */
    protected boolean checkForWin(Button[] nodes) {
        String[] moves = new String[nodes.length];
        for (int i = 0; i < moves.length; i++) moves[i] = nodes[i].getText();

        for (int i = 0; i < 9; i += 3) { // Check in rows
            if (equals(moves[i], moves[i + 1], moves[i + 2]) && !moves[i].equals(" ")) return true;
        }

        for (int i = 0; i < 3; i++) { // Check in columns
            if (equals(moves[i], moves[i + 3], moves[i + 6]) && !moves[i].equals(" ")) return true;
        }

        // Check Diagonal and Anti-Diagonal.
        if (equals(moves[0], moves[4], moves[8]) && !moves[0].equals(" ")) return true;
        else return equals(moves[2], moves[4], moves[6]) && !moves[2].equals(" ");
    }

    /**
     * Checks if there is a win in the game.
     *
     * @param array          JSON Array with game nodes to check for a win.
     * @param winningMoveIDs Integer array with cell IDs which caused a win.
     */
    protected boolean checkForWin(JSONArray array, int[] winningMoveIDs) {
        String[] moves = new String[array.size()];
        for (int i = 0; i < moves.length; i++) moves[i] = array.get(i).toString();

        for (int i = 0; i < 9; i += 3) { // Check in rows
            if (equals(moves[i], moves[i + 1], moves[i + 2]) && !moves[i].equals(" ")) {
                winningMoveIDs[0] = i;
                winningMoveIDs[1] = i + 1;
                winningMoveIDs[2] = i + 2;
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { // Check in columns
            if (equals(moves[i], moves[i + 3], moves[i + 6]) && !moves[i].equals(" ")) {
                winningMoveIDs[0] = i;
                winningMoveIDs[1] = i + 3;
                winningMoveIDs[2] = i + 6;
                return true;
            }
        }

        if (equals(moves[0], moves[4], moves[8]) && !moves[0].equals(" ")) { // Check Diagonal
            winningMoveIDs[0] = 0;
            winningMoveIDs[1] = 4;
            winningMoveIDs[2] = 8;
            return true;
        } else if (equals(moves[2], moves[4], moves[6]) && !moves[2].equals(" ")) { // Check Anti-Diagonal
            winningMoveIDs[0] = 2;
            winningMoveIDs[1] = 4;
            winningMoveIDs[2] = 6;
            return true;
        } else return false;
    }

    /**
     * Gets the total number of moves on a game excluding the ones not yet made.
     *
     * @return Total number of moves already made.
     */
    protected int getTotalMoves(Button[] buttons) {
        int moves = 0;
        for (Button button : buttons) if (!button.getText().equals(" ")) moves++;

        return moves;
    }

    /**
     * Used for getting the next or the previous move in the game.
     *
     * @param move The move to get the conjugate of.
     * @return The conjugate of a move.
     */
    protected States getConjugateMove(States move) {
        switch (move) {
            case X:
                return States.O;
            case O:
                return States.X;
            default:
                throw new IllegalStateException("Unexpected value: " + move);
        }
    }

    /**
     * Returns the modern name of a move.
     *
     * @param state The move to get the name of.
     * @return Urban name of the move.
     */
    protected String nameOfMove(Object state) {
        switch (state.toString()) {
            case "X":
                return "Cross       X";
            case "O":
                return "Nought      O";
            default:
                return "NA";
        }
    }

    /**
     * Returns the State of the move from a string.
     *
     * @param string String text to be converted to a move state.
     * @return State of the move.
     */
    protected States getMoveID(String string) {
        if (string.equalsIgnoreCase(States.X.toString())) return States.X;
        else if (string.equalsIgnoreCase(States.O.toString())) return States.O;

        return States.NONE;
    }

    /**
     * Generates a random number between 0 and 1 and decides the next move accordingly.
     *
     * @return State of a move.
     */
    protected States randomMoveGenerator() {
        Random random = new Random();

        switch (random.nextInt(2)) {
            case 0:
                return States.O;
            case 1:
                return States.X;
            default:
                return null;
        }
    }

    /**
     * Returns true when if object parameters are equal.
     *
     * @param objects Object Arguments to check for equality on.
     */
    private boolean equals(Object... objects) {
        Set<Object> set = new HashSet<>(Arrays.asList(objects));
        return set.size() == 1;
    }

    /**
     * Rounds up a number to one decimal place.
     */
    public double round(double value) {
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * Contains the possible states of a tic tac toe game.
     * <p>
     * Blank(None), Cross(X) or a Nought(O).
     * </p>
     */
    public enum States {
        NONE,
        X,
        O
    }
}
