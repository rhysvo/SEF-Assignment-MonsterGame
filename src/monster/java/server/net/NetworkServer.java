package monster.java.server.net;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

import monster.java.server.world.Entity;
import monster.java.server.world.Monster;

public class NetworkServer {

	private int port;
	private ServerSocket serverSocket;
	private ArrayList<NetworkPlayer> players;
	private int readyPlayers = 0;
	private Monster monster;
	private int worldSize;
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
	
	private String loadWorld() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("world.txt"));
		StringBuilder sb = new StringBuilder();
		
		while (in.hasNextLine()) {
			worldSize++;
			sb.append(in.nextLine() + ",");
		}
		
		world = sb.toString().split(",");
		
		in.close();
		return sb.toString();
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
			while (this.readyPlayers == 0
					|| (this.readyPlayers < this.players.size() 
					&& i < this.numPlayers)) {
				// add new NetworkPlayer object to list
				this.players.add(new NetworkPlayer(this.serverSocket.accept(),
						i));
				
				// Send an initial message to the client
				MessageProtocol.sendWorld(this.players.get(i), loadWorld());
				this.players.get(i).send("player:" + i);
				
				// sleep until the num players is set by p1
				if (i == 0) {
					System.out.println("Waiting for player count...");
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
			monster.setPos((int) Math.ceil(worldSize/2), 
						   (int) Math.ceil(worldSize/2));

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
		boolean exit = false,
				prep = true;
		
		while(!exit) {	
			// Allows players to move around for 10s
			if(prep) {
				sleep(2);
				prep = false;
			}
			
			monster.moveToPlayer(players);
			
			
			/* Original AI (basic) *//*
			// Select a target
			Entity player = monster.selectTarget(players);
			
			// Move towards target player
			monster.moveToTarget(player);
			*/
		}
	}
	
	/* * * Getters and Setters * * */
	
	public String[] getWorld() {
		return this.world;
	}
	
	public int getWorldSize() {
		return this.worldSize;
	}
	
	/* * * DEBUGGING CODE BELOW * * */
	
	private void sleep(float n) {
		try {
			Thread.sleep((int) (n * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
