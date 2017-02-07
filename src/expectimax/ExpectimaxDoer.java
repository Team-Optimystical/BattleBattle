package expectimax;

import java.util.HashMap;
import java.util.List;

public class ExpectimaxDoer {
	HashMap<State, Float> transpositionTable = new HashMap<>();
	
	public Float value(State state) {
		
		if (transpositionTable.containsKey(state)) {
			return transpositionTable.get(state);
		}

		Float val;
		if (state.isTerminal()) {
			val = state.score();
		} else if (state.isMinTurn()) {
			val = Float.POSITIVE_INFINITY;
			for (State neighbor : state.getNeighbors()) {
				val = Math.min(val, value(neighbor));
			}
		} else if (state.isMaxTurn()) {
			val = Float.NEGATIVE_INFINITY;
			for (State neighbor : state.getNeighbors()) {
				val = Math.max(val, value(neighbor));
			}
		} else if (state.isExpectTurn()) {
			val = 0f;

			try {
				List<State> neighbors = state.getNeighbors();
				List<Float> probs;
				probs = state.getProbs();
				
				for (int i = 0; i < neighbors.size(); ++i) {
					val += probs.get(i) * value(neighbors.get(i));
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
		return val;
	}
}
