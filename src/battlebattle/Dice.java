package battlebattle;

public class Dice {
	int[] values;
	float[] probabilities;
	
	public Dice(int num, int[] vals, float[] probs) {
		if (vals.length != probs.length) {
			throw new IllegalArgumentException("The lenght of values and probabilities should be equal");
		}
		
		values = vals.clone();
		probabilities = probs.clone();
	}
}
