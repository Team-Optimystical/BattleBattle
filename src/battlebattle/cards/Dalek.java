package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Dalek extends Player {
	private int currentPower;
	private boolean increasingPower;
	
	public Dalek() {
		super(5, 0);
	}

	public Dalek(Dalek toCopy) {
		super(toCopy);
		this.currentPower = toCopy.currentPower;
		this.increasingPower = toCopy.increasingPower;
	}

	@Override
	public Player clone() {
		return new Dalek(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new Action((game) -> {}));
		return actions;
	}

	@Override
	public void onPreRoll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPostRoll() {
		setRoll(currentPower);
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
		if (currentPower == 6) {
			increasingPower = false;
		} else if (currentPower == 1) {
			increasingPower = true;
		}
		
		if (increasingPower) {
			currentPower += 1;
		} else {
			currentPower -= 1;
		}
	}

	@Override
	public String getName() {
		return "Dalek";
	}

}
