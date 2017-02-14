package battlebattle.cards;

import battlebattle.Player;

public class Gladiator extends Player {
	
	public Gladiator() {
		super(6,0); // health, tokens to begin
	}

	@Override
	public void onStartTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTokenPlay() {
		// increase or decrease any battle die by 1.
		// TODO
		
	}

	@Override
	public void onRoll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTakeDamage() {
		// Every round: when you take damage, gain 3 tokens.
		tokens += 3;
	}

}
