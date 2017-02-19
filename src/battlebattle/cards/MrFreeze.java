package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class MrFreeze extends Player {
	private boolean freeze;
	private int opponentRoll;
	private boolean canFreeze;
	
	public MrFreeze() {
		super(5,3);
	}

	public MrFreeze(MrFreeze toCopy) {
		super(toCopy);
		this.freeze = toCopy.freeze;
		this.canFreeze = toCopy.canFreeze;
	}

	@Override
	public Player clone() {
		return new MrFreeze(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		if (canFreeze && this.tokens > 0) {
			switch (pNum) {
			case 1:
				actions.add(new Action((game) -> {
					MrFreeze f = (MrFreeze)game.p1;
					f.freeze = true;
					f.addTokens(-1);
				}));
				break;
			case 2:
				actions.add(new Action((game) -> {
					MrFreeze f = (MrFreeze)game.p2;
					f.freeze = true;
					f.addTokens(-1);
				}));
				
				break;
			}
		}
		
		return actions;
	}

	@Override
	public void onPreRoll() {
		if (freeze) {
			opponentRoll = opponent.dieValue();
		}
	}

	@Override
	public void onPostRoll() {
		if (freeze) {
			opponent.setRoll(opponentRoll);
		}
	}

	@Override
	public void onPreTurn() {
		if (freeze) {
			canFreeze = false;
		} else {
			canFreeze = true;
		}
		
		freeze = false;
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
		return "Mr Freeze";
	}

}
