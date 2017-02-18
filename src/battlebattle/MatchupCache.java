package battlebattle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchupCache implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Map<String, Map<String, Float>> scores;
	List<String> names;
	
	public MatchupCache() {
		scores = new HashMap<>();
		names = new ArrayList<>();
	}
	
	public void putWinRate(String p1Name, String p2Name, Float score) {
		if (!names.contains(p1Name)) {
			names.add(p1Name);
		}
		
		if (!names.contains(p2Name)) {
			names.add(p2Name);
		}
		
		if (!scores.containsKey(p1Name)) {
			scores.put(p1Name, new HashMap<>());
		}
		
		scores.get(p1Name).put(p2Name, score);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("P1(+) \\ P2(-)");
		
		for (String name : names) {
			sb.append(",\t" + name);
		}
		
		sb.append("\n");
		
		for (String name1 : names) {
			if (scores.containsKey(name1)) {
				sb.append(name1);
				
				Map<String, Float> scoresByP2 = scores.get(name1);
				for (String name2 : names) {
					sb.append(",\t");
					
					if (scoresByP2.containsKey(name2)) {
						sb.append(scoresByP2.get(name2));
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
		
		for (Map<String, Float> s : scores.values()) {
			s.remove(pName);
			
		}
	}
}
