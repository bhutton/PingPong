package PingPong;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage imgBackground = null;
	private BufferedImage imgGameOver = null;
	
	public void setBackgroundImage(String backgroundImg) {
		try { imgBackground = ImageIO.read(new File(backgroundImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBackground() {
		return imgBackground;
	}
	
	public void setGameOverImage(String gameOverImg) {
		try { imgGameOver = ImageIO.read(new File(gameOverImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getGameOverImage() {
		return imgGameOver;
	}
	
	public void drawBackground(Graphics g, int appletWidth, int appletHeight) {
		g.drawImage(this.imgBackground, 0, 0, appletWidth, appletHeight, null);
	}
	
	public void drawGameOver(Graphics g, int appletWidth, int appletHeight) {
		g.drawImage(this.imgGameOver, 0, 0, appletWidth, appletHeight, null);
	}
}
