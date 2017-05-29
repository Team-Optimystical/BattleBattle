package battlebattle;

import java.util.ArrayList;
import java.util.List;

import expectimax.NotExpectTurnException;
import expectimax.State;

/**
 * The class for the battle battle game. Each battle battle game has two players,
 * which take turns rolling and taking actions until one player is dead.</br>
 * </br>
 * This class takes the {@link State} interface, which means it can be used with
 * {@link expectimax.ExpectimaxDoer ExpectimaxDoer}. Based on the actions of each
 * player, the game can calculate all possible neighboring game states.</br>
 * </br>
 * This class also inherits the {@link Cloneable} interface, which is necessary
 * to enumerate all possible neighboring game states.
 * @author Alex Sampley
 *
 */
public class Game implements State, Cloneable {
	/**
	 * First player
	 */
	public Player p1;
	/**
	 * Second player
	 */
	public Player p2;
	
	/**
	 * Possible turn states: P1 = player 1, P2 = player 2, ROLL = players roll
	 * @author Alex Sampley
	 *
	 */
	private enum Turn {P1, P2, ROLL};
	
	/**
	 * Who's turn is it now.
	 */
	private Turn turn;
	/**
	 * The turn order for the current round.
	 */
	private Turn[] turnOrder = new Turn[3];
	/**
	 * Index to {@link turnOrder} to find who's turn it is.
	 */
	private int turnCounter;
	
	/**
	 * Create a new game of battle battle, between the two players supplied.
	 * The game starts with the rolling phase.
	 * @param p1 The first player.
	 * @param p2 The second player.
	 */
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
	
	/**
	 * Creates a copy of the current game instance. This is necessary to
	 * create neighbor states which do not effect the current state.
	 * @return A copy of the current game state.
	 */
	@Override
	public Game clone() {
		Game copy = new Game((Player)p1.clone(), (Player)p2.clone());
		copy.turn = this.turn;
		copy.turnCounter = this.turnCounter;
		return copy;
	}
	
	/**
	 * Returns true if and only if {@link o} is a game and is on the same turn,
	 * and the each player is considered equal, i.e. </br>
	 * {@code o.p1.equals(this.p1) && o.p2.equals(this.p2)}.</br>
	 * </br>
	 * Note that this is dependent upon a valid implementation of {@link Player#equals(Object)}
	 * @param o The game to check if it is equivalent to this game.
	 * @return True if and only if the game objects are equivalent.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Game) {
			Game g = (Game)o;
			return p1.equals(g.p1) && p2.equals(g.p2) && this.turn == g.turn && this.turnCounter == g.turnCounter;
		}
		
		return false;
	}
	
	/**
	 * Returns a hash code for the current game state, for use with collections such as
	 * {@link java.util.HashMap}. This should return the same value if {@link Game#equals(Object)},
	 * but it is dependent on the implementations of each player's {@link Player#hashCode()}.
	 * @return A hash code for the game object.
	 */
	@Override
	public int hashCode() {
		return p1.hashCode() ^ p2.hashCode();
	}
	
	/**
	 * Returns a string for the game, which merely uses each player's {@link Player#toString()}
	 * @return A string representation of the game.
	 */
	@Override
	public String toString() {
		return "{" + p1.toString() + "," + p2.toString() + "}";
	}
	
	/**
	 * Recomputes the turn order for the round. The first turn is rolling dice, followed by the
	 * player with the most health, then the player with the least health. If the players have
	 * the same amount of health, then the player with the first name alphabetically goes first.
	 */
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
	
	/**
	 * Recomputes who's turn it is. If it is the end of the round, it also calls
	 * {@link Game#resolveRound()} to finish the round, and {@link Game#recomputeTurnOrder()}
	 * to prepare for the next round.
	 */
	public void postAction() {
		if (turnCounter == 3) {
			turnCounter = 0;
			
			resolveRound();
			
			recomputeTurnOrder();
		}
		
		turn = turnOrder[turnCounter];
		
		++turnCounter;
	}
	
	/**
	 * Deals damage dealt by each player. Specifically, it will call each
	 * player's {@link Player#onPreDamage()}, followed by dealing damage to each
	 * player's opponent using the value from {@link Player#damageValue()}, and
	 * finally calling {@link Player#onPostDamage()}.
	 */
	public void resolveRound() {
		p1.onPreDamage();
		p2.onPreDamage();
		
		p1.incrementHealth(-p2.damageValue());
		p2.incrementHealth(-p1.damageValue());

		p1.onPostDamage();
		p2.onPostDamage();
	}

	/**
	 * Returns true if and only if one player is dead, by calling {@link Player#isDead()}.
	 * @return True if one or both players are dead.
	 */
	@Override
	public boolean isTerminal() {
		return p1.isDead() || p2.isDead();
	}

	/**
	 * Returns true if and only if player one is alive, and player two is dead, 
	 * by calling {@link Player#isDead()}.
	 * @return True if player one wins.
	 */
	@Override
	public boolean isMaxWin() {
		return !p1.isDead() && p2.isDead();
	}

	/**
	 * Returns true if and only if player two is alive, and player one is dead, 
	 * by calling {@link Player#isDead()}.
	 * @return True if player two wins.
	 */
	@Override
	public boolean isMinWin() {
		return p1.isDead() && !p2.isDead();
	}

	/**
	 * Returns a value associated with the state, which is 1 if {@link Game#isMaxWin()} 
	 * returns true, -1 if {@link Game#isMinWin()} returns true, and 0 otherwise.
	 */
	@Override
	public float score() {
		if (isMaxWin()) {
			//System.out.println(this + "Return 1");
			return 1;
		} else if (isMinWin()) {
			//System.out.println(this + "Return -1");
			return -1;
		} else {
			//System.out.println(this + "Return 0");
			return 0;
		}
	}

	/**
	 * Returns true if and only if the current turn is player one's.
	 * @return True if it is player one's turn.
	 */
	@Override
	public boolean isMaxTurn() {
		return turn.equals(Turn.P1);
	}

	/**
	 * Returns true if and only if the current turn is player two's.
	 * @return True if it is player two's turn.
	 */
	@Override
	public boolean isMinTurn() {
		return turn.equals(Turn.P2);
	}

	/**
	 * Returns true if and only if it is time to roll dice.
	 * @return True if it is time to roll dice.
	 */
	@Override
	public boolean isExpectTurn() {
		return turn.equals(Turn.ROLL);
	}

	/**
	 * Returns the neighbors of the game. </br>
	 * </br>
	 * If it is time to roll, the neighbors are
	 * all the possible combinations of rolls for both players. The probabilities of
	 * each neighbor after rolls can be gotten from the list returned from {@link Game#getProbs()}.</br>
	 * </br>
	 * If it is a player's turn the neighbors will be all the resulting states after executing
	 * that player's possible actions, from {@link Player#possibleActions()}. </br>
	 * </br>
	 * Each element in the list is independently created using {@link Game#clone()} on the original
	 * game instance.
	 * @return Returns all the possible neighboring states from the current game state.
	 */
	@Override
	public List<State> getNeighbors() {
		List<State> neighbors = new ArrayList<>();
		
		if (isExpectTurn()) {
			p1.onPreRoll();
			p2.onPreRoll();
			
			List<Integer> rolls1 = p1.rollVals();
			List<Integer> rolls2 = p2.rollVals();
			
			for (Integer r1 : rolls1) {
				for (Integer r2 : rolls2) {
					Game neighbor = this.clone();
					
					Action a = new Action((game) -> {
						game.p1.setRoll(r1);
						game.p2.setRoll(r2);
						
						game.p1.onPostRoll();
						game.p2.onPostRoll();
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
				
				if (isMaxTurn()) {
					neighbor.p1.onPreTurn();
				} else if (isMinTurn()) {
					neighbor.p2.onPreTurn();
				}
				
				a.execute(neighbor);
				neighbors.add(neighbor);
				
				if (isMaxTurn()) {
					neighbor.p1.onPostTurn();
				} else if (isMinTurn()) {
					neighbor.p2.onPostTurn();
				}
			}			
		}
		
		return neighbors;
	}

	/**
	 * Returns a list that directly corresponds to the probability of each roll returned
	 * from {@link Game#getNeighbors()}. If the turn was not a roll, then the function throws
	 * {@link NotExpectTurnException}.</br>
	 * </br>
	 * To access the probability of ith neighbor from {@link Game#getNeighbors()}, access the 
	 * ith element in the list returned from this function.
	 * @return A corresponding list for the probabilities of each neighbor.
	 * @throws NotExpectTurnException The turn was not a rolling turn.
	 */
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
