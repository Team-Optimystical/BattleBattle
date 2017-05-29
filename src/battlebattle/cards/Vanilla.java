package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Vanilla extends Player {
	
	public Vanilla() {
		super(4, 3);
	}

	public Vanilla(Vanilla toCopy) {
		super(toCopy);
	}

	@Override
	public Player clone() {
		return new Vanilla(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		for (int i = 1; i <= tokens; ++i) {
			final int TOKENS_USED = i;
			if (this.dieValue() + TOKENS_USED <= 6) {
				
				Action a;
				if (pNum == 1) {
					a = new Action((game) -> {
						game.p1.setRoll(game.p1.dieValue() + TOKENS_USED);
						game.p1.addTokens(-TOKENS_USED);
					});
				} else if (pNum == 2) {
					 a = new Action((game) -> {
						game.p2.setRoll(game.p2.dieValue() + TOKENS_USED);
						game.p2.addTokens(-TOKENS_USED);
					});
				} else {
					throw new RuntimeException("Player number is neither 1 nor 2");
				}
				
				actions.add(a);
			}
		}
		
		return actions;
	}

	@Override
	public void onPreRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDamage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostDamage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Vanilla";
	}
	
}
