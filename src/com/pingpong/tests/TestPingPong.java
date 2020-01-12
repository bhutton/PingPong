package com.pingpong.tests;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import com.pingpong.PingPong;
import org.junit.Test;
import java.util.Arrays;
import static org.mockito.Mockito.*;

public class TestPingPong {
	
	private final com.pingpong.PingPong pingPong = new PingPong();
	private final Graphics g = mock(Graphics.class);
	
	@Test
	public void testPaint() {
		pingPong.paint(g);
	}
	
	@Test
	public void testGetDimensions() {
		pingPong.setSize(800,600);
		Dimension appletSize = pingPong.getSize();
		pingPong.appletHeight = appletSize.height;
	    pingPong.appletWidth = appletSize.width;
		
		assertEquals(800, pingPong.appletWidth);
		assertEquals(600, pingPong.appletHeight);
	}
	
	@Test
	public void testStartThread() {
		pingPong.start();
		pingPong.startGameIfActive();
		assertNotNull(pingPong.getThread());
	}
	
	@Test
	public void testMovePaddleLeft() {
		pingPong.ball.setPaddleWidth(200);
	    pingPong.ball.setPaddleHeight(30);
	    pingPong.ball.setPaddleMoveAmount(50);
		
		pingPong.ball.setPaddleLocation(800,600);
		assertEquals(200, pingPong.ball.getPaddleX());
		pingPong.ball.movePaddleLeft();
		assertEquals(150, pingPong.ball.getPaddleX());
	}
	
	@Test
	public void testMovePaddleRight() {
		pingPong.ball.setPaddleWidth(200);
	    pingPong.ball.setPaddleHeight(30);
	    pingPong.ball.setPaddleMoveAmount(50);
		
		pingPong.ball.setPaddleLocation(800,600);
		assertEquals(200, pingPong.ball.getPaddleX());
		pingPong.ball.movePaddleRight();
		assertEquals(250, pingPong.ball.getPaddleX());
	}
	
	@Test
	public void testKeyPressedLeft() {
		pingPong.setFocusable(true);
	    pingPong.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
				pingPong, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z'
        );
	    pingPong.keyPressed(key);
	    
	    assertTrue(pingPong.called);
	}
	
	@Test
	public void testKeyPressRight() {
		pingPong.setFocusable(true);
	    pingPong.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
				pingPong, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_RIGHT,'Z'
        );
	    pingPong.keyPressed(key);
	    
	    assertTrue(pingPong.called);
	}
	
	@Test
	public void testKeyPressEnter() {
		pingPong.setFocusable(true);
	    pingPong.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
				pingPong, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_ENTER,'Z'
        );
	    pingPong.keyPressed(key);
	    
	    assertTrue(pingPong.called);
	}
	
	@Test
	public void testKeyPressSpace() {
		pingPong.setFocusable(true);
	    pingPong.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
				pingPong, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_SPACE,'Z'
        );
	    pingPong.keyPressed(key);
	    
	    assertTrue(pingPong.called);
	}
	
	@Test
	public void testLevel() {
		pingPong.level.incrementLevel();
		assertEquals(2, pingPong.level.getLevel());
	}

	@Test
    public void testGameStillGoing() {
        final File basePath =
                new File(PingPong.class.getProtectionDomain().
                        getCodeSource().getLocation().getPath());
        String backGroundImg = basePath
                + "/com/pingpong/images/background-1.png";

        pingPong.background.setBackgroundImage(backGroundImg);
	    pingPong.ball.initializeBrickArray();
	    pingPong.ball.createWall(3);

	    pingPong.level.setLives(2);

	    assertEquals("Game On", pingPong.checkForEndOfLevel());
    }

    @Test
    public void testGameOver() {
        final File basePath = new File(PingPong.class.
                getProtectionDomain().getCodeSource().
                getLocation().getPath());

        pingPong.background.loadBackGrounds(basePath);
        String backGroundImage = pingPong.background.getBackGroundImageFileName();
        pingPong.background.setBackgroundImage(pingPong.background.getBackGroundImageFileName());
        pingPong.background.getNextBackGroundImageFileName();

        pingPong.ball.initializeBrickArray();
        pingPong.ball.zeroBrickArray();
        pingPong.ball.createWall(3);
        pingPong.level.setLives(1);

        assertEquals("Game Over", pingPong.checkForEndOfLevel());
        assertEquals(true, pingPong.ball.brickArrayPopulated());
        assertEquals(backGroundImage, pingPong.background.getBackGroundImageFileName());
    }

    @Test
    public void testLevelComplete() {
        final File basePath = new File(
        		PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		);
        BufferedImage imgBackground1, imgBackground2;

        pingPong.background.loadBackGrounds(basePath);
        imgBackground1 = pingPong.background.getBackground();
        pingPong.gameStart = true;

        assertEquals("Level Finished", pingPong.checkForEndOfLevel());

        imgBackground2 = pingPong.background.getBackground();

        byte[] byteArray1 = ((DataBufferByte) imgBackground1.getData().getDataBuffer()).getData();
        byte[] byteArray2 = ((DataBufferByte) imgBackground2.getData().getDataBuffer()).getData();

        assertFalse(Arrays.equals(byteArray1, byteArray2));
    }

    @Test
    public void testLevelsLivesAndBackgroundsResetOnGameOver() {
        final File basePath = new File(
        		PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		);
        BufferedImage imgBackground1, imgBackground2;

        pingPong.background.loadBackGrounds(basePath);
        pingPong.background.setBackgroundImage(pingPong.background.getBackGroundImageFileName());
        imgBackground1 = pingPong.background.getBackground();

        pingPong.ball.zeroBrickArray();
        pingPong.ball.setScore(10);
		assertEquals(10, pingPong.ball.getScore());

		pingPong.setGameOver();
		pingPong.level.setLives(0);

        assertEquals("Game Over", pingPong.checkForEndOfLevel());

        imgBackground2 = pingPong.background.getBackground();
        byte[] byteArray1 = ((DataBufferByte) imgBackground1.getData().getDataBuffer()).getData();
        byte[] byteArray2 = ((DataBufferByte) imgBackground2.getData().getDataBuffer()).getData();

        assertArrayEquals(byteArray1, byteArray2);
		KeyEvent key = new KeyEvent(
				pingPong, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_ENTER,'\n'
		);

		pingPong.keyPressed(key);

        assertEquals(0, pingPong.ball.getScore());
    }

    @Test
    public void testBackgroundChangesOnLevelComplete() {
        final File basePath = new File(
        		PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		);
        pingPong.background.loadBackGrounds(basePath);
        pingPong.level.setLives(1);
        pingPong.gameStart = true;
        String background = pingPong.background.getBackGroundImageFileName();
        pingPong.checkForEndOfLevel();
        assertNotEquals(null, pingPong.background.getBackGroundImageFileName());
        assertNotEquals(background, pingPong.background.getBackGroundImageFileName());
    }

}
