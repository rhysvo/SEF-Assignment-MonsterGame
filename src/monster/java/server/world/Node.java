package monster.java.server.world;

public class Node {
	int x, y;
	
	Node[] adj = new Node[4];
	
	boolean wall;
	
	public Node(int x, int y, boolean wall) {
		this.x = x;
		this.y = y;
		this.wall = wall;
	}
	
	public int search(int from, int dist) {
		
		return 0;
	}

}
