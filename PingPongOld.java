package PingPong;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class PingPongOld extends Applet implements KeyListener,Runnable {
	private static final long serialVersionUID = 1L;
	int appletHeight, appletWidth, incX=1, incY=1;
	private static int delay = 100;
	private Thread animatorThread;
	boolean frozen = false, called=false;
	private Thread currentThread = null;

	Ball pp = new Ball(true, true, 0, 0);
	Messages msg = new Messages();
	Background bg = new Background();
	final File basePath = new File(PingPongOld.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	Level level = new Level();

	String[] backGroundArray = new String[10];
	String backGroundImg = this.basePath + "/../src/PingPong/images/background-1.png";
	int currentBackGroundImage;

    public void init() {

		backGroundArray = this.bg.loadBackGrounds(this.basePath);
        this.currentBackGroundImage = 0;

        String 	ballImg = this.basePath + "/../src/PingPong/images/soccer-ball-clipart-no-background-clipart-panda-free-clipart-Ek7jBT-clipart.png",
                brickImg = this.basePath + "/../src/PingPong/images/brick.png",
                paddleImg = this.basePath + "/../src/PingPong/images/paddle.png",
                gameOverImg = this.basePath + "/../src/PingPong/images/free-game-wallpaper-9.jpg",
				gameOverMessage = this.basePath + "/../src/PingPong/images/game-over-png-22.png";

        setSize(853,600);
		
		Dimension appletSize = this.getSize();
	    this.appletHeight = appletSize.height;
	    this.appletWidth = appletSize.width;

	    setFocusable(true);
	    requestFocus();

	    bg.setBackgroundImage(backGroundImg);
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
	    addKeyListener(this);
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

	public void stop() {
	    animatorThread = null;
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
		msg.setGameActive();
		startGameIfActive();
		pp.initializeBall();
		msg.setLevel(level.getLevel());
		msg.setLives(level.getLives());
		
	    executeThread();
	}

	private void executeThread() {
		while (testThread()) {
	    	initializeGame();
	    	runGame();
	    	endOfLevel();
		    repaint();
	    }
	}

	private void endOfLevel() {
        int numLives = 3;
		if (pp.getBricksLeft())
			if (level.getLives() > 1) {
				level.decreaseLives();
				msg.setLives(level.getLives());
			}
			else {
				msg.setGameOver();
				level.setLevel(1);
				level.setLives(3);
				msg.setLevel(level.getLevel());
				msg.setLives(level.getLives());
				pp.initializeBrickArray();
				pp.createWall(level.getLevel());
			}
		else {
		    level.setLives(numLives);
            pp.initializeBrickArray();
            pp.createWall(level.getLevel());
			this.backGroundImg = bg.getBackGroundImageFileName();
            bg.setBackgroundImage(this.backGroundImg);
			msg.setLives(level.getLives());
			msg.setLevel(level.getLevel());
            level.incrementLevel();
            this.backGroundImg = bg.getNextBackGroundImageFileName();

        }
	}

	private void runGame() {
		while (pp.checkBallActive() && pp.getBricksLeft()) {
			ballCalculations();
			repaint();
			
			try {
				Thread.sleep(10);
			} 
			catch (InterruptedException e) {
				break;
			}
		}
		
		pp.initializeBall();
	}

	private void ballCalculations() {
	    pp.calculateCurrentLocation(appletWidth, appletHeight);
		pp.setBallDirectionAfterReachingBricks();
		pp.setBallDirectionAfterReachingPaddle();
	}

	private void initializeGame() {
		pp.setBallActive();
		pp.ballSetStop();
		pp.calculateCurrentLocation(appletWidth, appletHeight);
	}
	
	public void paint(Graphics g) {
		bg.drawBackground(g, appletWidth, appletHeight);
		
		if (msg.getGameActive()) {
			pp.drawWall(g);
		    pp.drawBall(g);
		    pp.drawPaddle(g);
		    msg.displayGameStatsAtBottomOfScreen(g, appletWidth, appletHeight);
		}
		else 
			msg.displayMessage(g, 300, 79, appletWidth, appletHeight);
	}
}