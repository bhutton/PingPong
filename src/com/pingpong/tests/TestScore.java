package com.pingpong.tests;

import com.pingpong.Score;
import org.junit.Test;

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
}
