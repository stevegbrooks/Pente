
public class MainTester {

	public static void main(String[] args) {
		SbrPlayer sbr = new SbrPlayer(Stone.RED);
		MyBoard b = new MyBoard();
		
		sbr.getMove(b);
		sbr.getStone();

	}

}
