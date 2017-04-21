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

	public String[] loadBackGrounds(File basePath) {
		String[] backGroundArray = new String[10];
    	String path = basePath + "/../src/PingPong/images";
    	System.out.println(path);
        backGroundArray[0] = path + "/background-1.png";
        backGroundArray[1] = path + "/background-2.png";
		backGroundArray[2] = path + "/background-3.jpg";
		backGroundArray[3] = path + "/background-4.png";
		backGroundArray[4] = path + "/background-5.jpg";
		backGroundArray[5] = path + "/background-6.png";
		backGroundArray[6] = path + "/background-7.png";
		backGroundArray[7] = path + "/background-8.png";
		backGroundArray[8] = path + "/background-9.png";
		backGroundArray[9] = path + "/background-10.png";

		return backGroundArray;
    }
}
