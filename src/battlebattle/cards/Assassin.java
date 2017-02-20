package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Assassin extends Player {
	private int doubleDamageCount = 0;
	
	public Assassin() {
		super(3, 1);
	}

	public Assassin(Assassin toCopy) {
		super(toCopy);
		this.doubleDamageCount = toCopy.doubleDamageCount;
	}

	@Override
	public Player clone() {
		return new Assassin(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Assassin) {
			Assassin a = (Assassin)o;
			return super.equals(a) && a.doubleDamageCount == this.doubleDamageCount;
		}
		
		return false;
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		for (int i = 1; i <= tokens; ++i) {
			final int TOKENS_USED = i;
			
			switch (pNum) {
			case 1:
				actions.add(new Action((game) -> {
					Assassin a = (Assassin)game.p1;
					a.doubleDamageCount += TOKENS_USED;
					a.addTokens(-TOKENS_USED);
				}));
				break;
			case 2:
				actions.add(new Action((game) -> {
					Assassin a = (Assassin)game.p2;
					a.doubleDamageCount += TOKENS_USED;
					a.addTokens(-TOKENS_USED);
				}));
				break;
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
		return "Assassin";
	}
	
	@Override
	public int damageValue() {
		if (this.strengthValue() > opponent.strengthValue()) {
			int damage = 1;
			for (int a = 0; a < doubleDamageCount; ++a) {
				damage *= 2;
			}
			return damage;
		} else {
			return 0;
		}
	}

}
