package com.pingpong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Score {
    private int score;
    HashMap<Integer, String> scores;

    public Score() {
        this.score = 0;
    }

    public Score(String name) {

    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int score) {
        this.score += score;
    }

    public HashMap<Integer, String> getHighestScores() throws IOException {
        return loadScoresFromFile();
    }

    private HashMap<Integer, String> loadScoresFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/com/pingpong/file/highscores")));
        String line;
        scores = new HashMap<>();
        while ((line = reader.readLine()) != null)
            splitString(line);

        return scores;
    }

    private void splitString(String line) {
        if (line.contains("=")) {
            String[] strings = line.split("=");
            scores.put(Integer.parseInt(strings[1]), strings[0]);
        }
    }

    @Override
    public String toString() {
        Map<Integer, String> reversedMap = sortDescending();

        return buildString(reversedMap).toString();
    }

    private StringBuilder buildString(Map<Integer, String> reversedMap) {
        StringBuilder score = new StringBuilder();

        for (Map.Entry<Integer, String> line : reversedMap.entrySet()) {
            score.append(line.getKey());
            score.append("\t");
            score.append(line.getValue());
            score.append("\n");
        }
        return score;
    }

    private Map<Integer, String> sortDescending() {
        TreeMap<Integer, String> sortedMap = new TreeMap<>(scores);
        return sortedMap.descendingMap();
    }

    public boolean checkAgainstExisting() {
        for (Map.Entry<Integer, String> line : scores.entrySet())
            if (score > line.getKey()) {
                scores.put(score, "test");
                return true;
            }

        sortDescending();
        return false;
    }
}
