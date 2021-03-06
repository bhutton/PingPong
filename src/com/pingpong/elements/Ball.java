package com.pingpong.elements;

import com.pingpong.Score;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Ball extends Paddle {
    private int x;
    private int y;
    private int incX = 1;
    private final int incY = 3;
    private final int width = 50;
    private boolean right = true, down = true, ballIsActive = true, ballStart = false;
    private Score score = new Score(System.getProperty("user.name"));

    private BufferedImage imgBall = null;
    private double angle = 0;
    private double angleMultiplier = 0.1;

    public Ball() {
        super();
    }

    public void setBallImage(String ballImg) {
        try {
            imgBall = ImageIO.read(new File(ballImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getBall() {
        return imgBall;
    }

    public void setBallXValue(int x) {
        this.x = x;
    }

    public void setBallYValue(int y) {
        this.y = y;
    }

    public int getBallXPosition() {
        return this.x;
    }

    public int getBrickYPosition() {
        return this.y;
    }

    public void moveBallRight() {
        this.x += incX;
    }

    public void incY() {
        this.y += incY;
    }

    private void moveBallLeft() {
        this.x -= incX;
    }

    private void decY() {
        this.y -= incY;
    }

    public Ball moveBallLeftOrRight() {
        if (right) moveBallRight();
        else moveBallLeft();

        return this;
    }

    private void shiftY() {
        if (down) incY();
        else decY();
    }

    public boolean getRight() {
        return right;
    }

    public boolean getDown() {
        return down;
    }

    public void setDown() {
        this.down = true;
    }

    public void setBallUp() {
        this.down = false;
    }

    public void setLeft() {
        this.right = false;
        this.angleMultiplier = -0.1;
    }

    public void setRight() {
        this.right = true;
        this.angleMultiplier = 0.1;
    }

    public Boolean setBallDirectionOnReachingBricks() {
        this.setBallX(x).setBallY(y).setBallDown(down).setBallRight(right);
        this.down = checkBricks(this.width);
        this.score.incrementScore(1);
        this.setDirection();

        setAngle();

        return down;
    }

    private void setAngle() {
        if(this.angleMultiplier == 0.1)
            this.angleMultiplier = -0.1;
        else
            this.angleMultiplier = 0.1;
    }

    private void setDirection() {
        if (this.getBallRight())
            this.setRight();
        else
            this.setLeft();
    }

    public Boolean setBallDirectionAfterReachingPaddle() {
        setBallX(x).setBallY(y).setBallDown(down).setBallRight(right);
        down = checkPaddle(down, x, y);
        setAngle();
        return down;
    }

    public Ball checkStart() {
        int brickHeight = 50;
        if (this.getBrickYPosition() == 0)
            this.y = ((brickHeight + 5) * 4);

        return this;
    }

    public Ball checkRight(int appletWidth) {
        if ((this.getBallXPosition() + width + 2) > appletWidth) {
            this.right = false;
            incX = ThreadLocalRandom.current().nextInt(1, 5);
        }

        return this;
    }

    public Ball checkLeft() {
        if (this.getBallXPosition() <= 0) {
            this.right = true;
            incX = ThreadLocalRandom.current().nextInt(1, 5);
        }

        return this;
    }

    public Ball checkTop() {
        if (this.getBrickYPosition() <= 0) {
            this.down = true;
            incX = ThreadLocalRandom.current().nextInt(1, 5);
        }

        return this;
    }

    public void checkBottom(int appletHeight) {
        int height = 50;

        if ((this.getBrickYPosition() + height + 2) > appletHeight) {
            this.down = false;
            incX = ThreadLocalRandom.current().nextInt(1, 5);

            this.ballIsActive = false;
        }

    }

    public void calculateCurrentLocation(int appletWidth, int appletHeight) {
        checkEdges(appletWidth, appletHeight);
        updateBallCoordinates();
    }

    public Boolean checkBallActive() {
        return this.ballIsActive;
    }

    public Boolean setBallInActive() {
        return this.ballIsActive = false;
    }

    public Boolean setBallActive() {
        return this.ballIsActive = true;
    }

    public void checkEdges(int appletWidth, int appletHeight) {
        checkStart().checkLeft().checkRight(appletWidth).checkTop().checkBottom(appletHeight);
    }

    public void updateBallCoordinates() {
        if (ballStart)
            this.moveBallLeftOrRight().shiftY();

        if (!this.getBallStatus())
            this.x = this.getPaddleX() + (this.getPaddleWidth() / 2) - 30;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    public static BufferedImage create(BufferedImage image, double angle,
                                       GraphicsConfiguration gc) {
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w);
        int newh = (int) Math.floor(h);
        int transparency = image.getColorModel().getTransparency();
        BufferedImage result = gc.createCompatibleImage(neww, newh, transparency);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2.0, h / 2.0);
        g.drawRenderedImage(image, null);

        return result;
    }

    public double rotate() {
        if (this.ballStart)
            return this.angle += this.angleMultiplier;

        return this.angle;
    }

    private void drawBall(Graphics g) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        BufferedImage rotated1 = create(imgBall, rotate(), gc);

        g.drawImage(rotated1, this.x, this.y, null);
    }

    public void initializeBall() {
        this.x = this.getPaddleX();
        this.y = this.getPaddleY() - 50;
    }

    public Boolean ballSetStart() {
        return ballStart = true;
    }

    public void ballSetStop() {
        ballStart = false;
    }

    public void drawWindow(Graphics g) {
        this.drawWall(g);
        this.drawBall(g);
        this.drawPaddle(g);
    }

    public Boolean getBallStatus() {
        return ballStart;
    }

    public void ballCalculations(int appletWidth, int appletHeight) {
        calculateCurrentLocation(appletWidth, appletHeight);
        setBallDirectionOnReachingBricks();
        setBallDirectionAfterReachingPaddle();
    }
}
