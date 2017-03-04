package PingPong;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestBackGround {
	
	Background bg = new Background();

	@Test
	public void backgroundImg() {
		String 	backgroundImg = "./src/PingPong/images/12-vector-game-backgrounds-8320_imgs_8320.png";
		BufferedImage imgBackground1 = null, imgBackground2 = null;
		
		try { imgBackground1 = ImageIO.read(new File(backgroundImg)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		bg.setBackgroundImage(backgroundImg);
		imgBackground2 = bg.getBackground(); 
		
		byte[] byteArray1 = ((DataBufferByte) imgBackground1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgBackground2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void gameOverImg() {
		String 	gameOverImage = "./src/PingPong/images/free-game-wallpaper-9.jpg";
		BufferedImage imgGameOver1 = null, imgGameOver2 = null;
		
		try { imgGameOver1 = ImageIO.read(new File(gameOverImage)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		bg.setGameOverImage(gameOverImage);
		imgGameOver2 = bg.getGameOverImage(); 
		
		byte[] byteArray1 = ((DataBufferByte) imgGameOver1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgGameOver2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	/*@Test
	public void testDrawBall() {
		bg.drawBall();
	}*/
	
	

}