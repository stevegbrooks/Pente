import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SbrPlayer implements Player {
	private Stone friendlyStone;
	private HashMap<Integer, Coordinate> sbrBoard;
	private final int numOfRows = 19;
	private final int numOfCols = 19;
	private Stone enemyStone;
	
	public SbrPlayer(Stone stone) {
		friendlyStone = stone;
		sbrBoard = new HashMap<>();
		
		if (stone.equals(Stone.RED)) {
			enemyStone = Stone.YELLOW;
		} else {
			enemyStone = Stone.RED;
		}
	}

	@Override
	public Coordinate getMove(Board b) {
		Coordinate center = new MyCoordinate(9, 9);
		Random rand = new Random();
		Coordinate move = null;
		int lastMoveNum = b.getMoveNumber();
		//first four moves are somewhat deterministic
		//after that the AI takes over
		if (lastMoveNum == 0) {
			move = center;
		} else if (lastMoveNum == 1) {
			//the best move here is within one intersection of center
			move = pickRandCoordWithBounds(b, center, 1, 2);
		} else if (lastMoveNum == 2) {
			//last move will necessarily be 9,9
			//best move in this situation is as close as you can get to the center 
			//without being within 2 or fewer intersections
			while (true) {
				int randRow = rand.nextInt(numOfRows);
				int randCol = rand.nextInt(numOfCols);
				Coordinate candidate = new MyCoordinate(randRow, randCol);
				if (randRow > 5 && randRow < 13) {
					if (randCol > 5 && randCol < 13) {
						if (randRow >= 7 && randRow <= 11) {
							if (randCol == 6 || randCol == 12) {
								move = candidate;
								break;
							}
						} else if (randCol >= 7 && randCol <= 11) {
							if (randRow == 6 || randRow == 12) {
								move = candidate;
								break;
							}
						}
					} 
				}
			}
		} else if (lastMoveNum == 3) {
			//best move here is place a stone immediately next to the last one
			//that Sbr placed
			Coordinate lastMove = sbrBoard.get(lastMoveNum - 1);
			move = pickRandCoordWithBounds(b, lastMove, 1, 2);
		} else if (lastMoveNum == 4) {
			//best move here is place a stone immediately next to the center
			move = pickRandCoordWithBounds(b, center, 1, 2);
		} else {
			//try an extending move
			Coordinate extendingMove = flankOrExtend(b, friendlyStone);
			//try a flanking move
			Coordinate flankingMove = flankOrExtend(b, enemyStone);
			//if none of those turn up a move then just pick a random empty intersection
			if (flankingMove == null && extendingMove == null) {
				Coordinate lastMove = sbrBoard.get(lastMoveNum - 1);
				move = pickRandCoordWithBounds(b, lastMove, 1, 2);
			} else if (flankingMove == null) {
				move = extendingMove;
			} else {
				move = flankingMove;
			}
		}
		
		sbrBoard.put(lastMoveNum + 1, move);
		
		return move;
	}
	/**
	 * This method returns a random coordinate with bounds specified based on
	 * proximity to a supplied other coordinate.
	 * 
	 * You cannot call this method late game, as its possible
	 * there are no adjacent empty or in-bounds intersections to place,
	 * in which case it'll be an infinite loop.
	 * 
	 * It will only return a coordinate for an intersection that is currently
	 * empty and in bounds.
	 * 
	 * @param stone the stone to compare distance to
	 * @param lowerBound the minimum distance from parameter stone
	 * @param upperBound the maximum distance from parameter stone
	 * @return a MyCoordinate object for a legal move
	 */
	private Coordinate pickRandCoordWithBounds(Board b, Coordinate coord, 
			int lowerBound, int upperBound) {
		
		Random rand = new Random();
		Coordinate move = null;
		
		while (true) {
			int randRow = rand.nextInt(numOfRows);
			int randCol = rand.nextInt(numOfCols);
			
			int distanceX = Math.abs(coord.getRow() - randRow);
			int distanceY = Math.abs(coord.getColumn() - randCol);
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
	 * Checks for enemy runs and blocks them if 
	 * the enemy stone color is entered, and extends
	 * friendly runs if the friendly stone color is entered
	 *  
	 * If there isn't a move found by this method, then returns null. 
	 * 
	 * @param b the pente board
	 * @param stone either friendly or enemy stone color
	 * @return null or a flanking/extending move as a Coordinate
	 */
	private Coordinate flankOrExtend(Board b, Stone stone) {
		int lastMoveNum = b.getMoveNumber();
		Coordinate move = null;
		int counter = 0;

		ArrayList<Coordinate> primaryArray = new ArrayList<>();
		//find an stone based on parameter stone
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				Coordinate coord = new MyCoordinate(i, j);
				if (b.pieceAt(coord).equals(stone)) {
					primaryArray.add(coord);
				}
			}
		}
		//if you can't find the kind of stone you were looking for,
		//i.e. the board doesn't have a stone of the type you passed to 
		//this method
		if (primaryArray.isEmpty() == true) {
			//then if your intention was to find a flank, then do an extend
			//of your last move
			if (stone != getStone()) {
				Coordinate lastMove = sbrBoard.get(lastMoveNum - 1);
				move = pickRandCoordWithBounds(b, lastMove, 1, 2);
				//but if your intention was to find an extension, this means you 
				//dont have any pieces on the board, so find an enemy piece and flank
			} else {
				flankOrExtend(b, enemyStone);
			}
		} else {
			for (int t = 0; t < primaryArray.size(); t++) {
				Coordinate primaryCoord = primaryArray.get(t);
				//once an enemy stone is found, search around it for adjacent stones and
				//take the first flanking/extending opportunity.
				int primaryRow = primaryCoord.getRow();
				int primaryCol = primaryCoord.getColumn();

				int directionX = 0;
				int directionY = 0;

				Coordinate secondaryCoord = null;
				//iterate through all the intersections touching the primaryCoord
				for (int i = 0; i < numOfRows; i++) {
					for (int j = 0; j < numOfCols; j++) {
						//when you find an intersection that is touching the 
						//primaryCoord...
						int distanceX = Math.abs(i - primaryRow);
						int distanceY = Math.abs(j - primaryCol);
						if (distanceX <= 1 && distanceY <= 1) {
							if (distanceX + distanceY != 0) {
								secondaryCoord = new MyCoordinate(i, j);
								int secondaryRow = secondaryCoord.getRow();
								int secondaryCol = secondaryCoord.getColumn();
								//then check if that index has stone of the color passed to this method
								if (!b.isOutOfBounds(secondaryCoord)) {
									if (b.pieceAt(secondaryCoord).equals(stone)) {
										//if it does, then check direction that its in
										directionX = secondaryRow - primaryRow;
										directionY = secondaryCol - primaryCol;
										//create a search vector
										ArrayList<Coordinate> coordArray = new ArrayList<Coordinate>();
										//fill the search array with potentially eligible coordinates
										for (int s = -3; s <= 4; s++) {
											MyCoordinate coord = new MyCoordinate(s*directionX + primaryRow, 
													s*directionY + primaryCol);
											coordArray.add(coord);
										}
										//TODO: this part!
										//now search the array for an opportunity to flank/extend
										Coordinate emptyCoord = null;
										for (int s = 0; s < coordArray.size(); s++) {
											Coordinate candidate = coordArray.get(s);

											if (!b.isOutOfBounds(candidate)) {
												if (b.isEmpty(candidate)) {
													emptyCoord = candidate;
												}
											}
										}
										//make sure the EMPTY intersection is touching an intersection
										//that contains a stone of the color passed to this method
										if (emptyCoord != null) {
											for (int s = 0; s < coordArray.size(); s++) {
												Coordinate candidate = coordArray.get(s);
												int distanceX1 = Math.abs(candidate.getRow() - primaryRow);
												int distanceY1 = Math.abs(candidate.getColumn() - primaryCol);
												int distanceX2 = Math.abs(candidate.getRow() - secondaryRow);
												int distanceY2 = Math.abs(candidate.getColumn() - secondaryCol);
												if (distanceX1 <= 1 && distanceY1 <= 1) {
													if (distanceX1 + distanceY1 != 0) {
														if (!b.isOutOfBounds(candidate)) {
															if (b.isEmpty(candidate)) {
																move = candidate;
																break;
															}
														}
													}
												} else if (distanceX2 <= 1 && distanceY2 <= 1) {
													if (distanceX2 + distanceY2 != 0) {
														if (!b.isOutOfBounds(candidate)) {
															if (b.isEmpty(candidate)) {
																move = candidate;
																break;
															}
														}
													}
												}
											}
										} 
										if (move != null) {
											break;
										}
									}
								}
							}
						}
					}
					if (move != null) {
						break;
					}
				}
				if (move != null) {
					break;
				}
			}
		}
		return move;
	}

	@Override
	public Stone getStone() {
		return friendlyStone;
	}

}
