package monster.java.client.net;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monster.java.client.MonsterGame;

public class MessageProtocol {
	
	private static Pattern movePattern = Pattern
			.compile("([a-z]*):(\\d*),(\\d*),(\\d*)");
	
	/**
	 * 
	 * @param line
	 * @throws IOException 
	 */
	public static void processLine(String line) throws IOException {
		
		for (String msg : line.split(";")) {
			if (msg.startsWith("mv:"))
				processMove(msg);
			
			else if (msg.startsWith("dead:"))
				processDeath(msg);
		}
	}
	
	/**
	 * Process deaths of each player
	 * @param deathMsg
	 */
	public static void processDeath(String deathMsg) {
		
	}
	
	/**
	 * Process moves of the players
	 * @param moveMsg
	 * @throws IOException 
	 */
	public static void processMove(String moveMsg) throws IOException {
		/* Message looks like
		 * mv:pID,x,y
		 * where pID is playerID or 0 [MonsterID] and x & y are integers
		 */
		
		Matcher matcher = movePattern.matcher(moveMsg);
		
		if (!matcher.matches()) {
			System.out.println("Invalid mv message: " + moveMsg);
			throw new IOException();
		}
		
		int player = Integer.parseInt(matcher.group(2));
		int x = Integer.parseInt(matcher.group(3));
		int y = Integer.parseInt(matcher.group(4));
		
		MonsterGame.game.getPlayer(player).setPos(x, y);
	}
}
