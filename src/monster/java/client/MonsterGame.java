package monster.java.client;

import java.util.Scanner;

import javax.swing.JFrame;

import monster.java.client.game.Game;
import monster.java.client.gui.UIHandler;
import monster.java.client.world.PlayerController;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Driver class for MonsterGame
 * Initializes OpenGL and beings the UIHandler
 * @author Alex Matheson
 */
public class MonsterGame {

	// Instance for backwards references
	public static MonsterGame instance;
	public static Scanner sc;
	
	public UIHandler uiHandler;
	
	public static Game game;
	
	/* GAME SETTINGS */
	
	// Size of each tile (pixels)
	public static int tileSize = 32;
	// Size of world (tiles)
	public static int worldSize = 16;
	// Max speed of player/monster movement (seconds)
	// Does not affect network movement speeds
	public static float tickSpeed = 1;

	/* GL VARIABLES */
	
	// Game window
	private JFrame frame;
	private long lastFrame;
	
	/* GAME VARIABLES */
	
	// Local player
	private PlayerController player;

	private void run() {
		
		sc = new Scanner(System.in);
		
		/*
		
		getDelta();


		initGL();
		*/

		frame = new JFrame();		
		this.uiHandler = new UIHandler(frame);
		this.uiHandler.showMain();
		
		MonsterGame.sc.close();

	}
	
	private void loop() {
		
		while (!Display.isCloseRequested()) {
			
			// Use a time delta to keep track of functions
			// in respect to time
			int delta = getDelta();
			
			// Will probably also need to send input to
			// GUI handler
			
			// Need to use a GUI handler to determine
			// whether to show menu, highscores, or game
			
			// Render GL here (using the world class)
			
			// Refresh display and sync to 30 fps
			Display.update();
			Display.sync(30);
			
		}
		
		// Close everything after program terminates
		// (display is closed)
		Display.destroy();
		frame.dispose();
		
	}

	/**
	 * Initialize the display and set initial values
	 */
	private void initGL() {
		
		try {
			
			Display.setDisplayMode(new DisplayMode(tileSize * worldSize, tileSize * worldSize));
			Display.create();
			
		} catch (LWJGLException e) {
			
			e.printStackTrace();
			System.exit(0);
			
		}
		
		GL11.glClearColor(0.375F, 0.5625F, 0.75F, 1F);
		
	}
	
	/**
	 * Get current time
	 * @return long time (ms)
	 */
	public long getTime() {
		
		return System.nanoTime() / 1000000;
	
	}
	
	/**
	 * Get change in time since last frame, and
	 * update last frame time
	 * @return int delta time
	 */
	private int getDelta() {
		
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
		
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
