import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SbrPlayerTest {
	private SbrPlayer sbr;
	private HumanPlayer human;
	private MyBoard b;

	@Before
	public void setUp() {
		sbr = new SbrPlayer(Stone.RED);
		human = new HumanPlayer(Stone.YELLOW);
		b = new MyBoard();
	}

	@Test
	public void testStoneColor() {
		Stone red = sbr.getStone();
		assertEquals("should be a RED stone", Stone.RED, red);
	}
	
	@Test
	public void testGetMove() {
		Coordinate move1 = sbr.getMove(b);
		b.placeStone(Stone.RED, move1);
		Coordinate move2 = sbr.getMove(b);
		b.placeStone(Stone.YELLOW, move2);
		Coordinate move3 = sbr.getMove(b);
		b.placeStone(Stone.RED, move3);
	}

}
