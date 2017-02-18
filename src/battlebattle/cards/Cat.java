package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Cat extends Player {
	private static final int baseHealth = 1;
	private static final int recoverHealth = 1;
	
	private int healthPreDamage;
	
	public Cat() {
		super(baseHealth, 8);
	}
	
	public Cat(Cat copyCat) {
		super(copyCat);
		this.healthPreDamage = copyCat.healthPreDamage;
	}

	@Override
	public Player clone() {
		Cat copyCat = new Cat(this);
		return copyCat;
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<Action>();
		
		actions.add(new Action((game) -> {}));
		
		return actions;
	}
	
	@Override
	public int strengthValue() {
		switch (roll) {
		case 5:
		case 6:
			return 4;
		default:
			return roll;
		}
	}

	@Override
	public String getName() {
		return "Cat";
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
		healthPreDamage = getHealth();
	}

	@Override
	public void onPostDamage() {
		if (this.tokens > 0 && healthPreDamage > getHealth()) {
			this.health += recoverHealth;
			this.tokens -= 1;
		}
	}

}
