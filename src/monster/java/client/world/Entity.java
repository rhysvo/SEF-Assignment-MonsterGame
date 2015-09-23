package monster.java.client.world;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
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
		if (MonsterGame.instance.game.canMove(x + newx, y + newy)) {
			this.x += newx;
			this.y += newy;
			
			if (MonsterGame.instance.game.isOnline()) {
				MessageProtocol.sendMove(this.x, this.y);
			}
		}
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void colourEntity(int player) {
		switch(player) {
			case 1:
				// Player 1 = RED
				glColor3f(1F, 0F, 0F);
				break;
				
			case 2:
				// Player 2 = GREEN
				glColor3f(0F, 1F, 0F);
				break;
				
			case 3:
				// Player 3 = BLUE
				glColor3f(0F, 0F, 1F);
				break;
				
			case 4:
				// Player 4 = YELLOW
				glColor3f(1F, 1F, 0F);
				break;
				
			default:
				// Monster = BLACK
				glColor3f(0F, 0F, 0F);
		}
	}

	public void draw() {
		float tileSize = (float) MonsterGame.TILE_SIZE;
		
		glPushMatrix();
		glTranslatef(x * tileSize, y * tileSize, 0.0F);
		glBegin(GL_QUADS);
		{
			colourEntity(id);
			glVertex2f(0, 0);
			glVertex2f(tileSize, 0);
			glVertex2f(tileSize, tileSize);
			glVertex2f(0, tileSize);
		}
		glEnd();
		glPopMatrix();
	}

	public boolean atPos(int x, int y) {
		return this.x == x && this.y == y;
	}
}