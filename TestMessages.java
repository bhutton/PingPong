package PingPong;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestMessages {
	
	Messages msg = new Messages();
	
	@Test
	public void gameOverImg() {
		String 	gameOverImage = "./src/PingPong/images/game-over-png-22.png";
		BufferedImage imgGameOver1 = null, imgGameOver2 = null;
		
		try { imgGameOver1 = ImageIO.read(new File(gameOverImage)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		msg.setGameOverImage(gameOverImage);
		imgGameOver2 = msg.getGameOverImage(); 
		
		byte[] byteArray1 = ((DataBufferByte) imgGameOver1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgGameOver2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void testSetGameActive() {
		assertEquals(true, msg.setGameActive());
	}
	
	@Test
	public void testGetGameActive() {
		assertEquals(false, msg.getGameActive());
	}
	
	@Test
	public void testSetGameOver() {
		assertEquals(true, msg.setGameActive());
		assertEquals(false, msg.setGameOver());
		assertEquals(false, msg.getGameActive());
	}

	@Test
	public void testReturnMessage() {
		assertEquals("Press Enter to Try Again", msg.returnMessage());
	}
}
