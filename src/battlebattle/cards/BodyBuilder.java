package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class BodyBuilder extends Player {
	private boolean freeze;
	private int myRoll;
	private boolean canFreeze;
	
	public BodyBuilder() {
		super(5,3);
	}

	public BodyBuilder(BodyBuilder toCopy) {
		super(toCopy);
		this.freeze = toCopy.freeze;
		this.canFreeze = toCopy.canFreeze;
	}

	@Override
	public Player clone() {
		return new BodyBuilder(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		if (canFreeze && this.tokens > 0) {
			switch (pNum) {
			case 1:
				actions.add(new Action((game) -> {
					BodyBuilder b = (BodyBuilder)game.p1;
					b.freeze = true;
					b.addTokens(-1);
				}));
				break;
			case 2:
				actions.add(new Action((game) -> {
					BodyBuilder b = (BodyBuilder)game.p2;
					b.freeze = true;
					b.addTokens(-1);
				}));
				
				break;
			}
		}
		
		return actions;
	}

	@Override
	public void onPreRoll() {
		if (freeze) {
			myRoll = this.dieValue();
		}
	}

	@Override
	public void onPostRoll() {
		if (freeze) {
			this.setRoll(myRoll);
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
		return "Body Builder";
	}

}
