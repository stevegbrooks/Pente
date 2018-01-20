import java.util.Scanner;

public class HumanPlayer implements Player {
	private Stone stone;
	
	public HumanPlayer(Stone stoneColor) {
		this.stone = stoneColor;
	}
	
	@Override
	public Coordinate getMove(Board b) {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please enter a coordinate using the following"
				+ "format (comma separated): row#, col# ");
		String line = in.nextLine();
		String[] lineArr = line.split(",", 2);
		
		int row = Integer.parseInt(lineArr[0]);
		int col = Integer.parseInt(lineArr[1]);
		
		Coordinate coords = new MyCoordinate(row, col);
		in.close();
		
		//If RED...
		if (stone.equals(Stone.RED)) {
			//if its the first move of the game, then move center only
			if (b.getMoveNumber() == 0) {
				Coordinate center = new MyCoordinate(9,9);
				//if move isn't center, then throw exception
				if (!coords.equals(center)) {
					throw new IllegalArgumentException("Illegal Move "
							+ "- first move must be center (9,9)");
				} else {
					return coords;
				}
			//RED-players second move must be outside of 2 intersections of center
			} else if (b.getMoveNumber() == 2) {
				//if the move is inside this restricted area, then throw exception
				if (!((row < 7 || col > 11) &
						(row < 7 || col > 11))) {
					throw new IllegalArgumentException("Illegal Move "
							+ "- must be more than two intersections away from center");
				} else {
					return coords;
				}
					
			}
		}
		//If YELLOW, then any move is fine as long as its empty space
		if (!b.isEmpty(coords)) {
			throw new IllegalArgumentException("Illegal Move "
					+ "- space occupied");
		}
		return coords;
	}

	@Override
	public Stone getStone() {
		return stone;
	}

}
