package java.client.gui;

import javax.swing.JFrame;

/**
 * Handles whether to display menu, highscores
 * or game. Also passes inputs through to certain
 * GUIs.
 * @author Alex Matheson
 *
 */
public class UIHandler {

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
	
}
