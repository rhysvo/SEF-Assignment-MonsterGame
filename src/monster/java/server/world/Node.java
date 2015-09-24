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
	
	// 2d Node Array
	public static Node[][] nodes;
	
	public Node(int x, int y, boolean wall) {
		this.x = x;
		this.y = y;
		this.wall = wall;
	}
	
	public static void init(String[] world) {
		nodes = new Node[world.length][world.length];
		
		// Create all nodes
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[i].length(); j++) {
				nodes[i][j] = new Node(j, i, world[i].charAt(j) == '#');
			}
		}
		
		// Add all adjacent nodes
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[i].length(); j++) {
				nodes[i][j].add_adjs(nodes);
			}
		}
	}
	
	/**
	 * 
	 * @param nodes
	 * @param x
	 * @param y
	 * @return
	 */
	public static Node get_node(Node[][] nodes, int x, int y) {
		for(Node[] na : nodes) {
			for(Node n : na) {
				if(n.x == x && n.y == y)
					return n;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 */
	private void add_adjs(Node[][] nodes) {
		add_adj(0, get_node(nodes, x, y - 1));
		add_adj(1, get_node(nodes, x + 1, y));
		add_adj(2, get_node(nodes, x, y + 1));
		add_adj(3, get_node(nodes, x - 1, y));
	}
	
	/**
	 * 
	 * @param n
	 * @param node
	 */
	private void add_adj(int n, Node node) {
		adj[n] = node;
		// add to numAdj if node is a valid move
		if (node != null && !node.wall)
			numAdj++;
	}
	
	/**
	 * 
	 * @param players
	 * @return
	 */
	public int begin_search(ArrayList<NetworkPlayer> players) {
		int[] searches = {999, 999, 999, 999};
		int min = 999;
		
		for (int i = 0; i < searches.length; i++) {
			if(adj[i] != null)
				searches[i] = adj[i].search(new ArrayList<int[]>(), (i-2) % 4, 1, players);
			
			min = Math.min(min, searches[i]);
		}
		
		// TODO TODO TODO
		// TODO TODO TODO
		
		// where the problem was, Kyle
		// in the for (int i : searches) loop
		// you were checking if min == i, then returning i
		// effectively returning min
		
		// TODO TODO TODO
		// TODO TODO TODO
		for(int i = 0; i < searches.length; i++)
			if(min == searches[i])
				return i;
		
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			
		}
		
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
		// if node is a fork (more than 2 adj, i.e. 2 directions)
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
			if(x == player.getPlayer().X() && y == player.getPlayer().Y()) {
				System.out.printf("\nFOUND PLAYER in %d steps!\n", dist);
				return dist;
			}
		}
		
		int[] searches = {999, 999, 999, 999};
		int min = 999;
		
		for(int i = 0; i < 4; i++) {
			if(i != from && adj[i] != null)
				// new ArrayList<int[]>(searched) -> copies the arraylist.
				// otherwise the original list would be huge and slow.
				searches[i] = adj[i].search(new ArrayList<int[]>(searched), (i - 2) % 4, dist + 1, players);
			
			min = Math.min(min, searches[i]);
		}
		
		return min;
	}
	
	

}
