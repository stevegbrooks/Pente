
public class Game {

	public static void main(String[] args) {
		SbrPlayer sbr = new SbrPlayer(Stone.YELLOW);
		HumanPlayer human = new HumanPlayer(Stone.RED);
		MyBoard b = new MyBoard();
		
		while(true) {
			if (sbr.getStone().equals(Stone.RED)) {
				while(true) {
					try {
						b.placeStone(Stone.RED, sbr.getMove(b));
						break;
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
					}
				}
				System.out.println(b);
				if (b.gameOver()) {
					break;
				}
				while(true) {
					try {
						b.placeStone(Stone.YELLOW, human.getMove(b));
						break;
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
					}
				}
				System.out.println(b);
				System.out.println("Red Captures: " + b.getRedCaptures());
				System.out.println("Yellow Captures: " + b.getYellowCaptures());
				if (b.gameOver()) {
					break;
				}
			} else {
				while(true) {
					try {
						b.placeStone(Stone.RED, human.getMove(b));
						break;
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
					}
				}
				System.out.println(b);
				if (b.gameOver()) {
					break;
				}
				while(true) {
					try {
						b.placeStone(Stone.YELLOW, sbr.getMove(b));
						break;
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
					}
				}
				System.out.println(b);
				System.out.println("Red Captures: " + b.getRedCaptures());
				System.out.println("Yellow Captures: " + b.getYellowCaptures());
				if (b.gameOver()) {
					break;
				}
			}
		}
		Stone winningColor = b.getWinner();
		System.out.println("The winner is: " + winningColor);
	}
	
}

