
public class MyBoard implements Board {

	private Stone[][] board;
	private final int numOfRows = 19;
	private final int numOfColumns = 19;
	private final int centerRow = 9;
	private final int centerCol = 9;

	private int moveNumber;
	private int redCaptures;
	private int yellowCaptures;

	/**
	 * Constructor creates a new Pente board.
	 * moveNumber is set to 0.
	 */
	public MyBoard() {

		//creates a 19x19 board filled with empty stones
		//9,9 is center

		this.board = new Stone[numOfRows][numOfColumns];

		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				board[i][j] = Stone.EMPTY;
			}
		}

		moveNumber = 0;

	}
	/**
	 * This method takes a move and stone color from a Player 
	 * and places the appropriate the stone at that location.
	 * 
	 * It checks if the move is legal before placing.
	 * 
	 * If it is an illegal move it throws an exception.
	 * 
	 * It then updates the moveNumber counter.
	 * 
	 * After a legal stone has been placed, it checks to see
	 * if a capture has been made. If it has it updates
	 * the capture counter for the appropriate stone color.
	 */
	@Override
	public void placeStone(Stone s, Coordinate c) {
		int moveRow = c.getRow();
		int moveCol = c.getColumn();

		//check if out of bounds first
		if (isOutOfBounds(c)) {
			throw new IllegalArgumentException("Illegal Move "
					+ "- out of bounds");
		}

		//If RED...
		if (s.equals(Stone.RED)) {
			//if its the first move of the game, then center is only legal move
			if (moveNumber == 0) {
				//if move isn't center, then throw exception
				if (moveRow != centerRow && moveCol != centerCol) {
					throw new IllegalArgumentException("Illegal Move "
							+ "- first move must be center (9,9)");
				} else {
					board[moveRow][moveCol] = Stone.RED;
				}
				//RED-players second move must be outside of 2 intersections of center
			} else if (moveNumber == 2) {
				//if the move is inside this restricted area, then throw exception
				if (((moveRow >= 7 && moveCol >= 7) &&
						(moveRow <= 11 && moveCol <= 11))) {
					throw new IllegalArgumentException("Illegal Move "
							+ "- must be more than two intersections away from center");
				} else {
					board[moveRow][moveCol] = Stone.RED;
				}
			} else {
				board[moveRow][moveCol] = Stone.RED;
			}
		}

		//If YELLOW, then any move into a non-empty space is legal.
		if (s.equals(Stone.YELLOW)) {
			if (!isEmpty(c)) {
				throw new IllegalArgumentException("Illegal Move "
						+ "- space occupied");
			} else {
				board[moveRow][moveCol] = Stone.YELLOW;
			}
		}

		//increment moveNumber
		moveNumber++;
		//check for captures
		checkCapture(s, c);
	}

	@Override
	public Stone pieceAt(Coordinate c) {
		int row = c.getRow();
		int col = c.getColumn();

		Stone stone = board[row][col];

		return stone;
	}

	@Override
	public boolean isOutOfBounds(Coordinate c) {
		int moveRow = c.getRow();
		int moveCol = c.getColumn();

		if (moveRow > numOfRows || moveRow < 0) {
			return true;
		}
		if (moveCol > numOfColumns || moveCol < 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isEmpty(Coordinate c) {
		int row = c.getRow();
		int col = c.getColumn();

		Stone stone = board[row][col];
		if (stone.equals(Stone.EMPTY)) {
			return true;
		}
		return false;
	}

	@Override
	public int getMoveNumber() {
		return moveNumber;
	}

	@Override
	public int getRedCaptures() {
		return redCaptures;
	}

	@Override
	public int getYellowCaptures() {
		return yellowCaptures;
	}
	/**
	 * This method will check if anyone has gotten
	 * 5 in a row (horiz, diag, or vert).  It will also
	 * query the # of captures to check if either equal 
	 * 5 or more. If either of these things is true, then it 
	 * returns true.
	 */
	@Override
	public boolean gameOver() {
		//check if 5 in a row
		//then check if either redCaptures == 5 or yellowCaptures == 5
		//if any of those are true, then return true
		return false;
	}

	@Override
	public Stone getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		String row = new String();
		//print column headers
		for (int i = 0; i < numOfColumns; i++) {
			if (i < 10) {
				row += "   " + i;
			} else {
				row += "  " + i;
			}
		}
		row += '\n';

		//print row contents
		for (int i = 0; i < numOfRows; i++) {
			//print row #s
			if (i < 10) {
				row += " " + i;
			} else {
				row += i;
			}
			for (int j = 0; j < numOfColumns; j++) {
				//for each coordinate in each row
				//check the stone color and add to the string
				if (board[i][j].equals(Stone.EMPTY)) {
					row = row + "   |";
				} else if (board[i][j].equals(Stone.RED)) {
					row = row + " O |";
				} else {
					row = row + " X |";
				}
			}
			row = row + '\n';
		}
		return row;
	}

	private void checkCapture(Stone currentStoneColor, Coordinate c) {
		//check for captures - a capture can occur if a group of two
		//stones of the enemy color is flanked on both sides. This can
		//happen vertically, horizontally, or diagonally.

		//determine enemy stone color
		Stone enemyColor = null;
		if (currentStoneColor.equals(Stone.RED)) {
			enemyColor = Stone.YELLOW;
		} else if (currentStoneColor.equals(Stone.YELLOW)) {
			enemyColor = Stone.RED;
		}

		int moveRow = c.getRow();
		int moveCol = c.getColumn();

		MyCoordinate enemyAdjacent = null;
		MyCoordinate enemyAdjacent2 = null;

		int directionX = 0;
		int directionY = 0;

		//iterate through all the intersections touching the current one
		//TODO make sure it doesn't go out of bounds
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				//if the index is an intersection that is touching the 
				//getMove() coordinate...
				if (Math.abs(i - moveRow) <= 1 && Math.abs(j - moveCol) <= 1) {
					MyCoordinate adjacentCoord = new MyCoordinate(i, j);
					//then check if that index has an enemy stone
					if (pieceAt(adjacentCoord).equals(enemyColor)) {
						enemyAdjacent = adjacentCoord;
						//check direction that enemy is in
						directionX = i - moveRow;
						directionY = j - moveCol;
						//check next one over
						MyCoordinate adjacentCoord2 = new MyCoordinate(i + directionX, j + directionY);
						if (pieceAt(adjacentCoord2).equals(enemyColor)) {
							enemyAdjacent2 = adjacentCoord2;
							//check that there's a friendly piece next one over
							MyCoordinate adjacentCoord3 = new MyCoordinate(adjacentCoord2.getRow() + directionX, 
									adjacentCoord2.getColumn() + directionY);
							if (pieceAt(adjacentCoord3).equals(currentStoneColor)) {
								//capture confirmed! change enemyAdjacent and enemyAdjacent2 to EMPTY
								board[enemyAdjacent.getRow()][enemyAdjacent.getColumn()] = Stone.EMPTY;
								board[enemyAdjacent2.getRow()][enemyAdjacent2.getColumn()] = Stone.EMPTY;
								//and increase capture counter by 1 for the current color
								if (currentStoneColor.equals(Stone.RED)) {
									redCaptures++;
								} else {
									yellowCaptures++;
								}
							}
						}
					}
				}
			}
		}
	}
}
