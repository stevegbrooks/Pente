import java.util.Scanner;

public class MainTester {

	public static void main(String[] args) {
		SbrPlayer sbr = new SbrPlayer(Stone.RED);
		HumanPlayer human = new HumanPlayer(Stone.YELLOW);
		MyBoard b = new MyBoard();
		
		b.placeStone(sbr.getStone(), sbr.getMove(b));
		

	}

}
