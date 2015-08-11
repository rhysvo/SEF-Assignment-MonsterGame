package java.client.world;
	/**
	 * 'Tile' provides and manages information about all the tiles in the game
	 * that are processed by the 'MonsterGame' program.
	 * 
	 * Things that need to be coded:
	 * - Apply textures in 'changeTileState'
	 */
public class Tile {
	// Give the states of a tile
	public enum State {EMPTY, WALL, PLAYER, ROBOT}
	
	// Create a tile object
	State tile;
	
	Tile() {
		
	}
	
	/**
	 * Change the state of a tile by returning a given state
	 * Exits if state could not be changed
	 * @param newState
	 * @return
	 */
	public State changeTileState(State newState) {
		switch(tile) {
			case EMPTY:
				// ** Apply 'EMPTY' texture here ** //
				
				// Return tile must be EMPTY
				return State.EMPTY;
				
			case WALL:
				// ** Apply 'WALL' texture here ** //
				
				// Return tile is WALL
				return State.WALL;
				
			case PLAYER:
				// ** Apply 'PLAYER' texture here ** //
				
				// Return tile is PLAYER
				return State.PLAYER;
				
			case ROBOT:
				// ** Apply 'ROBOT' texture here ** //
				
				// Return tile is ROBOT
				return State.ROBOT;
				
			default:
				// Print that a valid state was not selected
				System.out.println("Could not change tile state. Exiting.");
				
				// Exit program
				System.exit(0);
		}
		
		// This is needed for eclipse
		return null;
	}
	
	/**
	 * Is the tile accessible to the player/robot
	 * @param tile
	 * @return
	 */
	public boolean isAccessible(State tile) {
		return tile != State.EMPTY;
	}
	
	
	

}
