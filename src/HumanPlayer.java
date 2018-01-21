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
		
		return coords;
	}

	@Override
	public Stone getStone() {
		return stone;
	}

}
