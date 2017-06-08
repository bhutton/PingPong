package PingPong;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class PingPong extends JFrame implements KeyListener,Runnable {
    private JFrame frame;
    DrawPanel drawPanel;

    int appletHeight = 600, appletWidth = 853;
	private Thread animatorThread;
    boolean gameStart=false, called=false;
	private Thread currentThread = null;

	Ball pp = new Ball(true, true, 0, 0);
	Messages msg = new Messages();
	Background bg = new Background();
	final File basePath = new File(PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	Level level = new Level();

	public static void main(String[] args) {
        new PingPong().go();
    }

    public void go() {
        drawPanel = new PingPong.DrawPanel();

        frame = new JFrame("PingPong");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setResizable(false);
        frame.setSize(853, 625);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addKeyListener(this);

        String 	ballImg = this.basePath + "/../src/PingPong/images/soccer-ball-clipart-no-background-clipart-panda-free-clipart-Ek7jBT-clipart.png",
                brickImg = this.basePath + "/../src/PingPong/images/brick.png",
                paddleImg = this.basePath + "/../src/PingPong/images/paddle.png",
                gameOverImg = this.basePath + "/../src/PingPong/images/free-game-wallpaper-9.jpg",
                gameOverMessage = this.basePath + "/../src/PingPong/images/game-over-png-22.png";

        this.bg.loadBackGrounds(this.basePath);
        initialiseGame(ballImg, brickImg, paddleImg, gameOverImg, gameOverMessage);

        run();
    }

    private void initialiseGame(String ballImg, String brickImg, String paddleImg, String gameOverImg, String gameOverMessage) {
        bg.setGameOverImage(gameOverImg);
        msg.setGameOverImage(gameOverMessage);
        pp.setBallImage(ballImg);
        pp.setBrickImage(brickImg);
        pp.setPaddleImage(paddleImg);
        pp.setPaddleWidth(174);
        pp.setPaddleHeight(30);
        pp.initializeBrickArray();
        pp.setPaddleMoveAmount(30);
        pp.setPaddleLocation(appletHeight, appletWidth);
        msg.setGameActive();
    }

    public void keyPressed( KeyEvent e ) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) this.called = pp.movePaddleLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) this.called = pp.movePaddleRight();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) this.called = msg.setGameActive();
        if (e.getKeyCode() == KeyEvent.VK_SPACE) this.called = pp.ballSetStart();
    }
    public void keyReleased( KeyEvent e ) {	}
    public void keyTyped( KeyEvent e ) { }

    public void start() {
        if (animatorThread == null)
            animatorThread = new Thread(this);

        animatorThread.start();
    }

    public void startGameIfActive() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        this.currentThread = Thread.currentThread();
    }

    public Boolean testThread() {
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

        return setGameOver();
    }

    public String setGameOver() {
        level.setGameStart();
        msg.setGameOver(level.getLevel(), level.getLives());
        pp.startGame(level.getLevel());
        bg.setStart();
        return "Game Over";
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

