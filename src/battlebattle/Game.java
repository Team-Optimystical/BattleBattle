package battlebattle;

import java.util.List;

import expectimax.NotExpectTurnException;
import expectimax.State;

public class Game implements State {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Float> getProbs() throws NotExpectTurnException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
