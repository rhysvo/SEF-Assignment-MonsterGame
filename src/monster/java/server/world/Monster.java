package monster.java.server.world;

import java.util.ArrayList;

import monster.java.server.MonsterServer;
import monster.java.server.net.MessageProtocol;
import monster.java.server.net.NetworkPlayer;

public class Monster extends Entity {
	private Entity target;
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
		
		// Default if wall/error
		else 
			System.out.print("");
	}

	
	/**
	 * Selects the closest target using trigonometry
	 * @param players
	 * @return
	 */
	public Entity selectTarget(ArrayList<NetworkPlayer> players) {
		// Create temporary int for comparisons
		int temp = 32;
		
		for(int i = 0; i < players.size(); i++) {
			// Create player Entity
			Entity player = players.get(i).getPlayer();
			
			// Create & initialise check integers for x & y
			int cx = findTargetDistX(player), cy = findTargetDistY(player);
			
			// Check the distance to current target
			int dist = (int) Math.floor(Math.sqrt(cx * cx + cy * cy));
			
			// Check if player distance less than current target
			if(dist < temp) {
				target = player;
				temp = dist;
			}
		}
		
		// Return the selected target Entity
		return target;
	}
	
	/**
	 * Move monster to the given target
	 * @param target
	 */
	public void moveToTarget(Entity target) {
		// Set monster coords
		int mx = this.X(), my = this.Y();
		
		// Set target coords
		int tx = target.X(), ty = target.Y();
		
		// Move monster left
		if(tx < mx)
			moveLeft(findTargetDistX(target));
		
		// Move monster right
		else
			moveRight(findTargetDistX(target));
		
		// Move monster up
		if(ty < my)
			moveUp(findTargetDistY(target));
		
		// Move monster down
		else
			moveDown(findTargetDistY(target));
	}

	/**
	 * Returns the X distance that Monster must travel
	 * @param player
	 * @return
	 */
	public int findTargetDistX(Entity player) {
		// Returns positive value of X distance to target
		return Math.abs(player.X() - this.X());
	}

	/**
	 * Returns Y distance that Monster must travel
	 * @param player
	 * @return
	 */
	public int findTargetDistY(Entity player) {
		// Returns positive value of Y distance to target
		return Math.abs(player.Y() - this.Y());
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

		// Make the Monster wait till ending move sequence for debugging
		try {
			Thread.sleep(Math.max(155, MonsterServer.MON_TICK));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the monster up the board
	 * 
	 * @param num
	 */
	private void moveUp(int num) {
		// Assign target current position 'y'
		int cy = target.Y();
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check if current target moved
			if (cy != target.Y())
				break;
				
			// Check monster doesn't exceed boundary
			if (this.y <= 0)
				break;

			// Move the monster up
			monsterMove(0, -1);
		}
		
		// Output direction and number
		if(num != 0)
			System.out.println("mUP: " + num);
	}

	/**
	 * Moves the monster down the board
	 * 
	 * @param num
	 */
	private void moveDown(int num) {
		// Assign target current position 'y'
		int cy = target.Y();
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check if current target moved
			if (cy != target.Y())
				break;
			
			// Check monster doesn't exceed boundary
			if (this.y >= worldSize)
				break;

			// Move the monster down
			monsterMove(0, 1);
		}
		
		// Output direction and number
		if(num != 0)
			System.out.println("mDN: " + num);
	}

	/**
	 * Moves the monster to the left
	 * 
	 * @param num
	 */
	private void moveLeft(int num) {
		// Assign target current position 'x'
		int cx = target.X();
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check if current target moved
			if (cx != target.X())
				break;
			
			// Check monster doesn't exceed boundary
			if (this.x <= 0)
				break;

			// Move the monster left
			monsterMove(-1, 0);
		}
		
		// Output direction and number
		if(num != 0)
			System.out.println("mLE: " + num);
	}

	/**
	 * Moves the monster to the right
	 * 
	 * @param num
	 */
	private void moveRight(int num) {
		// Assign target current position 'x'
		int cx = target.X();
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check if current target moved
			if (cx != target.X())
				break;
			
			// Check monster doesn't exceed boundary
			if (this.x >= worldSize)
				break;

			// Move the monster right
			monsterMove(1, 0);
		}
		
		// Output direction and number
		if(num != 0)
			System.out.println("mRI: " + num);
	}
	
	/* * * DEBUGGING CODE BELOW * * */
	
	public void outputDetails() {
		System.out.printf("Monster, x: %d y: %d\n", this.x, this.y);
		System.out.printf("Target, x: %d y: %d\n\n", target.X(), target.Y());
	}
	
	private void sleep(int n) {
		try {
			Thread.sleep(n*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
