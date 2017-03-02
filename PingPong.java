package PingPong;

import java.applet.Applet;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;


public class PingPong extends Applet implements KeyListener,Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int appletHeight, appletWidth, incX=1, incY=1, delay = 100;
	
	Thread animatorThread;
	
	boolean frozen = false; 
	
	
	
	
	Ball pp = new Ball(true, true, 0, 0);	
	Messages msg = new Messages();
	
	String s = "";
	
	public void init() {
		String 	ballImg = "../src/PingPong/ball.png",
				brickImg = "../src/PingPong/brick.png",
				paddleImg = "../src/PingPong/paddle.png"; 
		
		setSize(853,600);
		
		Dimension appletSize = this.getSize();
	    this.appletHeight = appletSize.height;
	    this.appletWidth = appletSize.width;
	    
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

	    while (currentThread == animatorThread) {
	    	
	    	pp.initializeBrickArray();
		    pp.createWall();
	    	
	    	pp.setBallActive();
	    	
	    	while (pp.checkBallActive()) {
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
	    	
	    	msg.setGameOver();
		    repaint();
	    }
	    
	}
	
	public void paint(Graphics g) {
		if (msg.getGameActive()) {
			pp.drawWall(g);
		    pp.drawBall(g);
		    pp.drawPaddle(g);
		}
		else msg.displayMessage(g, appletWidth, appletHeight);
	}
}
