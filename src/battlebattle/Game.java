package battlebattle;

import java.util.ArrayList;
import java.util.List;

import expectimax.NotExpectTurnException;
import expectimax.State;

public class Game implements State, Cloneable {
	Player p1;
	Player p2;
	
	private enum Turn {P1, P2, ROLL};
	private Turn turn;
	
	public Game(Player p1, Player p2) {
		this.turn = Turn.ROLL;
		this.p1 = p1;
		this.p1.setOpponent(p2);
		this.p2 = p2;
		this.p2.setOpponent(p1);
	}
	
	@Override
	public Game clone() {
		Game copy = new Game((Player)p1.clone(), (Player)p2.clone());
		copy.turn = this.turn;
		return copy;
	}

	@Override
	public boolean isTerminal() {
		return p1.isDead() || p2.isDead();
	}

	@Override
	public float score() {
		if (!p1.isDead() && p2.isDead()) {
			return 1;
		} else if (p1.isDead() && !p2.isDead()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isMaxTurn() {
		return turn.equals(turn.P1);
	}

	@Override
	public boolean isMinTurn() {
		return turn.equals(Turn.P2);
	}

	@Override
	public boolean isExpectTurn() {
		return turn.equals(Turn.ROLL);
	}

	@Override
	public List<State> getNeighbors() {
		List<State> neighbors = new ArrayList<>();
		
		if (isExpectTurn()) {
			List<Integer> rolls1 = p1.rollVals();
			List<Integer> rolls2 = p2.rollVals();
			
			for (Integer r1 : rolls1) {
				for (Integer r2 : rolls2) {
					Game neighbor = this.clone();
					neighbor.p1.setRoll(r1);
					neighbor.p2.setRoll(r2);
					
					neighbors.add(neighbor);
				}
			}
			
		} else if (isMaxTurn()) {
			
		} else if (isMinTurn()) {
			
		} else {
			throw new RuntimeException("It appears to be nobodies turn.");
		}
		
		return neighbors;
	}

	@Override
	public List<Float> getProbs() throws NotExpectTurnException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
