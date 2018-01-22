import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		SbrPlayer sbr = new SbrPlayer(Stone.RED);
		HumanPlayer human = new HumanPlayer(Stone.YELLOW);
		MyBoard b = new MyBoard();
		
		while(!b.gameOver()) {
			if (sbr.getStone().equals(Stone.RED)) {
				try {
					b.placeStone(Stone.RED, sbr.getMove(b));
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				}
				System.out.println(b);
				while(true) {
					try {
						b.placeStone(Stone.YELLOW, human.getMove(b));
						break;
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
					}
				}
				System.out.println(b);
			} else {
				try {
					b.placeStone(Stone.YELLOW, sbr.getMove(b));
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				}
				System.out.println(b);
				try {
					b.placeStone(Stone.RED, human.getMove(b));
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				}
				System.out.println(b);
			}
		}
		
	}
	
}

