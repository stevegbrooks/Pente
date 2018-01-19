
public class HumanPlayer implements Player {
	private Stone stone;
	
	public HumanPlayer(Stone stoneColor) {
		this.stone = stoneColor;
	}
	@Override
	public Coordinate getMove(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stone getStone() {
		return stone;
	}

}
