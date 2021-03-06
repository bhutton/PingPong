package com.pingpong.tests;

import static org.junit.Assert.*;

import com.pingpong.game.Level;
import org.junit.jupiter.api.Test;

public class TestLevel {
	
	private final Level level = new Level();
	
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
	
	@Test
	public void testGetDefaultLives() {
		assertEquals(3, level.getLives());
	}
	
	@Test
	public void testSetLives() {
		level.setLives(2);
		assertEquals(2, level.getLives());
	}
	
	@Test
	public void testReduceLives() {
		level.setLives(3);
		level.decreaseLives();
		assertEquals(2, level.getLives());
	}

}
