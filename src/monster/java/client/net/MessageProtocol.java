package monster.java.client.net;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monster.java.client.MonsterGame;
import monster.java.client.world.Entity;

public class MessageProtocol {

	private static Pattern movePattern = Pattern
			.compile("([a-z]*):(\\d*),(\\d*),(\\d*)");

	/**
	 * Send 'ready' to the server, which starts the game when all clients are
	 * ready
	 */
	public static void sendReady() {
		MonsterGame.instance.client.send("ready");
	}

	/**
	 * Send a player movement to the server to broadcast the position
	 * 
	 * @param x
	 *            player x
	 * @param y
	 *            player y
	 */
	public static void sendMove(int x, int y) {
		String msg = "mv:" + x + "," + y;
		MonsterGame.instance.client.send(msg);
	}

	/**
	 * Process a line of messages from the server
	 * 
	 * @param line
	 * @throws IOException
	 */
	public static void process(String line) throws IOException {

		System.out.println(line);

		for (String msg : line.split(";")) {
			if (msg.startsWith("mv:")) {
				// movement broadcast
				processMove(msg);

			} else if (msg.startsWith("kill:")) {
				// player killed broadcast
				processDeath(msg);

			} else if (msg.startsWith("player:")) {
				// initial player id message
				int id = Integer.parseInt(msg.split(":")[1]);
				MonsterGame.instance.game.addLocalPlayer(id + 1);
				// first player is able to set number of players here
				if (id == 0) {
					int numPlayers = 0;
					do {
						System.out.print("How many players? (1-4):");
						try {
							// get and sanitize input
							numPlayers = Integer
									.parseInt(MonsterGame.instance.sc
											.nextLine());
							if (numPlayers < 0 || numPlayers > 4)
								System.out
										.println("There must be between 1 and 4 players.");
						} catch (NumberFormatException e) {
							System.out.println("Please enter a valid number.");
						}
					} while (numPlayers == 0);
					// send player's choice to the server
					MonsterGame.instance.client.send("num:" + numPlayers);
				}

			} else if (msg.equals("begin")) {
				// all players ready, begin game
				MonsterGame.instance.game.start();
			} else if (msg.startsWith("world:")) {
				// string containing the world file
				processWorld(msg);
			}
		}
	}

	/**
	 * Process deaths of each player
	 * 
	 * @param deathMsg
	 */
	public static void processDeath(String deathMsg) {

	}

	/**
	 * Cut up the msg and send the array of strings to Game to be loaded
	 * 
	 * @param worldMsg
	 */
	public static void processWorld(String worldMsg) {

		String[] worldStrings = worldMsg.replace("world:", "").split(",");
		MonsterGame.instance.game.loadWorld(worldStrings);

	}

	/**
	 * Process moves of the players
	 * 
	 * @param moveMsg
	 * @throws IOException
	 */
	public static void processMove(String moveMsg) throws IOException {
		/*
		 * Message looks like mv:pID,x,y where pID is playerID or 0 [MonsterID]
		 * and x & y are integers
		 */

		Matcher matcher = movePattern.matcher(moveMsg);

		if (!matcher.matches()) {
			System.out.println("Invalid mv message: " + moveMsg);
			throw new IOException();
		}

		int player = Integer.parseInt(matcher.group(2));
		int x = Integer.parseInt(matcher.group(3));
		int y = Integer.parseInt(matcher.group(4));

		Entity p = MonsterGame.instance.game.getEntity(player);
		p.setPos(x, y);
	}
}
