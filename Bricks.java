package PingPong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bricks {
	
	private int[][][] takenBricks = new int[225][830][3];
	private int brickX = 0, brickY = 0, numRows, numCols, brickHeight=50;
	//, xCoord = 0, yCoord = 0
	BufferedImage imgBrick = null;
	
	public Bricks(String brickImg) {
		initializeArray();
		setBrickImage(brickImg);
	}
	
	public void initializeArray() {
		// Initialize array and setting taken bricks to 0 indicating none
		for (int count = 0; count < 81; count++) {
			for (int counter = 0; counter < 3; counter++) {
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
	
	public Bricks setBrickY(int row) {
		this.brickY = row;
		
		return this;
	}
	
	public Bricks setBrickXCoord(int x) {
		this.takenBricks[brickX][brickY][0] = x;
		
		return this;
	}
	
	public Bricks setBrickYCoord(int y) {
		this.takenBricks[brickX][brickY][1] = y;
		
		return this;
	}
	
	public void setBrickEnabled(int enabled) {
		this.takenBricks[brickX][brickY][2] = enabled;
	}
	
	public Bricks setNumRows() {
		this.numRows = brickY;
		
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
	
	public Boolean checkBricks(Boolean down, int x, int y) {
		
		//Boolean isActive = false;
		
		if (y <= 0) return true;
		
		if (y < ((brickHeight + 5) * (getNumRows()-1))) {
			
			for (int count = 0; count < this.numCols; count++) 
    			for (int counter = 0; counter < this.numRows; counter++) {
    				setBrickX(count).setBrickY(counter);
    				getActive();
    				
    				if (this.checkActive(x)) return true;
    				
    				System.out.println(this.takenBricks[brickX][brickY][2]);
    			}
			
		}
		
		return down;

	}
	
	public Boolean checkActive(int x) {
		System.out.println(this.getX());
		if (this.getX() >= x-60 && this.getX() <= x+120) {
			setInActive();
			
			return true;
			
		}
		
		return false;
	}
	
	public boolean getActive() {
		if (this.takenBricks[brickX][brickY][2] == 0) return true;
		else return false;
	}
	
	public void setInActive() {
		//System.out.println("brickY = " + brickY);
		// if Y = 2 is active first
		this.takenBricks[brickX][brickY][2] = 1;
	}
	
	

}
