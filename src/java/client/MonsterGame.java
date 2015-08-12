package java.client;

import java.client.world.LocalPlayer;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Driver class for MonsterGame
 * Contains global settings and main game loop
 * TODO: Split (?) for networking, i.e. move game loop
 * @author Alex Matheson
 */
public class MonsterGame {

	// Instance for backwards references
	public static MonsterGame instance;

	/* GAME SETTINGS */
	public static int tileSize = 32;
	public static int worldSize = 16;

	/* GL VARIABLES */
	
	// Game window
	private JFrame frame;
	private long lastFrame;
	
	/* GAME VARIABLES */
	
	// Current player
	private LocalPlayer player;

	private void run() {
		
		getDelta();

		frame = new JFrame();

		initGL();
		loop();

	}
	
	private void loop() {
		
		while (!Display.isCloseRequested()) {
			
			// Use a time delta to keep track of functions
			// in respect to time
			int delta = getDelta();
			
			// Update user input on every frame
			player.pollInput(delta);
			
			Display.update();
			Display.sync(30);
			
		}
		
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
