package com.pingpong.tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.pingpong.PingPong;
import com.pingpong.elements.Background;
import org.junit.Test;

public class TestBackGround {
	
	private final Background bg = new Background();

	@Test
	public void backgroundImg() {
		String 	backgroundImg = "./src/com/pingpong/images/background-1.png";
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
		String 	gameOverImage = "./src/com/pingpong/images/game-over.jpg";
		BufferedImage imgGameOver1 = null, imgGameOver2 = null;
		
		try { imgGameOver1 = ImageIO.read(new File(gameOverImage)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		bg.setGameOverImage(gameOverImage);
		imgGameOver2 = bg.getGameOverImage(); 
		
		byte[] byteArray1 = ((DataBufferByte) imgGameOver1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgGameOver2.getData().getDataBuffer()).getData();

		assertArrayEquals(byteArray1, byteArray2);
	}

	@Test
	public void testLoadingBackgrounds() {
		final File basePath = new File(PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		bg.loadBackGrounds(basePath);
		assert(bg.getBackGroundListSize() == 10);
	}

	@Test
	public void testGetBackgroundImageFileName() {
		final File basePath = new File(PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		bg.loadBackGrounds(basePath);
		assert(bg.getBackGroundImageFileName() != null);
	}

	@Test
	public void testGetNextBackGroundImageFileName() {
		final File basePath = new File(PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		bg.loadBackGrounds(basePath);

		String backGroundImageFileName1 = bg.getBackGroundImageFileName();
		String backGRoundImageFileName2 = bg.getNextBackGroundImageFileName();

		assert(!Objects.equals(backGroundImageFileName1, backGRoundImageFileName2));
	}

	@Test
	public void testBackgroundRolloverAtLastImage() {
		final File basePath = new File(PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		bg.loadBackGrounds(basePath);

		String filename = bg.getBackGroundImageFileName();

		bg.setLastBackground();
		assertEquals(filename,bg.getNextBackGroundImageFileName());
	}
}

