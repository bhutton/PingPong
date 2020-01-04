package com.pingpong;

import com.pingpong.elements.Background;
import com.pingpong.elements.Ball;
import com.pingpong.game.Level;
import com.pingpong.game.Messages;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
public class PingPong extends JFrame implements KeyListener, Runnable {
    private static final int NUM_LIVES = 3;
    private static final int paddleWidth = 174;
    private static final int paddleHeight = 30;
    private static final int paddleMovementAmount = 30;
    private static final int frameWidth = 853;
    private static final int frameHeight = 625;

    public int appletHeight = 600;
    public int appletWidth = 853;

    private JFrame frame;
    private DrawPanel drawPanel;

    private Thread animatorThread;
    private Thread currentThread = null;
    public boolean gameStart = false;
    public boolean called = false;

    private HashMap<String, String> artifacts = new HashMap<>();

    public final Ball ball = new Ball();
    private final Messages message = new Messages();
    public final Background background = new Background();
    private final File basePath = new File(
            PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
    );
    public final Level level = new Level();

    public static void main(String[] args) {
        new PingPong().go();
    }

    private void go() {
        drawPanel = new PingPong.DrawPanel();

        frame = new JFrame("com/pingpong");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setResizable(false);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addKeyListener(this);

        String imagePath = "/com/pingpong/images/";

        artifacts.put("BallImage", this.basePath + imagePath + "ball.png");
        artifacts.put("BrickImage", this.basePath + imagePath + "brick.png");
        artifacts.put("PaddleImage", this.basePath + imagePath + "paddle.png");
        artifacts.put("GameOverImage", this.basePath + imagePath + "game-over.jpg");
        artifacts.put("GameOverMessage", this.basePath + imagePath + "game-over-message.png");

        this.background.loadBackGrounds(this.basePath);
        initialiseGame();

        run();
    }

    private void initialiseGame() {
        background.setGameOverImage(this.artifacts.get("GameOverImage"));
        message.setGameOverImage(this.artifacts.get("GameOverMessage"));
        ball.setBallImage(this.artifacts.get("BallImage"));
        ball.setBrickImage(this.artifacts.get("BrickImage"));
        ball.setPaddleImage(this.artifacts.get("PaddleImage"));
        ball.setPaddleWidth(paddleWidth);
        ball.setPaddleHeight(paddleHeight);
        ball.initializeBrickArray();
        ball.setPaddleMoveAmount(paddleMovementAmount);
        ball.setPaddleLocation(appletHeight, appletWidth);
        message.setGameActive();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) this.called = ball.movePaddleLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) this.called = ball.movePaddleRight();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) this.called = message.setGameActive();
        if (e.getKeyCode() == KeyEvent.VK_SPACE) this.called = ball.ballSetStart();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void start() {
        if (animatorThread == null)
            animatorThread = new Thread(this);

        animatorThread.start();
    }

    public void startGameIfActive() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        this.currentThread = Thread.currentThread();
    }

    private Boolean testThread() {
        return this.currentThread == animatorThread;
    }


    public Thread getThread() {
        return this.currentThread;
    }


    public void run() {
        executeThread();
    }

    private void executeThread() {
        while (testThread()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }

            initializeGame();
            runGame();
            checkForEndOfLevel();
            frame.repaint();
        }
    }

    private void initializeGame() {
        ball.setBallActive();
        ball.ballSetStop();
        ball.calculateCurrentLocation(appletWidth, appletHeight);
    }

    private void runGame() {
        this.gameStart = false;

        while (ball.checkBallActive() && ball.getBricksLeft()) {

            this.gameStart = true;

            ball.ballCalculations(this.appletWidth, this.appletHeight);
            frame.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }

        ball.initializeBall();
    }

    public String checkForEndOfLevel() {
        if (ball.getBricksLeft())
            return decreaseLives();
        return setEndOfLevel(NUM_LIVES);
    }

    private String setEndOfLevel(int numLives) {
        ball.startGame(level.getLevel());
        if (gameStart)
            background.getNextBackGroundImageFileName();
        message.setGameStart(level.getLevel(), level.getLives());
        level.newLevel(numLives);
        return "Level Finished";
    }

    private String decreaseLives() {
        message.setLives(level.decreaseLives());

        if (level.stillAlive())
            return "Game On";

        setGameOver();
        return "Game Over";
    }

    public void setGameOver() {
        level.setGameStart();
        message.setGameOver(level.getLevel(), level.getLives());
        ball.startGame(level.getLevel());
        background.setStart();
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            background.drawBackground(g, appletWidth, appletHeight);

            if (message.getGameActive()) {
                ball.drawWindow(g);
                message.displayGameStatsAtBottomOfScreen(g, appletWidth, appletHeight);
            } else
                message.displayMessage(g, 300, 79, appletWidth, appletHeight);
        }
    }
}

