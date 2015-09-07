package monster.java.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import monster.java.server.world.NetworkPlayer;

public class NetworkServer {

	private int port;
	private ServerSocket serverSocket;
	private ArrayList<NetworkPlayer> players;
	private int readyPlayers;
	
	public NetworkServer(int port) {
		this.port = port;
	}
	
	public void addReady() {
		this.readyPlayers++;
	}
	
	public void init() {
		try {
			this.serverSocket = new ServerSocket(this.port);
			
			System.out.println("Waiting for players on port " + this.port);
			
			int i = 0;
			while (this.readyPlayers < this.players.size() && (i = this.players.size()) < 4) {
				
				this.players.add(new NetworkPlayer(this.serverSocket.accept()));
				this.players.get(i).send("player:" + i);
				
			}
			
		} catch (IOException e) {
			
			System.out.println("Error connecting to players.");
			e.printStackTrace();
			
		}
	}
	
	public void run() {
		
		

	}
	
}
