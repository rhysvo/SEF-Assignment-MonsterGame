package monster.java.server;

import monster.java.server.net.NetworkServer;

public class MonsterServer {
	
	public static final int PORT = 3286;
	public static NetworkServer server;
	
	public static void main(String[] args) {

		server = new NetworkServer(PORT);
		server.init();
		server.run();
		
	}

}
