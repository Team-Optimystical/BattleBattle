package tictactoe;

import expectimax.ExpectimaxDoer;

public class Test {
	static ExpectimaxDoer exp = new ExpectimaxDoer();
	
	public static void main(String[] args) {
		Board game = new Board();
		
		printVal(game);
		printVal(game);
		
		game.put(Board.Player.X, 1, 1);
		game.put(Board.Player.O, 1, 0);
		game.put(Board.Player.X, 0, 0);
		game.put(Board.Player.O, 2, 0);
		
		printVal(game);
	}
	
	public static void printVal(Board game) {
		long startTime = System.currentTimeMillis();
		float val = exp.value(game);
		long endTime = System.currentTimeMillis();
		
		System.out.print("Board:\n" + game.toString());
		System.out.println("Time: " + ((endTime - startTime) / 1000f) + " Value: " + val);
	}
}
