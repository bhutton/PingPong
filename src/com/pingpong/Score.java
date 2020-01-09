package com.pingpong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int score) {
        this.score+=score;
    }

    public HashMap<String, Integer> getHighestScores() throws IOException {
        return loadScoresFromFile();
    }

    private HashMap<String, Integer> loadScoresFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/com/pingpong/file/highscores")));
        String line;
        HashMap<String, Integer> map = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            if (line.contains("=")) {
                String[] strings = line.split("=");
                map.put(strings[0], Integer.parseInt(strings[1]));
            }
        }
        return map;
    }
}
