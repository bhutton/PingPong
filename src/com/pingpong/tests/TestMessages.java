package com.pingpong.tests;

import com.pingpong.game.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestMessages {
	
	private final Messages messages = new Messages();
	private final Graphics graphicsMock = mock(Graphics.class);

	@BeforeEach
	public void setup() throws Exception {
		BufferedWriter bw;
		File file = new File("src/com/pingpong/tests/file/highscores");
		FileWriter fw;
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);

		bw.write("bloggs=20\n");
		bw.write("fred=10\n");
		bw.close();
	}

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
		assertTrue(messages.setGameActive());
		assertFalse(messages.setGameOver(1,1));
		assertFalse(messages.getGameActive());
	}

	@Test
	public void testReturnMessage() {
		assertEquals("Press Enter to Try Again", messages.returnMessage());
	}
	
	@Test
	public void testDisplayGameStatsAtBottomOfScreen() {
		messages.displayGameStatsAtBottomOfScreen(graphicsMock, 800, 600);
		verify(graphicsMock).drawString("Lives: " + 0, 650, 590);
		verify(graphicsMock).drawString("Level: " + 0, 730, 590);
		verify(graphicsMock).drawString("Score: " + 0, 570, 590);
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
		int xStartMessage = 10;
		int xGameOver = 0;
		int yGameOver = -14;

		messages.setPath("src/com/pingpong/tests/file/highscores");
		messages.displayGameOverMessage(graphicsMock, 20, 20, 20, 20);
		verify(graphicsMock).setColor(Color.GRAY);
		verify(graphicsMock).drawImage(null, xGameOver, yGameOver, null);
		verify(graphicsMock).drawString("20	bloggs", xStartMessage, 26);
		verify(graphicsMock).drawString("10	fred", xStartMessage, 46);
		verify(graphicsMock).drawString(messages.returnMessage(), xStartMessage, 66);
	}

	@Test
	public void displayMessageWithNewHighScoreAdded() {
		int xStartMessage = 10;
		int xGameOver = 0;
		int yGameOver = -14;

		String userScore = "15\t" + System.getProperty("user.name");

		messages.setScore(15);
		messages.setPath("src/com/pingpong/tests/file/highscores");
		messages.displayGameOverMessage(graphicsMock, 20, 20, 20, 20);
		verify(graphicsMock).setColor(Color.GRAY);
		verify(graphicsMock).drawImage(null, xGameOver, yGameOver, null);
		verify(graphicsMock).drawString("20	bloggs", xStartMessage, 26);
		verify(graphicsMock).drawString("10	fred", xStartMessage, 46);
		verify(graphicsMock).drawString(userScore, xStartMessage, 66);
		verify(graphicsMock).drawString(messages.returnMessage(), xStartMessage, 86);
 	}
}
