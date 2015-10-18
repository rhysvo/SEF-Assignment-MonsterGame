package monster.java.server.world;

import java.util.ArrayList;

import monster.java.server.net.MessageProtocol;
import monster.java.server.net.NetworkPlayer;

public class Monster extends Entity {
	private int worldSize;

	public Monster(String[] world) {
		this.worldSize = world.length;
		
		Node.init(world);
		
		MessageProtocol.sendMonsterMove(this.x, this.y);
	}
	
	/**
	 * Sets the position of monster
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y) {
		// Set monster position to given coords
		super.setPos(x, y);
		
		// Broadcast monster coords to players
		MessageProtocol.sendMonsterMove(x, y);
	}
	
	/**
	 * Finds closest player and moves one step towards player
	 * @param players
	 */
  	public void moveToPlayer(ArrayList<NetworkPlayer> players) {
  		// Get direction of closest player
		int d = Node.getNode(Node.nodes, x, y).beginSearch(players);
		
		// Move up
		if(d == 0)
			monsterMove(0, -1);
		
		// Move right
		else if(d == 1)
			monsterMove(1, 0);
		
		// Move down
		else if(d == 2)
			monsterMove(0, 1);
		
		// Move left
		else if(d == 3)
			monsterMove(-1, 0);
	}
  	
	/**
	 * Moves the monster around the board
	 * 
	 * @param x
	 * @param y
	 */
	private void monsterMove(int x, int y) {
		if(this.x + x > worldSize || this.y + y > worldSize)
			return;
		
		if(this.x + x < 0 || this.y + y < 0)
			return;
		
		// Add x, y values to Monster position
		this.addPos(x, y);
		
		// Transmit Monster location to clients
		MessageProtocol.sendMonsterMove(this.x, this.y);
	}
}
