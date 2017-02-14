package battlebattle;

import java.util.Random;

public abstract class Player {
	protected static Random rand = new Random();
	
	protected int tokens;
	protected int roll;
	protected int health;
	protected Player opponent;
	
	/**
	 * 
	 * @param health Health of the player
	 * @param tokens How many tokens player has
	 */
	public Player(int health, int tokens) {
		this.health = health;
		this.tokens = tokens;
		this.roll = roll();
	}
	
	public int roll() {
		return rand.nextInt(6) + 1;
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
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void addTokens(int i) {
		this.tokens += i;
	}
	
	public void setOpponent(Player o) {
		this.opponent = o;
	}
	
	public abstract void onStartTurn();
	
	public abstract void onTokenPlay();
	
	public abstract void onRoll();
	
	public abstract void onTakeDamage();
}
