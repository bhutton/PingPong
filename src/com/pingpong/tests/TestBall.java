package com.pingpong.tests;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static org.junit.Assert.*;

import com.pingpong.elements.Ball;
import org.junit.Test;

public class TestBall {

    private final Ball ball = new Ball();

    @Test
    public void testSetBallImg() throws IOException, NullPointerException {
        String current = new java.io.File(".").getCanonicalPath();
        String ballImg = current + "/src/com/pingpong/images/ball.png";
        BufferedImage imgBall1 = ImageIO.read(new File(ballImg));

        ball.setBallImage(ballImg);
        BufferedImage imgBall2 = ball.getBall();

        byte[] byteArray1 = ((DataBufferByte) imgBall1.getData().getDataBuffer()).getData();
        byte[] byteArray2 = ((DataBufferByte) imgBall2.getData().getDataBuffer()).getData();

        assertArrayEquals(byteArray1, byteArray2);
    }

    @Test
    public void testSetBallXValueAndGetReturn() {
        int x = 10;

        ball.setBallXValue(x);

        assertEquals(x, ball.getBallXPosition());
    }

    @Test
    public void testSetBallYValueAndGetReturn() {
        int y = 10;

        ball.setBallYValue(y);

        assertEquals(y, ball.getBrickYPosition());
    }

    @Test
    public void testIncrementingXValue() {
        int x = 10;

        ball.setBallXValue(x);
        ball.moveBallRight();

        assertEquals(11, ball.getBallXPosition());
    }

    @Test
    public void testIncrementingYValue() {
        int y = 10;

        ball.setBallYValue(y);
        ball.incY();

        assertEquals(13, ball.getBrickYPosition());
    }

    @Test
    public void testShiftingBallRight() {
        int x = 10;

        ball.setBallXValue(x);
        ball.moveBallLeftOrRight();
        assertEquals(11, ball.getBallXPosition());
    }

    @Test
    public void testShiftingBallLeft() {
        int x = 10;

        ball.setBallXValue(x);
        ball.setLeft();
        ball.moveBallLeftOrRight();
        assertEquals(9, ball.getBallXPosition());
    }

    @Test
    public void testCheckBallDirectionAfterReachingBricks() {
        int x = 10, y = 10;

        ball.createWall(4);
        ball.setBallUp();
        ball.setBallXValue(x);
        ball.setBallYValue(y);
        ball.ballSetStart();

        double rotate = ball.rotate();

        assertFalse(ball.getDown());
        assertTrue(ball.rotate() > rotate);

        assertTrue(ball.setBallDirectionOnReachingBricks());
        rotate = ball.rotate();
        assertTrue(ball.rotate() < rotate);

        assertTrue(ball.setBallDirectionOnReachingBricks());
        rotate = ball.rotate();
        assertTrue(ball.rotate() > rotate);
    }

    @Test
    public void TestBallBouncesOffSideOfBrickWhenGoingDown() {
        ball.createWall(2);
        ball.setBallX(1);
        ball.setBallY(1);

        ball.setBallDown(true);
        ball.setBallRight(true);
        assertTrue(ball.getRight());
        assertTrue(ball.setBallDirectionOnReachingBricks());
        assertFalse(ball.getRight());
    }

    @Test
    public void testCheckBallDirectionAfterReachingPaddle() {
        int x = 450, y = 800;

        ball.createWall(2);
        ball.ballSetStart();
        ball.setDown();
        ball.setBallXValue(x);
        ball.setBallYValue(y);
        ball.setPaddleLocation(600, 600);
        ball.setPaddleWidth(200);
        ball.setPaddleHeight(30);

        assertTrue(ball.getDown());
        assertFalse(ball.setBallDirectionAfterReachingPaddle());

        double rotate = ball.rotate();
        assertTrue(ball.rotate() < rotate);

        assertFalse(ball.setBallDirectionAfterReachingPaddle());
        rotate = ball.rotate();
        assertTrue(ball.rotate() > rotate);
    }

    @Test
    public void testCheckBallDirectionAfterMissingPaddle() {
        int x = 0, y = 800;

        ball.createWall(2);
        ball.setDown();
        ball.setBallXValue(x);
        ball.setBallYValue(y);
        ball.setPaddleLocation(600, 600);
        ball.setPaddleWidth(200);
        ball.setPaddleHeight(30);

        assertTrue(ball.getDown());
        assertTrue(ball.setBallDirectionAfterReachingPaddle());
    }

    @Test
    public void testCheckStart() {
        int brickHeight = 50;
        int y = ((brickHeight + 5) * 4);

        ball.setBallYValue(0);
        ball.checkStart();

        assertEquals(y, ball.getBrickYPosition());
    }

    @Test
    public void testCheckBallHitsRightSideOfScreen() {
        int appletWidth = 800;

        ball.setBallXValue(800);
        ball.setRight();
        ball.checkRight(appletWidth);
        assertFalse(ball.getRight());
    }

    @Test
    public void ballSpinsRight() {
        ball.setRight();
        ball.ballSetStart();

        assertTrue(ball.rotate() > 0);
    }

    @Test
    public void ballSpinsLeft() {
        ball.setLeft();
        ball.ballSetStart();

        assertTrue(ball.rotate() < 0);
    }

    @Test
    public void ballOnlyRotatesWhenGameOn() {
        ball.ballSetStop();

        assertEquals(0.0, ball.rotate(), 0.0);
    }

    @Test
    public void testCheckBallHitsLeftSideOfScreen() {
        ball.setBallXValue(0);
        ball.setLeft();
        ball.checkLeft();
        assertTrue(ball.getRight());
    }

    @Test
    public void testCheckBallHitsTopOfScreen() {
        ball.setBallUp();
        ball.setBallYValue(0);
        ball.checkTop();
        assertTrue(ball.getDown());
    }

    @Test
    public void testCheckBallHitsBottomOfScreen() {
        int appletHeight = 600;
        ball.setBallDown(true);
        ball.setBallYValue(600);
        ball.checkBottom(appletHeight);
        assertFalse(ball.getDown());
    }

    @Test
    public void testCheckEdges() {
        ball.setBallXValue(600);
        ball.setBallYValue(600);
        ball.setRight();
        ball.setDown();

        assertTrue(ball.getRight());
        assertTrue(ball.getDown());
        assertTrue(ball.checkBallActive());

        ball.checkEdges(600, 600);

        assertFalse(ball.getRight());
        assertFalse(ball.getDown());
        assertFalse(ball.checkBallActive());

        ball.setBallXValue(0);
        ball.checkEdges(600, 600);

        assertTrue(ball.getRight());
        assertFalse(ball.getDown());
        assertFalse(ball.checkBallActive());

        ball.setBallYValue(0);
        ball.checkEdges(600, 600);
        assertTrue(ball.getRight());
        assertFalse(ball.getDown());
        assertFalse(ball.checkBallActive());
    }

    @Test
    public void testUpdateBallCoordinates() {
        ball.ballSetStart();
        ball.setBallXValue(600);
        ball.setBallYValue(600);
        ball.setRight();
        ball.setDown();
        ball.updateBallCoordinates();
        assertEquals(601, ball.getBallXPosition());
        assertEquals(603, ball.getBrickYPosition());

        ball.setLeft();
        ball.setBallUp();
        ball.updateBallCoordinates();
        assertEquals(600, ball.getBallXPosition());
        assertEquals(600, ball.getBrickYPosition());
    }

    @Test
    public void testCheckBallActive() {
        ball.checkBallActive();
    }

    @Test
    public void testSetBallInActive() { assertFalse(ball.setBallInActive()); }

    @Test
    public void testSetBallActive() {
        assertTrue(ball.setBallActive());
    }

    @Test
    public void testInitializeBall() {
        ball.initializeBall();

        assertEquals(ball.getBallX(), ball.getPaddleX());
        assertEquals(ball.getBallY(), ball.getPaddleY());
    }

    @Test
    public void testSetStart() {
        assertTrue(ball.ballSetStart());
    }

    @Test
    public void testBallMovesWithPaddle() {
        ball.initializeBall();
        ball.setPaddleWidth(120);
        ball.setPaddleLocation(800, 600);
        ball.ballSetStop();
        ball.setPaddleMoveAmount(30);

        assertFalse(ball.getBallStatus());

        ball.movePaddleRight();
        ball.calculateCurrentLocation(800, 600);

        assertEquals(270, ball.getPaddleX());
        assertEquals(270 + ball.getPaddleWidth() / 2 - 30, ball.getBallXPosition());
    }

}
