package PingPong;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage imgBackground = null;
	
	public void setBackgroundImage(String backgroundImg) {
		try { imgBackground = ImageIO.read(new File(backgroundImg)); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public BufferedImage getBackground() {
		return imgBackground;
	}
}
