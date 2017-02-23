package PingPong;

import java.awt.Graphics;

public class Paddle extends Bricks {
	
	private int paddleX, paddleY, paddleWidth, paddleHeight;
	private int appletWidth;

	public Paddle() {
		super();
	}
	
	public void setPaddleLocation(int appletHeight, int appletWidth) {
		this.appletWidth = appletWidth;
		paddleX = appletWidth/2 - paddleWidth/2;
		paddleY = appletHeight - paddleHeight;
	}
	
	public void movePaddleLeft() {
		if (paddleX >= 0) paddleX-=30;
	}
	
	public void movePaddleRight() {
		if (paddleX+paddleWidth <= appletWidth) paddleX+=30;
	}
	
	public void setPaddleWidth(int width) {
		paddleWidth = width;
	}
	
	public void setPaddleHeight(int height) {
		paddleHeight = height;
	}
	
	public void drawPaddle(Graphics g) {
		Boolean raised = true;
		g.fill3DRect(paddleX, paddleY, paddleWidth, paddleHeight, raised);
	}
	
	public boolean checkPaddle(Boolean direction, int x, int y) {
		// TODO Auto-generated method stub
		if (y >= paddleY)
			return true;
		
		return direction;
	}
}
