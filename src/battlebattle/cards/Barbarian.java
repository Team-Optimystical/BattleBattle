package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Barbarian extends Player {
	public Barbarian() {
		super(6,0);
	}
	
	public Barbarian(Barbarian toCopy) {
		super(toCopy);
	}
	
	public String getName() {
		return "Barbarian";
	}

	@Override
	public Player clone() {
		return new Barbarian(this);
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
		int roll1 = super.roll();
		int roll2 = super.roll();
		
	}

	@Override
	public void onTakeDamage() {
		// TODO Auto-generated method stub
		
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
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		return actions;
	}
}
