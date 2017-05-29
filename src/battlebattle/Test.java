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
import battlebattle.cards.BodyBuilder;
import battlebattle.cards.Boxer;
import battlebattle.cards.Bruiser;
import battlebattle.cards.Cat;
import battlebattle.cards.ConArtist;
import battlebattle.cards.Dalek;
import battlebattle.cards.Giant;
import battlebattle.cards.Gladiator;
import battlebattle.cards.MrFreeze;
import battlebattle.cards.Necromancer;
import battlebattle.cards.Ninja;
import battlebattle.cards.SimpleTest;
import battlebattle.cards.Vanilla;
import expectimax.ExpectimaxDoer;

public class Test {
	private static final float MIN_FINISH_FOUND = 0.9f;
	
	public static List<Class<? extends Player>> playerMap = new ArrayList<>();
	public static List<Class<? extends Player>> redoMap = new ArrayList<>();
	static {
		playerMap.add(Assassin.class);
//		playerMap.add(Barbarian.class);
		playerMap.add(BodyBuilder.class);
		playerMap.add(Boxer.class);
		playerMap.add(Bruiser.class);
		playerMap.add(Cat.class);
		playerMap.add(ConArtist.class);
		playerMap.add(Dalek.class);
		playerMap.add(Giant.class);
		playerMap.add(Gladiator.class);
		playerMap.add(MrFreeze.class);
		playerMap.add(Necromancer.class);
		playerMap.add(Ninja.class);
		playerMap.add(Vanilla.class);
	}
	static {
		redoMap.add(Vanilla.class);
//		redoMap.add(ConArtist.class);
//		redoMap.add(Bruiser.class);
//		redoMap.add(BodyBuilder.class);
//		redoMap.add(MrFreeze.class);
	}
	
//	static {
//		playerMap.add(SimpleTest.class);
//	}
	
	public static ExpectimaxDoer doMatchup(Game game) throws InstantiationException, IllegalAccessException {
		ExpectimaxDoer exp = new ExpectimaxDoer();
		
		printValue(exp, game, MIN_FINISH_FOUND);
		
		return exp;
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
		
		for (Class<? extends Player> redo : redoMap) {
			try {
				cache.blast(redo.newInstance().getName());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		for (Class<? extends Player> p1 : playerMap) {
			for (Class<? extends Player> p2 : playerMap) {
				if (p1.equals(p2)) continue;
				
				try {
					Player p1p = p1.newInstance();
					Player p2p = p2.newInstance();
					if (!cache.containsMatchup(p1p.getName(), p2p.getName())) {
						Game game = new Game(p1p, p2p);
						ExpectimaxDoer exp = doMatchup(game);

						cache.putScore(game.p1.getName(), game.p2.getName(), exp.value(game, MIN_FINISH_FOUND));
						cache.putWinRate(game.p1.getName(), game.p2.getName(), exp.probMaxWin(game, MIN_FINISH_FOUND));
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
	
	public static void printValue(ExpectimaxDoer exp, Game game, float fracTerminal) {
		System.out.print(
			"P1: " + game.p1.getName() + 
			" P2: " + game.p2.getName()
		);
		
		long startTime = System.currentTimeMillis();
		float p1wr = exp.probMaxWin(game, fracTerminal);
		float fracTerminalFound = exp.probTerminal(game);
		int depth = exp.depthExplored(game);
		float meanBranchingFactor = exp.avgBranchingFactor();
		long endTime = System.currentTimeMillis();
		
		float time_s = (endTime - startTime) / 1000;
		
		System.out.print(
			"\n\tP1WR: " + p1wr + 
			"\n\tFracTerminal: " + fracTerminalFound + 
			"\n\tDepth: " + depth + 
			"\n\tMeanBranchingFactor: " + meanBranchingFactor + 
			"\n\tTime: " + time_s + "s\n"
		);
	}
}
