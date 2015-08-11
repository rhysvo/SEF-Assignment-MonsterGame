package java.client.world;

public class Tile {
	// Give the states of a tile
	public enum State {EMPTY, WALL, PLAYER, ROBOT}
	
	// Create a tile object
	State tile;
	
	// Constructor for the tile class
	Tile() {
		
	}
	
	/* Things that need to be coded */	
	
	/**
	 * Change the state of a tile by returning a given state
	 * Exits if state could not be changed
	 * @param newState
	 * @return
	 */
	public State changeTileState(State newState) {
		switch(tile) {
			case EMPTY:
				// ** Apply 'EMPTY' texture ** //
				return State.EMPTY;
				
			case WALL:
				// ** Apply 'WALL' texture ** //
				return State.WALL;
				
			case PLAYER:
				// ** Apply 'PLAYER' texture ** //
				return State.PLAYER;
				
			case ROBOT:
				// ** Apply 'ROBOT' texture ** //
				return State.ROBOT;
				
			default:
				System.out.println("Could not change tile state. Exiting.");
				System.exit(0);
		}
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
