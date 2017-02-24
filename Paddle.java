package PingPong;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Paddle extends Bricks {
	
	private int paddleX, paddleY, paddleWidth, paddleHeight;
	private int appletWidth;
	
	BufferedImage imgPaddle = null;

	public Paddle() {
		super();
	}
	
	public void setPaddleImage(String paddleImg) {
		try { imgPaddle = ImageIO.read(new File(paddleImg)); } 
		catch (IOException e) { e.printStackTrace(); }
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
		g.drawImage(this.imgPaddle, paddleX, paddleY, null);
	}
	
	public boolean checkPaddle(Boolean direction, int x, int y) {
		
		System.out.println(y);
		System.out.println(paddleY);
		if (y >= paddleY)
			return false;
		
		return direction;
	}
}
