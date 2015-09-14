package monster.java.client.world;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
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

	/**
	 * 
	 */
	public void draw() {
		glPushMatrix();
		for (int i = 0; i < MonsterGame.WORLD_SIZE; i++) {
			for (int j = 0; j < MonsterGame.WORLD_SIZE; j++) {
				glLoadIdentity();
				glTranslatef(i * MonsterGame.TILE_SIZE, MonsterGame.TILE_SIZE,
						0.0f);
				drawTile(this.world[j][i]);
			}
		}
		glPopMatrix();
	}

	private void drawTile(Tile tile) {

		switch (tile) {
		case EMPTY:
			break;

		case WALL:
			glBegin(GL_LINE_LOOP);
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

		default:
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
	public boolean isAccessible(Tile tile) {
		return tile == Tile.EMPTY;
	}

	public void loadTilesFromServer(String sentInformation) {
		/*
		 * Receive information from the server - Map size - Wall location
		 * 
		 * Split information up
		 */
	}

}
