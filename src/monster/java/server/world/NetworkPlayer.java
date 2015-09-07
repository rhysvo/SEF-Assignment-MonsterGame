package monster.java.server.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import monster.java.server.MonsterServer;

public class NetworkPlayer extends Thread {
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean isReady = false;
	
	public NetworkPlayer(Socket socket) {
		this.socket = socket;
		
		System.out.println("New player connected to " + socket.getInetAddress() + " " + socket.getPort());
		
		try {
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Unable to connect to player streams.");
			e.printStackTrace();
		}
		
		// Begin the thread
		this.start();
	}
	
	public void send(String msg) {
		this.out.println(msg);
	}
	
	public void run() {
		String inputLine;
		try {
			while ((inputLine = this.in.readLine()) != null) {
				System.out.println(inputLine);
	
				// TODO: handle incoming messages
				if (inputLine == "ready" && !this.isReady) {
					this.isReady = true;
					MonsterServer.server.addReady();
				}				
			}
		} catch (IOException e) {
			System.out.println("Bad message from client " + this.socket.getInetAddress());
			e.printStackTrace();
		}
	}

}
