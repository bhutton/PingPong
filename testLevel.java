package PingPong;

import static org.junit.Assert.*;

import org.junit.Test;

public class testLevel {
	
	private Level level = new Level();
	
	@Test
	public void testInitialLevel() {
		assertEquals(1, level.getLevel());
	}

	@Test
	public void testSetLevel() {
		level.setLevel(2);
		assertEquals(2, level.getLevel());
	}
	
	@Test
	public void testIncLevel() {
		level.setLevel(2);
		level.incrementLevel();
		assertEquals(3, level.getLevel());
	}

}
