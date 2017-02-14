package PingPong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bricks {
	
	private int[][][] takenBricks = new int[225][830][3];
	private int brickX = 0, brickY = 0, xCoord = 0, yCoord = 0, numRows, numCols, brickHeight=50;
	BufferedImage imgBrick = null;
	
	public Bricks(String brickImg) {
		initializeArray();
		setBrickImage(brickImg);
	}
	
	public void initializeArray() {
		// Initialize array and setting taken bricks to 0 indicating none
		for (int count = 0; count < 225; count++) {
			for (int counter = 0; counter < 830; counter++) {
				this.takenBricks[count][counter][2] = 0;
			}
		}
	}
	
	public void setBrickImage(String brickImg) {
		try { imgBrick = ImageIO.read(new File(brickImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBrick() {
		return imgBrick;
	}
	
	public Bricks setBrickX(int col) {
		this.brickX = col;
		
		return this;
	}
	
	public Bricks setBrickY(int y) {
		this.brickY = y;
		
		return this;
	}
	
	public void setBrickXCoord(int x) {
		this.takenBricks[brickX][brickY][0] = x;
	}
	
	public void setBrickYCoord(int y) {
		this.takenBricks[brickX][brickY][1] = y;
	}
	
	public void setBrickEnabled(int enabled) {
		this.takenBricks[brickX][brickY][2] = enabled;
	}
	
	public Bricks setNumRows() {
		this.numRows = brickY+1;
		
		return this;
	}
	
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
	
	public int getX() {
		return this.takenBricks[brickX][brickY][0];
	}
	
	public int getY() {
		return this.takenBricks[brickX][brickY][1];
	}
	
	public void setInActive() {
		this.takenBricks[brickX][brickY][2] = 1;
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
	
	public int getRowLoc() {
		return brickY;
	}
	
	public void checkActive(int x) {
		if (this.getX() >= x-60 && this.getX() <= x+120) {
			setInActive();
			
		}
		
		//return false;
	}
	
	public int wallHeight() {
		return getNumRows() * 5;
	}
	
	public Boolean checkBricks(Boolean down, int x, int y) {
		
		//Boolean isActive = false;
		
		if (y <= 0) return true;
		
		if (y < ((brickHeight + 5) * (getNumRows() - 1))) {
			for (int count = 0; count < this.numCols; count++) 
    			for (int counter = 0; counter < this.numRows; counter++) {
    				setBrickX(count).setBrickY(counter);
    				getActive();
    				
    				this.checkActive(x);

    			}
    		
			
			//if (isActive) return down; 
			
			//return down;
		}
		
		return down;

	}
	
	public boolean getActive() {
		if (this.takenBricks[brickX][brickY][2] == 0) return true;
		else return false;
	}
	
	

}
