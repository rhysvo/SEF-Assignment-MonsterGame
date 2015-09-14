package monster.java.client.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.List;

import monster.java.client.MonsterGame;
import monster.java.client.net.MessageProtocol;
import monster.java.client.world.Entity;
import monster.java.client.world.PlayerController;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Main game loop and renderer
 * Contains list of entities
 * Draws all entities + world, and passes inputs
 * @author Alex
 *
 */
public class Game extends Thread {
	private List<Entity> players = new ArrayList<Entity>();
	private PlayerController pc;

	public Game() {
		//Monster Entity
		players.add(new Entity(MonsterGame.worldSize/2, MonsterGame.worldSize/2));
	}
	
	public void addLocalPlayer(int id) {
		this.pc = new PlayerController(addPlayer(id));
	}
	
	public Entity addPlayer(int id) {
		Entity entity;
		switch (id) {
		case 1:
			entity = new Entity(0, 0);
			break;
		case 2:
			entity = new Entity(MonsterGame.worldSize, 0);
			break;
		case 3:
			entity = new Entity(0, MonsterGame.worldSize);
			break;
		case 4:
			entity = new Entity(MonsterGame.worldSize, MonsterGame.worldSize);
			break;
		default:
			System.out.println("Invalid player id when adding player.");
			entity = null;
			break;
		}
		entity.setID(id);
		players.add(entity);
		if (players.size() > 1) {
			MessageProtocol.sendReady();
		}
		return entity;
	}
	
	public Entity getEntity(int id) {
		for (Entity p : players)
			if (p.getID() == id)
				return p;
		return null;
	}
	
	public void run() {

		try {
			Display.setDisplayMode(new DisplayMode(MonsterGame.worldSize
					* MonsterGame.tileSize, MonsterGame.worldSize
					* MonsterGame.tileSize));
			Display.setTitle("MonsterGame");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}		
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0 ,640.0 ,640.0 ,0.0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		GL11.glClearColor(1F, 1F, 1F, 1F);
		glColor3f(0F, 0F, 0F);
		
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			glPushMatrix();
			for(int i = 0; i < MonsterGame.worldSize * MonsterGame.tileSize; i+=MonsterGame.tileSize) {
		        for(int j = 0; j < MonsterGame.worldSize * MonsterGame.tileSize; j+=MonsterGame.tileSize) {
		        	glLoadIdentity();
		        	glTranslatef(i, j, 0.0f);
		        	glBegin(GL_LINE_LOOP);
		           		glVertex3f(0.0F, 0.0f, 0.0f);
						glVertex3f(0.0F, (float)MonsterGame.tileSize, 0.0f);
						glVertex3f((float)MonsterGame.tileSize, (float)MonsterGame.tileSize, 0.0f);
						glVertex3f((float)MonsterGame.tileSize, 0.0F, 0.0f);
					glEnd();
		         }
		     }
			glPopMatrix();
			
			if(Keyboard.isKeyDown((Keyboard.KEY_ESCAPE))){
				Display.destroy();
				System.exit(0);
			}
			
			for(Entity box : players){
				
				box.draw();
			}
			
			while(Keyboard.next()) {
				if(Keyboard.getEventKeyState()){
					if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						pc.moveLeft();
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						pc.moveRight();
						System.out.println("Right key pressed");
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
						pc.moveUp();
						System.out.println("Up key pressed");
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						pc.moveDown();
						System.out.println("Down key pressed");
					}
				}
			}
			Display.update();
			Display.sync(60);
			
		}
		
		Display.destroy();
	}

}
