package monster.java.client.world;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import monster.java.client.MonsterGame;
import monster.java.client.util.Sprite;
import monster.java.client.util.TextureLoading;

/**
 * 'World' provides and manages information about all the tiles in the game that
 * are processed by the 'MonsterGame' program.
 * 
 * Things that need to be coded: - Apply textures in 'changeTileState' - Create
 * the updatePos method
 */
public class World {

	// Create possible states of a tile
	private enum Tile {
		EMPTY, WALL, BLOCKED
	};

	// Create Tile array for the world
	private Tile[][] world;

	public World() {
		this.world = new Tile[MonsterGame.WORLD_SIZE][MonsterGame.WORLD_SIZE];
		for (int i = 0; i < MonsterGame.WORLD_SIZE; i++) {
			for (int j = 0; j < MonsterGame.WORLD_SIZE; j++) {
				this.world[j][i] = Tile.EMPTY;
			}
		}
	}

	public int size() {
		return this.world.length;
	}

	public void draw() {
		glPushMatrix();
		for (int i = 0; i < this.world.length; i++) {
			for (int j = 0; j < this.world[0].length; j++) {
				glLoadIdentity();
				glTranslatef(i * MonsterGame.TILE_SIZE,
						j * MonsterGame.TILE_SIZE, 0.0f);
				drawTile(this.world[j][i]);
			}
		}
		glPopMatrix();
		glColor3f(0, 0, 0);
	}

	public void loadWorld(String[] worldStrings) {
		this.world = new Tile[worldStrings.length][worldStrings.length];
		for (int i = 0; i < worldStrings.length; i++) {
			char[] worldLine = worldStrings[i].toCharArray();
			for (int j = 0; j < worldLine.length; j++) {
				this.world[i][j] = worldLine[j] == '#' ? Tile.WALL : Tile.EMPTY;
			}
		}
	}

	// method to draw tiles from the sprite sheet
	private void drawTile(Tile tile) {
		float tileSize = (float) MonsterGame.TILE_SIZE;
		Sprite currentSprite = getTileTexture(tile);

		int gx = currentSprite.getX();
		int gy = currentSprite.getY();
		int gx2 = currentSprite.getX() + MonsterGame.TILE_SIZE;
		int gy2 = currentSprite.getY() + MonsterGame.TILE_SIZE;

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
	}

	// method to retrieve correct texture
	// based on the enum for Tile type.
	private Sprite getTileTexture(Tile tile) {
		TextureLoading entityTexture = MonsterGame.instance.game
				.getTextureLoading();

		switch (tile) {
		case WALL:
			return entityTexture.getSprite("wall");

		case BLOCKED:
			return entityTexture.getSprite("empty");

		case EMPTY:
			return entityTexture.getSprite("empty");

		default:
			return entityTexture.getSprite("empty");
		}
	}

	/**
	 * Is the tile accessible to the player/robot
	 * 
	 * @param tile
	 * @return
	 */
	public boolean isAccessible(int x, int y) {
		return this.world[y][x] == Tile.EMPTY;
	}

}
