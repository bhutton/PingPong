package com.pingpong;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Score {
    private int score;
    HashMap<Integer, String> scores;
    private String username;
    private String path = "src/com/pingpong/file/highscores";

    public Score(String username) {
        this.username = username;
        this.score = 0;
    }

    public Score(String username, String path) {
        this.username = username;
        this.path = path;
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
            reader = new BufferedReader(new FileReader(new File(this.path)));
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
            addExistingScore(scores, Integer.parseInt(strings[1]), strings[0]);
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
        try {
            return updateScores();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sortDescending();
        return false;
    }

    private boolean updateScores() throws IOException {
        HashMap<Integer, String> newScore = new HashMap<>();

        if (scores.size() == 0)
            scores.put(score, username);

        if (scores.size() > 0)
            return processScores(newScore);

        return false;
    }

    private boolean processScores(HashMap<Integer, String> newScore) throws IOException {
        Map<Integer, String> sortedScores = sortDescending();
        BufferedWriter bw = createHighScoresFile();
        boolean response = false;

        for (Map.Entry<Integer, String> line : sortedScores.entrySet())
            response = checkHighScore(response, newScore, bw, line);

        bw.close();
        scores = newScore;
        return response;
    }

    private boolean checkHighScore(boolean response, HashMap<Integer, String> newScore, BufferedWriter bw,
                                   Map.Entry<Integer, String> line) throws IOException {
        if (newScore.size() < 10) {
            writeScoreToFile(bw, line.getValue(), line.getKey());
            addExistingScore(newScore, line.getKey(), line.getValue());

            response = addScore(response, newScore, bw, line);
        }
        return response;
    }

    private void addExistingScore(HashMap<Integer, String> newScore, Integer key, String value) {
        newScore.put(key, value);
    }

    private void writeScoreToFile(BufferedWriter bw, String value, Integer key) throws IOException {
        bw.write(value + "=" + key + "\n");
    }

    private boolean addScore(boolean response, HashMap<Integer, String> newScore, BufferedWriter bw,
                             Map.Entry<Integer, String> line) throws IOException {
        if (score > line.getKey() && !response) {
            addExistingScore(newScore, score, username);

            writeScoreToFile(bw, username, score);
            response = true;
        }

        return response;
    }

    private BufferedWriter createHighScoresFile() throws IOException {
        BufferedWriter bw;
        File file = new File(this.path);
        FileWriter fw;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        return bw;
    }
}
