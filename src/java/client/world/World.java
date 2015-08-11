package java.client.world;

public class World {
	
	// Create Tile Array
	Tile[][] tiles;
	
	protected World (int a) {
		
		// Initialise Tile Array
		this.tiles = new Tile[a][a];
	}
	
	public void updatePosition(Player player, int[] oldPosition, int[] newPosition) {
		
	}
}
