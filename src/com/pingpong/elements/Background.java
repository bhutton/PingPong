package com.pingpong.elements;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Background {
	private BufferedImage imgBackground = null;
	private BufferedImage imgGameOver = null;
	private ArrayList<String> backGroundList = new ArrayList<>(10);
	private int backGroundListIndex;

	public void setBackgroundImage(String backgroundImg) {
		try { imgBackground = ImageIO.read(new File(backgroundImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}

	public void setStart() {
		this.backGroundListIndex = 0;
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

	public void loadBackGrounds(File basePath) {
    	String path = basePath + "/com/pingpong/images";
    	this.backGroundListIndex = 0;

		backGroundList.add(path + "/background-1.png");
		backGroundList.add(path + "/background-2.png");
		backGroundList.add(path + "/background-3.png");
		backGroundList.add(path + "/background-4.png");
		backGroundList.add(path + "/background-5.png");
		backGroundList.add(path + "/background-6.png");
		backGroundList.add(path + "/background-7.png");
		backGroundList.add(path + "/background-8.png");
		backGroundList.add(path + "/background-9.png");
		backGroundList.add(path + "/background-10.png");

        this.setBackgroundImage(this.backGroundList.get(this.backGroundListIndex));
    }

    public void setLastBackground() {
		this.backGroundListIndex = 9;
		this.setBackgroundImage(this.backGroundList.get(9));
	}
    
    public String getBackGroundImageFileName() {
		return this.backGroundList.get(backGroundListIndex);
	}

	public String getNextBackGroundImageFileName() {
        this.backGroundListIndex++;

        if (this.backGroundListIndex == this.backGroundList.size())
			this.backGroundListIndex = 0;

        this.setBackgroundImage(this.getBackGroundImageFileName());
		return this.getBackGroundImageFileName();
	}

	public int getBackGroundListSize() {
		return this.backGroundList.size();
	}
}
