package PingPong;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestPaddle {
	
	private Ball pp = new Ball(true, true, 0, 0);
	
	@Test
	public void testSetPaddleImg() {
		String 	paddleImg = "./src/PingPong/paddle.png";
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
	
	
	
	
}
