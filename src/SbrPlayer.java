import java.util.Random;

public class SbrPlayer implements Player {
	private Stone stone;
	
	public SbrPlayer(Stone stoneColor) {
		this.stone = stoneColor;
	}

	@Override
	public Coordinate getMove(Board b) {
		final int numOfRows = 19;
		final int numOfCols = 19;
		Random rand = new Random();
		MyCoordinate randCoord = null;
		
		//generate a random coordinate
					
		int moveRow = rand.nextInt(numOfRows);
		int moveColumn = rand.nextInt(numOfCols);
		randCoord = new MyCoordinate(moveRow, moveColumn);
						
		return randCoord;
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
