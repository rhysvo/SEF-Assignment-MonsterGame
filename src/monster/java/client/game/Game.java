package monster.java.client.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;
import java.util.List;

import monster.java.client.MonsterGame;
import monster.java.client.net.MessageProtocol;
import monster.java.client.util.TextureLoading;
import monster.java.client.world.Entity;
import monster.java.client.world.PlayerController;
import monster.java.client.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

/**
 * Main game loop and renderer Contains list of entities Draws all entities +
 * world, and passes inputs
 * 
 * @author Alex
 *
 */
public class Game extends Thread {
	private List<Entity> players = new ArrayList<Entity>();
	private World world;
	private PlayerController pc;
	private boolean online = true;
	public static Texture p1, p2, p3, p4, monster;
	
	public Game() {
		this.world = new World();
	}
	
	public int getWorldSize() {
		return this.world.size();
	}
	
	public void loadWorld(String[] worldStrings) {
		this.world.loadWorld(worldStrings);
	}

	public void addLocalPlayer(int id) {
		this.pc = new PlayerController(addPlayer(id));
	}

	public Entity addPlayer(int id) {
		Entity entity;
		switch (id) {
		case 0:
			entity = new Entity(this.world.size() / 2, this.world.size() / 2);
			break;
		case 1:
			entity = new Entity(0, 0);
			break;
		case 2:
			entity = new Entity(this.world.size() - 1, 0);
			break;
		case 3:
			entity = new Entity(0, this.world.size() - 1);
			break;
		case 4:
			entity = new Entity(this.world.size() - 1, this.world.size() - 1);
			break;
		default:
			System.out.println("Invalid player id when adding player.");
			entity = null;
			break;
		}
		entity.setID(id);
		this.players.add(entity);
		//if (this.online && this.players.size() > 1) {
			MessageProtocol.sendReady();
		//}
		return entity;
	}

	public Entity getEntity(int id) {
		for (Entity p : this.players)
			if (p.getID() == id)
				return p;
		return addPlayer(id);
	}
	
	public boolean canMove(int x, int y) {
		if (x < 0 || x >= this.world.size() || y < 0 || y >= this.world.size())
			return false;
		
		for (Entity p : this.players) {
			if (p.atPos(x, y))
				return false;
		}		
		return this.world.isAccessible(x, y);
	}

	public void run() {

		try {
			
			Display.setDisplayMode(new DisplayMode(MonsterGame.GAME_SIZE,
					MonsterGame.GAME_SIZE));
			Display.setTitle("MonsterGame");
			Display.create();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//Load textures in from file
		TextureLoading texture = new TextureLoading();
		p1 = texture.loadTexture("p1");
		
		texture = new TextureLoading();
		p2 = texture.loadTexture("p2");
		
		texture = new TextureLoading();
		p3 = texture.loadTexture("p3");
		
		texture = new TextureLoading();
		p4 = texture.loadTexture("p4");
		
		texture = new TextureLoading();
		monster = texture.loadTexture("monster");
		
		// create the monster
		getEntity(0);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, MonsterGame.GAME_SIZE, MonsterGame.GAME_SIZE, 0.0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glClearColor(1F, 1F, 1F, 1F);
		glColor3f(0F, 0F, 0F);

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			
			
			this.world.draw();

			for (Entity player : this.players)
				player.draw();

			if (Keyboard.isKeyDown((Keyboard.KEY_ESCAPE))) {
				Display.destroy();
				System.exit(0);
			}

			this.getKeyInput();

			Display.update();
			Display.sync(60);

		}

		Display.destroy();
	}

	private void getKeyInput() {
		
		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState()) {

				switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_LEFT:
					pc.moveLeft();
					break;
				case Keyboard.KEY_RIGHT:
					pc.moveRight();
					break;
				case Keyboard.KEY_UP:
					pc.moveUp();
					break;
				case Keyboard.KEY_DOWN:
					pc.moveDown();
					break;
				}
			}
		}
	}
	
	public void setOffline() {
		this.online = false;
	}
	
	public boolean isOnline() {
		return this.online;
	}

}
