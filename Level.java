package PingPong;

public class Level {
	int level, lives;
	
	public Level() {
		this.level = 1;
		this.lives = 3;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void incrementLevel() {
		this.level++;
	}
	
	public void setLives(int numLives) {
		this.lives = numLives;
	}
	
	public int getLives() {
		return this.lives;
	}

	public Boolean stillAlive() {
		return getLives() > 0;
	}
	
	public int decreaseLives() {
		return --this.lives;

		//return stillAlive();
	}
}
