
public class MyCoordinate implements Coordinate {
	
	private int row;
	private int column;
	
	public MyCoordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

}
