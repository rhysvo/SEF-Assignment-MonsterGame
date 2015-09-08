package monster.java.client.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

public class Entity {
		public int x, y;
		private float colorRed, colorBlue, colorGreen;
		
		public Entity(int x, int y) {
			this.x = x;
			this.y = y;
			
			Random randomGenerator = new Random();
			colorRed = randomGenerator.nextFloat();
			colorBlue = randomGenerator.nextFloat();
			colorGreen = randomGenerator.nextFloat();
		}
		
		public void update(int newx, int newy) {
			if(x + newx >= 0 && x + newx < 10 && y +newy >= 0 && y+newy < 10){
				this.x += newx;
				this.y += newy;
			}
			else
				System.out.println("Invalid Move");
			
		}
		
		public void draw() {
			
			glPushMatrix();
			glTranslatef(x * 64F, y * 64F, 0.0F);
			glBegin(GL_QUADS);
				glVertex2f(0, 0);
				glVertex2f(64, 0);
				glVertex2f(64, 64);
				glVertex2f(0, 64);
			glEnd();
			glPopMatrix();
		}
	}