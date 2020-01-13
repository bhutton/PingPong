package com.pingpong;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Score {
    private int score;
    HashMap<Integer, String> scores;
    private String username;

//    public Score() {
//        this.score = 0;
//    }

    public Score(String username) {
        this.username = username;
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int score) {
        this.score += score;
    }

    public HashMap<Integer, String> getHighestScores() {
        return loadScoresFromFile();
    }

    private HashMap<Integer, String> loadScoresFromFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File("src/com/pingpong/file/highscores")));
            String line;
            scores = new HashMap<>();
            while ((line = reader.readLine()) != null)
                splitString(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        BufferedWriter bw;

        try {
            if (scores.size() > 0) {
                File file = new File("src/com/pingpong/file/highscores");
                FileWriter fw;
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);

                for (Map.Entry<Integer, String> line : scores.entrySet()) {
                    bw.write(line.getValue() + "=" + line.getKey() + "\n");
                    if (score > line.getKey()) {
                        scores.put(score, username);

                        bw.write(username + "=" + score + "\n");
                        bw.close();
                        return true;
                    }
                }
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sortDescending();
        return false;
    }
}
