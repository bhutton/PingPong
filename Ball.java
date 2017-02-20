package PingPong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Ball extends Bricks {
	int x,y,incX=1, incY=3, width=50, height=50, brickHeight = 50;
	boolean forward = true, right = true, down = true;
	
	BufferedImage imgBall = null;
	
	public Ball(Boolean down, Boolean right, int x, int y) {
		super(down,right,x,y);
	}
	
	/*public Ball(String ballImg) {
		setBallImage(ballImg);
	}*/
	
	public void setBallImage(String ballImg) {
		try { imgBall = ImageIO.read(new File(ballImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBall() {
		return imgBall;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void incX() {
		this.x += incX;
	}
	
	public void incY() {
		this.y += incY;
	}
	
	public void decX() {
		this.x -= incX;
	}
	
	public void decY() {
		this.y -= incY;
	}
	
	public Ball shiftX() {
		if (right) incX();
		else decX();
		
		return this;
	}
	
	public Ball shiftY() {
		if (down) incY();
		else decY();
		
		return this;
	}
	
	/*public void setX(int x) {
		this.x = x;
	}*/
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getDown() {
		return down;
	}
	
	public void setDown() {
		this.down = true;
	}
	
	public void setDirection() {
		//checkBricks(getDown(), getRight(), getX(), getY());
		down = checkBricks(getDown(), getRight(), this.getX(), this.getY());
		
		//this.down = down;
	}
	
	public Ball checkStart() {
		if (this.getY() == 0) this.y = ((brickHeight + 5) * 4);
		
		return this;
	}
	
	public Ball checkRight(int appletWidth) {
		if ((this.getX() + width + 2) > appletWidth) {
			this.right = false;
			incX = ThreadLocalRandom.current().nextInt(1, 5);
		}
		
		return this;
	}
	
	public Ball checkLeft() {
		if (this.getX() <= 0) {
			this.right = true;
			incX = ThreadLocalRandom.current().nextInt(1, 5);
		}
		
		return this;
	}
	
	public Ball checkTop() {
		if (this.getY() <= 0) {
			this.down = true;
			incX = ThreadLocalRandom.current().nextInt(1, 5);
		}
		
		return this;
	}
	
	public Ball checkBottom(int appletHeight) {
		if ((this.getY() + height + 2) > appletHeight) {
			this.down = false;
			incX = ThreadLocalRandom.current().nextInt(1, 5);
		}
		
		return this;
	}
	
	public void calculateLocation(int appletWidth, int appletHeight) {
		
		// check location start and whether ball hits edges
		checkStart().checkLeft().checkRight(appletWidth).checkTop().checkBottom(appletHeight);
    	
    	// Update ball coordinates
    	shiftX().shiftY();
  
	}
}
