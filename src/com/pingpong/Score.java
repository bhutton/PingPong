package com.pingpong;

public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore(int score) {
        this.score+=score;
    }
}
