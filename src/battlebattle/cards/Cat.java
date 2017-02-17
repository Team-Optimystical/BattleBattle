package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Cat extends Player {
	private static final int baseHealth = 1;
	private static final int recoverHealth = 1;
	
	public Cat() {
		super(baseHealth, 8);
	}
	
	public Cat(Cat copyCat) {
		super(copyCat);
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
	public void onStartTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTokenPlay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTakeDamage() {
		if (this.tokens > 0 && health <= 0) {
			this.health += recoverHealth;
			this.tokens -= 1;
		}
	}

	@Override
	public String getName() {
		return "Cat";
	}

}
