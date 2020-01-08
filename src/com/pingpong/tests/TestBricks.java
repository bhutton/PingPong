package com.pingpong.tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pingpong.elements.Ball;
import org.junit.Test;

public class TestBricks {
	
	private final Ball ball = new Ball();

	@Test
	public void testSetBrickImg() throws IOException {
		String current = new java.io.File( "." ).getCanonicalPath();
		String paddleImg = current + "/src/com/pingpong/images/brick.png";
		BufferedImage imgBrick1 = ImageIO.read(new File(paddleImg)), imgBrick2;
		
		try { imgBrick1 = ImageIO.read(new File(paddleImg)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		ball.setBallImage(paddleImg);
		imgBrick2 = ball.getBall();
		
		byte[] byteArray1 = ((DataBufferByte) imgBrick1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgBrick2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void setWallDimensions() {
		ball.setWallDimensions(8,2);
		
		assertEquals(8, ball.getNumCols());
		assertEquals(2, ball.getNumRows());
	}
	
	@Test
	public void testInitializeBrickArray() {
		ball.initializeBrickArray();
		assertTrue(ball.returnSizeOfBrickArray() > 0);
	}
	
	@Test
	public void testSetBrickColumnLocation() {
		ball.setBrickColumnLocation(2);
		assertEquals(2, ball.returnBrickColumnLocation());
	}
	
	@Test
	public void testSetBrickRowLocation() {
		ball.setBrickRowLocation(2);
		assertEquals(2, ball.returnBrickRowLocation());
	}
	
	@Test
	public void testSetXCoordinateOfCurrentLocation() {
		ball.setBrickColumnLocation(2);
		ball.setBrickRowLocation(2);
		ball.setBrickXCoordinates(2);
		assertEquals(2, ball.getBrickXCoordinates());
	}
	
	@Test
	public void testSetYCoordinateOfCurrentLocation() {
		ball.setBrickColumnLocation(2);
		ball.setBrickRowLocation(2);
		ball.setBrickYCoordinates(2);
		assertEquals(2, ball.getBrickYCoordinates());
	}
	
	@Test
	public void testSetBrickEnabled() {
		int enabled = 0;
		ball.setBrickColumnLocation(2);
		ball.setBrickRowLocation(2);
		ball.setBrickEnabled(enabled);
		assertTrue(ball.getActive());
	}
	
	@Test
	public void testSetBrickTaken() {
		int taken = 1;
//		ball.brickArray[0][0][2] = 0;
		ball.setBrickStatus(0, 0, 2, 0);
		ball.setNumRows(1);
		ball.setNumCols(1);
		assertEquals(0, ball.getScore());

		ball.setBrickColumnLocation(2);
		ball.setBrickRowLocation(2);
		ball.setBrickEnabled(taken);
		ball.setBrickTaken();

		assertFalse(ball.getActive());
		assertEquals(1, ball.getScore());
	}
	
	@Test
	public void testCreateWall() {
		ball.createWall(2);
		assertTrue(ball.getNumCols() > 0);
		assertTrue(ball.getNumRows() > 0);
	}
	
	@Test
	public void testReturnXLocationOfSpecificBrick() {
		ball.createWall(2);
		ball.setBrickRowLocation(1);
		ball.setBrickColumnLocation(2);
		assertTrue(ball.getBrickXPosition() > 0);
	}
	
	@Test
	public void testReturnYLocationOfSpecificBrick() {
		ball.createWall(2);
		ball.setBrickRowLocation(1);
		ball.setBrickColumnLocation(1);
		assertTrue(ball.getBrickYCoordinates() > 0);
	}
	
	@Test
	public void testGetNumberOfRows() {
		ball.createWall(2);
		assertTrue(ball.getNumRows() > 0);
	}
	
	@Test
	public void testGetNumberOfCols() {
		ball.createWall(2);
		assertTrue(ball.getNumCols() > 0);
	}
	
	@Test
	public void testIncrementingRows() {
		ball.createWall(2);
		ball.setRowLoc(1);
		ball.incRowLoc();
		assertEquals(2, ball.getRowLoc());
	}
	
	@Test
	public void testIncrementingCols() {
		ball.createWall(2);
		ball.setColLoc(1);
		ball.incColLoc();
		assertEquals(2, ball.getColLoc());
	}
	
	@Test
	public void testGetWallHeight() {
		ball.createWall(2);
		assertTrue(ball.wallHeight() > 0);
	}
	
	@Test
	public void testCheckBricks() {
		ball.createWall(2);
		ball.setBallY(60);
		ball.setBrickYCoordinates(60);
		ball.setBallX(200);
		ball.setBallUp();
		ball.setBrickRowLocation(1);
		ball.setBallRight(true);
		ball.setBallDown(false);
		assertFalse(ball.getDown());
		assertTrue(ball.checkBricks(55));
	}
	
	@Test
	public void testBallGoesPastTakenBrickLocation() {
		
		ball.setBallX(50);
		ball.setBallY(120);
		ball.setBallXValue(50);
		ball.setBallYValue(120);
		ball.setBrickYCoordinates(120);
		
		ball.setBallDown(false);
		
		assertEquals(false, ball.checkBricks(55));
		
		// Check that ball bounces
		ball.setBallDirectionOnReachingBricks();
		assertEquals(true, ball.checkBricks(55));
		
		// Check that ball goes through
		ball.setBallDown(false);
		assertEquals(false, ball.checkBricks(55));
	}
	
	@Test
	public void testCheckSetBallX() {
		ball.setBallX(1);
		assertEquals(1, ball.getBallX());
	}
	
	@Test
	public void testCheckSetBallY() {
		ball.setBallY(1);
		assertEquals(1, ball.getBallY());
	}
	
	@Test
	public void testSetBallDown() {
		ball.setBallDown(true);
		assertEquals(true, ball.getBallDown());
		ball.setBallDown(false);
		assertEquals(false, ball.getBallDown());
	}
	
	@Test
	public void testSetBallRight() {
		ball.setBallRight(true);
		assertEquals(true, ball.getBallRight());
		ball.setBallRight(false);
		assertEquals(false, ball.getBallRight());
	}
	
	@Test
	public void testCheckActive() {
		ball.createWall(2);
		ball.setBallX(1);
		ball.setBallY(1);

		ball.setBallDown(false);
		assertEquals(true, ball.checkActive(20, 90, false));
		assertEquals(false, ball.checkActive(20, 90, false));
	}

	@Test
	public void TestBallBouncesRightWhenGoingDown() {
		ball.createWall(2);
		ball.setBallX(1);
		ball.setBallY(1);

		ball.setBallDown(true);
		ball.setBallRight(true);
		assertEquals(true, ball.getBallRight());
		assertEquals(true, ball.checkActive(20, 90, ball.getBallRight()));
		assertEquals(false, ball.getBallRight());
	}
	
	@Test
	public void testGetActive() {
		ball.initializeBrickArray();
		ball.createWall(2);
		ball.setBrickColumnLocation(0);
		ball.setBrickRowLocation(1);
		assertTrue(ball.getActive());
		ball.setBrickTaken();
		assertFalse(ball.getActive());
	}
	
	@Test
	public void testAnyBricksLeft() {
		ball.initializeBrickArray();
		assertFalse(ball.getBricksLeft());
		ball.createWall(2);
		assertTrue(ball.getBricksLeft());
	}
	
}
