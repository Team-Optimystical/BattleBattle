package expectimax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExpectimaxDoer {
	private Map<State, Float> valueTable = new HashMap<>();
	private Map<State, Float> probMaxTable = new HashMap<>();
	private Map<State, Float> probMinTable = new HashMap<>();
	private Map<State, Integer> depthExplored = new HashMap<>();
	Function<State, Float> h = (s) -> 0f;
	
	private Map<State, Integer> b = new HashMap<>();
	
	public void setHeuristic(Function<State, Float> h) {
		this.h = h;
	}
	
	public float avgBranchingFactor() {
		float avg = 0;
		int numStates = b.size();
		
		for (int branchingFactor : b.values()) {
			avg += (float)branchingFactor / (float)numStates;
		}
		
		return avg;
	}
	
	public Map<State, Float> getTranspositionTable() {
		return valueTable;
	}
	
	public Float probMaxWin(State state) {
		value(state);
		return probMaxTable.get(state);
	}
	
	public Float probMaxWin(State state, Integer depth) {
		value(state, depth);
		return probMaxTable.get(state);
	}
	
	public Float probMinWin(State state) {
		value(state);
		return probMinTable.get(state);
	}
	
	public Float probMinWin(State state, Integer depth) {
		value(state, depth);
		return probMinTable.get(state);
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
		Float val;
		Float probMaxWin;
		Float probMinWin;
		List<State> neighbors = null;
		Integer newDepth = depth == null ? null : depth - 1;
		
		if (state.isTerminal()) { // If the state is terminal return the score
			val =  state.score();
			probMaxWin = state.isMaxWin() ? 1f : 0f;
			probMinWin = state.isMinWin() ? 1f : 0f;
		} else if (depth != null && depth == 0) { // If max depth has been reached, return heuristic
			if (valueTable.containsKey(state)) {
				return valueTable.get(state);
			} else {
				val = h.apply(state);
				probMaxWin = 0f;
				probMinWin = 0f;
			}
		} else if (valueTable.containsKey(state) && 
				(depth == null
				|| depthExplored.get(state) == null
				|| depthExplored.get(state) >= depth)) {
			// If we have a transposition table entry of appropriate depth
			// return it
			return valueTable.get(state);
		} else if (state.isMinTurn()) {
			val = Float.POSITIVE_INFINITY;
			probMaxWin = Float.POSITIVE_INFINITY;
			probMinWin = Float.NEGATIVE_INFINITY;
			neighbors = state.getNeighbors();
			for (State neighbor : neighbors) {
				val = Math.min(val, value(neighbor, newDepth));
				probMaxWin = Math.min(probMaxWin, probMaxTable.get(neighbor));
				probMinWin = Math.max(probMinWin, probMinTable.get(neighbor));
			}
		} else if (state.isMaxTurn()) {
			val = Float.NEGATIVE_INFINITY;
			probMaxWin = Float.NEGATIVE_INFINITY;
			probMinWin = Float.POSITIVE_INFINITY;
			neighbors = state.getNeighbors();
			for (State neighbor : neighbors) {
				val = Math.max(val, value(neighbor, newDepth));
				probMaxWin = Math.max(probMaxWin, probMaxTable.get(neighbor));
				probMinWin = Math.min(probMinWin, probMinTable.get(neighbor));
			}
		} else if (state.isExpectTurn()) {
			val = 0f;
			probMaxWin = 0f;
			probMinWin = 0f;
			neighbors = state.getNeighbors();
			
			try {
				List<Float> probs = state.getProbs();
				for (int i = 0; i < neighbors.size(); ++i) {
					val += probs.get(i) * value(neighbors.get(i), newDepth);
					probMaxWin += probs.get(i) * probMaxTable.get(neighbors.get(i));
					probMinWin += probs.get(i) * probMinTable.get(neighbors.get(i));
				}
			} catch (NotExpectTurnException e) {
				RuntimeException f = new RuntimeException("Attempt to get probabilities on expect turn, but error encountered");
				f.initCause(e);
				throw f;
			}
		} else {
			throw new RuntimeException("It appears the state is not terminal or anybody's turn. This is bad.");
		}
		
		valueTable.put(state, val);
		depthExplored.put(state, depth);
		
		probMaxTable.put(state, probMaxWin);
		probMinTable.put(state, probMinWin);
		
//		System.out.println("State: " + state + " Depth: " + depth + 
//				"\nValue: " + val + " Max: " + probMaxWin + " Min: " + probMinWin);
		
		int branchingFactor = neighbors == null ? 0 : neighbors.size();
		b.put(state, branchingFactor);
		
		return val;
	}
}
