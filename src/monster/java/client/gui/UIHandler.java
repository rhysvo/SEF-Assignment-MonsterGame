package monster.java.client.gui;

import javax.swing.JFrame;

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

	@SuppressWarnings("unused")
	private JFrame frame;
	
	public UIHandler(JFrame frame) {
		this.frame = frame;
	}
	
	public void showMain() {		
		new MainMenu().begin();
		
	}
	
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
