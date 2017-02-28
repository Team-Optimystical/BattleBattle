package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Ninja extends Player {
	private int preDamageHealth;
	private final int EXTRA_HEAL_AMOUNT = 1;
	
	public Ninja() {
		super(3, 5);
	}

	public Ninja(Ninja toCopy) {
		super(toCopy);
		this.preDamageHealth = toCopy.preDamageHealth;
	}

	@Override
	public Player clone() {
		return new Ninja(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		for (int i = 1; i <= tokens; ++i) {
			final int TOKENS_USED = i;
			
			if (this.dieValue() - TOKENS_USED >= 1) {
				switch (pNum) {
				case 1:
					actions.add(new Action((game) -> {
						game.p1.setRoll(game.p1.dieValue() - TOKENS_USED);
						game.p1.addTokens(-TOKENS_USED);
					}));
					break;
				case 2:
					actions.add(new Action((game) -> {
						game.p2.setRoll(game.p2.dieValue() - TOKENS_USED);
						game.p2.addTokens(-TOKENS_USED);
					}));
					break;
				}
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
		preDamageHealth = getHealth();
	}

	@Override
	public void onPostDamage() {
		if (opponent.dieValue() == this.dieValue() * 2) {
			this.health = preDamageHealth + EXTRA_HEAL_AMOUNT;
		}
	}

	@Override
	public String getName() {
		return "Ninja";
	}

	@Override
	public int strengthValue() {
		switch (roll) {
		case 5:
			return 3;
		case 6:
			return 4;
		default:
			return roll;
		}
	}
}
