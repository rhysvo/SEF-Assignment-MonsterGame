package monster.java.client.gui;

import monster.java.client.MonsterGame;
import monster.java.client.net.NetworkClient;

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
		
		while (!connected) {
			System.out.println("Enter IP/host of server, or 0 to go back.");
			String in = MonsterGame.instance.sc.nextLine();
			
			if (in.equals("0"))
				return;
			
			try {
				
				MonsterGame.instance.client = new NetworkClient(in, 3286);
				MonsterGame.instance.client.start();
				System.out.println("Waiting for at least 2 players...");
				
			} catch (Exception e) {
				System.out.println("There was an error connecting to the server.");
			}
			
			connected = true;
		}
	}

}
