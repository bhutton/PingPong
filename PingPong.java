package PingPong;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

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
	
	
	Game game = new Game(true, true, 0, 0);	
	
	String s = "";
	
	public void init() {
		setSize(853,600);
		
		Dimension appletSize = this.getSize();
	    this.appletHeight = appletSize.height;
	    this.appletWidth = appletSize.width;
	    
	    game.setBallImage(ballImg);
	    game.setBrickImage(brickImg);
	    game.initializeBrickArray();
	    game.createWall();
	    mouseListener();
	    
	}
	
	public void mouseListener() {
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
	    	
	    	game.calculateCurrentLocation(appletWidth, appletHeight);
	    	game.setBallDirectionAfterReachingBricks();
	    	
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
	}
}
