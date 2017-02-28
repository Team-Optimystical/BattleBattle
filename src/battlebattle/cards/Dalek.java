package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Dalek extends Player {
	private int currentPower;
	private boolean increasingPower;
	private static final int MIN_POWER = 2;
	private static final int MAX_POWER = 6;
	private static final int POWER_INC = 1;
	
	public Dalek() {
		super(5, 0);
		currentPower = MIN_POWER;
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
	public boolean equals(Object o) {
		if (o instanceof Dalek) {
			Dalek d = (Dalek)o;
			return super.equals(d) 
					&& d.currentPower == this.currentPower
					&& d.increasingPower == this.increasingPower;
		}
		
		return false;
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
		if (currentPower >= MAX_POWER) {
			currentPower = MAX_POWER;
			increasingPower = false;
		} else if (currentPower <= MIN_POWER) {
			currentPower = MIN_POWER;
			increasingPower = true;
		}
		
		if (increasingPower) {
			currentPower += POWER_INC;
		} else {
			currentPower -= POWER_INC;
		}
	}

	@Override
	public String getName() {
		return "Dalek";
	}

}
