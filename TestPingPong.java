package PingPong;

import static org.junit.Assert.*;
import java.awt.Dimension;
import java.awt.Graphics;
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

}
