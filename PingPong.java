package PingPong;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;

import PingPong.Ball;
import PingPong.Bricks;

public class PingPong extends Applet implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int appletHeight, appletWidth, incX=1, incY=1, delay = 100;
	
	Thread animatorThread;
	
	boolean frozen = false; 
	
	String 	ballImg = "../src/PingPong/ball.png",
			brickImg = "../src/PingPong/brick-green.png"; 
	
	Ball ball = new Ball(ballImg);
	Bricks bricks = new Bricks(brickImg);
	
	
	public void init(){
		
		// Initialize Window Size
		setSize(800,600);
		
		/*addMouseListener(new MouseAdapter() {
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
		});*/
	}
	
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
	    	
	    	ball.calculateLocation(bricks.getNumRows(), appletWidth, appletHeight);
	    	ball.setDirection(bricks.checkBricks(ball.getDown(), ball.getX(), ball.getY()));
	    	
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
		
		//int brickX = 0, brickY = 0;
		int x=5,y=5;
		Boolean isActive;
		
		// Get Windows Dimensions
		Dimension appletSize = this.getSize();
	    appletHeight = appletSize.height;
	    appletWidth = appletSize.width;
	   
	    // Draw ball
	    g.drawImage(ball.getBall(), ball.getX(), ball.getY(), null);
	    
	    bricks.setColLoc(0);
	    
	    // Draw bricks at top of screen
	    for (int count = 0; count < 15; count++) {
	    	
	    	bricks.setRowLoc(0);
	    	
	    	// Set X Coordinate of Brick
	    	bricks.setBrickX(bricks.getColLoc()).setBrickXCoord(x);;
	    	
	    	y = 5;
	    	
	    	for (int counter = 0; counter < 2; counter++) {

	    		// Set Y Coordinate of Brick
		    	bricks.setBrickY(bricks.getRowLoc()).setBrickYCoord(y);
		    	
		    	isActive = bricks.getActive() ? g.drawImage(bricks.getBrick(), x, y, null) : null; 		    	
		    			    	
		    	bricks.incRowLoc();
		    	y += 55;
		    }
		    
		    bricks.incColLoc();
		    x += 55;
	    }
	    
	    bricks.setNumRows().setNumCols();
	}
}
