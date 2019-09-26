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

package com.wimi.tictactoe.client.game.mechanics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * @Description Plays the sounds of the game.
 */
public class AudioEngine {

    public static synchronized void playClickSound() {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                InputStream multimediaAudioStream = AudioEngine.class.getResourceAsStream("/assets/sounds/MultimediaClick.wav");
                InputStream bufferedMultimediaStream = new BufferedInputStream(multimediaAudioStream);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedMultimediaStream);
                clip.open(inputStream);
                clip.start();

                multimediaAudioStream.close();
                bufferedMultimediaStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static synchronized void playGlassClickSound() {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                InputStream multimediaAudioStream = AudioEngine.class.getResourceAsStream("/assets/sounds/GlassClick.wav");
                InputStream bufferedMultimediaStream = new BufferedInputStream(multimediaAudioStream);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedMultimediaStream);
                clip.open(inputStream);
                clip.start();

                multimediaAudioStream.close();
                bufferedMultimediaStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
