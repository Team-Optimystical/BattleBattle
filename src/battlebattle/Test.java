package battlebattle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import battlebattle.cards.Assassin;
import battlebattle.cards.Barbarian;
import battlebattle.cards.Cat;
import battlebattle.cards.ConArtist;
import battlebattle.cards.Dalek;
import battlebattle.cards.Giant;
import battlebattle.cards.Gladiator;
import battlebattle.cards.Ninja;
import battlebattle.cards.Vanilla;
import expectimax.ExpectimaxDoer;

public class Test {
	public static List<Class<? extends Player>> playerMap = new ArrayList<>();
	static {
		playerMap.add(Cat.class);
		playerMap.add(Barbarian.class);
		playerMap.add(Gladiator.class);
		playerMap.add(Giant.class);
		playerMap.add(Dalek.class);
		playerMap.add(Ninja.class);
		playerMap.add(Vanilla.class);
		playerMap.add(ConArtist.class);
		playerMap.add(Assassin.class);
	}
	
	public static void doMatchup(Class<? extends Player> p1, Class<? extends Player> p2, MatchupCache cache) throws InstantiationException, IllegalAccessException {
		ExpectimaxDoer exp = new ExpectimaxDoer();
		exp.useAlphaBetaPruning = false;
		Game game = new Game(p1.newInstance(), p2.newInstance());
		
		printValue(cache, exp, game, 40);
	}
	
	public static void main(String[] args) {
		MatchupCache cache = new MatchupCache();
		
		String cacheFileName = args[0] + ".cache";
		String csvFileName = args[0] + ".csv";
		
		// load matchup file
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(cacheFileName)))) {
			cache = (MatchupCache)ois.readObject();
			
			System.out.println("Loaded cache:\n" + cache);
		} catch (FileNotFoundException e1) {
			System.err.println("File not found: " + cacheFileName);
		} catch (IOException e1) {
			System.err.println("Unable to read file: " + cacheFileName);
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading file: " + cacheFileName);
		}
		
		for (Class<? extends Player> p1 : playerMap) {
			for (Class<? extends Player> p2 : playerMap) {
				if (p1.equals(p2)) continue;
				
				try {
					if (!cache.containsMatchup(p1.newInstance().getName(), p2.newInstance().getName())) {
						doMatchup(p1, p2, cache);
					}
					
					// overwrite original file
					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(cacheFileName)))) {
						oos.writeObject(cache);
					} catch (IOException e1) {
						System.err.println("Unable to write file: " + cacheFileName);
					}
					
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(csvFileName)))) {
						bw.write(cache.toCSV());
					} catch (IOException e) {
						System.err.println("Unable to write file: " + csvFileName);
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void printValue(MatchupCache cache, ExpectimaxDoer exp, Game game, int depth) {
		System.out.print(
			"P1: " + game.p1.getName() + 
			" P2: " + game.p2.getName()
		);
		
		long startTime = System.currentTimeMillis();
		float value = exp.value(game, depth);
		long endTime = System.currentTimeMillis();
		
		float time_s = (endTime - startTime) / 1000;
		
		System.out.print(
			" Value: " + value + 
			" Depth: " + depth + 
			" Time: " + time_s + "s\n"
		);
		
		cache.putWinRate(game.p1.getName(), game.p2.getName(), value);
	}
}
