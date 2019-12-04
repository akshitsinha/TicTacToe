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

/**
 * Used when the game is being played in IMPOSSIBLE difficulty.
 */
public class Minimax extends Structure {

    private final int cellID;
    private final States aiMove;
    private int minimaxCalls;

    /**
     * Gets the best cell to make a move at using the Minimax algorithm.
     *
     * @param currentMove The move to be made by the AI.
     */
    public Minimax(Button[] buttons, States currentMove) {
        String[] strings = new String[9];
        for (int i = 0; i < 9; i++) strings[i] = buttons[i].getText();

        aiMove = currentMove;
        cellID = getBestMove(strings);
        Console.log("Computer is making a move at " + cellID + " with " + minimaxCalls + " calls.");
    }

    /**
     * Description:
     * Minimax is a recursive algorithm which is used to choose an optimal move
     * for a player assuming that the other player is also playing optimally.
     */
    private int minimax(String[] gameBoard, boolean maximizer) {
        int score = evaluate(gameBoard, aiMove);
        minimaxCalls++;

        if (score == 10) return score;
        else if (score == -10) return score;
        else if (!isMovesLeft(gameBoard)) return 0;

        if (maximizer) {
            int max = Integer.MIN_VALUE;

            for (int i = 0; i < gameBoard.length; i++) {
                if (gameBoard[i].equals(" ")) {
                    gameBoard[i] = aiMove.toString();

                    max = Math.max(max, minimax(gameBoard, false));
                    gameBoard[i] = " ";
                }
            }

            return max;
        } else {
            int min = Integer.MAX_VALUE;

            for (int i = 0; i < gameBoard.length; i++) {
                if (gameBoard[i].equals(" ")) {
                    gameBoard[i] = getConjugateMove(aiMove).toString();

                    min = Math.min(min, minimax(gameBoard, true));
                    gameBoard[i] = " ";
                }
            }

            return min;
        }
    }

    private int getBestMove(String[] board) {
        int bestValue = Integer.MIN_VALUE;
        int bestCell = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i].equals(" ")) {
                board[i] = aiMove.toString();
                int moveValue = minimax(board, false);

                board[i] = " ";
                if (moveValue > bestValue) {
                    bestCell = i;
                    bestValue = moveValue;
                }
            }
        }

        return bestCell;
    }

    private boolean isMovesLeft(String[] strings) {
        for (String string : strings) if (string.equals(" ")) return true;
        return false;
    }

    private int evaluate(String[] strings, States currentMove) {
        for (int i = 0; i < 9; i += 3) { // Check in rows
            if (equals(strings[i], strings[i + 1], strings[i + 2]) && !strings[i].equals(" ")) {
                if (strings[i].equals(currentMove.toString())) return +10;
                else if (strings[i].equals(getConjugateMove(currentMove).toString())) return -10;
            }
        }

        for (int i = 0; i < 3; i++) { // Check in columns
            if (equals(strings[i], strings[i + 3], strings[i + 6]) && !strings[i].equals(" ")) {
                if (strings[i].equals(currentMove.toString())) return +10;
                else if (strings[i].equals(getConjugateMove(currentMove).toString())) return -10;
            }
        }

        if (equals(strings[0], strings[4], strings[8]) && !strings[0].equals(" ")) { // Check Diagonal
            if (strings[0].equals(currentMove.toString())) return +10;
            else if (strings[0].equals(getConjugateMove(currentMove).toString())) return -10;
        } else if (equals(strings[2], strings[4], strings[6]) && !strings[2].equals(" ")) { // Check Anti-Diagonal
            if (strings[2].equals(currentMove.toString())) return +10;
            else if (strings[2].equals(getConjugateMove(currentMove).toString())) return -10;
        }

        return 0;
    }

    public int getCellID() {
        return cellID;
    }
}
