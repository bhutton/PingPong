package PingPong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bricks {
	
	// Contains the x,y coordinates and whether the brick is active or not.
	private int[][][] takenBricks = new int[225][830][3];
	
	private int brickX = 0, // Points to the relevant column in the array
				brickY = 0, // Points to the relevant row in the array
				numRows, 
				numCols, 
				brickHeight=50, 
				brickWidth=100, 
				active=0, 	// The brick should be displayed
				taken=1;	// The brick should not be displayed

	BufferedImage imgBrick = null;
	
	public Bricks(Boolean down, Boolean right, int x, int y) {
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
	
	// Sets current column position in the bricks array
	public Bricks setBrickX(int col) {
		this.brickX = col;
		
		return this;
	}
	
	// Sets current row position in the bricks array
	public Bricks setBrickY(int row) {
		this.brickY = row;
		
		return this;
	}
	
	// Sets the screen X coordinate for a brick
	public Bricks setBrickXCoord(int x) {
		this.takenBricks[brickX][brickY][0] = x;
		
		return this;
	}
	
	// Sets the screen Y coordinate for a brick
	public Bricks setBrickYCoord(int y) {
		this.takenBricks[brickX][brickY][1] = y;
		
		return this;
	}
	
	// Enables brick
	public void setBrickEnabled(int enabled) {
		this.takenBricks[brickX][brickY][2] = enabled;
	}
	
	// Sets number of rows displayed
	public Bricks setNumRows() {
		this.numRows = brickY;
		
		return this;
	}
	
	// Sets number of columns displayed
	public Bricks setNumCols() {
		this.numCols = brickX+1;
		
		return this;
	}
	
	public int getNumRows() {
		return this.numRows;
	}
	
	public int getNumCols() {
		return this.numCols;
	}
	
	// Return screen X position of specific brick
	public int getX() {
		return this.takenBricks[brickX][brickY][0];
	}
	
	// Return screen Y position of specific brick
	public int getY() {
		return this.takenBricks[brickX][brickY][1];
	}
	
	public Bricks setColLoc(int x) {
		brickX = x;
		
		return this;
	}
	
	public void incColLoc() {
		brickX++;
	}
	
	public int getColLoc() {
		return brickX;
	}
	
	public Bricks setRowLoc(int y) {
		brickY = y;
		
		return this;
	}
	
	public void incRowLoc() {
		brickY++;
	}
	
	public void decRowLoc() {
		brickY--;
	}
	
	
	public int getRowLoc() {
		return brickY;
	}
	
	
	
	public int wallHeight() {
		return getNumRows() * 5;
	}
	
	// Check whether ball intersects with an active brick
	// and if so remove it then reverse direction of ball
	public Boolean checkBricks(Boolean down, Boolean right, int x, int y) {
		
		//if (y <= 0) return true;
		
		if (y < ((brickHeight) * (getNumRows()))) {
			for (int count = 0; count < this.numCols; count++) 
    			for (int counter = 0; counter < this.numRows; counter++) {
    				setBrickX(count).setBrickY(counter);
    				if (checkActive(x, right)) return true;
    			}
		}
		
		return down;
	}
	
	public Boolean checkActive(int x, Boolean leftRight) {
	
		int left = x-brickWidth, right = x+brickWidth;

		//Boolean direction = leftRight ? left=x, right =  x+brickWidth
		
		if (leftRight) {
			left = x;
			right = x+brickWidth;
		}
		
		if (getX() >= left && getX() <= right) {
			return setBrickTaken();	
		}
		
		return false;
	}
	
	// Determine whether brick is currently active
	public boolean getActive() {
		if (this.takenBricks[brickX][brickY][2] == active) return true;
		return false;
	}
	
	// Sets brick to be removed from screen
	public Boolean setBrickTaken() {
		
		for (int count = this.numRows-1; count >= 0; count--) {
			if (this.takenBricks[brickX][count][2] == active) {
				this.takenBricks[brickX][count][2] = taken;
				return true;
			}
		}
		
		return false;
	}
	
	

}
