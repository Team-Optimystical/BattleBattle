package battlebattle;

import java.util.Random;

public abstract class Player {
	protected static Random rand = new Random();
	
	protected int tokens;
	protected int roll;
	protected int health;
	
	public Player(int health, int tokens) {
		this.health = health;
		this.tokens = tokens;
	}
	
	public void roll() {
		roll = rand.nextInt(6) + 1;
	}
	
	/**
	 * Value of die without any effects.
	 * @return
	 */
	public int dieValue() {
		return roll;
	}
	
	/**
	 * Value to be taken for victor of round.
	 * @return
	 */
	public int strengthValue() {
		return roll;
	}
}
