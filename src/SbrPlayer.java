import java.util.Random;

public class SbrPlayer implements Player {
	private Stone stone;
	
	public SbrPlayer(Stone stoneColor) {
		this.stone = stoneColor;
	}

	@Override
	public Coordinate getMove(Board b) {
		final int totalNumOfCoords = 19 * 19;
		Random rand = new Random();
		MyCoordinate randCoord = null;
		MyCoordinate move = null;
		int moveNumber = b.getMoveNumber();
		
		//generate a random coordinate - but follow the rules of the game
		
		//IF AI IS RED
		if (stone.compareTo(Stone.RED) == 0) {
			if (moveNumber == 0) {
				//first move is center
				move = new MyCoordinate(9, 9);
			} else if (moveNumber == 2) {
				//second move is restricted
				for (int i = 0; i < totalNumOfCoords; i++) {
					
					int moveRow = rand.nextInt(19);
					int moveColumn = rand.nextInt(19);
					randCoord = new MyCoordinate(moveRow, moveColumn);
					
					//first, check that its an empty coordinate
					if (b.isEmpty(randCoord)) {
						
						int randRow = randCoord.getRow();
						int randCol = randCoord.getColumn();
						
						//then, check that its 3 or more intersections 
						//away from center
						if ((randRow < 7 || randRow > 11) &
								(randCol < 7 || randCol > 11)) {
							move = randCoord;
							break;
						}
					}
				}
				//for all other moves after the first and second
				//just pick a random non-empty coordinate
			} else {
				move = pickRandNonEmptyCoord(b);
			}
		//IF AI IS YELLOW - any non-empty coordinate for any move
		} else if (stone.compareTo(Stone.YELLOW) == 0) {
			move = pickRandNonEmptyCoord(b);
		} else {
			System.out.println("Player must be assigned to either RED or YELLOW");
		}
		return move;
	}
	
	private MyCoordinate pickRandNonEmptyCoord(Board b) {
		
		Random rand = new Random();
		MyCoordinate randCoord = null;
		MyCoordinate move = null;
		
		final int totalNumOfCoords = 19 * 19;
		
		for (int i = 0; i < totalNumOfCoords; i++) {
		
			int moveRow = rand.nextInt(19);
			int moveColumn = rand.nextInt(19);
			randCoord = new MyCoordinate(moveRow, moveColumn);
			
			if (b.isEmpty(randCoord)) {
				move = randCoord;
				break;
			}
		}
		return move;
	}

	@Override
	public Stone getStone() {
		return stone;
	}

}
