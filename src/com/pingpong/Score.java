package com.pingpong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Score {
    private int score;
    HashMap<String, Integer> scores;

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
        scores = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            if (line.contains("=")) {
                String[] strings = line.split("=");
                scores.put(strings[0], Integer.parseInt(strings[1]));
            }
        }
        return scores;
    }

    @Override
    public String toString() {
        StringBuilder score = new StringBuilder();

        for(Map.Entry<String, Integer> line : scores.entrySet()) {
            score.append(line.getKey());
            score.append("\t");
            score.append(line.getValue());
            score.append("\n");
        }
        return score.toString();
    }
}
