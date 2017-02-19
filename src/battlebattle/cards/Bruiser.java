package battlebattle.cards;

import java.util.Arrays;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Bruiser extends Player {
	
	public Bruiser() {
		super(5, 0);
	}

	public Bruiser(Bruiser toCopy) {
		super(toCopy);
	}

	@Override
	public Player clone() {
		return new Bruiser(this);
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
		if (opponent.strengthValue() == this.strengthValue()) {
			this.incrementHealth(1);
		}
	}

	@Override
	public String getName() {
		return "Bruiser";
	}
	
	@Override
	public int strengthValue() {
		switch (roll) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return roll + 1;
		default:
			return roll;
		}
	}
}
