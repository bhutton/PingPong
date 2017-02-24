package PingPong;

import java.applet.Applet;
import java.awt.Dimension;
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
	
	String 	ballImg = "../src/PingPong/ball.png",
			brickImg = "../src/PingPong/brick.png",
			paddleImg = "../src/PingPong/paddle.png"; 
	
	
	Ball pp = new Ball(true, true, 0, 0);	
	
	String s = "";
	
	public void init() {
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
		
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		Thread currentThread = Thread.currentThread();

	    while (currentThread == animatorThread) {
	    	
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
	}
	
	public void paint(Graphics g) {
		pp.drawWall(g);
	    pp.drawBall(g);
	    pp.drawPaddle(g);
	}
}
