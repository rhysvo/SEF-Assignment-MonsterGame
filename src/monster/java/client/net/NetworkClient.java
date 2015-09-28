package monster.java.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Initializes and handles IO between game and server.
 * 
 * @author Alex
 *
 */
public class NetworkClient extends Thread {

	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket;

	/**
	 * Create a socket-connected client and initialize input and output streams
	 * to the server
	 * 
	 * @param host
	 * @param port
	 */
	public NetworkClient(String host, int port) {

		try {
			this.clientSocket = new Socket(host, port);
			System.out.println("Connected to server.");
			
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Threaded loop that reads messages from the input stream and processes
	 * them in MessageProtocol
	 */
	public void run() {
		String msg;
		try {
			// process loop
			while ((msg = in.readLine()) != null)
				MessageProtocol.process(msg);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close after disconnect
			try {
				this.clientSocket.close();
			} catch (IOException e) {
				System.out.println("Failed to close connection.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send a message to the server
	 * 
	 * @param msg
	 */
	public void send(String msg) {
		this.out.println(msg);
	}
}
