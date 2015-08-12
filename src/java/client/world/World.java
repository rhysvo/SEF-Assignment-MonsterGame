package java.client.world;

/**
 * 'World' provides and manages information about all the tiles in the game that
 * are processed by the 'MonsterGame' program.
 * 
 * Things that need to be coded: 
 * - Apply textures in 'changeTileState'
 * - Create the updatePos method
 */
public class World {

	// Create possible states of a tile
	public enum Tile { EMPTY, WALL, BLOCKED };
	
	Tile[][] tiles = new Tile[2][2];

	public World(int a) {

	}

	/**
	 * Change the state of a tile by returning a given state Exits if state
	 * could not be changed
	 * 
	 * @param newState
	 * @return
	 */
	public Tile changeTileState(Tile newState) {
		switch (newState) {
		case EMPTY:
			// ** Apply 'EMPTY' texture here ** //

			// Return tile is EMPTY
			return Tile.EMPTY;

		case WALL:
			// ** Apply 'WALL' texture here ** //

			// Return tile is WALL
			return Tile.WALL;

		case BLOCKED:
			// ** Apply correct texture here ** //

			// Return tile is BLOCKED
			return Tile.BLOCKED;

		default:
			// Output invalid state
			System.out.println("Invalid tile state: " + newState);
			System.out.println("Exiting.");

			// Exit the system
			System.exit(0);
		}

		// This is for eclipse
		return null;
	}

	/**
	 * Is the tile accessible to the player/robot
	 * 
	 * @param tile
	 * @return
	 */
	public boolean isAccessible(Tile tile) {
		return tile != Tile.EMPTY;
	}

	public void updatePosition(Player player, int[] oldPosition, int[] newPosition) {

		// Get player number
		// Select player
		// Get newPosition
		// Check new position isn't occupied by PLAYER/WALL/ROBOT
		// Set newPosition to PLAYER if passes checks

		// Get oldPosition, set oldPosition to EMPTY
	}

}
