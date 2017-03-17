package PingPong;

public class Level {
	int level;
	
	public Level() {
		this.level = 1;
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
}
