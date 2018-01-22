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
		final int centerRow = 9;
		final int centerCol = 9;
		
		Random rand = new Random();
		MyCoordinate move = null;
		int moveNum = b.getMoveNumber();
		
		if (moveNum == 0) {
			move = new MyCoordinate(centerRow, centerCol);
		} else {
			int moveRow = rand.nextInt(numOfRows);
			int moveColumn = rand.nextInt(numOfCols);
			move = new MyCoordinate(moveRow, moveColumn);
		}									
		return move;
	}

	@Override
	public Stone getStone() {
		return stone;
	}

}
