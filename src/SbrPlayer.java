import java.util.HashMap;
import java.util.Random;

public class SbrPlayer implements Player {
	private Stone stone;
	private HashMap<Integer, Coordinate> sbrBoard;
	private final int numOfRows = 19;
	private final int numOfCols = 19;
	
	public SbrPlayer(Stone stoneColor) {
		this.stone = stoneColor;
		sbrBoard = new HashMap<>();
	}

	@Override
	public Coordinate getMove(Board b) {
		MyCoordinate center = new MyCoordinate(9, 9);
		Random rand = new Random();
		MyCoordinate move = null;
		int lastMoveNum = b.getMoveNumber();
		//first four moves are somewhat deterministic
		//after that the AI takes over
		if (lastMoveNum == 0) {
			move = center;
		} else if (lastMoveNum == 1) {
			//the best move here is within one intersection of center
			move = pickRandCoordWithBounds(b, center, 0, 1);
		} else if (lastMoveNum == 2) {
			//last move will necessarily be 9,9
			//best move in this situation is as close as you can get to the center 
			//without being within 2 or fewer intersections
			final int lowerBound = 3; //e.g. coord = 9,12 or 9,6
			final int upperBound = 6; //e.g. coord = 6,6 or 12,12
			move = pickRandCoordWithBounds(b, center, lowerBound, upperBound);
		} else if (lastMoveNum == 3) {
			//best move here is place a stone immediately next to the last one
			//that Sbr placed
			Coordinate lastMove = sbrBoard.get(lastMoveNum - 1);
			move = pickRandCoordWithBounds(b, lastMove, 0, 1);
		} else {
			//TODO make the AI stuff here
			/**
			 * my idea is to have 3 checks as in MyBoard:
			 * 1). Check for a pente opportunity, if its there, take it
			 * 2). Check for a capture opportunity, if its there, take it
			 * 3). Check for an opponent 4 in a row, then 3 in a row, if there
			 * 		are any, then block them appropriately
			 * 
			 * If all these checks turn up false, then call makeBestMove().
			 */
			int moveRow = rand.nextInt(numOfRows);
			int moveColumn = rand.nextInt(numOfCols);
			move = new MyCoordinate(moveRow, moveColumn);
		}
		
		sbrBoard.put(lastMoveNum + 1, move);
		
		return move;
	}
	/**
	 * This method returns a random coordinate with bounds specified based on
	 * proximity to a supplied other coordinate.
	 * 
	 * It will only return a coordinate for an intersection that is currently
	 * empty and in bounds.
	 * 
	 * @param stone the stone to compare distance to
	 * @param lowerBound the minimum distance from parameter stone
	 * @param upperBound the maximum distance from parameter stone
	 * @return a MyCoordinate object for a legal move
	 */
	private MyCoordinate pickRandCoordWithBounds(Board b, Coordinate stone, 
			int lowerBound, int upperBound) {
		
		Random rand = new Random();
		MyCoordinate move = null;
		
		while (true) {
			int randRow = rand.nextInt(numOfRows);
			int randCol = rand.nextInt(numOfCols);
			
			int distanceX = Math.abs(stone.getRow() - randRow);
			int distanceY = Math.abs(stone.getColumn() - randCol);
			int netDistance = distanceX + distanceY;
			//outside of 2 intersections from center, but as close as possible
			if (netDistance >= lowerBound && netDistance <= upperBound 
					&& netDistance != 0) {
				MyCoordinate candidate = new MyCoordinate(randRow, randCol);
				if (!b.isOutOfBounds(candidate)) {
					if (b.isEmpty(candidate)) {
						move = candidate;
						break;
					}
				}
			}
		}
		return move;
	}
	
	/**
	 * This method finds the optimal move for Sbr assuming
	 * there are no pente, capture, or defensive opportunities 
	 * available.
	 * 
	 * If Sbr is calling this method, it means there are already at least
	 * two pieces of Sbr's on the board already.
	 * 
	 * The general strategy is to place a stone such that you extend any 
	 * current runs, and fill in gaps in current runs.
	 *  
	 * @param lastMove the last move that was made by Sbr,
	 * which will always equal (b.getMoveNumber - 1).
	 */
	private Coordinate makeBestMove(Board b, Integer lastMoveKey) {		
		Coordinate lastMove = sbrBoard.get(lastMoveKey);
		int moveRow = lastMove.getRow();
		int moveCol = lastMove.getColumn();

		Random rand = new Random();
		Coordinate move = null;
		
		int directionX = 0;
		int directionY = 0;

		//iterate through all the intersections
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				//if the index is an intersection that is close* to the 
				//current coordinate *and is not the current coordinate*
					//close = within 4 intersections
				int distanceX = Math.abs(i - moveRow);
				int distanceY = Math.abs(j - moveCol);
				if (distanceX <= 4 && distanceY <= 4) {
					if ((distanceX + distanceY) != 0) {
						Coordinate adjacentCoord = new MyCoordinate(i, j);
						//then check if that index has a friendly stone in it
						if (b.pieceAt(adjacentCoord).equals(getStone())) {
							//if it does, then record the direction, 
							directionX = i - moveRow;
							directionY = j - moveCol;
							//now start the search
							final int SEARCH_AREA = 8;
							Coordinate[] searchArray = new Coordinate[SEARCH_AREA];
							//fill the search array with eligible coordinates
							for (int s = -3; s <= SEARCH_AREA/2; s++) {
								Coordinate coord = new MyCoordinate(s*directionX + moveRow, 
										s*directionY + moveCol);
								int index = s + 3;
								searchArray[index] = coord;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private void scanSearchArray(Board b, Coordinate[] searchArray) {
		int friendlyCounter = 0;
		int emptyCounter = 0;
		int enemyCounter = 0;
		for (int s = 0; s < searchArray.length; s++) {
			//record the direction of the array
			//make sure its in-bounds
			if (!b.isOutOfBounds(searchArray[s])) {
				if (b.pieceAt(searchArray[s]).equals(getStone())) {
				} else {
				}
			}
		}
	}
	
	@Override
	public Stone getStone() {
		return stone;
	}

}
