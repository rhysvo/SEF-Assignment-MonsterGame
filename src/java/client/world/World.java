package java.client.world;

public class World {
	
	// Create Tile Array
	Tile[][] tiles;
	
	protected World (int a) {
		
		// Initialise Tile Array
		this.tiles = new Tile[a][a];
	}
	
	public void updatePosition(Player player, int[] oldPosition, int[] newPosition) {
		
		// Get player number
		// Select player
		// Get newPosition
			// Check new position isn't occupied by PLAYER/WALL/ROBOT
		// Set newPosition to PLAYER if passes checks
		
		// Get oldPosition, set oldPosition to EMPTY
	}
	
	
	public void setWalls(Tile[][] firstPos, Tile[][] secondPos) {
		// Sets a specific region to WALL
	}
}
