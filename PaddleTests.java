package PingPong;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaddleTests {

	@Test
	public void testCheckPaddleReturnsTrue() {
		Ball pp = new Ball(true, true, 0, 0);		
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
		assertEquals(true, pp.checkPaddle(true, 40, 50));
	}
	
	@Test
	public void testBallReachesPaddle() {
		Ball pp = new Ball(true, true, 0, 0);
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    assertEquals(false, pp.checkPaddle(true, 400, 800));
	}
	
	@Test
	public void testBallShortOfPaddle() {
		Ball pp = new Ball(true, true, 0, 0);		
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    assertEquals(true, pp.checkPaddle(true, 400, 0));
	}
	
	@Test
	public void testBallHitsPaddle() {
		Ball pp = new Ball(true, true, 0, 0);		
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setBallXValue(300);
	    pp.setBallYValue(600);
	    assertEquals(false,pp.setBallDirectionAfterReachingPaddle());
	}
	
	@Test
	public void testBallMissesPaddle() {
		Ball pp = new Ball(true, true, 0, 0);		
		pp.setPaddleLocation(600, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    pp.setBallXValue(0);
	    pp.setBallYValue(600);
	    assertEquals(true,pp.setBallDirectionAfterReachingPaddle());
	}
	
}
