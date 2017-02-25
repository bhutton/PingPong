package PingPong;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestBall {
	
	private Ball pp = new Ball(true, true, 0, 0);
	
	

	@Test
	public void testSetBallImg() {
		String 	ballImg = "./src/PingPong/ball.png";
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
		
		assertEquals(y, pp.getY());
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
		
		assertEquals(13, pp.getY());
	}
	
	@Test
	public void testShiftingBallLeft() {
		int x = 10;
		
		pp.setBallXValue(x);
		pp.shiftX();
		assertEquals(11, pp.getX());
	}

}
