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

import com.wimi.tictactoe.client.game.mechanics.GameEngine.States;
import javafx.scene.control.Button;

import java.util.Random;

/**
 * @Description Contains the basic methods used for running a tic tac toe game.
 */
public class Structure {

    /**
     * Returns an string of the modern name of a move.
     *
     * @param state A Move
     * @return Urban name of the move.
     */
    static String nameOfMove(Object state) {
        switch (state.toString()) {
            case "X":
                return "Cross";
            case "O":
                return "Nought";
            default:
                return "NA";
        }
    }

    /**
     * Used for getting the next or the previous move in the game.
     *
     * @param move The move to get the conjugate of.
     * @return The conjugate of a move.
     */
    public States getConjugateMove(States move) {
        switch (move) {
            case X:
                return States.O;
            case O:
                return States.X;
            case NONE:
                return States.NONE;
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
    public States getMove(String string) {
        if (string.equalsIgnoreCase(States.X.toString())) {
            return States.X;
        } else if (string.equalsIgnoreCase(States.O.toString())) {
            return States.O;
        }

        return States.NONE;
    }

    /**
     * @param buttons Button matrix to get moves from.
     * @return 2D Array of States which contains the state of the move. Nought, Cross or a Blank Space.
     */
    public States[][] getMoveStates(Button[][] buttons) {
        States[][] moves = new States[buttons[0].length][buttons[1].length];

        for (int i = 0; i < buttons[0].length; i++) {
            for (int j = 0; j < buttons[1].length; j++) {
                moves[i][j] = this.getMove(buttons[i][j].getText());
            }
        }

        return moves;
    }

    /**
     * Generates a random number between 0 and 1 and decides the next move accordingly.
     *
     * @return State of a move.
     */
    public States randMoveGen() {
        Random random = new Random();
        int randInt = random.nextInt(2);

        switch (randInt) {
            case 0:
                return States.O;
            case 1:
                return States.X;
            default:
                return States.NONE;
        }
    }
}
