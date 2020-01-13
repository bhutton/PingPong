package com.pingpong.tests;

import com.pingpong.Score;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class TestScore {
    @Test
    public void checkStartScore() {
        Score score = new Score();

        assertEquals(0, score.getScore());
    }

    @Test
    public void checkIncrementScore() {
        Score score = new Score();

        score.incrementScore(1);
        assertEquals(1, score.getScore());
    }

    @Test
    public void checkIncrementScoreByTwo() {
        Score score = new Score();

        score.incrementScore(2);
        assertEquals(2, score.getScore());
    }

    @Test
    public void highestScores() {
        HashMap<Integer, String> highScores = new HashMap<>();
        highScores.put(10, "fred");
        highScores.put(20, "bloggs");
        Score score = new Score();

        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void displayScores() {
        TreeMap<Integer, String> highScores = new TreeMap<>();
        highScores.descendingMap();
        highScores.put(20, "bloggs");
        highScores.put(10, "fred");


        Score score = new Score();
        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void checkCurrentAgainstExistingScores() {
        Score score = new Score();

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

        //TODO use username environment variable to get local user
        Score score = new Score(System.getProperty("user.name"));
        score.getHighestScores();
        score.incrementScore(15);
        score.checkAgainstExisting();
        assertEquals(highScores, score.toString());
    }
}
