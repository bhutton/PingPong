package com.pingpong.oldtests;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestBricks {
	
	private Ball pp = new Ball(true, true, 0, 0);

	@Test
	public void testSetBrickImg() {
		String 	paddleImg = "./src/PingPong/images/brick.png";
		BufferedImage imgBrick1 = null, imgBrick2 = null;
		
		try { imgBrick1 = ImageIO.read(new File(paddleImg)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		pp.setBallImage(paddleImg);
		imgBrick2 = pp.getBall();
		
		byte[] byteArray1 = ((DataBufferByte) imgBrick1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgBrick2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void setWallDimensions() {
		pp.setWallDimensions(8,2);
		
		assertEquals(8, pp.getNumCols());
		assertEquals(2, pp.getNumRows());
	}
	
	@Test
	public void testInitializeBrickArray() {
		pp.initializeBrickArray();
		assertTrue(pp.returnSizeOfBrickArray() > 0);
	}
	
	@Test
	public void testSetBrickColumnLocation() {
		pp.setBrickColumnLocation(2);
		assertEquals(2, pp.returnBrickColumnLocation());
	}
	
	@Test
	public void testSetBrickRowLocation() {
		pp.setBrickRowLocation(2);
		assertEquals(2, pp.returnBrickRowLocation());
	}
	
	@Test
	public void testSetXCoordinateOfCurrentLocation() {
		pp.setBrickColumnLocation(2);
		pp.setBrickRowLocation(2);
		pp.setBrickXCoord(2);
		assertEquals(2, pp.getBrickXCoord());
	}
	
	@Test
	public void testSetYCoordinateOfCurrentLocation() {
		pp.setBrickColumnLocation(2);
		pp.setBrickRowLocation(2);
		pp.setBrickYCoord(2);
		assertEquals(2, pp.getBrickYCoord());
	}
	
	@Test
	public void testSetBrickEnabled() {
		int enabled = 0;
		pp.setBrickColumnLocation(2);
		pp.setBrickRowLocation(2);
		pp.setBrickEnabled(enabled);
		assertEquals(true, pp.getActive());
	}
	
	@Test
	public void testSetBrickTaken() {
		int taken = 1;
		pp.setBrickColumnLocation(2);
		pp.setBrickRowLocation(2);
		pp.setBrickEnabled(taken);
		assertEquals(false, pp.getActive());
	}
	
	@Test
	public void testCreateWall() {
		pp.createWall(2);
		assertTrue(pp.getNumCols() > 0);
		assertTrue(pp.getNumRows() > 0);
	}
	
	@Test
	public void testReturnXLocationOfSpecificBrick() {
		pp.createWall(2);
		pp.setBrickRowLocation(1);
		pp.setBrickColumnLocation(2);
		assertTrue(pp.getBrickX() > 0);
	}
	
	@Test
	public void testReturnYLocationOfSpecificBrick() {
		pp.createWall(2);
		pp.setBrickRowLocation(1);
		pp.setBrickColumnLocation(1);
		assertTrue(pp.getBrickYCoord() > 0);
	}
	
	@Test
	public void testGetNumberOfRows() {
		pp.createWall(2);
		assertTrue(pp.getNumRows() > 0);
	}
	
	@Test
	public void testGetNumberOfCols() {
		pp.createWall(2);
		assertTrue(pp.getNumCols() > 0);
	}
	
	@Test
	public void testIncrementingRows() {
		pp.createWall(2);
		pp.setRowLoc(1);
		pp.incRowLoc();
		assertEquals(2, pp.getRowLoc());
	}
	
	@Test
	public void testIncrementingCols() {
		pp.createWall(2);
		pp.setColLoc(1);
		pp.incColLoc();
		assertEquals(2, pp.getColLoc());
	}
	
	@Test
	public void testGetWallHeight() {
		pp.createWall(2);
		assertTrue(pp.wallHeight() > 0);
	}
	
	@Test
	public void testCheckBricks() {
		pp.createWall(2);
		pp.setBallY(60);
		pp.setBrickYCoord(60);
		pp.setBallX(200);
		pp.setBallUp();
		pp.setBrickRowLocation(1);
		pp.setBallRight(true);
		pp.setBallDown(false);
		assertEquals(false, pp.getDown());
		assertEquals(true, pp.checkBricks(55));
	}
	
	@Test
	public void testBallGoesPastTakenBrickLocation() {
		
		pp.setBallX(50);
		pp.setBallY(120);
		pp.setBallXValue(50);
		pp.setBallYValue(120);
		pp.setBrickYCoord(120);
		
		pp.setBallDown(false);
		
		assertEquals(false,pp.checkBricks(55));
		
		// Check that ball bounces
		pp.setBallDirectionAfterReachingBricks();
		assertEquals(true,pp.checkBricks(55));
		
		// Check that ball goes through
		pp.setBallDown(false);
		assertEquals(false,pp.checkBricks(55));
	}
	
	@Test
	public void testCheckSetBallX() {
		pp.setBallX(1);
		assertEquals(1, pp.getBallX());
	}
	
	@Test
	public void testCheckSetBallY() {
		pp.setBallY(1);
		assertEquals(1, pp.getBallY());
	}
	
	@Test
	public void testSetBallDown() {
		pp.setBallDown(true);
		assertEquals(true, pp.getBallDown());
		pp.setBallDown(false);
		assertEquals(false, pp.getBallDown());
	}
	
	@Test
	public void testSetBallRight() {
		pp.setBallRight(true);
		assertEquals(true, pp.getBallRight());
		pp.setBallRight(false);
		assertEquals(false, pp.getBallRight());
	}
	
	@Test
	public void testCheckActive() {
		pp.createWall(2);
		pp.setBallX(1);
		pp.setBallY(1);

		pp.setBallDown(false);
		assertEquals(true, pp.checkActive(20, 90, false));
		assertEquals(false, pp.checkActive(20, 90, false));
	}

	@Test
	public void TestBallBouncesRightWhenGoingDown() {
		pp.createWall(2);
		pp.setBallX(1);
		pp.setBallY(1);

		pp.setBallDown(true);
		pp.setBallRight(true);
		assertEquals(true, pp.getBallRight());
		assertEquals(true, pp.checkActive(20, 90, pp.getBallRight()));
		assertEquals(false, pp.getBallRight());
	}
	
	@Test
	public void testGetActive() {
		pp.initializeBrickArray();
		pp.createWall(2);
		pp.setBrickColumnLocation(0);
		pp.setBrickRowLocation(1);
		assertEquals(true, pp.getActive());
		pp.setBrickTaken();
		assertEquals(false, pp.getActive());
	}
	
	@Test
	public void testAnyBricksLeft() {
		pp.initializeBrickArray();
		assertEquals(false, pp.getBricksLeft());
		pp.createWall(2);
		assertEquals(true, pp.getBricksLeft());
	}
	
}
