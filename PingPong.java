package PingPong;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

//import PingPong.Ball;
//import PingPong.Bricks;

public class PingPong extends Applet implements KeyListener,Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int appletHeight, appletWidth, incX=1, incY=1, delay = 100;
	
	Thread animatorThread;
	
	boolean frozen = false; 
	
	String 	ballImg = "../src/PingPong/ball.png",
			brickImg = "../src/PingPong/brick-green.png"; 
	
	
	Ball ballRef, ball;
	Bricks brickRef, bricks;
	
	
	String s = "";
	
	public void main() {
		// Get Windows Dimensions
		Dimension appletSize = this.getSize();
	    this.appletHeight = appletSize.height;
	    this.appletWidth = appletSize.width;
			    
		bricks = new Bricks(50, 100, 0, 0);
		ball = new Ball(true, true, 0, 0);
		
		bricks.initializeArray();
		bricks.setBrickImage(brickImg);
		
		ballRef = bricks;
		//brickRef = ball;
		
		
		
	}
	
	//public void init(){
		
		
		
		// Initialize Window Size
		//setSize(800,600);
		
		//addKeyListener(new MyListener);
		//addKeyListener( this );
		
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
	//}
	
	public void keyPressed( KeyEvent e ) { }
	   public void keyReleased( KeyEvent e ) { }
	   public void keyTyped( KeyEvent e ) {
	      char c = e.getKeyChar();
	 
	      if ( c != KeyEvent.CHAR_UNDEFINED ) {
	         s = s + c;
	         repaint();
	         e.consume();
	         
	      }
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
	    	
	    	ball.calculateLocation(appletWidth, appletHeight);
	    	//ball.setDirection(bricks.checkBricks(ball.getDown(), ball.getRight(), ball.getX(), ball.getY()));
	    	ball.setDirection();
	    	
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
		/*Dimension appletSize = this.getSize();
	    appletHeight = appletSize.height;
	    appletWidth = appletSize.width;*/
	   
	    bricks.setColLoc(0);
	    
	    // Draw bricks at top of screen
	    for (int count = 0; count < 8; count++) {
	    	
	    	bricks.setRowLoc(0);
	    	
	    	// Set X Coordinate of Brick
	    	bricks.setBrickX(bricks.getColLoc()).setBrickXCoord(x);;
	    	
	    	y = 5;
	    	
	    	for (int counter = 0; counter < 2; counter++) {

	    		// Set Y Coordinate of Brick
		    	bricks.setBrickY(bricks.getRowLoc()).setBrickYCoord(y);
		    	
		    	//isActive = bricks.getActive() ? g.drawImage(bricks.getBrick(), x, y, null) : null;
		    	
		    	if (bricks.getActive()){
		    		g.drawImage(bricks.getBrick(), x, y, null);
		    		g.drawImage(bricks.getBrick(), x+50, y, null);
		    	}
		    			    	
		    	bricks.incRowLoc();
		    	y += 55;
		    }
		    
		    bricks.incColLoc();
		    x += 105;
	    }
	    
	    bricks.setNumRows().setNumCols();
	    
	    // Draw ball
	    g.drawImage(ball.getBall(), ball.getX(), ball.getY(), null);
	}
}
