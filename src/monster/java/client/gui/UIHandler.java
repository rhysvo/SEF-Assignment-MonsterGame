package monster.java.client.gui;

import javax.swing.JFrame;

import monster.java.client.MonsterGame;
import monster.java.client.game.Game;
import monster.java.client.net.NetworkClient;
import monster.java.server.MonsterServer;


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
		/* Command Line */
		//boolean quit = false;
		MainMenu main = new MainMenu();
		main.startCommandLine();
		
	}
	
	public void showLobby() {
		Menu lobby = new Lobby(this);
		lobby.startCommandLine();
	}
	
	public void showHighScores() {
		// TODO
	}
	
	public void localTest() {
		MonsterServer.local();
		System.out.println("Created local server");
		MonsterGame.instance.game = new Game();
		MonsterGame.instance.game.setLocal(true);
		MonsterGame.instance.client = new NetworkClient("localhost", 3286);
		MonsterGame.instance.client.run();
		MonsterServer.killLocal();
	}
	
}
