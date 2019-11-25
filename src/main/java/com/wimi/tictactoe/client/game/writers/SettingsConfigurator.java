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
import com.wimi.tictactoe.util.Levels;
import com.wimi.tictactoe.util.Themes;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description Initializes the game settings/ options on JSON.
 */
@SuppressWarnings("unchecked")
public class SettingsConfigurator {

    private final String path = System.getenv("TEMP") + "//.tictactoe//options.json";
    private final File optionsAssets = new File(path);
    private final JSONParser jsonParser = new JSONParser();
    private JSONObject jsonObject = new JSONObject();

    public void init() {
        if (optionsAssets.exists()) {
            Console.log("Initialization of options.json already done.");

            try (FileReader reader = new FileReader(optionsAssets)) {
                Console.log("Getting the already written JSON elements.");
                jsonObject = (JSONObject) jsonParser.parse(reader);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                boolean creation = optionsAssets.createNewFile();
                if (creation) Console.log("Created the settings file.");
                else Console.log("Could not create the settings file.");

                FileWriter fileWriter = new FileWriter(optionsAssets);
                jsonObject.put("theme", Themes.DARK.toString()); // Dark Theme by default.
                jsonObject.put("SFX", true); // SFX on by default.
                jsonObject.put("maxTime", 5); // Max time allowed when playing on Timed mode.
                jsonObject.put("difficulty", Levels.INTERMEDIATE.toString()); // Difficulty when playing against computer.
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.close();

                FileReader reader = new FileReader(optionsAssets);
                jsonObject = (JSONObject) jsonParser.parse(reader);
                reader.close();
                Console.log("Written default JSON values on file. Options JSON. Thru " + this.getClass().getSimpleName());
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setJsonKey(Object key, Object value) {
        try (FileWriter fileWriter = new FileWriter(optionsAssets)) {
            jsonObject.replace(key, value);
            fileWriter.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getJsonKey(Object key) {
        try (FileReader reader = new FileReader(optionsAssets)) {
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject.get(key);
    }

    /**
     * Returns whether a certain JSON key exists or not.
     *
     * @param key The key value to check for.
     */
    public boolean containsKey(Object key) {
        try (FileReader reader = new FileReader(optionsAssets)) {
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject.containsKey(key);
    }

    /**
     * @return The preferred theme by the user.
     */
    public Themes getTheme() {
        try (FileReader reader = new FileReader(optionsAssets)) {
            jsonObject = (JSONObject) jsonParser.parse(reader);
            if (jsonObject.get("theme").equals(Themes.DARK.toString())) return Themes.DARK;
            else if (jsonObject.get("theme").equals(Themes.LIGHT.toString())) return Themes.LIGHT;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return Is SFX is turned on.
     */
    public boolean getSFX() {
        try (FileReader reader = new FileReader(optionsAssets)) {
            jsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject.get("SFX").equals(true);
    }
}