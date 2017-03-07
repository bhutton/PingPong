package PingPong;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.Test;

public class TestPingPong {
	
	private PingPong pp = new PingPong();

	@Test
	public void testRun() {
		pp.run();
	}
	
	@Test
	public void testPaint() {
		
		mock
		Graphics gMock = Mockito.mock(Graphics.class);
		
		pp.paint(gMock);
	}

}
