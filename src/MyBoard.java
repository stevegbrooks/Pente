
public class MyBoard implements Board {
	
	private Coordinate[][] board;
	
	/**
	 * Constructor takes in an integer which is the desired
	 * side length of the square board.
	 */
	public MyBoard() {
	
		//creates a 19x19 board - fill with coordinates
			//9,9 is center
		final int numOfRows = 19;
		final int numOfColumns = 19;
		
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				board[i][j] = new MyCoordinate(i, j);
			}
		}
		
	}
	
	@Override
	public void placeStone(Stone s, Coordinate c) {
		// TODO Auto-generated method stub

	}

	@Override
	public Stone pieceAt(Coordinate c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOutOfBounds(Coordinate c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty(Coordinate c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMoveNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRedCaptures() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYellowCaptures() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean gameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Stone getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

}
