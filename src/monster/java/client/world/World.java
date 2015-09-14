package monster.java.client.world;

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
	private enum Tile { EMPTY, WALL, BLOCKED };
	
	// Create Tile array for the world
	private Tile[][] world;

	public World() {
		this.world = new Tile[10][10];
	}

	/**
	 * 
	 */
	public void draw() {
		
	}
	
	/**
	 * (DEPRECATED) Change the state of a tile by returning a given state Exits 
	 * if state could not be changed.
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

			// Exit the system
		
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
		return tile == Tile.EMPTY;
	}

	public void loadTilesFromServer(String sentInformation) {
		/*
		 *  Receive information from the server
		 *		- Map size
		 *		- Wall location
		 *	
		 *	Split information up
		 */
	}
	
	public void updatePosition(PlayerController player, int[] oldPosition, int[] newPosition) {

		/* 
		 * Get player information
		 * 		- Number
		 * 		- Old Location
		 * 		- New Location
		 * 
		 * Get world information
		 * 		- Available tiles 
		 * 
		 * Compare information
		 * 		- Is New Location available
		 * 
		 * Modify information
		 * 		- Prevent player moving if spot unavailable
		 * 		- Set newPosition to BLOCKED if pass
		 * 		- Set oldPosition to EMPTY if pass
		 */
	}

}
