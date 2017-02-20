package PingPong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bricks {
	
	// Contains the x,y coordinates and whether the brick is active or not.
	private int[][][] takenBricks = new int[225][830][3];
	
	private int brickColumnLocation = 0, // Points to the relevant column in the array
				brickRowLocation = 0, // Points to the relevant row in the array
				numRows, 
				numCols, 
				brickHeight=50, 
				brickWidth=110, 
				active=0, 	// The brick should be displayed
				taken=1;	// The brick should not be displayed
	
	private int x,y;
	private Boolean down,right;

	BufferedImage imgBrick = null;
	
	public Bricks() {
		initializeArray();
	}
	
	public Bricks(String brickImg) {
		initializeArray();
		setBrickImage(brickImg);
	}

	// Initialize array and set all bricks as active
	public void initializeArray() {
		for (int count = 0; count < 8; count++) {
			for (int counter = 0; counter < 3; counter++) {
				this.takenBricks[count][counter][2] = active;
			}
		}
	}
	
	// Import brick image
	public void setBrickImage(String brickImg) {
		try { imgBrick = ImageIO.read(new File(brickImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBrick() {
		return imgBrick;
	}
	
	/*
	 * Sets current column position in the bricks array
	 */
	public Bricks setBrickColumnLocation(int col) {
		this.brickColumnLocation = col;
		
		return this;
	}
	
	/*
	 * Sets current row position in the bricks array
	 */
	public Bricks setBrickRowLocation(int row) {
		this.brickRowLocation = row;
		
		return this;
	}
	
	/*
	 * Sets the screen X coordinate for a brick
	 */
	public Bricks setBrickXCoord(int x) {
		this.takenBricks[brickColumnLocation][brickRowLocation][0] = x;
		
		return this;
	}
	
	/*
	 * Sets the screen Y coordinate for a brick
	 */
	public Bricks setBrickYCoord(int y) {
		this.takenBricks[brickColumnLocation][brickRowLocation][1] = y;
		
		return this;
	}
	
	public int getBrickXCoord() {
		return this.takenBricks[brickColumnLocation][brickRowLocation][0];
	}
	
	public int getBrickYCoord() {
		return this.takenBricks[brickColumnLocation][brickRowLocation][1];
	}
	
	/*
	 * Enables brick
	 */
	public void setBrickEnabled(int enabled) {
		this.takenBricks[brickColumnLocation][brickRowLocation][2] = enabled;
	}
	
	/*
	 * Sets number of rows displayed 
	 */
	public Bricks setNumRows() {
		this.numRows = brickRowLocation;
		
		return this;
	}
	
	/*
	 * Sets number of columns displayed
	 */
	public Bricks setNumCols() {
		this.numCols = brickColumnLocation;
		
		return this;
	}
	
	public int getNumRows() {
		return this.numRows;
	}
	
	public int getNumCols() {
		return this.numCols;
	}
	
	/*
	 * Return screen X position of specific brick
	 */
	public int getBrickX() {
		return this.takenBricks[brickColumnLocation][brickRowLocation][0];
	}
	
	/*
	 *  Return screen Y position of specific brick
	 */
	public int getY() {
		return this.takenBricks[brickColumnLocation][brickRowLocation][1];
	}
	
	public Bricks setColLoc(int col) {
		brickColumnLocation = col;
		
		return this;
	}
	
	public void incColLoc() {
		brickColumnLocation++;
	}
	
	public int getColLoc() {
		return brickColumnLocation;
	}
	
	public Bricks setRowLoc(int row) {
		brickRowLocation = row;
		
		return this;
	}
	
	public void incRowLoc() {
		brickRowLocation++;
	}
	
	public void decRowLoc() {
		brickRowLocation--;
	}
	
	
	public int getRowLoc() {
		return brickRowLocation;
	}
	
	
	
	public int wallHeight() {
		return getNumRows() * 5;
	}
	
	/*
	 * Check whether ball intersects with an active brick
	 * and if so remove it then reverse direction of ball
	 */
	public Boolean checkBricks() {
		
		if (y < ((brickHeight) * (getNumRows()))) {
			for (int count = 0; count < this.numCols; count++) 
    			for (int counter = 0; counter < this.numRows; counter++) {
    				this.setBrickColumnLocation(count).setBrickRowLocation(counter);
    				
    				if (checkActive(x, right)) return true;
    			}
		}
		
		return down;
	}
	
	public Bricks setBallX(int x) {
		this.x = x;
		return this;
	}
	
	public Bricks setBallY(int y) {
		this.y = y;
		return this;
	}
	
	public Bricks setBallDown(Boolean down) {
		this.down = down;
		return this;
	}
	
	public Bricks setBallRight(Boolean right) {
		this.right = right;
		return this;
	}
	
	/*
	 * Setup array that holds the x,y coordinates of the wall
	 */
	public void createWall() {
		
		// Screen coordinates of the individual bricks
		int x=5, y=5;
		
		setColLoc(0);
	    
	    for (int col = 0; col < 8; col++) {	    	
	    	setRowLoc(0); 
	    	y = 5;	
	    	
	    	for (int row = 0; row < 2; row++) {
	    		setBrickYCoord(y).setBrickXCoord(x).incRowLoc(); 
	    		y += 55;
		    }
		    
		    incColLoc(); x += 105;
	    }
	    
	    setNumRows().setNumCols();
	}		   
	
	public Boolean checkActive(int x, Boolean leftRight) {
		int left = x-brickWidth, right = x+brickWidth;

		if (getBrickX() >= left && getBrickX() <= right) return setBrickTaken();			
		return false;
	}
	
	/*
	 * Determine whether brick is currently active
	 */
	public boolean getActive() {
		if (this.takenBricks[brickColumnLocation][brickRowLocation][2] == active) return true;
		return false;
	}
	
	/*
	 * Sets brick to be removed from screen
	 */
	public Boolean setBrickTaken() {
		
		for (int row = this.numRows-1; row >= 0; row--) {
			if (this.takenBricks[brickColumnLocation][row][2] == active) {
				this.takenBricks[brickColumnLocation][row][2] = taken;
				
				return true;
			}
		}
		
		return false;
	}
	
	

}
