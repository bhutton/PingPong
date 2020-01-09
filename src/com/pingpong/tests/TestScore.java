package com.pingpong.tests;

import com.pingpong.Score;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    public void highestScores() throws IOException {
        HashMap<String, Integer> highScores = new HashMap<>();
        highScores.put("fred", 10);
        highScores.put("bloggs", 20);
        Score score = new Score();

        assertEquals(highScores, score.getHighestScores());
    }

    @Test
    public void displayScores() throws IOException {
        String highScores = "fred\t10\nbloggs\t20\n";

        Score score = new Score();
        score.getHighestScores();
        assertEquals(highScores, score.toString());
    }

    @Test
    public void checkCurrentAgainstExistingScores() throws IOException {
        Score score = new Score();

        score.incrementScore(5);
        score.getHighestScores();
        assertFalse(score.checkAgainstExisting());

        score.incrementScore(15);
        assertTrue(score.checkAgainstExisting());
    }
}
