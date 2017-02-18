package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class ConArtist extends Player {
	public ConArtist() {
		super(4, 3);
	}

	public ConArtist(ConArtist toCopy) {
		super(toCopy);
	}

	@Override
	public Player clone() {
		return new ConArtist(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		if (tokens >= 1) {
			switch (pNum) {
			case 1:
				actions.add(new Action((game) -> {
					int temp = game.p1.dieValue();
					game.p1.setRoll(game.p2.dieValue());
					game.p2.setRoll(temp);
					game.p1.addTokens(-1);
				}));
				break;
			case 2:
				actions.add(new Action((game) -> {
					int temp = game.p1.dieValue();
					game.p1.setRoll(game.p2.dieValue());
					game.p2.setRoll(temp);
					game.p2.addTokens(-1);
				}));
				break;
			}
		}
		
		return actions;
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
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Con Artist";
	}
	
	@Override
	public int strengthValue() {
		switch (roll) {
		case 1:
			return 6;
		default:
			return roll;
		}
	}

}
