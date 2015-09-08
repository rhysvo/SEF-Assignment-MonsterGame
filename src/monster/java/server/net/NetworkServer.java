package monster.java.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class NetworkServer {

	private int port;
	private ServerSocket serverSocket;
	private ArrayList<NetworkPlayer> players;
	private int readyPlayers = 0;

	public NetworkServer(int port) {
		this.port = port;
		this.players = new ArrayList<NetworkPlayer>();
	}

	/**
	 * Increment the ready counter to break from the initialization loop.
	 */
	public void addReady() {
		this.readyPlayers++;
	}

	/**
	 * Initialize connections with player clients, and create new threads for
	 * each ongoing conversation.
	 */
	public void init() {

		try {

			// create the server socket
			this.serverSocket = new ServerSocket(this.port);

			System.out.println("Waiting for players on port " + this.port);

			int i = 0;

			// loop while less than 4 players and not all players are ready
			while (this.readyPlayers < this.players.size()
					&& (i = this.players.size()) < 4) {

				// add new NetworkPlayer object to list
				this.players.add(new NetworkPlayer(this.serverSocket.accept()));
				// send an initial message to the client
				this.players.get(i).send("player:" + i);

			}

		} catch (IOException e) {

			System.out.println("Error connecting to players.");
			e.printStackTrace();

		}
	}

	public void run() {
		// TODO
	}

}
