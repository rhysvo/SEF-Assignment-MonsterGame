package monster.java.client.gui;

import javax.swing.JFrame;

import monster.java.client.MonsterGame;
import monster.java.client.game.Game;


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
		Menu main = new MainMenu(this);
		main.startCommandLine();
	}
	
	public void showLobby() {
		Menu lobby = new Lobby(this);
		lobby.startCommandLine();
	}
	
	public void showHighScores() {
		// TODO
	}
	
	public void runGame() {
		// TODO
	}
	
	public void localTest() {
		MonsterGame.instance.game = new Game();
		Game game = MonsterGame.instance.game;
		game.setOffline();
		game.addLocalPlayer(1);
		game.run();
	}
	
}
