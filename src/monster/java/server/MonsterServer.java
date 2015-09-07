package monster.java.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MonsterServer {
	
	static final int PORT = 3286;
	
	public void main(String[] args) {
		
		try {
			
			ServerSocket serverSocket = new ServerSocket(PORT);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String inputLine, outputLine;
			
			out.println("Hello");
			
			while ((inputLine = in.readLine()) != null) {
				
				System.out.println(inputLine);
				out.println("looping");
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

}
