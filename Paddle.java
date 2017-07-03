package com.pingpong;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Paddle extends Bricks {
	
	private int paddleX, paddleY, paddleWidth, paddleHeight;
	private int appletWidth;
	private int paddleMoveAmount;
	
	private BufferedImage imgPaddle = null;

	public void setPaddleImage(String paddleImg) {
		try { imgPaddle = ImageIO.read(new File(paddleImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void setPaddleLocation(int appletHeight, int appletWidth) {
		this.appletWidth = appletWidth;
		paddleX = appletWidth/2 - paddleWidth/2;
		paddleY = appletHeight - (paddleHeight + 30);
	}
	
	public void setPaddleMoveAmount(int amount) {
		this.paddleMoveAmount = amount;
	}
	
	public Boolean movePaddleLeft() {
		if (paddleX >= 0) {
			paddleX-=this.paddleMoveAmount;
			if (paddleX < 0)
				paddleX+=this.paddleMoveAmount;
		}
				
		return true;
	}
	
	public Boolean movePaddleRight() {
		if (this.paddleX+this.paddleWidth <= this.appletWidth) { 
			paddleX+=this.paddleMoveAmount;
			if (paddleX+paddleWidth > this.appletWidth)
				paddleX-=this.paddleMoveAmount;
		}
			
		return true;
	}
	
	public void setPaddleWidth(int width) {
		this.paddleWidth = width;
	}
	
	public int getPaddleWidth() {
		return this.paddleWidth;
	}
	
	public void setPaddleHeight(int height) {
		paddleHeight = height;
	}
	
	public void drawPaddle(Graphics g) {
		g.drawImage(this.imgPaddle, paddleX, paddleY, null);
	}
	
	public int getPaddleX() {
		return paddleX;
	}
	
	public void setPaddleX(int x) {
		this.paddleX = x;
	}
	
	public int getPaddleY() {
		return paddleY;
	}
	
	public boolean checkPaddle(Boolean direction, int x, int y) {
		if (y >= paddleY - (paddleHeight*1.7))
			if (x >= paddleX-this.paddleMoveAmount && x <= paddleX+paddleWidth)
				return false;

		return direction;
	}
}
