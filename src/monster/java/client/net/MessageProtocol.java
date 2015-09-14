package monster.java.client.net;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monster.java.client.MonsterGame;
import monster.java.client.game.Game;
import monster.java.client.world.Entity;

public class MessageProtocol {
	
	private static Pattern movePattern = Pattern
			.compile("([a-z]*):(\\d*),(\\d*),(\\d*)");

	public static void sendReady() {
		MonsterGame.client.getPrintWriterOut().println("ready");
	}
	
	public static void sendMove(int x, int y) {
		String msg = "mv:" + x + "," + y;
		MonsterGame.client.getPrintWriterOut().println(msg);
	}
	
	/**
	 * 
	 * @param line
	 * @throws IOException 
	 */
	public static void processLine(String line) throws IOException {
		
		System.out.println(line);
		
		for (String msg : line.split(";")) {
			if (msg.startsWith("mv:"))
				processMove(msg);
			
			else if (msg.startsWith("dead:"))
				processDeath(msg);
			
			else if (msg.startsWith("player:")) {
				int id = Integer.parseInt(msg.split(":")[1]);
				MonsterGame.game = new Game();
				MonsterGame.game.addLocalPlayer(id + 1);
				
			} else if (msg.equals("begin")) {
				MonsterGame.game.start();
			}
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
		
		Entity p = MonsterGame.game.getEntity(player);
		p.setPos(x, y);
	}
}
