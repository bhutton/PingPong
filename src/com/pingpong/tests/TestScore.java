package com.pingpong.tests;

import com.pingpong.Score;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

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
}
