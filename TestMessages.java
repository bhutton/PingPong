package PingPong;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.Test;

public class TestMessages {
	
	Messages msg = new Messages();
	
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
		assertEquals("Game Over", msg.returnMessage());
	}
}
