package battlebattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Player implements Cloneable {
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
	
	public Player(Player toCopy) {
		this.health = toCopy.health;
		this.tokens = toCopy.tokens;
		this.roll = toCopy.roll;
	}
	
	@Override
	public abstract Player clone();
	
	public List<Integer> rollVals() {
		List<Integer> vals = new ArrayList<>();
		vals.add(1);
		vals.add(2);
		vals.add(3);
		vals.add(4);
		vals.add(5);
		vals.add(6);
		return vals;
	}
	
	public List<Float> rollProbs() {
		List<Float> probs = new ArrayList<>();
		for (int i = 0; i < 6; ++i) {
			probs.add(1f / 6f);
		}
		return probs;
	}
	
	public void setRoll(int val) {
		roll = val;
		onRoll();
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
