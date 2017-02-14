package battlebattle;

import battlebattle.cards.Barbarian;
import battlebattle.cards.Gladiator;
import expectimax.ExpectimaxDoer;

public class Test {
	public static void main(String[] args) {
		ExpectimaxDoer exp = new ExpectimaxDoer();
		Game game = new Game(new Gladiator(), new Barbarian());
		
		//printValue(exp, game, 0);
		//printValue(exp, game, 1);
		//printValue(exp, game, 2);
		//printValue(exp, game, 3);
		//printValue(exp, game, 4);
		//printValue(exp, game, 5);
		//printValue(exp, game, 6);
		printValue(exp, game, 100);
	}
	
	public static void printValue(ExpectimaxDoer exp, Game game, int depth) {
		long startTime = System.currentTimeMillis();
		float value = exp.value(game, depth);
		long endTime = System.currentTimeMillis();
		
		float time_s = (endTime - startTime) / 1000;
		System.out.println("Value: " + value + " Depth: " + depth + " Time: " + time_s + "s");
	}
}
