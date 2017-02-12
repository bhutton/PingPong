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
		//if (!frozen) {
		
		if (animatorThread == null) 
			animatorThread = new Thread(this);
		
		animatorThread.start();
		
		
		//}
			 
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
		
		int brickX = 0, brickY = 0;
		Boolean isActive;
		
		// Get Windows Dimensions
		Dimension appletSize = this.getSize();
	    appletHeight = appletSize.height;
	    appletWidth = appletSize.width;
	   
	    // Draw ball
	    g.drawImage(ball.getBall(), ball.getX(), ball.getY(), null);
	    
	    bricks.setColLoc(0).setRowLoc(0);
	    
	    // Draw bricks at top of screen
	    for (int count = 5; count < 800; count += 55) {
	    	
	    	// Set X Coordinate of Brick
	    	bricks.setBrickX(bricks.getColLoc()).setBrickY(bricks.getRowLoc()).setBrickXCoord(count);
	    	
	    	bricks.setRowLoc(0);
	    	
		    for (int counter = 5; counter <= bricks.wallHeight(); counter += 55) {
		    	
		    	// Set Y Coordinate of Brick
		    	bricks.setBrickY(bricks.getRowLoc()).setBrickYCoord(counter);
		    	
		    	isActive = bricks.getActive() ? g.drawImage(bricks.getBrick(), count, counter, null) : null; 		    	
		    			    	
		    	bricks.incRowLoc();
		    }
		    
		    bricks.incColLoc();
	    }
	    
	    bricks.setNumRows().setNumCols();
	}
}
