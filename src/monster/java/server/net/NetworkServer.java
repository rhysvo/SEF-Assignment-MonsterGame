package monster.java.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import monster.java.server.world.Entity;
import monster.java.server.world.Monster;

public class NetworkServer {

	private int port;
	private ServerSocket serverSocket;
	private ArrayList<NetworkPlayer> players;
	private int readyPlayers = 0;
	private Monster monster;

	public NetworkServer(int port) {
		this.port = port;
		this.players = new ArrayList<NetworkPlayer>();
	}

	/**
	 * Broadcast a message to all connected players
	 * 
	 * @param msg
	 */
	public void broadcast(String msg) {
		for (NetworkPlayer client : players) {
			client.send(msg);
		}
	}

	/**
	 * Increment the ready counter to break from the initialization loop.
	 */
	public void addReady() {
		this.readyPlayers++;
		System.out.println(this.readyPlayers + " players ready.");
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
			while (this.readyPlayers == 0
					|| (this.readyPlayers < this.players.size() 
					&& i < 2)) {
				// add new NetworkPlayer object to list
				this.players.add(new NetworkPlayer(this.serverSocket.accept(),
						i));
				// send an initial message to the client
				this.players.get(i).send("player:" + i);
				i++;
				break;

			}
			
			System.out.println(i + " players ready, starting game.");
			
			Thread.sleep(1000);
			
			MessageProtocol.sendBegin();
			
			// Create the Monster
			monster = new Monster();
			monster.setPos(8, 8);

		} catch (IOException e) {

			System.out.println("Error connecting to players.");
			e.printStackTrace();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Server-side game loop
	 */
	public void run() {
		boolean exit = false;
		
		while(!exit) {			
			// Select a target
			int p1 = monster.selectTarget(players);
			Entity player = players.get(p1).getPlayer();

			// Output current location of Monster and Target
			monster.outputDetails();
			
			// Follow the player
			if(player.X() == monster.X())
				if(player.Y() == monster.Y())
					continue;
			
			if(player.X() < monster.X())
				monster.moveLeft(monster.findTargetDistX(player));
			
			else
				monster.moveRight(monster.findTargetDistX(player));
			
			if(player.Y() < monster.Y())
				monster.moveUp(monster.findTargetDistY(player));
			
			else
				monster.moveDown(monster.findTargetDistY(player));
			
			// Test moving around the board
			/*monster.moveLeft(4);			
			monster.moveUp(4);			
			monster.moveRight(4);
			monster.moveDown(4);*/
			
			
			
			// Wait for 5 seconds before proceeding
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
