package battlebattle.cards;

import java.util.ArrayList;
import java.util.List;

import battlebattle.Action;
import battlebattle.Player;

public class Gladiator extends Player {
	public static int tokensPerHit = 1;
	private int healthPreDamage;
	
	public Gladiator() {
		super(6, 0); // health, tokens to begin
	}
	
	public String getName() {
		return "Gladiator";
	}
	
	public Gladiator(Gladiator toCopy) {
		super(toCopy);
		this.healthPreDamage = toCopy.healthPreDamage;
	}

	@Override
	public Player clone() {
		return new Gladiator(this);
	}

	@Override
	public List<Action> possibleActions() {
		List<Action> actions = new ArrayList<>();
		
		actions.add(new Action((game) -> {}));
		
		for (int i = 1; i <= tokens; ++i) {
			for (int changeThem = 0; changeThem <= i; ++changeThem) {
				int changeUs = i - changeThem;
				
				// iterate over adding and subtracting from each player
				for (int cu = -1; cu <= 1; ++cu) {
					for (int ct = -1; ct <= 1; ++ct) {
						if (opponent.dieValue() + (ct * changeThem) >= 1 
								&& opponent.dieValue() + (ct * changeThem) <= 6
								&& this.dieValue() + (cu * changeUs) >= 1
								&& this.dieValue() + (cu * changeUs) <= 6) {
							final int CT = ct;
							final int CU = cu;
							final int CHANGE_THEM = changeThem;
							final int CHANGE_US = changeUs;
							final int TOKENS_USED = i;
							
							Action a;
							if (pNum == 1) {
								a = new Action((game) -> {
									game.p2.setRoll(game.p2.dieValue() + (CT * CHANGE_THEM));
									game.p1.setRoll(game.p1.dieValue() + (CU * CHANGE_US));
									game.p1.addTokens(-TOKENS_USED);
								});
							} else if (pNum == 2) {
								 a = new Action((game) -> {
									game.p1.setRoll(game.p1.dieValue() + (CT * CHANGE_THEM));
									game.p2.setRoll(game.p2.dieValue() + (CU * CHANGE_US));
									game.p2.addTokens(-TOKENS_USED);
								});
							} else {
								throw new RuntimeException("Player number is neither 1 nor 2");
							}
							
							actions.add(a);
						}
					}
				}
			}
		}
		
		return actions;
	}

	@Override
	public void onPreRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPostTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreDamage() {
		healthPreDamage = getHealth();
	}

	@Override
	public void onPostDamage() {
		if (healthPreDamage > getHealth()) {
			tokens += tokensPerHit;
		}
	}

}
