package monster.java.server;

import monster.java.server.net.NetworkServer;

public class MonsterServer {
	public static final int PORT = 3286;
	public static NetworkServer server;
	public static int MON_TICK = 750;
	public static final boolean DEBUG = false;
	
	/**
	 * Main driver, runs a server in a loop (for when server is ran stand alone)
	 * @param args
	 */
	public static void main(String[] args) {
		while (true) {
			runServer();
		}
	}
	
	/**
	 * Run a single instance of the server
	 */
	public static void runServer() {
		server = new NetworkServer(PORT);
		server.run();
		server.close();
	}

}
