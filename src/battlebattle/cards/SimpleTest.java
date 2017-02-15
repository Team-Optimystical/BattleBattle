package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class SimpleTest extends Player {
	
	public SimpleTest() {
		super(1, 0);
	}
	
	public SimpleTest(SimpleTest toCopy) {
		super(toCopy);
	}

	@Override
	public Player clone() {
		return new SimpleTest(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<Action>();
		
		actions.add(new Action((game) -> {}));
		
		return actions;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "SimpleTest";
	}
	
	public List<Float> rollProbs() {
		List<Float> probs = new ArrayList<>();
		probs.add(0.5f);
		probs.add(0.5f);
		return probs;
	}
	
	public List<Integer> rollVals() {
		List<Integer> vals = new ArrayList<>();
		vals.add(1);
		vals.add(2);
		return vals;
	}

}
