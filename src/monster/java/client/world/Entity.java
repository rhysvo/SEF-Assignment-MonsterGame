package monster.java.client.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import monster.java.client.MonsterGame;

public class Entity {
		public int x, y;
		
		public Entity(int x, int y) {
			this.x = x;
			this.y = y;

		}
		
		public void update(int newx, int newy) {
			if(x + newx >= 0 && x + newx < MonsterGame.worldSize && y +newy >= 0 && y+newy < MonsterGame.worldSize){
				this.x += newx;
				this.y += newy;
			}
			else
				System.out.println("Invalid Move");
			
		}
		
		public void setPos(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void draw() {
			
			float tileSize = (float)MonsterGame.tileSize;
			
			glPushMatrix();
			glTranslatef(x * tileSize, y * tileSize, 0.0F);
			glBegin(GL_QUADS);
				glVertex2f(0, 0);
				glVertex2f(tileSize, 0);
				glVertex2f(tileSize, tileSize);
				glVertex2f(0, tileSize);
			glEnd();
			glPopMatrix();
		}
	}