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

/**
 * @Description Creates the default game directories for the game files.
 */
public class DirectoryCreator {

    private final String defaultDir = System.getenv("TEMP") + "\\.tictactoe\\saves\\";
    private final File file = new File(defaultDir);

    public void createDirectories() {
        if (!file.isDirectory()) {
            boolean createDir = file.mkdirs();

            if (createDir) Console.log("Created default directories for game files.");
            else Console.log("Unable to create the saves directory.");
        } else Console.log("The default directories already exists.");
    }
}
