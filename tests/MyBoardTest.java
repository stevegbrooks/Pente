import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyBoardTest {
	private MyBoard b;
	private MyCoordinate c;
	private MyCoordinate c2;
	private Stone sY;
	private Stone sR;
	
	@Before
	public void setUp() {
		b = new MyBoard();
		c = new MyCoordinate(9,9);
		sY = Stone.YELLOW;
		sR = Stone.RED;
		c2 = new MyCoordinate(9,8);
	}

	@Test
	public void testBoardNotNull() {
		assertNotNull("This board should have something in it", b);
	}
	
	@Test
	public void testPlaceStone() {
		b.placeStone(sY, c);
		
		Stone stone = b.pieceAt(c);
		
		assertEquals(sY, stone);
	}
	
	@Test
	public void testMoveNumber() {
		b.placeStone(sR, c);
		b.placeStone(sY, c2);
		
		int moveNum = b.getMoveNumber();
		
		assertEquals(2, moveNum);
	}
	
	@Test
	public void testCheckCapture() {
		b.placeStone(sR, c); //9,9 RED
		b.placeStone(sY, c2); //9,8 YELLOW
		MyCoordinate c4 = new MyCoordinate(9,7);
		b.placeStone(sY, c4); //9,7 YELLOW
		MyCoordinate c5 = new MyCoordinate(9,6);
		b.placeStone(sR, c5); //9,6 RED
		//9,7 and 9,8 should now be empty
		Stone stone = b.pieceAt(c4);
		Stone stone2 = b.pieceAt(c2);

		assertEquals(Stone.EMPTY, stone);
		assertEquals(Stone.EMPTY, stone2);
	}
	
	@Test
	public void testCheckFiveRow2() {
		b.placeStone(sY, c); //9,9 YELLOW
		b.placeStone(sY, c2); //9,8 YELLOW
		MyCoordinate c3 = new MyCoordinate(9,7);
		b.placeStone(sY, c3); 
		MyCoordinate c4 = new MyCoordinate(9,6);
		b.placeStone(sY, c4); 
		MyCoordinate c5 = new MyCoordinate(9,5);
		b.placeStone(sY, c5);
		
		assertTrue(b.gameOver());
		assertEquals(Stone.YELLOW, b.getWinner());
	}

}
