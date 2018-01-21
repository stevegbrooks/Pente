import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SbrPlayerTest {
	private SbrPlayer sbr;
	private MyBoard b;

	@Before
	public void setUp() {
		sbr = new SbrPlayer(Stone.RED);
	}

	@Test
	public void testStoneColor() {
		Stone red = sbr.getStone();
		assertEquals("should be a RED stone", Stone.RED, red);
	}
	
	@Test
	public void testGetMove() {
		b = new MyBoard();
		Coordinate move = sbr.getMove(b);
	}

}
