package PingPong;

import java.awt.Graphics;

public class Messages {
	
	Boolean gameActive = false;
	
	public Boolean setGameActive() {
		return gameActive = true;
	}
	
	public Boolean getGameActive() {
		return gameActive;
	}
	
	public Boolean setGameOver() {
		return gameActive = false;
	}
	
	public String returnMessage() {
		return "Game Over, Press Enter to Start Again";
	}
	
	public void displayMessage(Graphics g, int width, int height) {
		int x = width / 2 - 100;
		int y = height / 2;
		g.drawString(returnMessage(), x, y);
	}
}
