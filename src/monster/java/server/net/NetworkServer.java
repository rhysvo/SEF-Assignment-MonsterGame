package monster.java.server.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

import monster.java.server.MonsterServer;
import monster.java.server.world.Entity;
import monster.java.server.world.Monster;

public class NetworkServer extends Thread {

	private int port;
	private ServerSocket serverSocket;
	private ArrayList<NetworkPlayer> players;
	private int readyPlayers = 0;
	private Monster monster;
	private String[] world;
	private int numPlayers = 5;

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
		System.out.println(this.readyPlayers + " player(s) ready.");
	}
	
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	/**
	 * Load world from file, set it to the world attr and return it
	 * to send to clients
	 * @return
	 * @throws FileNotFoundException
	 */
	private String loadWorld() {
		Scanner in;
		try {
			in = new Scanner(getClass().getClassLoader().getResource("world.txt").openStream());
			StringBuilder sb = new StringBuilder();
			
			while (in.hasNextLine()) {
				sb.append(in.nextLine() + ",");
			}
			
			world = sb.toString().split(",");
			
			in.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 */
	public void close() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize connections with player clients, and create new threads for
	 * each ongoing conversation.
	 */
	public void init() {

		try {
			System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
			
			// Create the server socket
			this.serverSocket = new ServerSocket(this.port);

			System.out.println("Waiting for players on port " + this.port);
			
			int i = 0;
			// loop while less than 4 players and not all players are ready
			while (!(this.numPlayers == 1 && this.players.size() == 1) && (this.readyPlayers == 0
					|| (this.readyPlayers < this.numPlayers 
					&& i < this.numPlayers))) {
				// add new NetworkPlayer object to list
				this.players.add(new NetworkPlayer(this.serverSocket.accept(),
						i));
				
				// Send an initial message to the client
				MessageProtocol.sendWorld(this.players.get(i), loadWorld());
				this.players.get(i).send("player:" + i);
				this.players.get(i).setName("Player " + (i + 1));
				
				// sleep until the num players is set by p1
				if (i == 0) {
					//System.out.println("Waiting for player count...");
					while (this.numPlayers == 5) {
						Thread.sleep(1000);
					}
				}
				i++;
			}
			
			System.out.println(i + " players ready, starting game.");
			
			Thread.sleep(1000);
			
			MessageProtocol.sendBegin();
			
			// Create the Monster
			monster = new Monster(world);
			monster.setPos((int) Math.ceil(world.length/2), 
						   (int) Math.ceil(world.length/2));

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
		this.init();
		
		boolean exit = false;
		
		// Allow players to move around before monster
		this.sleepn(5);
		
		while(!exit) {
			// Sleep BEFORE moving so players can't easily move away
			try {
				Thread.sleep(Math.max(165, MonsterServer.MON_TICK));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Begin the AI movement
			monster.moveToPlayer(players);
			
			// Increase the speed by 1%
			MonsterServer.MON_TICK = (int) Math.ceil(MonsterServer.MON_TICK*0.99);
			
			for (NetworkPlayer player : this.players) {
				Entity playerObj = player.getPlayer();
				if (playerObj.isAlive()) {
					if (playerObj.X() == monster.X() && playerObj.Y() == monster.Y()) {
						// if player at monster's position, broadcast the
						// kill message and remove the player from the array
						MessageProtocol.sendKill(player);
						playerObj.kill();
						playerObj.setRank(numAlivePlayers());
						System.out.println("Player " + player.getID() + " died.");
						sleepn(1);
					}
				}
			}
			
			// all players are dead, exit
			if (numAlivePlayers() == 0)
				exit = true;
			
			// no players are connected, destroy the socket
			// and exit
			if (numConnectedPlayers() == 0) {
				this.close();
				return;
			}
			
		}
		
		// create and send win message
		String winMsg = "end:";
		for (int i = 0; i < this.players.size(); i++) {
			// try to wait for last player to send their message...
			while (this.players.get(i).time == 0) sleepn(1);
			winMsg = winMsg + getRankedPlayer(i).getName() + ":" + this.players.get(i).time + ",";
		}
		// replace last comma with semi
		winMsg = winMsg.substring(0, winMsg.length() - 1) + ";";
		this.broadcast(winMsg);
		
		// close connections
		for (NetworkPlayer player : this.players) {
			player.close();
		}
		
		// end
		this.close();
	}
	
	public NetworkPlayer getRankedPlayer(int i) {
		for (NetworkPlayer player : this.players) {
			if (player.getPlayer().getRank() == i)
				return player;
		}
		return null;
	}
	
	public int numAlivePlayers() {
		int i = 0;
		for (NetworkPlayer player : this.players)
			if (player.getPlayer().isAlive())
				i++;
		return i;
	}
	
	public int numConnectedPlayers() {
		int i = 0;
		for (NetworkPlayer player : this.players)
			if (player.connected)
				i++;
		return i;
		
	}
	
	/* * * Getters and Setters * * */
	
	public String[] getWorld() {
		return this.world;
	}
	
	public int getWorldSize() {
		return this.world.length;
	}
	
	/* * * DEBUGGING CODE BELOW * * */
	
	private void sleepn(float n) {
		try {
			Thread.sleep((int) (n * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
