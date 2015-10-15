package monster.java.server.world;

import java.util.ArrayList;

import monster.java.server.net.NetworkPlayer;

public class Node {
	// Coords of Node
	private int x, y;
	
	// Denotes if Node is a wall
	private boolean wall;
	
	// Number of valid adjacent nodes (that aren't walls)
	private int numAdj = 0;
	
	// Adjacent node array
	private Node[] adj = new Node[4];
	
	// 2d Node Array for map
	public static Node[][] nodes;
	
	public Node(int x, int y, boolean wall) {
		this.x = x;
		this.y = y;
		this.wall = wall;
	}
	
	/**
	 * Initialise Nodes for given world
	 * @param world
	 */
	public static void init(String[] world) {
		// Set 2d node array to the world size
		nodes = new Node[world.length][world.length];
		
		// Create all nodes
		for(int i = 0; i < world.length; i++)
			for(int j = 0; j < world[i].length(); j++)
				nodes[i][j] = new Node(j, i, world[i].charAt(j) == '#');
		
		// Add all adjacent nodes
		for(int i = 0; i < world.length; i++)
			for(int j = 0; j < world[i].length(); j++)
				nodes[i][j].addAllAdjacent(nodes);
	}
	
	/**
	 * 
	 * @param nodes
	 * @param x
	 * @param y
	 * @return
	 */
	public static Node getNode(Node[][] nodes, int x, int y) {
		for(Node[] na : nodes)
			for(Node n : na)
				if(n.x == x && n.y == y)
					return n;
		
		return null;
	}
	
	private void addAllAdjacent(Node[][] nodes) {
		addAdjacent(0, getNode(nodes, x, y - 1));
		addAdjacent(1, getNode(nodes, x + 1, y));
		addAdjacent(2, getNode(nodes, x, y + 1));
		addAdjacent(3, getNode(nodes, x - 1, y));
	}
	
	/**
	 * Adds adjacent single node to given node
	 * n = 0:UP 1:RIGHT 2:DOWN 3:LEFT 
	 * @param n
	 * @param node
	 */
	private void addAdjacent(int n, Node node) {
		adj[n] = node;
		
		// Add to numAdj if node is a valid move
		if (node != null && !node.wall)
			numAdj++;
	}
	
	/**
	 * Begin searching for players
	 * @param players
	 * @return
	 */
	public int beginSearch(ArrayList<NetworkPlayer> players) {
		// Initialise search for adj variables and default to wall
		int[] searches = {999, 999, 999, 999};
		
		// Initialise minimum search, default to wall
		int min = 999;
		
		for (int i = 0; i < searches.length; i++) {
			// Continue expanding through adjacent nodes
			if(adj[i] != null)
				searches[i] = adj[i].search(new ArrayList<int[]>(), (i-2) % 4, 1, players);
			
			// Assigns min variable for comparison
			min = Math.min(min, searches[i]);
		}
		
		if (min == 999)
			return -1;
		
		// Returns the index of the min number
		for(int i = 0; i < searches.length; i++)
			if(min == searches[i])
				return i;
		
		// Stop process for n/1000 seconds
		try {
			Thread.sleep(10000);
		} catch (Exception e) { }
		
		// Return function failed
		return -1;
	}
	
	private boolean inArray(ArrayList<int[]> searched) {
		for (int[] set : searched)
			if (set[0] == x && set[1] == y)
				return true;
		return false;
	}
	
	/**
	 * 
	 * @param from
	 * @param dist
	 * @param players
	 * @return
	 */
	private int search(ArrayList<int[]> searched, int from, int dist, ArrayList<NetworkPlayer> players) {
		// If node is a fork (more than 2 adj, i.e. 2 directions)
		// add it to the list, to make sure that when we
		// loop, we don't check this one again
		if (this.numAdj > 2) {
			if (inArray(searched)) {
				return 999;
			}
			searched.add(new int[]{x, y});
		}
		
		if (wall || dist > 20)
			return 999;
		
		for(NetworkPlayer player : players) {
			if (!player.getPlayer().isAlive())
				continue;
			if(x == player.getPlayer().X() && y == player.getPlayer().Y()) {
				return dist;
			}
		}
		
		int[] searches = {999, 999, 999, 999};
		int min = 999;
		
		for(int i = 0; i < 4; i++) {
			if(i != from && adj[i] != null)
				// New ArrayList<int[]>(searched) -> copies the arraylist.
				// otherwise the original list would be huge and slow.
				searches[i] = adj[i].search(new ArrayList<int[]>(searched), (i - 2) % 4, dist + 1, players);
			
			min = Math.min(min, searches[i]);
		}
		
		return min;
	}
}
