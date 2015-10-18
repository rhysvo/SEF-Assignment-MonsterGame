package monster.java.client.gui;

import monster.java.client.MonsterGame;
import monster.java.client.game.Game;
import monster.java.client.net.NetworkClient;


/**
 * Handles whether to display menu, highscores
 * or game. Also passes inputs through to certain
 * GUIs.
 * @author Alex Matheson
 *
 */
public class UIHandler {
	
	/**
	 * Display the main menu
	 */
	public void showMain() {		
		new MainMenu().begin();
	}
	
	/**
	 * Create a new NetworkClient and Game instance
	 * to begin the game
	 */
	public void runClient() {
		String in = MainMenu.GameMenu.IPAdr;
		
		if (in.equals("0"))
			return;
		
		try {
			
			MonsterGame.instance.game = new Game();
			MonsterGame.instance.client = new NetworkClient(in, 3286);
			
			MonsterGame.instance.client.start();
			System.out.println("Waiting for at least 2 players...");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There was an error connecting to the server.");
		}
	}
	
	public void showHighScores() {
		// TODO
	}
	
}
