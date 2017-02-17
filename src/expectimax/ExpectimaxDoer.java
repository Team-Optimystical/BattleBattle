package expectimax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExpectimaxDoer {
	HashMap<State, Float> transpositionTable = new HashMap<>();
	HashMap<State, Integer> depthExplored = new HashMap<>();
	Function<State, Float> h = (s) -> 0f;
	
	public boolean useTranspositionTable = true;
	public boolean useAlphaBetaPruning = false;
	
	public void setHeuristic(Function<State, Float> h) {
		this.h = h;
	}
	
	public Map<State, Float> getTranspositionTable() {
		return transpositionTable;
	}
	
	/**
	 * Return a value associated with a state, with no maximum depth.
	 * @param state state to get the value from.
	 * @return value of the state, fully exploring the tree.
	 */
	public Float value(State state) {
		return value(state, null);
	}
	
	/**
	 * Return a value associated with a state, going up to a maximum depth. If the
	 * depth is null, the search continues until a terminal state is reached. If the
	 * depth is not null, and there is a non-terminal state, the value is taken from
	 * the heuristic function. The default heuristic function returns 0 for all states.
     * Note that a depth of one means the neighbors of the first state are expanded.
	 * @param state state to get the value from.
	 * @param depth maximum depth, or null for no max.
	 * @return value of the state, exploring a maximum depth.
	 */
	public Float value(State state, Integer depth) {
		return value(state, depth, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
	}
	
	protected Float value(State state, Integer depth, Float alpha, Float beta) {
		Float val;
		Integer newDepth = depth == null ? null : depth - 1;
		
		if (state.isTerminal()) { // If the state is terminal return the score
			val =  state.score();
		} else if (depth != null && depth == 0) { // If max depth has been reached, return heuristic
			if (useTranspositionTable && transpositionTable.containsKey(state)) {
				return transpositionTable.get(state);
			} else {
				return h.apply(state);
			}
		} else if (useTranspositionTable && transpositionTable.containsKey(state) && 
				(depth == null
				|| depthExplored.get(state) == null
				|| depthExplored.get(state) >= depth)) {
			// If we have a transposition table entry of appropriate depth
			// return it
			return transpositionTable.get(state);
		} else if (state.isMinTurn()) {
			val = Float.POSITIVE_INFINITY;
			for (State neighbor : state.getNeighbors()) {
				val = Math.min(val, value(neighbor, newDepth, alpha, beta));
				beta = Math.min(beta, val);
				
				if (beta <= alpha && useAlphaBetaPruning) {
					break;
				}
			}
		} else if (state.isMaxTurn()) {
			val = Float.NEGATIVE_INFINITY;
			for (State neighbor : state.getNeighbors()) {
				val = Math.max(val, value(neighbor, newDepth, alpha, beta));
				alpha = Math.max(alpha, val);
				
				if (beta <= alpha && useAlphaBetaPruning) {
					break;
				}
			}
		} else if (state.isExpectTurn()) {
			val = 0f;

			try {
				List<State> neighbors = state.getNeighbors();
				List<Float> probs = state.getProbs();
				
				for (int i = 0; i < neighbors.size(); ++i) {
					val += probs.get(i) * value(neighbors.get(i), newDepth, alpha, beta);
				}
			} catch (NotExpectTurnException e) {
				RuntimeException f = new RuntimeException("Attempt to get probabilities on expect turn, but error encountered");
				f.initCause(e);
				throw f;
			}
		} else {
			throw new RuntimeException("It appears the state is not terminal or anybody's turn. This is bad.");
		}
		
		if (useTranspositionTable) {
			transpositionTable.put(state, val);
			depthExplored.put(state, depth);
		}

//		if (state.isExpectTurn()) {
//			System.out.println("Expect Turn");
//		} else if (state.isMaxTurn()) {
//			System.out.println("Max Turn");
//		} else if (state.isMinTurn()) {
//			System.out.println("Min Turn");
//		}
//		
//		System.out.println("State: " + state + " Value: " + val + " Depth: " + depth);
		
		return val;
	}
}
