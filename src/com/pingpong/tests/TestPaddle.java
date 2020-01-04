package com.pingpong.tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pingpong.elements.Ball;
import org.junit.Test;

public class TestPaddle {
	
	private final Ball ball = new Ball();
	
	@Test
	public void testSetPaddleImg() throws IOException {
		String current = new java.io.File( "." ).getCanonicalPath();
		String paddleImg = current + "/src/com/pingpong/images/paddle.png";
		BufferedImage imgPaddle1 = ImageIO.read(new File(paddleImg)), imgPaddle2;
		
		try { imgPaddle1 = ImageIO.read(new File(paddleImg)); }
		catch (IOException e) { e.printStackTrace(); }

		ball.setBallImage(paddleImg);
		imgPaddle2 = ball.getBall();

		byte[] byteArray1 = ((DataBufferByte) imgPaddle1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgPaddle2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void testCheckPaddleReturnsTrue() {				
		ball.setPaddleLocation(800, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
		assertTrue(ball.checkPaddle(true, 40, 50));
	}
	
	@Test
	public void testBallReachesPaddle() {
		ball.setPaddleLocation(800, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
		assertFalse(ball.checkPaddle(true, 400, 800));
	}
	
	@Test
	public void testBallShortOfPaddle() {
		ball.setPaddleLocation(800, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
		assertTrue(ball.checkPaddle(true, 400, 0));
	}
	
	@Test
	public void testBallHitsPaddle() {
		ball.setPaddleLocation(600, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
	    ball.setBallXValue(300);
	    ball.setBallYValue(600);
	    assertEquals(false, ball.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testBallMissesPaddle() {
		ball.setPaddleLocation(600, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
	    ball.setBallXValue(0);
	    ball.setBallYValue(600);
	    assertEquals(true, ball.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testPaddleAtLeftScreenEdge() {
		ball.setPaddleLocation(600, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
	    ball.setPaddleMoveAmount(30);
	    ball.setPaddleX(0);
	    
	    assertEquals(0, ball.getPaddleX());
	    ball.movePaddleLeft();
	    assertEquals(0, ball.getPaddleX());
	}
	
	@Test
	public void testPaddleAtRightScreenEdge() {
		ball.setPaddleLocation(600, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
	    ball.setPaddleMoveAmount(30);
	    ball.setPaddleX(400);
	    
	    assertEquals(400, ball.getPaddleX());
	    ball.movePaddleRight();
	    assertEquals(400, ball.getPaddleX());
	}
	
	@Test
	public void testPaddleMovesLeft() {
		ball.setPaddleLocation(600, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
	    ball.setPaddleMoveAmount(30);
	    
	    assertEquals(300, ball.getPaddleX());
	    ball.movePaddleLeft();
	    assertEquals(270, ball.getPaddleX());
	}
	
	@Test
	public void testPaddleMovesRight() {
		ball.setPaddleLocation(600, 600);
		ball.setPaddleWidth(200);
	    ball.setPaddleHeight(30);
	    ball.setPaddleMoveAmount(30);
	    
	    assertEquals(300, ball.getPaddleX());
	    ball.movePaddleRight();
	    assertEquals(330, ball.getPaddleX());
	}
	
}
