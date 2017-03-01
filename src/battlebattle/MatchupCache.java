package battlebattle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchupCache implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Map<String, Map<String, Float>> scores;
	Map<String, Map<String, Float>> probP1Win;
	List<String> names;
	
	public MatchupCache() {
		scores = new HashMap<>();
		probP1Win = new HashMap<>();
		names = new ArrayList<>();
	}
	
	public void putWinRate(String p1Name, String p2Name, Float winrate) {
		verifyName(p1Name);
		verifyName(p2Name);
		
		if (!probP1Win.containsKey(p1Name)) {
			probP1Win.put(p1Name, new HashMap<>());
		}
		
		probP1Win.get(p1Name).put(p2Name, winrate);
	}
	
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
	
	public String toCSV() {
		return toString();
	}

	public boolean containsMatchup(String p1, String p2) {
		return scores.containsKey(p1) && scores.get(p1).containsKey(p2);
	}
	
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
