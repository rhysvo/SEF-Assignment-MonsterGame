package monster.java.client.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Initializes and handles IO between game
 * and server.
 * @author Alex
 *
 */
public class NetworkClient {
	
	public NetworkClient(String host, int port) {
		
		try {
			Socket clientSocket = new Socket(host, port);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
		
			String fromServer, fromClient;
			
			Scanner input = new Scanner(System.in);
			
			while ((fromServer = in.readLine()) != null) {
				
				System.out.println("Server: " + fromServer);
				fromClient = input.nextLine();
				if (fromClient != null) {
					System.out.println("Client: " + fromClient);
					out.println(fromClient);
				}
				
			}
			
			clientSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
