package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Barbarian extends Player {
	private int preDamageHealth;
	
	public Barbarian() {
		super(6,0);
	}
	
	public Barbarian(Barbarian toCopy) {
		super(toCopy);
		this.preDamageHealth = toCopy.preDamageHealth;
	}
	
	public String getName() {
		return "Barbarian";
	}

	@Override
	public Player clone() {
		return new Barbarian(this);
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
		int dHealth = preDamageHealth - getHealth();
		
		// double damage
		incrementHealth(-dHealth);
	}
	
	@Override
	public int strengthValue() {
		switch (roll) {
		case 1:
		case 2:
		case 3: 
			return 4;
		default:
			return roll;
		}
	}
	
	@Override
	public int damageValue() {
		if (opponent.dieValue() < this.dieValue()) {
			return 1;
		}
		
		return 0;
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		return actions;
	}
}
