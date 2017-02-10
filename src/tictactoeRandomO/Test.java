package tictactoeRandomO;

import expectimax.ExpectimaxDoer;

public class Test {
	static ExpectimaxDoer exp = new ExpectimaxDoer();
	
	public static void main(String[] args) {
		Board game = new Board();
		
		game.put(Board.Player.X, 0, 0);
		game.put(Board.Player.O, 1, 1);
		game.put(Board.Player.X, 1, 0);
		game.put(Board.Player.O, 2, 0);
		game.put(Board.Player.X, 2, 2);
		game.put(Board.Player.O, 2, 1);

		printVal(game, 0);
		printVal(game, 1);
		printVal(game, 2);
		printVal(game, 1);
		printVal(game, 0);
	}

	public static void printVal(Board game) {
		printVal(game, null);
	}
	
	public static void printVal(Board game, Integer depth) {
		long startTime = System.currentTimeMillis();
		float val = exp.value(game, depth);
		long endTime = System.currentTimeMillis();
		
		System.out.print("Board:\n" + game.toString());
		System.out.println("Time: " + ((endTime - startTime) / 1000f) + " Value: " + val);
		System.out.println("Maximum Depth: " + depth);
	}
}
