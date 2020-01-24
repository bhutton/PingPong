package com.pingpong.tests;

import com.pingpong.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


public class TestScore {
    private String path = "src/com/pingpong/tests/file/highscores";

    @BeforeEach
    public void setup() throws Exception {
        BufferedWriter bw;
        File file = new File(this.path);
        FileWriter fw;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);

        bw.write("bloggs=20\n");
        bw.write("fred=10\n");
        bw.close();
    }

    @Test
    public void checkStartScore() {
        Score score = new Score(System.getProperty("user.name"), this.path);

        assertEquals(0, score.getScore());
    }

    @Test
    public void checkIncrementScore() {
        Score score = new Score(System.getProperty("user.name"), this.path);

        score.incrementScore(1);
        assertEquals(1, score.getScore());
    }

    @Test
    public void checkIncrementScoreByTwo() {
        Score score = new Score(System.getProperty("user.name"), this.path);

        score.incrementScore(2);
        assertEquals(2, score.getScore());
    }

    @Test
    public void highestScores() {
        HashMap<Integer, String> highScores = new HashMap<>();
        highScores.put(10, "fred");
        highScores.put(20, "bloggs");
        Score score = new Score(System.getProperty("user.name"), this.path);

        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void displayScores() {
        TreeMap<Integer, String> highScores = new TreeMap<>();
        highScores.descendingMap();
        highScores.put(20, "bloggs");
        highScores.put(10, "fred");

        Score score = new Score(System.getProperty("user.name"), this.path);
        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void checkCurrentAgainstExistingScores() {
        Score score = new Score(System.getProperty("user.name"), this.path);

        score.incrementScore(5);
        score.getHighestScores();
        assertFalse(score.checkAgainstExisting());

        score.incrementScore(15);
        assertTrue(score.checkAgainstExisting());
    }

    @Test
    public void insertCurrentIntoExistingSet() {
        String userScore = System.getProperty("user.name");
        String highScores = "20\tbloggs\n15\t" + userScore + "\n10\tfred\n";

        Score score = new Score(System.getProperty("user.name"), this.path);
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        assertEquals(highScores, score.toString());
    }

    @Test
    public void addHighScoresToFile() {
        String userScore = System.getProperty("user.name");
        Score score = new Score(userScore, this.path);
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        score.getHighestScores();

        String highScores = "20\tbloggs\n15\t" + userScore + "\n10\tfred\n";
        assertEquals(highScores, score.toString());
    }

    @Test
    public void addHighScoresToFileWithDifferentSet() throws IOException {
        BufferedWriter bw;
        File file = new File(this.path);
        FileWriter fw;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);

        bw.write("bloggs=20\n");
        bw.write("fred=1\n");
        bw.close();

        String userScore = System.getProperty("user.name");
        Score score = new Score(userScore, this.path);
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        score.getHighestScores();

        String highScores = "20\tbloggs\n15\t" + userScore + "\n1\tfred\n";
        assertEquals(highScores, score.toString());
    }

    @Test
    public void limitFileSizeToTenRows() throws IOException {
        BufferedWriter bw;
        File file = new File(this.path);
        FileWriter fw;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);

        bw.write("fred=1\n");
        bw.write("fred=2\n");
        bw.write("fred=3\n");
        bw.write("fred=4\n");
        bw.write("fred=5\n");
        bw.write("fred=6\n");
        bw.write("fred=7\n");
        bw.write("fred=8\n");
        bw.write("fred=9\n");
        bw.write("fred=10\n");
        bw.close();

        String userScore = System.getProperty("user.name");
        Score score = new Score(userScore, this.path);
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        score.getHighestScores();

        String highScores = "15\t" + userScore + "\n" + "10\tfred\n9\tfred\n8\tfred\n7\tfred\n6\tfred\n5\tfred\n4\tfred\n3\tfred\n2\tfred\n";
        assertEquals(highScores, score.toString());
    }

    @Test
    public void addScoreToEmptySet() throws IOException {
        BufferedWriter bw;
        File file = new File(this.path);
        FileWriter fw;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        bw.close();

        String userScore = System.getProperty("user.name");
        Score score = new Score(userScore, this.path);
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        score.getHighestScores();

        String highScores = "15\t" + userScore + "\n";
        assertEquals(highScores, score.toString());
    }
}
