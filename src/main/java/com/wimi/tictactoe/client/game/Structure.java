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

import com.wimi.tictactoe.util.Console;
import javafx.scene.control.Button;

import java.util.Objects;
import java.util.Random;

/**
 * @Description Contains the basic methods used for running a tic tac toe game.
 */
public class Structure {

    /**
     * Checks if there is a win in the game.
     */
    protected boolean checkForWin(Button[] nodes) {
        String[] moves = new String[nodes.length];
        for (int i = 0; i < moves.length; i++) moves[i] = nodes[i].getText();
        long timeForAlgorithm = System.nanoTime();

        if ((Objects.equals(moves[0], moves[1]) && Objects.equals(moves[1], moves[2]) && !moves[0].equals(" ")) ||
                (Objects.equals(moves[3], moves[4]) && Objects.equals(moves[4], moves[5]) && !moves[3].equals(" ")) ||
                (Objects.equals(moves[6], moves[7]) && Objects.equals(moves[7], moves[8]) && !moves[6].equals(" ")) ||
                (Objects.equals(moves[0], moves[3]) && Objects.equals(moves[3], moves[6]) && !moves[0].equals(" ")) ||
                (Objects.equals(moves[1], moves[4]) && Objects.equals(moves[4], moves[7]) && !moves[1].equals(" ")) ||
                (Objects.equals(moves[2], moves[5]) && Objects.equals(moves[5], moves[8]) && !moves[2].equals(" ")) ||
                (Objects.equals(moves[0], moves[4]) && Objects.equals(moves[4], moves[8]) && !moves[0].equals(" ")) ||
                (Objects.equals(moves[2], moves[4]) && Objects.equals(moves[4], moves[6]) && !moves[2].equals(" "))) {
            Console.log("Time taken to determine game result was " + (System.nanoTime() - timeForAlgorithm) + "ns.");
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
