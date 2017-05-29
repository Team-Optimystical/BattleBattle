package battlebattle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to cache the results of a matchup between two players.</br> 
 * </br>
 * This inherits the {@link Serializable} interface so that it can be written to disk, 
 * in binary form. As well, a call to {@link MatchupCache#toCSV()} can be used to write
 * it to a csv file to view outside of the program.
 * @author Alex Sampley
 *
 */
public class MatchupCache implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Map<String, Map<String, Float>> scores;
	Map<String, Map<String, Float>> probP1Win;
	List<String> names;
	
	/**
	 * Create an empty matchup cache.
	 */
	public MatchupCache() {
		scores = new HashMap<>();
		probP1Win = new HashMap<>();
		names = new ArrayList<>();
	}
	
	/**
	 * Put a win rate for player 1 against player 2.
	 * @param p1Name Name for player 1
	 * @param p2Name Name for player 2
	 * @param winrate The fraction of games won by player 1.
	 */
	public void putWinRate(String p1Name, String p2Name, Float winrate) {
		verifyName(p1Name);
		verifyName(p2Name);
		
		if (!probP1Win.containsKey(p1Name)) {
			probP1Win.put(p1Name, new HashMap<>());
		}
		
		probP1Win.get(p1Name).put(p2Name, winrate);
	}
	
	/**
	 * Put a score for player 1 against player 2.</br>
	 * </br>
	 * This function should not be used, since it does not affect the csv data.
	 * @param p1Name Name for player 1
	 * @param p2Name Name for player 2
	 * @param winrate The score for the game.
	 */
	@Deprecated
	public void putScore(String p1Name, String p2Name, Float score) {
		verifyName(p1Name);
		verifyName(p2Name);
		
		if (!scores.containsKey(p1Name)) {
			scores.put(p1Name, new HashMap<>());
		}
		
		scores.get(p1Name).put(p2Name, score);
	}
	
	/**
	 * Verify that the name is in the list, if not, add it.
	 * @param name
	 */
	private void verifyName(String name) {
		if (!names.contains(name)) {
			names.add(name);
			Collections.sort(names);
		}
	}
	
	/**
	 * Return a string in a csv style format, which has the winrate of the row
	 * player against the column player.
	 * @return String containing a csv compatible format.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Player \\ Opponent");
		
		for (String name : names) {
			sb.append("," + name);
		}
		
		sb.append("\n");
		
		for (String name1 : names) {
			if (probP1Win.containsKey(name1)) {
				sb.append(name1);
				
				Map<String, Float> probP1byP2 = probP1Win.get(name1);
				for (String name2 : names) {
					sb.append(",");
					
					if (probP1byP2.containsKey(name2)) {
						sb.append(probP1byP2.get(name2));
					}
				}
				
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns a string containing a csv format of the winrates of different player matchups.
	 * @return String containing a csv compatible format.
	 */
	public String toCSV() {
		return toString();
	}

	/**
	 * Returns true if the cache contains a matchup between player 1 and 2.
	 * @param p1 Player 1 name.
	 * @param p2 Player 2 name.
	 * @return True if the cache contains the matchup.
	 */
	public boolean containsMatchup(String p1, String p2) {
		return probP1Win.containsKey(p1) && probP1Win.get(p1).containsKey(p2);
	}
	
	/**
	 * Clear all entries in the cache for the given player.
	 * @param pName Name of the player for whom to remove all matchups.
	 */
	public void blast(String pName) {
		names.remove(pName);
		scores.remove(pName);
		probP1Win.remove(pName);
		
		for (Map<String, Float> s : scores.values()) {
			s.remove(pName);
		}
		for (Map<String, Float> s : probP1Win.values()) {
			s.remove(pName);
		}
	}
}
