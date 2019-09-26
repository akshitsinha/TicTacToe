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

import com.wimi.tictactoe.util.Console;

import java.io.File;
import java.io.IOException;

/**
 * @Description Creates the game file for a new game.
 */
public class GameCreator {

    private final String gameAssetsPath = System.getenv("TEMP") + "//.tictactoe//saves//";
    private final File defaultPath = new File(gameAssetsPath);

    public void createGameAssets(String gameName) {
        File gameAssetPath = new File(gameAssetsPath + gameName + ".nc");
        // File Extension stands for Noughts and Crosses (.nc)

        if (isStringValid(gameName)) {
            try {
                boolean creation = gameAssetPath.createNewFile();

                if (creation) Console.log("File created successfully with the name " + gameName);
                else {
                    Console.log("Could not create file.");
                    debugAssetPath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else Console.log("Illegal file name entered. Only alphabets and numbers are allowed.");
    }

    /**
     * Check if the name of the game is valid. That is only containing only alphabets.
     *
     * @param input Input string.
     * @return Is String name valid for game name.
     */
    private boolean isStringValid(String input) {
        return input.matches("[a-zA-Z0-9]*");
    }

    /**
     * Runs when game fails to find the path of the saves directory.
     */
    private void debugAssetPath() {
        if (!defaultPath.isDirectory()) {
            Console.log("The default game saves directory does not exist. Trying to create one.");
            boolean dir = defaultPath.mkdirs();

            if (dir) Console.log("Created game directory. Try again?");
            else Console.log("Still could not create the game saves directory.");
        }
    }
}
