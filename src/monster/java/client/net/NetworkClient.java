package monster.java.client.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import monster.java.client.MonsterGame;

/**
 * Initializes and handles IO between game and server.
 * 
 * @author Alex
 *
 */
public class NetworkClient {

	public NetworkClient(String host, int port) {

		try {
			Socket clientSocket = new Socket(host, port);
			System.out.println("Connected to server.");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			String fromServer, fromClient;

			while ((fromServer = in.readLine()) != null) {

				System.out.println("Server: " + fromServer);
				fromClient = MonsterGame.sc.nextLine();
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
