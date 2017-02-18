package battlebattle.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Giant extends Player {
	private int bonusStrength;
	
	public Giant() {
		super(6, 3);
	}

	public Giant(Giant toCopy) {
		super(toCopy);
		this.bonusStrength = toCopy.bonusStrength;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Giant) {
			Giant g = (Giant)o;
			return super.equals(g) && this.bonusStrength == g.bonusStrength;
		}
		
		return false;
	}

	@Override
	public Player clone() {
		return new Giant(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<Action>();
		
		actions.add(new Action((game) -> {}));
		
		for (int i = 1; i <= this.tokens; ++i) {
			final int TOKENS_USED = i;
			
			if (this.pNum == 1) {
				actions.add(new Action((game) -> {
					Giant g = (Giant)game.p1;
					g.bonusStrength += 3 * TOKENS_USED;
					g.addTokens(-TOKENS_USED);
				}));
			} else if (this.pNum == 2) {
				actions.add(new Action((game) -> {
					Giant g = (Giant)game.p2;
					g.bonusStrength += 3 * TOKENS_USED;
					g.addTokens(-TOKENS_USED);
				}));
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
		switch (roll) {
		case 3:
		case 4:
			roll = this.roll();
		}
	}

	@Override
	public void onPostDamage() {
		bonusStrength = 0;
	}

	@Override
	public String getName() {
		return "Giant";
	}

	@Override
	public List<Integer> rollVals() {
		return Arrays.asList(1, 2, 5, 6);
	}
	
	@Override
	public List<Float> rollProbs() {
		float quarter = 1.0f / 4.0f;
		return Arrays.asList(quarter, quarter, quarter, quarter);
	}
	
	@Override
	public int damageValue() {
		if (opponent.strengthValue() * 2 <= this.strengthValue()) {
			return 2;
		} else if (opponent.strengthValue() < this.strengthValue()) {
			return 1;
		} else {
			return 0;
		}
	}
}
