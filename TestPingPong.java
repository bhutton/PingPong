package PingPong;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestPingPong {
	
	private PingPong pp = new PingPong();
	Graphics g = mock(Graphics.class);
	
	@Test	
	public void testRun() {
		pp.run();
	}
	
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
		assertTrue(pp.getThread() != null);
	}
	
	@Test
	public void testMovePaddleLeft() {
		pp.pp.setPaddleWidth(200);
	    pp.pp.setPaddleHeight(30);
	    pp.pp.setPaddleMoveAmount(50);
		
		pp.pp.setPaddleLocation(800,600);
		assertEquals(200, pp.pp.getPaddleX());
		pp.pp.movePaddleLeft();
		assertEquals(150, pp.pp.getPaddleX());
	}
	
	@Test
	public void testMovePaddleRight() {
		pp.pp.setPaddleWidth(200);
	    pp.pp.setPaddleHeight(30);
	    pp.pp.setPaddleMoveAmount(50);
		
		pp.pp.setPaddleLocation(800,600);
		assertEquals(200, pp.pp.getPaddleX());
		pp.pp.movePaddleRight();
		assertEquals(250, pp.pp.getPaddleX());
	}
	
	@Test
	public void testKeyPressedLeft() throws AWTException {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testKeyPressRight() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_RIGHT,'Z');
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testKeyPressEnter() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_ENTER,'Z');
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testKeyPressSpace() {
		pp.setFocusable(true);
	    pp.requestFocus();
	    
	    KeyEvent key = new KeyEvent(pp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_SPACE,'Z');
	    pp.keyPressed(key);
	    
	    assertTrue(pp.called);
	}
	
	@Test
	public void testLevel() {
		
		pp.level.getLevel();
		pp.level.incrementLevel();
		
		assertEquals(2, pp.level.getLevel());
	}


}
