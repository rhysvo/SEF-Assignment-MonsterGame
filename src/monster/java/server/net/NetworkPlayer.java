package monster.java.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import monster.java.server.MonsterServer;
import monster.java.server.world.Entity;

public class NetworkPlayer extends Thread {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Entity player;
	private int id;
	public static String socketID;
	
	private boolean isReady = false;

	public NetworkPlayer(Socket socket, int id) {
		this.socket = socket;
		this.id = id + 1;

		System.out.println("New player connected to " + socket.getInetAddress()
				+ " " + socket.getPort());
		
		
		socketID = socket.getInetAddress().toString();
		System.out.println(socketID);

		try {

			// initialize output and input streams
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		} catch (IOException e) {
			System.out.println("Unable to connect to player streams.");
			e.printStackTrace();
		}

		this.player = new Entity();
		
		// Begin the thread
		this.start();
	}

	/**
	 * Send a message to the player's client
	 * 
	 * @param msg
	 */
	public void send(String msg) {
		this.out.println(msg);
	}

	/**
	 * Threaded loop for all messages
	 */
	public void run() {
		String inputLine;
		try {
			while ((inputLine = this.in.readLine()) != null) {
				if (MonsterServer.DEBUG) 
					System.out.println("MSG: " + inputLine);

				if (inputLine.equals("ready") && !this.isReady) {
					
					this.isReady = true;
					MonsterServer.server.addReady();
					
				} else {
				
					// handle all other messages in the protocol
					// processor class
					MessageProtocol.process(this, inputLine);

				}
			}
		} catch (SocketException e) {
			
			System.out.println("Connection lost to " + this.socket.getInetAddress());
			MessageProtocol.sendDisconnect(this);
			
		} catch (IOException e) {
			
			System.out.println("Bad message from client "
					+ this.socket.getInetAddress());
			e.printStackTrace();
		
		}
	}

	/**
	 * Get the player's entity
	 * 
	 * @return player
	 */
	public Entity getPlayer() {
		return this.player;
	}
	
	/**
	 * Get the player's ID
	 * 
	 * @return ID
	 */
	public int getID() {
		return this.id;
	}
	
}
