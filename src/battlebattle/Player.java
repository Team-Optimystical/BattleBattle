package battlebattle;

import java.util.Random;

public abstract class Player {
	protected static Random rand = new Random();
	
	protected int tokens;
	protected int roll;
	protected int health;
	
	/**
	 * 
	 * @param health Health of the player
	 * @param tokens How many tokens player has
	 */
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
	
	public void addTokens(int i) {
		this.tokens += i;
	}
	
	public abstract void onStartTurn();
	
	public abstract void onTokenPlay();
	
	public abstract void onRoll();
	
	public abstract void onTakeDamage();
}
