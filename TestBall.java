package PingPong;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestBall {
	
	private Ball pp = new Ball(true, true, 0, 0);
	
	@Test
	public void testSetBallImg() {
		String 	ballImg = "./src/PingPong/images/ball.png";
		BufferedImage imgBall1 = null, imgBall2 = null;
		
		try { imgBall1 = ImageIO.read(new File(ballImg)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		pp.setBallImage(ballImg);
		imgBall2 = pp.getBall();
		
		byte[] byteArray1 = ((DataBufferByte) imgBall1.getData().getDataBuffer()).getData();
		byte[] byteArray2 = ((DataBufferByte) imgBall2.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byteArray1, byteArray2);
	}
	
	@Test
	public void testSetBallXValueAndGetReturn() {
		int x = 10;
		
		pp.setBallXValue(x);
		
		assertEquals(x, pp.getX());
	}
	
	@Test
	public void testSetBallYValueAndGetReturn() {
		int y = 10;
		
		pp.setBallYValue(y);
		
		assertEquals(y, pp.getBrickY());
	}
	
	@Test
	public void testIncrementingXValue() {
		int x = 10;
		
		pp.setBallXValue(x);
		pp.incX();
		
		assertEquals(11, pp.getX());
	}
	
	@Test
	public void testIncrementingYValue() {
		int y = 10;
		
		pp.setBallYValue(y);
		pp.incY();
		
		assertEquals(13, pp.getBrickY());
	}
	
	@Test
	public void testShiftingBallRight() {
		int x = 10;
		
		pp.setBallXValue(x);
		pp.shiftX();
		assertEquals(11, pp.getX());
	}
	
	@Test
	public void testShiftingBallLeft() {
		int x = 10;
		
		pp.setBallXValue(x);
		pp.setLeft();
		pp.shiftX();
		assertEquals(9, pp.getX());
	}
	
	@Test
	public void testCheckBallDirectionAfterReachingBricks() {
		int x = 10, y = 10;
		
		pp.createWall(4);
		pp.setBallUp();
		pp.setBallXValue(x);
		pp.setBallYValue(y);		
		
		assertEquals(false, pp.getDown());
		assertEquals(true, pp.setBallDirectionAfterReachingBricks());
	}

	@Test
	public void testCheckBallHitsSideOfBrick() {
		int x = 10, y = 10;

		pp.createWall(4);
		//pp.setBallUp();
		pp.setBallXValue(x);
		pp.setBallYValue(y);

		assertEquals(true, pp.getDown());
		assertEquals(true, pp.setBallDirectionAfterReachingBricks());

		pp.setBallUp();
		pp.setBallXValue(x);
		pp.setBallYValue(y);

		System.out.println("Ball X = " + pp.getBallX());
		System.out.println("Ball Down First = " + pp.getBallDown());
		System.out.println("Set Ball Direction = " + pp.setBallDirectionAfterReachingBricks());
		System.out.println("Ball Down Second = " + pp.getBallDown());

		//assertEquals(true, pp.getBallDown());
	}
	
	@Test
	public void testCheckBallDirectionAfterReachingPaddle() {
		int x = 450, y = 800;
		
		pp.createWall(2);
		pp.setDown();
		pp.setBallXValue(x);
		pp.setBallYValue(y);	
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
		
		assertEquals(true, pp.getDown());
		assertEquals(false, pp.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testCheckBallDirectionAfterMissingPaddle() {
		int x = 0, y = 800;
		
		pp.createWall(2);
		pp.setDown();
		pp.setBallXValue(x);
		pp.setBallYValue(y);	
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
		
		assertEquals(true, pp.getDown());
		assertEquals(true, pp.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testCheckStart() {
		int brickHeight = 50;
		int y = ((brickHeight + 5) * 4);
		
		pp.setBallYValue(0);
		pp.checkStart();
		
		assertEquals(y,pp.getBrickY());
	}
	
	@Test
	public void testCheckBallHitsRightSideOfScreen() {
		int appletWidth = 800;
		
		pp.setBallXValue(800);
		pp.setRight();
		pp.checkRight(appletWidth);
		assertEquals(false, pp.getRight());
	}
	
	@Test
	public void testCheckBallHitsLeftSideOfScreen() {
		pp.setBallXValue(0);
		pp.setLeft();
		pp.checkLeft();
		assertEquals(true, pp.getRight());
	}
	
	@Test
	public void testCheckBallHitsTopOfScreen() {
		pp.setBallUp();
		pp.setBallYValue(0);
		pp.checkTop();
		assertEquals(true,pp.getDown());
	}
	
	@Test
	public void testCheckBallHitsBottomOfScreen() {
		int appletHeight = 600;
		pp.setBallDown(true);
		pp.setBallYValue(600);
		pp.checkBottom(appletHeight);
		assertEquals(false,pp.getDown());
	}
	
	@Test
	public void testCheckEdges() {
		pp.setBallXValue(600);
		pp.setBallYValue(600);
		pp.setRight();
		pp.setDown();
		
		assertEquals(true,pp.getRight());
		assertEquals(true,pp.getDown());
		assertEquals(true,pp.checkBallActive());
		
		pp.checkEdges(600, 600);
		
		assertEquals(false,pp.getRight());
		assertEquals(false,pp.getDown());
		assertEquals(false,pp.checkBallActive());
		
		pp.setBallXValue(0);
		pp.checkEdges(600, 600);
		
		assertEquals(true,pp.getRight());
		assertEquals(false,pp.getDown());
		assertEquals(false,pp.checkBallActive());
		
		pp.setBallYValue(0);
		pp.checkEdges(600, 600);
		assertEquals(true,pp.getRight());
		assertEquals(false,pp.getDown());
		assertEquals(false,pp.checkBallActive());
	}
	
	@Test
	public void testUpdateBallCoordinates() {
		pp.ballSetStart();
		pp.setBallXValue(600);
		pp.setBallYValue(600);
		pp.setRight();
		pp.setDown();
		pp.updateBallCoordinates();
		assertEquals(601,pp.getX());
		assertEquals(603,pp.getBrickY());
		
		pp.setLeft();
		pp.setBallUp();
		pp.updateBallCoordinates();
		assertEquals(600,pp.getX());
		assertEquals(600,pp.getBrickY());
	}
	
	@Test
	public void testCheckBallActive() {
		pp.checkBallActive();
	}
	
	@Test
	public void testSetBallInActive() {
		assertEquals(false,pp.setBallInActive());
	}
	
	@Test
	public void testSetBallActive() {
		assertEquals(true,pp.setBallActive());
	}
	
	@Test
	public void testItializeBall() {
		pp.initializeBall();
		
		assertTrue(pp.getBallX() == pp.getPaddleX());
		assertTrue(pp.getBallY() == pp.getPaddleY());
	}
	
	@Test
	public void testSetStart() {
		assertEquals(true, pp.ballSetStart());
	}
	
	@Test
	public void testSetStop() {
		assertEquals(true, pp.ballSetStop());
	}
	
	@Test
	public void testBallMoveswithPaddle() {
		pp.initializeBall();
		pp.setPaddleWidth(120);
		pp.setPaddleLocation(800, 600);
		pp.ballSetStop();
		pp.setPaddleMoveAmount(30);
		
		assertEquals(false, pp.getBallStatus());
		
		pp.movePaddleRight();
		pp.calculateCurrentLocation(800, 600);
		
		assertEquals(270, pp.getPaddleX());
		assertEquals(270 + pp.getPaddleWidth() / 2 - 30, pp.getX());
	}

}
