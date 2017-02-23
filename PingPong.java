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
			brickImg = "../src/PingPong/brick.png"; 
	
	
	Ball game = new Ball(true, true, 0, 0);	
	
	String s = "";
	
	public void init() {
		setSize(853,600);
		
		Dimension appletSize = this.getSize();
	    this.appletHeight = appletSize.height;
	    this.appletWidth = appletSize.width;
	    
	    game.setBallImage(ballImg);
	    game.setBrickImage(brickImg);
	    game.setPaddleWidth(200);
	    game.setPaddleHeight(30);
	    game.initializeBrickArray();
	    game.createWall();
	    game.setPaddleLocation(appletHeight, appletWidth);
	    mouseListener();
	    addKeyListener(this);
	    
	    
	}
	
	public void mouseListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
	        
				/*if (frozen) {
					frozen = false;
					start();
				} 
				else {
					frozen = true;
					stop();
				}*/
			}
		});
	}
	
	
	
	public void keyPressed( KeyEvent e ) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) game.movePaddleLeft();
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) game.movePaddleRight();
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
	    	
	    	game.calculateCurrentLocation(appletWidth, appletHeight);
	    	game.setBallDirectionAfterReachingBricks();
	    	game.setBallDirectionAfterReachingPaddle();
	    	
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
		game.drawWall(g);
	    game.drawBall(g);
	    game.drawPaddle(g);
	}
}
