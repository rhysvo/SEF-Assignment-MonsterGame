package monster.java.server;

import monster.java.server.net.NetworkServer;

public class MonsterServer {
	public static final int PORT = 3286;
	public static NetworkServer server;
	public static int MON_TICK = 750;
	public static final boolean DEBUG = false;
	
	public static void main(String[] args) {
		while (true) {
			runServer();
			server = new NetworkServer(PORT);
			server.run();
			server.destroy();
		}
	}
	
	public static void runServer() {
		server = new NetworkServer(PORT);
		server.run();
		server.destroy();
	}

}
