package expectimax;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ExpectimaxDoer {
	HashMap<State, Float> transpositionTable = new HashMap<>();
	HashMap<State, Integer> depthExplored = new HashMap<>();
	Function<State, Float> h = (s) -> 0f;
	
	public void setHeuristic(Function<State, Float> h) {
		this.h = h;
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
		
		if (transpositionTable.containsKey(state) && 
				(depth == null
				|| depthExplored.get(state) == null
				|| depthExplored.get(state) >= depth)) {
			// If we have the value stored to sufficient depth
			// return the stored value
			return transpositionTable.get(state);
		} else if (depth != null && depth <= 0 && !state.isTerminal()) { 
			// If we've reached the max depth
			// and are not terminal
			// return the heuristic
			return h.apply(state);
		}

		// If we haven't stored or reached the max depth, find the value
		Float val;
		Integer newDepth = depth == null ? null : depth - 1;
		
		if (state.isTerminal()) {
			val = state.score();
		} else if (state.isMinTurn()) {
			val = Float.POSITIVE_INFINITY;
			for (State neighbor : state.getNeighbors()) {
				val = Math.min(val, value(neighbor, newDepth));
			}
		} else if (state.isMaxTurn()) {
			val = Float.NEGATIVE_INFINITY;
			for (State neighbor : state.getNeighbors()) {
				val = Math.max(val, value(neighbor, newDepth));
			}
		} else if (state.isExpectTurn()) {
			val = 0f;

			try {
				List<State> neighbors = state.getNeighbors();
				List<Float> probs;
				probs = state.getProbs();
				
				for (int i = 0; i < neighbors.size(); ++i) {
					val += probs.get(i) * value(neighbors.get(i), newDepth);
				}
			} catch (NotExpectTurnException e) {
				RuntimeException f = new RuntimeException("Attempt to get probabilities on expect turn, but error encountered");
				f.initCause(e);
				throw f;
			}
		} else {
			throw new RuntimeException("It appears the state is not terminal or anybody's turn. This is bad.");
		}

		transpositionTable.put(state, val);
		
		//System.out.println("States evaluated: " + transpositionTable.size());
		
		depthExplored.put(state, depth);
		return val;
	}
}
