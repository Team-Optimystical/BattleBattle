package battlebattle;

import java.util.ArrayList;
import java.util.List;

import expectimax.NotExpectTurnException;
import expectimax.State;

public class Game implements State, Cloneable {
	public Player p1;
	public Player p2;
	
	private enum Turn {P1, P2, ROLL};
	private Turn turn;
	private Turn[] turnOrder = new Turn[3];
	private int turnCounter;
	
	public Game(Player p1, Player p2) {
		this.turn = Turn.ROLL;
		this.p1 = p1;
		this.p1.setOpponent(p2);
		this.p1.pNum = 1;
		this.p2 = p2;
		this.p2.setOpponent(p1);
		this.p2.pNum = 2;
		
		recomputeTurnOrder();
	}
	
	@Override
	public Game clone() {
		Game copy = new Game((Player)p1.clone(), (Player)p2.clone());
		copy.turn = this.turn;
		copy.turnCounter = this.turnCounter;
		return copy;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Game) {
			Game g = (Game)o;
			return p1.equals(g.p1) && p2.equals(g.p2) && turn.equals(g.turn);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return p1.hashCode() ^ p2.hashCode();
	}
	
	@Override
	public String toString() {
		return "{" + p1.toString() + "," + p2.toString() + "}";
	}
	
	public void recomputeTurnOrder() {
		turnOrder[0] = Turn.ROLL;
		if (p1.getHealth() > p2.getHealth()) {
			turnOrder[1] = Turn.P1;
			turnOrder[2] = Turn.P2;
		} else if (p1.getHealth() < p2.getHealth()) {
			turnOrder[1] = Turn.P2;
			turnOrder[2] = Turn.P1;
		} else if (0 > p1.getName().toLowerCase().compareTo(p2.getName().toLowerCase())) {
			turnOrder[1] = Turn.P1;
			turnOrder[2] = Turn.P2;
		} else {
			turnOrder[1] = Turn.P2;
			turnOrder[2] = Turn.P1;
		}
	}
	
	public void postAction() {
		if (turnCounter == 3) {
			turnCounter = 0;
			
			resolveRound();
			
			recomputeTurnOrder();
		}
		
		turn = turnOrder[turnCounter];
		
//		switch (turn) {
//		case P1:
//			System.out.println("Max turn");
//			break;
//		case P2:
//			System.out.println("Min turn");
//			break;
//		case ROLL:
//			System.out.println("Expect turn");
//		}
		
		++turnCounter;
	}
	
	public void resolveRound() {
		if (p1.strengthValue() > p2.strengthValue()) {
			//System.out.println("p1 deals " + p1.damageValue() + " damage.");
			p2.damage(p1.damageValue());
		} else if (p1.strengthValue() < p2.strengthValue()) {
			//System.out.println("p2 deals " + p2.damageValue() + " damage.");
			p1.damage(p2.damageValue());
		}
	}

	@Override
	public boolean isTerminal() {
		return p1.isDead() || p2.isDead();
	}

	@Override
	public float score() {
		if (!p1.isDead() && p2.isDead()) {
			//System.out.println(this + "Return 1");
			return 1;
		} else if (p1.isDead() && !p2.isDead()) {
			//System.out.println(this + "Return -1");
			return -1;
		} else {
			//System.out.println(this + "Return 0");
			return 0;
		}
	}

	@Override
	public boolean isMaxTurn() {
		return turn.equals(Turn.P1);
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
					
					Action a = new Action((game) -> {
						game.p1.setRoll(r1);
						game.p2.setRoll(r2);
					});
					
					a.execute(neighbor);
					
					neighbors.add(neighbor);
				}
			}
			
		} else {
			List<Action> actions;
			
			if (isMaxTurn()) {
				actions = p1.possibleActions();
			} else if (isMinTurn()) {
				actions = p2.possibleActions();
			} else {
				throw new RuntimeException("It appears to be nobodies turn.");
			}
			
			for (Action a : actions) {
				Game neighbor = this.clone();
				
				a.execute(neighbor);
				neighbors.add(neighbor);
			}			
		}
		
		return neighbors;
	}

	@Override
	public List<Float> getProbs() throws NotExpectTurnException {
		if (!isExpectTurn()) {
			throw new NotExpectTurnException();
		}
		
		List<Float> neighborProbs = new ArrayList<>();
		
		List<Float> probs1 = p1.rollProbs();
		List<Float> probs2 = p2.rollProbs();
		
		for (Float p1 : probs1) {
			for (Float p2 : probs2) {
				neighborProbs.add(p1 * p2);
			}
		}
		
		return neighborProbs;
	}
	
}
