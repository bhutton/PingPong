package com.pingpong;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
class PingPong extends JFrame implements KeyListener,Runnable
{
    private JFrame frame;
    private DrawPanel drawPanel;

	private Thread animatorThread;
	private Thread currentThread = null;
    int appletHeight = 600, appletWidth = 853;
    boolean gameStart=false, called=false;

    private HashMap<String,String> artifacts = new HashMap<>();

    final Ball pp = new Ball();
	private final Messages msg = new Messages();
	final Background bg = new Background();
	private final File basePath = new File(
	        PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
    );
	final Level level = new Level();

	public static void main(String[] args)
    {
        new PingPong().go();
    }

    private void go()
    {
        drawPanel = new PingPong.DrawPanel();

        frame = new JFrame("com/pingpong");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setResizable(false);
        frame.setSize(853, 625);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addKeyListener(this);

        String imagePath = "/com/pingpong/images/";

        artifacts.put("BallImage",this.basePath + imagePath + "ball.png");
        artifacts.put("BrickImage",this.basePath + imagePath + "brick.png");
        artifacts.put("PaddleImage",this.basePath + imagePath + "paddle.png");
        artifacts.put("GameOverImage",this.basePath + imagePath + "game-over.jpg");
        artifacts.put("GameOverMessage",this.basePath + imagePath + "game-over-message.png");

        this.bg.loadBackGrounds(this.basePath);
        initialiseGame();

        run();
    }

    private void initialiseGame()
    {
        bg.setGameOverImage(this.artifacts.get("GameOverImage"));
        msg.setGameOverImage(this.artifacts.get("GameOverMessage"));
        pp.setBallImage(this.artifacts.get("BallImage"));
        pp.setBrickImage(this.artifacts.get("BrickImage"));
        pp.setPaddleImage(this.artifacts.get("PaddleImage"));
        pp.setPaddleWidth(174);
        pp.setPaddleHeight(30);
        pp.initializeBrickArray();
        pp.setPaddleMoveAmount(30);
        pp.setPaddleLocation(appletHeight, appletWidth);
        msg.setGameActive();
    }

    public void keyPressed( KeyEvent e )
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) this.called = pp.movePaddleLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) this.called = pp.movePaddleRight();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) this.called = msg.setGameActive();
        if (e.getKeyCode() == KeyEvent.VK_SPACE) this.called = pp.ballSetStart();
    }
    public void keyReleased( KeyEvent e ) {	}
    public void keyTyped( KeyEvent e ) { }

    public void start()
    {
        if (animatorThread == null)
            animatorThread = new Thread(this);

        animatorThread.start();
    }

    public void startGameIfActive()
    {
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
            }
            catch (InterruptedException e) {
                break;
            }

            initializeGame();
            runGame();
            checkForEndOfLevel();
            frame.repaint();
        }
    }

    private void initializeGame() {
        pp.setBallActive();
        pp.ballSetStop();
        pp.calculateCurrentLocation(appletWidth, appletHeight);
    }

    private void runGame() {
        this.gameStart = false;

        while (pp.checkBallActive() && pp.getBricksLeft()) {

            this.gameStart = true;

            pp.ballCalculations(this.appletWidth, this.appletHeight);
            frame.repaint();

            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                break;
            }
        }

        pp.initializeBall();
    }

    public String checkForEndOfLevel() {
        int numLives = 3;
        if (pp.getBricksLeft())
            return decreaseLives();
        return setEndOfLevel(numLives);
    }

    private String setEndOfLevel(int numLives) {
        pp.startGame(level.getLevel());
        if (gameStart)
            bg.getNextBackGroundImageFileName();
        msg.setGameStart(level.getLevel(), level.getLives());
        level.newLevel(numLives);
        return "Level Finished";
    }

    private String decreaseLives() {
        msg.setLives(level.decreaseLives());

        if (level.stillAlive())
            return "Game On";

        setGameOver();
        return "Game Over";
    }

    public void setGameOver() {
        level.setGameStart();
        msg.setGameOver(level.getLevel(), level.getLives());
        pp.startGame(level.getLevel());
        bg.setStart();
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            bg.drawBackground(g, appletWidth, appletHeight);

            if (msg.getGameActive()) {
                pp.drawWindow(g);
                msg.displayGameStatsAtBottomOfScreen(g, appletWidth, appletHeight);
            }
            else
                msg.displayMessage(g, 300, 79, appletWidth, appletHeight);
        }
    }
}

