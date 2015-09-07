package java.client.gui;

import java.util.Scanner;

/**
 * Lobby menu for creating new games and
 * displaying connections to other players
 * @author Alex Matheson
 *
 */
public class Lobby extends Menu {

	/*
	 * Let's all go to the lobby,
	 * Let's all go to the lobby!
	 * Let's all go to the lobbyy,
	 * And get ourselves some snacks.
	 */
	protected Lobby(UIHandler uiHandler) {
		super(uiHandler);
	}

	@Override
	protected void processButtonValue(String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void startCommandLine() {
		// TODO: send a request to server to ask for current games
		boolean connected = false;
		Scanner input = new Scanner(System.in);
		
		while (!connected) {
			System.out.println("Enter IP/host of server, or 0 to go back.");
			if (input.nextLine() == "0")
				return;
			
			try {
				/* connect to server,
				 * send initial info */
			} catch (Exception e) {
				System.out.println("There was an error connecting to the server.");
			}
		}
		
		input.close();
	}

}
