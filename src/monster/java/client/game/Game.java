package monster.java.client.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
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
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import monster.java.client.world.Entity;

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
public class Game {
List<Entity> players = new ArrayList<Entity>();
	
	public Game(){
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("MonsterGame");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		players.add(new Entity(0, 0));
		
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0 ,640.0 ,640.0 ,0.0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		GL11.glClearColor(1F, 1F, 1F, 1F);
		glColor3f(0F, 0F, 0F);
		
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			glPushMatrix();
			for(int i = 0; i < 640; i+=64) {
		        for(int j = 0; j < 640; j+=64) {
		        	glLoadIdentity();
		        	glTranslatef(i, j, 0.0f);
		        	glBegin(GL_LINE_LOOP);
		           		glVertex3f(0.0F, 0.0f, 0.0f);
						glVertex3f(0.0F, 64.0f, 0.0f);
						glVertex3f(64.0f, 64.0F, 0.0f);
						glVertex3f(64.0f, 0.0F, 0.0f);
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
						players.get(0).update(-1, 0);
						System.out.println("Left key pressed");
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						players.get(0).update(1, 0);
						System.out.println("Right key pressed");
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
						players.get(0).update(0, -1);
						System.out.println("Up key pressed");
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						players.get(0).update(0, 1);
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
