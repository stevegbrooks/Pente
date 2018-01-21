import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyBoardTest {
	private MyBoard b;
	
	@Before
	public void setUp() {
		b = new MyBoard();
	}

	@Test
	public void testBoardNotNull() {
		assertNotNull("This board should have something in it", b);
	}

}
