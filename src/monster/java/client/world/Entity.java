package monster.java.client.world;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

import monster.java.client.MonsterGame;
import monster.java.client.game.Game;
import monster.java.client.net.MessageProtocol;
import monster.java.client.util.Sprite;
import monster.java.client.util.TextureLoading;

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
		
			case 0:
				// Monster = BLACK
				glColor3f(0F, 0F, 0F);
				break;
				
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
				//BLACK
				glColor3f(0F, 0F, 0F);
		}
	}
	
	public Sprite getEntityTexture(int player) {
		switch(player) {
		
			case 0:
				// Monster 
				return Game.spriteMap.get("monster");
				
			case 1:
				// Player 1
				return Game.spriteMap.get("p1");
				
			case 2:
				// Player 2
				return Game.spriteMap.get("p2");
				
			case 3:
				// Player 3
				return Game.spriteMap.get("p3");
				
			case 4:
				// Player 4
				return Game.spriteMap.get("p4");
				
			default:
				// Player 1 Sprite
				return Game.spriteMap.get("p1");
		}
	}
	
	

	public void draw() {
		float tileSize = (float) MonsterGame.TILE_SIZE;	
		glPushMatrix();
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, Game.spritesheet);
		Sprite currentSprite = getEntityTexture(id);
		
		int gx = currentSprite.getX();
        int gy = currentSprite.getY();
        int gx2 = currentSprite.getX() + currentSprite.getWidth();
        int gy2 = currentSprite.getY() + currentSprite.getHeight();
        
		
		glTranslatef(x * tileSize, y * tileSize, 0.0F);
		glBegin(GL_QUADS);
		{
			glColor3f(1F, 1F, 1F);
			glTexCoord2f(gx, gy);
			glVertex2f(0, 0);
			glTexCoord2f(gx, gy2);
			glVertex2f(0, tileSize);
			glTexCoord2f(gx2, gy2);
			glVertex2f(tileSize, tileSize);
			glTexCoord2f(gx2, gy);
			glVertex2f(tileSize, 0);
		}
		glEnd();
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
		glPopMatrix();
	}

	public boolean atPos(int x, int y) {
		return this.x == x && this.y == y;
	}
}