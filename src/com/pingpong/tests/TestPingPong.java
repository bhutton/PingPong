package com.pingpong.tests;

import static org.junit.Assert.*;

import java.awt.AWTException;
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
	
	private final com.pingpong.PingPong pp = new PingPong();
	private final Graphics g = mock(Graphics.class);
	
	@Test
	public void testPaint() {
		pp.paint(g);
	}
	
	@Test
	public void testGetDimensions() {
		pp.setSize(800,600);
		Dimension appletSize = pp.getSize();
		pp.appletHeight = appletSize.height;
	    pp.appletWidth = appletSize.width;
		
		assertEquals(800, pp.appletWidth);
		assertEquals(600, pp.appletHeight);
	}
	
	@Test
	public void testStartThread() {
		pp.start();
		pp.startGameIfActive();
		assertNotNull(pp.getThread());
	}
	
	@Test
	public void testMovePaddleLeft() {
		pp.ball.setPaddleWidth(200);
	    pp.ball.setPaddleHeight(30);
	    pp.ball.setPaddleMoveAmount(50);
		
		pp.ball.setPaddleLocation(800,600);
		assertEquals(200, pp.ball.getPaddleX());
		pp.ball.movePaddleLeft();
		assertEquals(150, pp.ball.getPaddleX());
	}
	
	@Test
	public void testMovePaddleRight() {
		pp.ball.setPaddleWidth(200);
	    pp.ball.setPaddleHeight(30);
	    pp.ball.setPaddleMoveAmount(50);
		
		pp.ball.setPaddleLocation(800,600);
		assertEquals(200, pp.ball.getPaddleX());
		pp.ball.movePaddleRight();
		assertEquals(250, pp.ball.getPaddleX());
	}
	
	@Test
	public void testKeyPressedLeft() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
	            pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z'
        );
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testKeyPressRight() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
	            pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_RIGHT,'Z'
        );
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testKeyPressEnter() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
	            pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_ENTER,'Z'
        );
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testKeyPressSpace() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(
	    		pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_SPACE,'Z'
        );
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testLevel() {
		pp.level.incrementLevel();
		assertEquals(2, pp.level.getLevel());
	}

	@Test
    public void testGameStillGoing() {
        final File basePath =
                new File(PingPong.class.getProtectionDomain().
                        getCodeSource().getLocation().getPath());
        String backGroundImg = basePath
                + "/com/pingpong/images/background-1.png";

        pp.background.setBackgroundImage(backGroundImg);
	    pp.ball.initializeBrickArray();
	    pp.ball.createWall(3);

	    pp.level.setLives(2);

	    assertEquals("Game On", pp.checkForEndOfLevel());
    }

    @Test
    public void testGameOver() {
        final File basePath = new File(PingPong.class.
                getProtectionDomain().getCodeSource().
                getLocation().getPath());

        pp.background.loadBackGrounds(basePath);
        String backGroundImage = pp.background.getBackGroundImageFileName();
        pp.background.setBackgroundImage(pp.background.getBackGroundImageFileName());
        pp.background.getNextBackGroundImageFileName();

        pp.ball.initializeBrickArray();
        pp.ball.zeroBrickArray();
        pp.ball.createWall(3);
        pp.level.setLives(1);

        assertEquals("Game Over", pp.checkForEndOfLevel());
        assertEquals(true, pp.ball.brickArrayPopulated());
        assertEquals(backGroundImage, pp.background.getBackGroundImageFileName());
    }

    @Test
    public void testLevelComplete() {
        final File basePath = new File(
        		PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		);
        BufferedImage imgBackground1, imgBackground2;

        pp.background.loadBackGrounds(basePath);
        imgBackground1 = pp.background.getBackground();
        pp.gameStart = true;

        assertEquals("Level Finished", pp.checkForEndOfLevel());

        imgBackground2 = pp.background.getBackground();

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

        pp.background.loadBackGrounds(basePath);
        pp.background.setBackgroundImage(pp.background.getBackGroundImageFileName());
        imgBackground1 = pp.background.getBackground();

        pp.ball.zeroBrickArray();
        pp.level.setLives(0);
        //pp.bg.setBackgroundIndex(3);

        assertEquals("Level Finished", pp.checkForEndOfLevel());

        imgBackground2 = pp.background.getBackground();
        byte[] byteArray1 = ((DataBufferByte) imgBackground1.getData().getDataBuffer()).getData();
        byte[] byteArray2 = ((DataBufferByte) imgBackground2.getData().getDataBuffer()).getData();

        assertArrayEquals(byteArray1, byteArray2);
    }

    @Test
    public void testBackgroundChangesOnLevelComplete() {
        final File basePath = new File(
        		PingPong.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		);
        pp.background.loadBackGrounds(basePath);
        pp.level.setLives(1);
        pp.gameStart = true;
        String background = pp.background.getBackGroundImageFileName();
        pp.checkForEndOfLevel();
        assertNotEquals(null, pp.background.getBackGroundImageFileName());
        assertNotEquals(background, pp.background.getBackGroundImageFileName());
    }
}
