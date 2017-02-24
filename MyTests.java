package PingPong;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyTests {

	@Test
	public void testCheckPaddleReturnsTrue() {
		Ball pp = new Ball(true, true, 0, 0);
		
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    
		assertEquals(true, pp.checkPaddle(true, 40, 50));
	}
	
	@Test
	public void testBallHitsPaddle() {
		Ball pp = new Ball(true, true, 0, 0);
		
		pp.setPaddleLocation(800, 600);
		pp.setPaddleWidth(200);
	    pp.setPaddleHeight(30);
	    
	    assertEquals(false, pp.checkPaddle(true, 400, 800));
	}

}
