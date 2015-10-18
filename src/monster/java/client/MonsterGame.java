package monster.java.client;

import java.util.Scanner;

import monster.java.client.game.Game;
import monster.java.client.gui.UIHandler;
import monster.java.client.net.NetworkClient;

/**
 * Driver class for MonsterGame
 * Initializes OpenGL and beings the UIHandler
 * @author Alex Matheson
 */
public class MonsterGame {

	// Instance for backwards references
	public static MonsterGame instance;
	public Scanner sc;
	public NetworkClient client;
	
	public UIHandler uiHandler;
	public Game game;
	
	/* GAME SETTINGS */
	
	// Size of each tile (pixels)
	public static final int TILE_SIZE = 32;
	
	// Size of world (tiles)
	public static final int WORLD_SIZE = 16;
	
	public static final int GAME_SIZE = TILE_SIZE * WORLD_SIZE;
	
	private void run() {
		
		this.sc = new Scanner(System.in);
			
		this.uiHandler = new UIHandler();
		this.uiHandler.showMain();

	}

	/**
	 * Driver method - creates a new instance of this
	 * class and runs it
	 * @param args
	 */
	public static void main(String[] args) {
		
		instance = new MonsterGame();
		instance.run();

	}

}
