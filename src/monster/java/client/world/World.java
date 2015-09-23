package monster.java.client.world;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import monster.java.client.MonsterGame;

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
				glTranslatef(i * MonsterGame.TILE_SIZE, j
						* MonsterGame.TILE_SIZE, 0.0f);
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

	private void drawTile(Tile tile) {

		switch (tile) {
		case WALL:
			glColor3f(0.5f, 0.5f, 0.5f);
			glBegin(GL_QUADS);
			{
				glVertex3f(0.0F, 0.0f, 0.0f);
				glVertex3f(0.0F, (float) MonsterGame.TILE_SIZE, 0.0f);
				glVertex3f((float) MonsterGame.TILE_SIZE,
						(float) MonsterGame.TILE_SIZE, 0.0f);
				glVertex3f((float) MonsterGame.TILE_SIZE, 0.0F, 0.0f);
			}
			glEnd();
			break;

		case BLOCKED:
			// leave empty
			break;

		case EMPTY:
		default:
			glColor3f(0, 0, 0);
			glBegin(GL_LINE_LOOP);
			{
				glVertex3f(0.0F, 0.0f, 0.0f);
				glVertex3f(0.0F, (float) MonsterGame.TILE_SIZE, 0.0f);
				glVertex3f((float) MonsterGame.TILE_SIZE,
						(float) MonsterGame.TILE_SIZE, 0.0f);
				glVertex3f((float) MonsterGame.TILE_SIZE, 0.0F, 0.0f);
			}
			glEnd();
		}
	}

	/**
	 * Is the tile accessible to the player/robot
	 * 
	 * @param tile
	 * @return
	 */
	public boolean isAccessible(int x, int y) {
		return this.world[x][y] == Tile.EMPTY;
	}

}
