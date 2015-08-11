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
	// Swap tile states
	// Draw tiles on the board
	
	/**
	 * Is the tile accessible to the player/robot
	 * @param tile
	 * @return
	 */
	public boolean isAccessible(State tile) {
		if(tile != State.EMPTY)
			return true;
	
		else
			return false;
	}
	
	
	

}
