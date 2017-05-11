package PingPong;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Ball extends Paddle {
	private int x,y,incX=1, incY=3, width=50;
	private boolean right = true, down = true, ballIsActive = true, ballStart = false;
	
	private BufferedImage imgBall = null;
	
	public Ball(Boolean down, Boolean right, int x, int y) {
		super();
	}
	
	public void setBallImage(String ballImg) {
		try { imgBall = ImageIO.read(new File(ballImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBall() {
		return imgBall;
	}
	
	public Ball setBallXValue(int x) {
		this.x = x;
		
		return this;
	}
	
	public Ball setBallYValue(int y) {
		this.y = y;
		
		return this;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getBrickY() {
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
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getDown() {
		return down;
	}
	
	public Ball setDown() {
		this.down = true;
		
		return this;
	}
	
	public Ball setBallUp() {
		this.down = false;
		
		return this;
	}
	
	public void setLeft() {
		this.right = false;
	}
	
	public void setRight() {
		this.right = true;
	}
	
	public Boolean setBallDirectionAfterReachingBricks() {
		setBallX(x).setBallY(y).setBallDown(down).setBallRight(right);
		down = checkBricks(width);

		if (getBallRight())
			setRight();
		else
			setLeft();

		return down;
	}
	
	public Boolean setBallDirectionAfterReachingPaddle() {
		setBallX(x).setBallY(y).setBallDown(down).setBallRight(right);
		down = checkPaddle(down, x, y);
		return down;
	}

	public Ball checkStart() {
        int brickHeight = 50;
		if (this.getBrickY() == 0)
			this.y = ((brickHeight + 5) * 4);
		
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
		if (this.getBrickY() <= 0) {
			this.down = true;
			incX = ThreadLocalRandom.current().nextInt(1, 5);
		}
		
		return this;
	}
	
	public Ball checkBottom(int appletHeight) {
		int height=50;

		if ((this.getBrickY() + height + 2) > appletHeight) {
			this.down = false;
			incX = ThreadLocalRandom.current().nextInt(1, 5);
			
			this.ballIsActive = false;
		}
		
		return this;
	}
	
	public void calculateCurrentLocation(int appletWidth, int appletHeight) {
		checkEdges(appletWidth, appletHeight);
    	updateBallCoordinates();
	}
	
	public Boolean checkBallActive() {
		return this.ballIsActive;
	}
	
	public Boolean setBallInActive() {
		return this.ballIsActive = false;
	}
	
	public Boolean setBallActive() {
		return this.ballIsActive = true;
	}
	
	public void checkEdges(int appletWidth, int appletHeight) {
		checkStart().checkLeft().checkRight(appletWidth).checkTop().checkBottom(appletHeight);    	
	}
	
	public void updateBallCoordinates() {
		if (ballStart)
			shiftX().shiftY();

		if (!getBallStatus())
			this.x = getPaddleX() + (getPaddleWidth() / 2) - 30;
	}
	
	public void drawBall(Graphics g) {
		g.drawImage(this.imgBall, this.x, this.y, null);
	}
	
	public void initializeBall() {
		this.x = this.getPaddleX();
		this.y = this.getPaddleY()-50;
	}
	
	public Boolean ballSetStart() {
		return ballStart = true;
	}
	
	public Boolean ballSetStop() {
		ballStart = false;
		return true;
	}
	
	public Boolean getBallStatus() {
		return ballStart;
	}
}
