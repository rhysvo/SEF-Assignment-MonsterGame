package monster.java.client.world;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import monster.java.client.MonsterGame;
import monster.java.client.net.MessageProtocol;

public class Entity {
		private int x, y, id;
		
		public Entity(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void setID(int id) {
			this.id = id;
		}
		
		public int getID() {
			return this.id;
		}
		
		public void update(int newx, int newy) {
			if(x + newx >= 0 && x + newx < MonsterGame.worldSize && y +newy >= 0 && y+newy < MonsterGame.worldSize){
				this.x += newx;
				this.y += newy;
			}
			else
				System.out.println("Invalid Move");
			
			MessageProtocol.sendMove(this.x, this.y);
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