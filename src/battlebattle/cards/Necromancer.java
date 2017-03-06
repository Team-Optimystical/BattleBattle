package battlebattle.cards;

import java.util.Arrays;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Necromancer extends Player {

	public Necromancer() {
		super(6, 0);
	}
	
	public Necromancer(Necromancer toCopy) {
		super(toCopy);
	}
	
	@Override
	public Player clone() {
		return new Necromancer(this);
	}

	@Override
	public List<Action> possibleActions() {
		return Arrays.asList(new Action((game) -> {}));
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
		if (opponent.strengthValue() + 3 <= this.strengthValue()
			&& this.getHealth() < 6) {
			this.incrementHealth(1);
		}
	}

	@Override
	public String getName() {
		return "Necromancer";
	}
	
	@Override
	public int strengthValue() {
		switch (roll) {
		case 3:
			return 1;
		case 4:
			return 6;
		default:
			return roll;
		}
	}
}
