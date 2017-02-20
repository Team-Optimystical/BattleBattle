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
	
	public int pNum;
	
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
	
	public void incrementHealth(int amount) {
		this.health += amount;
	}
	
	@Override
	public abstract Player clone();
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player p = (Player)o;
			return health == p.health && tokens == p.tokens && roll == p.roll;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return health ^ (roll << 3) ^ (tokens << 6);
	}
	
	@Override
	public String toString() {
		return "{h:" + health + ",t:" + tokens + ",r:" + roll + "}";
	}
	
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
	
	/**
	 * Returns a list of possible actions. Note that care must be taken
	 * when defining actions, so that they are done in a completely game
	 * dependant way, independent of the player object that this is called
	 * on.
	 * @return
	 */
	public abstract List<Action> possibleActions(); 
	
	public void setRoll(int val) {
		roll = val;
	}
	
	public int roll() {
		float sel = rand.nextFloat();
		
		List<Integer> rolls = this.rollVals();
		List<Float> probs = this.rollProbs();
		
		for (int i = 0; i < rolls.size(); ++i) {
			sel -= probs.get(i);
			
			if (sel <= 0) {
				return rolls.get(i);
			}
		}
		
		return rolls.get(rolls.size());
	}
	
	/**
	 * Value of die without any effects.
	 * @return
	 */
	public int dieValue() {
		return roll;
	}
	
	/**
	 * Value to be taken to determine victor of round.
	 * @return
	 */
	public int strengthValue() {
		return roll;
	}
	
	/**
	 * Returns the damage dealt based on the opponents roll. If the
	 * opponent has a lower roll, returns 1, otherwise returns 0.
	 * @return
	 */
	public int damageValue() {
		return this.strengthValue() > opponent.strengthValue() ? 1 : 0;
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
	
	/**
	 * Called on the player before the rolls for each player is determined.
	 */
	public abstract void onPreRoll();
	/**
	 * Called on the player after both players have rolled.
	 */
	public abstract void onPostRoll();
	/**
	 * Called at the start of this player's turn. Note that the actions may
	 * be requested before the calling of this function, so this function
	 * should not change the actions available.
	 */
	public abstract void onPreTurn();
	/**
	 * Called at the end of this player's turn.
	 */
	public abstract void onPostTurn();
	/**
	 * Called before either player takes damage.
	 */
	public abstract void onPreDamage();
	/**
	 * Called after damage has been dealt.
	 */
	public abstract void onPostDamage();

	public int getHealth() {
		return health;
	}

	public abstract String getName();
}
