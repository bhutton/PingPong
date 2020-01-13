package com.pingpong.tests;

import com.pingpong.Score;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class TestScore {
    @Before
    public void setup() throws Exception {
        BufferedWriter bw;
        File file = new File("src/com/pingpong/file/highscores");
        FileWriter fw;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);

        bw.write("bloggs=20\n");
        bw.write("fred=10\n");
        bw.close();
    }

    @Test
    public void checkStartScore() {
        Score score = new Score(System.getProperty("user.name"));

        assertEquals(0, score.getScore());
    }

    @Test
    public void checkIncrementScore() {
        Score score = new Score(System.getProperty("user.name"));

        score.incrementScore(1);
        assertEquals(1, score.getScore());
    }

    @Test
    public void checkIncrementScoreByTwo() {
        Score score = new Score(System.getProperty("user.name"));

        score.incrementScore(2);
        assertEquals(2, score.getScore());
    }

    @Test
    public void highestScores() {
        HashMap<Integer, String> highScores = new HashMap<>();
        highScores.put(10, "fred");
        highScores.put(20, "bloggs");
        Score score = new Score(System.getProperty("user.name"));

        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void displayScores() {
        TreeMap<Integer, String> highScores = new TreeMap<>();
        highScores.descendingMap();
        highScores.put(20, "bloggs");
        highScores.put(10, "fred");

        Score score = new Score(System.getProperty("user.name"));
        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void checkCurrentAgainstExistingScores() {
        Score score = new Score(System.getProperty("user.name"));

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

        Score score = new Score(System.getProperty("user.name"));
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        assertEquals(highScores, score.toString());
    }

    @Test
    public void addHighScoresToFile() {
        String userScore = System.getProperty("user.name");
        Score score = new Score(userScore);
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        score.getHighestScores();

        String highScores = "20\tbloggs\n15\t" + userScore + "\n10\tfred\n";
        assertEquals(highScores, score.toString());
    }
}
