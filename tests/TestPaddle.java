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
	
	private final Ball pp = new Ball();
	
	@Test
	public void testSetPaddleImg() {
		String 	paddleImg = "./src/com/pingpong/images/paddle.png";
		BufferedImage imgPaddle1 = null, imgPaddle2 = null;
		
		try { imgPaddle1 = ImageIO.read(new File(paddleImg)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		pp.setBallImage(paddleImg);
		imgPaddle2 = pp.getBall();
		
		byte[] byteArray1 = ((DataBufferByte) imgPaddle1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgPaddle2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void testCheckPaddleReturnsTrue() {				
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
		assertEquals(true, pp.checkPaddle(true, 40, 50));
	}
	
	@Test
	public void testBallReachesPaddle() {
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    assertEquals(false, pp.checkPaddle(true, 400, 800));
	}
	
	@Test
	public void testBallShortOfPaddle() {
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    assertEquals(true, pp.checkPaddle(true, 400, 0));
	}
	
	@Test
	public void testBallHitsPaddle() {
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setBallXValue(300);
	    pp.setBallYValue(600);
	    assertEquals(false,pp.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testBallMissesPaddle() {
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setBallXValue(0);
	    pp.setBallYValue(600);
	    assertEquals(true,pp.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testPaddleAtLeftScreenEdge() {
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setPaddleMoveAmount(30);
	    pp.setPaddleX(0);
	    
	    assertEquals(0, pp.getPaddleX());
	    pp.movePaddleLeft();
	    assertEquals(0, pp.getPaddleX());
	}
	
	@Test
	public void testPaddleAtRightScreenEdge() {
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setPaddleMoveAmount(30);
	    pp.setPaddleX(400);
	    
	    assertEquals(400, pp.getPaddleX());
	    pp.movePaddleRight();
	    assertEquals(400, pp.getPaddleX());
	}
	
	@Test
	public void testPaddleMovesLeft() {
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setPaddleMoveAmount(30);
	    
	    assertEquals(300, pp.getPaddleX());
	    pp.movePaddleLeft();
	    assertEquals(270, pp.getPaddleX());
	}
	
	@Test
	public void testPaddleMovesRight() {
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setPaddleMoveAmount(30);
	    
	    assertEquals(300, pp.getPaddleX());
	    pp.movePaddleRight();
	    assertEquals(330, pp.getPaddleX());
	}
	
}
