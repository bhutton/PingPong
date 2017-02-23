package PingPong;

import java.awt.Graphics;

public class Paddle extends Bricks {
	
	int paddleX, paddleY, paddleWidth, paddleHeight;

	public Paddle() {
		super();
	}
	
	public void setPaddleLocation(int appletHeight, int appletWidth) {
		paddleX = appletWidth/2 - 50;
		paddleY = appletHeight - 20;
		paddleWidth = 100;
		paddleHeight = 20;
	}
	
	public void movePaddleLeft() {
		paddleX--;
	}
	
	public void movePaddleRight() {
		paddleX++;
	}
	
	public void drawPaddle(Graphics g) {
		Boolean raised = true;
		g.fill3DRect(paddleX, paddleY, paddleWidth, paddleHeight, raised);
	}
}
