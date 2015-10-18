package monster.java.client.world;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import monster.java.client.MonsterGame;
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

			MessageProtocol.sendMove(this.x, this.y);
		}
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Method and switch statement to return relevant texture
	// from hash map.
	public Sprite getEntityTexture(int player) {
		TextureLoading entityTexture = MonsterGame.instance.game
				.getTextureLoading();
		switch (player) {

		case 0:
			// Monster
			return entityTexture.getSprite("monster");

		case 1:
			// Player 1
			return entityTexture.getSprite("p1");

		case 2:
			// Player 2
			return entityTexture.getSprite("p2");

		case 3:
			// Player 3
			return entityTexture.getSprite("p3");

		case 4:
			// Player 4
			return entityTexture.getSprite("p4");

		default:
			// Player 1 Sprite
			return entityTexture.getSprite("p1");
		}
	}

	public void draw() {
		glPushMatrix();

		float tileSize = (float) MonsterGame.TILE_SIZE;
		Sprite currentSprite = getEntityTexture(id);

		int gx = currentSprite.getX();
		int gy = currentSprite.getY();
		int gx2 = currentSprite.getX() + MonsterGame.TILE_SIZE;
		int gy2 = currentSprite.getY() + MonsterGame.TILE_SIZE;

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
		glPopMatrix();
	}

	public boolean atPos(int x, int y) {
		return this.x == x && this.y == y;
	}	
}
