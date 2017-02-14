package battlebattle.cards;

import battlebattle.Player;

public class Barbarian extends Player {
	public Barbarian() {
		super(6,0);
	}

	@Override
	public void onStartTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTokenPlay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoll() {
		// TODO Auto-generated method stub
		int roll1 = super.roll();
		int roll2 = super.roll();
		
	}

	@Override
	public void onTakeDamage() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int strengthValue() {
		switch (roll) {
		case 1:
		case 2:
		case 3: 
			return 4;
		default:
			return roll;
		}
	}

}
