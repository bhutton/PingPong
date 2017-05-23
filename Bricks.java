package PingPong;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bricks {
	
	// Contains the x,y coordinates and whether the brick is active or not.
	private int[][][] brickArray = new int[225][830][3];
	
	private int brickColumnLocation = 0, 
				brickRowLocation = 0, 
				numRows=2, 
				numCols, 
				brickHeight=50,

				brickIsActive=0, 	
				brickHasBeenTaken=1;
	
	private int x,y;
	private Boolean down,right;

	private BufferedImage imgBrick = null;
	private int ballWidth;

	public Bricks() {
		initializeBrickArray();
	}
	
	public Bricks(String brickImg) {
		initializeBrickArray();
		setBrickImage(brickImg);
	}
	
	public void setWallDimensions(int cols, int rows) {
		this.numCols = cols;
		this.numRows = rows;
	}

	// Initialize array and set all bricks as active
	public void initializeBrickArray() {
		for (int count = 0; count < this.numCols; count++) {
			for (int counter = 0; counter < this.numRows; counter++) {
				this.brickArray[count][counter][2] = brickIsActive;
			}
		}
	}
	
	/*
	 * Setup array that holds the x,y coordinates of the wall
	 */
	public void createWall(int numLevels) {
		
		// Screen coordinates of the individual bricks
		int x=5, y=5;
		this.numRows = numLevels;
		
		setColLoc(0);
	    
	    for (int col = 0; col < 8; col++) {	    	
	    	setRowLoc(0); 
	    	y = 5;	
	    	
	    	for (int row = 0; row < this.numRows; row++) {
	    		setBrickYCoord(y).setBrickXCoord(x).incRowLoc(); 
	    		y += 55;
		    }
		    
		    incColLoc(); x += 105;
	    }
	    
	    setNumRows().setNumCols();
	}		
	
	public int returnSizeOfBrickArray() {
		return this.brickArray.length;
	}
	
	// Import brick image
	public void setBrickImage(String brickImg) {
		try { imgBrick = ImageIO.read(new File(brickImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBrickImage() {
		return imgBrick;
	}
	
	/*
	 * Sets current column position in the bricks array
	 */
	public Bricks setBrickColumnLocation(int col) {
		this.brickColumnLocation = col;
		
		return this;
	}
	
	public int returnBrickColumnLocation() {
		return this.brickColumnLocation;
	}
	
	
	
	/*
	 * Sets current row position in the bricks array
	 */
	public Bricks setBrickRowLocation(int row) {
		this.brickRowLocation = row;
		
		return this;
	}
	
	public int returnBrickRowLocation() {
		return this.brickRowLocation;
	}
	
	/*
	 * Sets the screen X coordinate for a brick
	 */
	public Bricks setBrickXCoord(int x) {
		this.brickArray[brickColumnLocation][brickRowLocation][0] = x;
		
		return this;
	}
	
	/*
	 * Sets the screen Y coordinate for a brick
	 */
	public Bricks setBrickYCoord(int y) {
		this.brickArray[brickColumnLocation][brickRowLocation][1] = y;
		
		return this;
	}
	
	public int getBrickXCoord() {
		return this.brickArray[brickColumnLocation][brickRowLocation][0];
	}
	
	public int getBrickYCoord() {
		return this.brickArray[brickColumnLocation][brickRowLocation][1];
	}
	
	/*
	 * Enables brick
	 */
	public void setBrickEnabled(int enabled) {
		this.brickArray[brickColumnLocation][brickRowLocation][2] = enabled;
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
		return this.brickArray[brickColumnLocation][brickRowLocation][0];
	}
	
	/*
	 *  Return screen Y position of specific brick
	 */
	public int getBrickY() {
		return this.brickArray[brickColumnLocation][brickRowLocation][1];
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
	public Boolean checkBricks(int ballWidth) {

		this.ballWidth = ballWidth;
		
		if (this.y < ((this.brickHeight) * (getNumRows()))) {
			for (int count = 0; count < this.numCols; count++) 
    			for (int counter = 0; counter < this.numRows; counter++) {

    				this.setBrickColumnLocation(count).setBrickRowLocation(counter);
    				
    				if (checkActive(this.x, this.y, this.right))
    					return true;
    			}
		}

		return this.down;
	}
	
	public Bricks setBallX(int x) {
		this.x = x;
		return this;
	}
	
	public Bricks setBallY(int y) {
		this.y = y;
		return this;
	}
	
	public int getBallX() {
		return this.x;
	}
	
	public int getBallY() {
		return this.y;
	}
	
	public Bricks setBallDown(Boolean down) {
		this.down = down;
		return this;
	}
	
	public Boolean getBallDown() {
		return this.down;
	}
	
	public Bricks setBallRight(Boolean right) {
		this.right = right;
		return this;
	}
	
	public Boolean getBallRight() {
		return this.right;
	}
	
	public Boolean getBricksDown() {
		return this.down;
	}
	
	public int getBrickRowThatBallIsOn(int y) {
		for(int row = 0; row < this.numRows; row++)
			if ( y < ((row+1) * this.brickHeight) ) return row;
		
		return numRows-1;
	}
	
	public Boolean checkActive(int x, int y, Boolean leftRight) {
		int brickWidth=110;
		int left = x - (brickWidth - (this.ballWidth/2)),
			right = x + (brickWidth - (this.ballWidth/2)),
			row = getBrickRowThatBallIsOn(y);
		
		if (this.brickArray[brickColumnLocation][row][2] == brickHasBeenTaken)
			return false;
		
		if (getBrickX() >= left && getBrickX() <= right) {
			Boolean brickTaken = setBrickTaken();
			checkIfBallDownThenBounce(leftRight);
			return brickTaken;
		}

		return false;
	}

	private void checkIfBallDownThenBounce(Boolean leftRight) {
		if (this.getBallDown()) {
            if (leftRight)
                setBallRight(false);
            else
                setBallRight(true);
        }
	}

	/*
	 * Determine whether brick is currently active
	 */
	public boolean getActive() {
		return this.brickArray[this.brickColumnLocation][this.brickRowLocation][2] == this.brickIsActive;
	}
	
	/*
	 * Sets brick to be removed from screen
	 */
	public Boolean setBrickTaken() {		
		for (int row = this.numRows-1; row >= 0; row--) {
			if (this.brickArray[brickColumnLocation][row][2] == brickIsActive) {
				this.brickArray[brickColumnLocation][row][2] = brickHasBeenTaken;
						
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean getBricksLeft() {
		for (int row = 0; row < this.numRows; row++)
			for (int col = 0; col < this.numCols; col++) {
				if (this.brickArray[col][row][2] == brickIsActive) 
					return true;
			}
		
		return false;
	}
	
	public void drawWall(Graphics g) {
		for (brickRowLocation = 0; brickRowLocation < numRows; brickRowLocation++)
			for (brickColumnLocation = 0; brickColumnLocation < numCols; brickColumnLocation++)
				drawBrick(g);
	}
	
	public void drawBrick(Graphics g) {
		if (getActive()) 
			g.drawImage(getBrickImage(), getBrickXCoord(), getBrickYCoord(), null);
	}
	

}
