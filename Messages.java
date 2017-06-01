package PingPong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Messages {
	
	Boolean gameActive = false;
	
	private BufferedImage imgGameOver = null;
	
	int level, lives;
	
	public void setGameOverImage(String gameOverImg) {
		try { imgGameOver = ImageIO.read(new File(gameOverImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getGameOverImage() {
		return imgGameOver;
	}
	
	public Boolean setGameActive() {
		return gameActive = true;
	}
	
	public Boolean getGameActive() {
		return gameActive;
	}
	
	public Boolean setGameOver(int level, int lives) {
		this.level = level;
		this.lives = lives;
		return gameActive = false;
	}
	
	public String returnMessage() {
		return "Press Enter to Try Again";
	}
	
	public void displayMessage(Graphics g, int width, int height, int appletWidth, int appletHeight) {
		int xGameOver = appletWidth / 2 - (width/2);
		int yGameOver = appletHeight / 2 - (height/2);
		int xStartMessage = appletWidth / 2 - 80;
		int yStartMessage = appletHeight / 2 + 50;
		
		g.setColor(Color.GRAY);
		g.drawImage(this.imgGameOver, xGameOver, yGameOver, null);
		g.drawString(returnMessage(), xStartMessage, yStartMessage);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getLives() {
		return this.lives;
	}

	/*public void setNewGame(int level, int lives) {
		setGameOver(level, lives);
		setLevel(level);
		setLives(lives);
	}*/
	
	public void displayGameStatsAtBottomOfScreen(Graphics g, int appletWidth, int appletHeight) {
		g.setColor(Color.BLACK);
		g.fill3DRect(0, appletHeight-30, appletWidth, appletHeight, true);
		g.setColor(Color.WHITE);
		g.drawString("Lives: " + Integer.toString(getLives()), appletWidth-150, appletHeight-10);
		g.drawString("Level: " + Integer.toString(getLevel()), appletWidth-70, appletHeight-10);
	}
}
