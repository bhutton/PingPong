package PingPong;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage imgBackground = null;
	private BufferedImage imgGameOver = null;
	private String[] backGroundArray = new String[10];
	private int backGroundArrayIndex;
	
	public void setBackgroundImage(String backgroundImg) {
		try { imgBackground = ImageIO.read(new File(backgroundImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}

	public void setStart() {
		this.backGroundArrayIndex = 0;
		this.setBackgroundImage(this.getBackGroundImageFileName());
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
		g.drawImage(this.getBackground(), 0, 0, appletWidth, appletHeight, null);
	}

	public void drawGameOver(Graphics g, int appletWidth, int appletHeight) {
		g.drawImage(this.imgGameOver, 0, 0, appletWidth, appletHeight, null);
	}

	public String[] loadBackGrounds(File basePath) {
    	String path = basePath + "/../src/PingPong/images";
    	this.backGroundArrayIndex = 0;

        this.backGroundArray[0] = path + "/background-1.png";
        this.backGroundArray[1] = path + "/background-2.png";
		this.backGroundArray[2] = path + "/background-3.jpg";
		this.backGroundArray[3] = path + "/background-4.png";
		this.backGroundArray[4] = path + "/background-5.jpg";
		this.backGroundArray[5] = path + "/background-6.png";
		this.backGroundArray[6] = path + "/background-7.png";
		this.backGroundArray[7] = path + "/background-8.png";
		this.backGroundArray[8] = path + "/background-9.png";
		this.backGroundArray[9] = path + "/background-10.png";

		this.setBackgroundImage(this.backGroundArray[this.backGroundArrayIndex]);

		return this.backGroundArray;
    }
    
    public String getBackGroundImageFileName() {
		return this.backGroundArray[this.backGroundArrayIndex];
	}

	public String getNextBackGroundImageFileName() {
        this.backGroundArrayIndex++;
        if (this.backGroundArrayIndex == this.backGroundArray.length)
			this.backGroundArrayIndex = 0;
        this.setBackgroundImage(this.getBackGroundImageFileName());
		return this.getBackGroundImageFileName();
	}

	public void setBackgroundIndex(int index) {
		this.backGroundArrayIndex = index;
	}

	//public int getBackgroundIndex() {
	//	return this.backGroundArrayIndex;
	//}
}
