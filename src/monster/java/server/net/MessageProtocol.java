package monster.java.server.net;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monster.java.server.MonsterServer;

public class MessageProtocol {

	private static Pattern movePattern = Pattern
			.compile("([a-z]*):(\\d*),(\\d*)");

	// OUTGOING MESSAGES //
	
	/**
	 * Broadcast a player's movement to all clients
	 * 
	 * @param client
	 * @param x
	 * @param y
	 */
	public static void sendMove(NetworkPlayer client, int x, int y) {
		
		String msg = "mv:" + client.getID() + "," + x + "," + y + ";";		
		MonsterServer.server.broadcast(msg);
		
	}
	
	/**
	 * Broadcast after a player disconnects
	 * 
	 * @param client
	 */
	public static void sendDisconnect(NetworkPlayer client) {
		
		String msg = "dc:" + client.getID() + ";";
		MonsterServer.server.broadcast(msg);
		
	}
	
	/**
	 * Broadcast a player's death
	 * 
	 * @param client
	 */
	public static void sendKill(NetworkPlayer client) {
		
		String msg = "kill:" + client.getID();
		MonsterServer.server.broadcast(msg);
		
	}
	
	/**
	 * Broadcast monster movement to all clients
	 * 
	 * @param x
	 * @param y
	 */
	public static void sendMonsterMove(int x, int y) {
		
		String msg = "mv:0," + x + "," + y + ";";
		MonsterServer.server.broadcast(msg);
		
	}
	
	public static void sendBegin() {
		MonsterServer.server.broadcast("begin");
	}
	
	// INCOMING MESSAGES //
	
	/**
	 * Process a message from a client and perform appropriate actions
	 * 
	 * @param client
	 * @param line
	 * @throws IOException
	 */
	public static void process(NetworkPlayer client, String line)
			throws IOException {

		for (String msg : line.split(";")) {

			if (msg.startsWith("mv:"))
				processMove(client, msg);

		}

	}

	/**
	 * Process a player move
	 * 
	 * @param client
	 * @param mvMsg
	 * @throws IOException
	 */
	private static void processMove(NetworkPlayer client, String mvMsg)
			throws IOException {

		// Message should look like
		// mv:10,12
		// (where 10 is x and 12 is y)

		Matcher matcher = movePattern.matcher(mvMsg);

		if (!matcher.matches()) {
			System.out.println("Invalid mv message: " + mvMsg);
			throw new IOException();
		}

		int x = Integer.parseInt(matcher.group(2));
		int y = Integer.parseInt(matcher.group(3));

		client.getPlayer().setPos(x, y);
		sendMove(client, x, y);
		
	}

}
