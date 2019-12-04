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

package com.wimi.tictactoe.client.game.algo;

import com.wimi.tictactoe.client.game.Structure;
import com.wimi.tictactoe.util.Console;
import javafx.scene.control.Button;

import java.util.Random;

/**
 * Used when the game is being played in INTERMEDIATE difficulty.
 */
public class InterMove extends Structure {

    private final int cellID;

    public InterMove(Button[] buttons, States currentMove) {
        cellID = interMoveGen(buttons, currentMove);
        Console.log("Computer is making a move at " + cellID);
    }

    /**
     * Has a 70% chance of using Minimax algorithm to return the optimally best move
     * or else returns any random available spot.
     */
    private int interMoveGen(Button[] gameButtons, States currentMove) {
        if (getRNG()) {
            Minimax minimax = new Minimax(gameButtons, currentMove);
            return minimax.getCellID();
        } else return getAnyAvailSpot(gameButtons);
    }

    /**
     * RNGs and has a 70% chance to return true.
     */
    private boolean getRNG() {
        Random random = new Random();
        return random.nextFloat() <= 0.7f;
    }

    public int getCellID() {
        return cellID;
    }
}
