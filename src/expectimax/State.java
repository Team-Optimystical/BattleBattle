package expectimax;

import java.util.List;

public interface State {

	// PLEASE override these so that equivalent states are equal.
	@Override
	public boolean equals(Object o);
	@Override
	public int hashCode();
	
	/**
	 * Returns true if this state does not expand to any more states.
	 * @return true if the state is terminal.
	 */
	public boolean isTerminal();
	
	/**
	 * Returns the score at a node. This is ill-defined if it is not a 
	 * terminal state.
	 * @return the value of the node, which max is trying to maximize, 
	 *         and min is trying to minimize.
	 */
	public float score();
	
	/**
	 * Returns true if it is the maximizing player's turn.
	 * @return true if it is max's turn.
	 */
	public boolean isMaxTurn();
	
	/**
	 * Returns true if it is the minimizing player's turn.
	 * @return true if it is min's turn.
	 */
	public boolean isMinTurn();
	
	/**
	 * Returns true if it is neither the min nor max player's turn, but is a
	 * probability branch.
	 * @return true if it is neither min nor max's turn.
	 */
	public boolean isExpectTurn();
	
	/**
	 * Get the neighbors of the state. The order should correspond with the 
	 * function {@link #getProbs()}.
	 * @return a list of neighboring states.
	 */
	public List<State> getNeighbors();

	/**
	 * Get the neighbors of the state. The order should correspond with the 
	 * function {@link #getNeighbors()}. This should only work if it is Expect's turn.
	 * @return a list of neighboring the probabilities of each neighboring state.
	 * @throws NotExpectTurnException It is either min or max's turn.
	 */
	public List<Float> getProbs() throws NotExpectTurnException;
}
