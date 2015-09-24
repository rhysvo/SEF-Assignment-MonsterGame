package monster.java.server.world;

import java.util.ArrayList;

import monster.java.server.net.NetworkPlayer;

public class Node {
	// Coords of Node
	private int x, y;
	
	// Denotes if Node is a wall
	private boolean wall;
	
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
		try {
			adj[n] = node;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param players
	 * @return
	 */
	public int begin_search(ArrayList<NetworkPlayer> players) {
		int[] searches = {999, 999, 999, 999};
		int min = 0;
		
		for (int i = 0; i < searches.length; i++) {
			try {
				searches[i] = adj[i].search((i-2) % 4, 1, players);
			} catch(NullPointerException a) {
				continue;
			} catch(ArrayIndexOutOfBoundsException b) {
				b.printStackTrace();
			}
			
			min = Math.min(min, searches[i]);
		}
		
		for(int i = 0; i < searches.length; i++)
			System.out.println(searches[i]);
		
		for(int i : searches)
			if(min == i)
				return i;
		
		return -1;
	}
	
	/**
	 * 
	 * @param from
	 * @param dist
	 * @param players
	 * @return
	 */
	private int search(int from, int dist, ArrayList<NetworkPlayer> players) {
		if(wall || dist > 50)
			return 999;
		
		for(NetworkPlayer player : players) {
			if(x == player.getPlayer().X() && y == player.getPlayer().Y()) {
				System.out.printf("\nFOUND PLAYER in %d steps!\n", dist);
				return dist;
			}
		}
		
		int[] searches = {999, 999, 999, 999};
		int min = 0;
		
		for(int i = 0; i < 4; i++) {
			if(i != from) {
				if(adj[i] != null)
					searches[i] = adj[i].search((i - 2) % 4, dist + 1, players);
				
				try {
					Thread.sleep(0);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			min = Math.min(min, searches[i]);
		}
		
		return min;
	}
	
	

}
