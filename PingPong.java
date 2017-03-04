package PingPong;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.event.KeyAdapter;


public class PingPong extends Applet implements KeyListener,Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int appletHeight, appletWidth, incX=1, incY=1, delay = 100;
	
	Thread animatorThread;
	
	boolean frozen = false; 
	
	private BufferedImage 	imgBall = null,
							imgGameOver = null,
							imgBackground = null;
	
	
	
	
	Ball pp = new Ball(true, true, 0, 0);
	Messages msg = new Messages();
	Background bg = new Background();
	
	String s = "";
	
	public void init() {
		String 	ballImg = "../src/PingPong/images/soccer-ball-clipart-no-background-clipart-panda-free-clipart-Ek7jBT-clipart.png",
				brickImg = "../src/PingPong/images/brick.png",
				paddleImg = "../src/PingPong/images/paddle.png",
				gameOverImg = "../src/PingPong/images/free-game-wallpaper-9.jpg",
				gameOverMessage = "../src/PingPong/images/game-over-png-22.png",
				backGroundImg = "../src/PingPong/images/12-vector-game-backgrounds-8320_imgs_8320.png";
	
		
		setSize(853,600);
		
		/*try { imgGameOver = ImageIO.read(new File(gameOverImg)); } 
		catch (IOException e) { e.printStackTrace(); }*/
		
		/*try { imgBackground = ImageIO.read(new File(backGroundImg)); } 
		catch (IOException e) { e.printStackTrace(); }*/
		
		
		Dimension appletSize = this.getSize();
	    this.appletHeight = appletSize.height;
	    this.appletWidth = appletSize.width;
	    
	    bg.setBackgroundImage(backGroundImg);
	    bg.setGameOverImage(gameOverImg);
	    msg.setGameOverImage(gameOverMessage);
	    pp.setBallImage(ballImg);
	    pp.setBrickImage(brickImg);
	    pp.setPaddleImage(paddleImg);
	    pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.initializeBrickArray();
	    pp.createWall();
	    pp.setPaddleLocation(appletHeight, appletWidth);
	    //mouseListener();
	    addKeyListener(this);
	}
	
	/*public void mouseListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
	        
				if (frozen) {
					frozen = false;
					start();
				} 
				else {
					frozen = true;
					stop();
				}
			}
		});
	}*/
	
	
	
	public void keyPressed( KeyEvent e ) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) pp.movePaddleLeft();
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) pp.movePaddleRight();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) msg.setGameActive();
		if (e.getKeyCode() == KeyEvent.VK_SPACE) pp.ballSetStart();
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

	public void run() {
		
		msg.setGameActive();
		
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		Thread currentThread = Thread.currentThread();
		
		pp.initializeBall();

	    while (currentThread == animatorThread) {
	    	
	    	pp.initializeBrickArray();
		    pp.createWall();
	    	
	    	pp.setBallActive();
	    	pp.ballSetStop();
	    	
	    	pp.calculateCurrentLocation(appletWidth, appletHeight);
	    	
	    	
	    	while (pp.checkBallActive() && pp.getBricksLeft()) {
		    	pp.calculateCurrentLocation(appletWidth, appletHeight);
		    	pp.setBallDirectionAfterReachingBricks();
		    	pp.setBallDirectionAfterReachingPaddle();
		    	
		    	repaint();
		    	
		    	try {
		    		Thread.sleep(10);
		    	} 
		    	catch (InterruptedException e) {
		    		break;
		    	}
	    	}
	    	
	    	pp.initializeBall();
	    	msg.setGameOver();
		    repaint();
	    }
	    
	}
	
	public void paint(Graphics g) {
		
		bg.drawBackground(g, appletWidth, appletHeight);
		
		if (msg.getGameActive()) {
			pp.drawWall(g);
		    pp.drawBall(g);
		    pp.drawPaddle(g);
		}
		else {
			//bg.drawGameOver(g, appletWidth, appletHeight);
			msg.displayMessage(g, 300, 79, appletWidth, appletHeight);
		}
	}
}
