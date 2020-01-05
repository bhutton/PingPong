package com.pingpong.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pingpong.game.Messages;
import org.junit.Test;

public class TestMessages {
	
	private final Messages messages = new Messages();
	private final Graphics graphicsMock = mock(Graphics.class);
	
	@Test
	public void gameOverImg() throws IOException {
		String current = new java.io.File( "." ).getCanonicalPath();
		String gameOverImage = current + "/src/com/pingpong/images/game-over-message.png";
		BufferedImage imgGameOver1 = ImageIO.read(new File(gameOverImage)), imgGameOver2;
		
		messages.setGameOverImage(gameOverImage);
		imgGameOver2 = messages.getGameOverImage();
		
		byte[] byteArray1 = ((DataBufferByte) imgGameOver1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgGameOver2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void testSetGameActive() {
		assertEquals(true, messages.setGameActive());
	}
	
	@Test
	public void testGetGameActive() {
		assertEquals(false, messages.getGameActive());
	}
	
	@Test
	public void testSetGameOver() {
		assertEquals(true, messages.setGameActive());
		assertEquals(false, messages.setGameOver(1,1));
		assertEquals(false, messages.getGameActive());
	}

	@Test
	public void testReturnMessage() {
		assertEquals("Press Enter to Try Again", messages.returnMessage());
	}
	
	@Test
	public void testDisplayGameStatsAtBottomOfScreen() {
		messages.displayGameStatsAtBottomOfScreen(graphicsMock, 800, 600);
	}
	
	@Test
	public void testSetAndGetLevel() {
		messages.setLevel(2);
		assertEquals(2, messages.getLevel());
	}
	
	@Test
	public void testSetAndGetLives() {
		messages.setLives(2);
		assertEquals(2, messages.getLives());
	}

	@Test
	public void displayMessage() {
		int xStartMessage = -70;
		int yStartMessage = 60;
		int xGameOver = 0;
		int yGameOver = 0;

		messages.displayMessage(graphicsMock, 20, 20, 20, 20);
		verify(graphicsMock).setColor(Color.GRAY);
		verify(graphicsMock).drawImage(null, xGameOver, yGameOver, null);
		verify(graphicsMock).drawString(messages.returnMessage(), xStartMessage, yStartMessage);
	}
}
