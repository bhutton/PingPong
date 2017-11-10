package com.pingpong.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pingpong.game.Messages;
import org.junit.Test;

public class TestMessages {
	
	private final Messages msg = new Messages();
	private final Graphics g = mock(Graphics.class);
	
	@Test
	public void gameOverImg() throws IOException {
		String current = new java.io.File( "." ).getCanonicalPath();
		String gameOverImage = current + "/src/PingPong/images/game-over-message.png";
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
		assertEquals(false, msg.setGameOver(1,1));
		assertEquals(false, msg.getGameActive());
	}

	@Test
	public void testReturnMessage() {
		assertEquals("Press Enter to Try Again", msg.returnMessage());
	}
	
	@Test
	public void testDisplayGameStatsAtBottomOfScreen() {
		msg.displayGameStatsAtBottomOfScreen(g, 800, 600);
	}
	
	@Test
	public void testSetAndGetLevel() {
		msg.setLevel(2);
		assertEquals(2, msg.getLevel());
	}
	
	@Test
	public void testSetAndGetLives() {
		msg.setLives(2);
		assertEquals(2, msg.getLives());
	}

}
