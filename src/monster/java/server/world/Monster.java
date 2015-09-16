package monster.java.server.world;

import java.util.ArrayList;

import monster.java.server.MonsterServer;
import monster.java.server.net.MessageProtocol;
import monster.java.server.net.NetworkPlayer;

public class Monster extends Entity {
	private int tx, ty;

	public Monster() {
		MessageProtocol.sendMonsterMove(this.x, this.y);
	}

	public void setPos(int x, int y) {
		super.setPos(x, y);
		MessageProtocol.sendMonsterMove(x, y);
	}

	public void outputDetails() {
		System.out.printf("Monster, x: %d y: %d\n", this.x, this.y);
		System.out.printf("Target, x: %d y: %d\n\n", tx, ty);
	}

	public int selectTarget(ArrayList<NetworkPlayer> player) {
		
		for (int i = 0; i < player.size(); i++) {
			tx = findTargetX(player.get(i).getPlayer());
			ty = findTargetY(player.get(i).getPlayer());
		}
		
		return 0;
	}

	public int findTargetX(Entity player) {
		return Math.abs(player.X() - this.X());
	}

	public int findTargetY(Entity player) {
		return Math.abs(player.Y() - this.Y());
	}

	/**
	 * Moves the monster around the board
	 * 
	 * @param x
	 * @param y
	 */
	private void monsterMove(int x, int y) {
		// Add x, y values to Monster position
		this.addPos(x, y);
		
		// Transmit Monster location to clients
		MessageProtocol.sendMonsterMove(this.x, this.y);

		// Make the Monster wait till ending move sequence
		try {
			Thread.sleep(MonsterServer.MON_TICK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Moves the monster up the board
	 * 
	 * @param num
	 */
	public void moveUp(int num) {
		// Output direction and number
		System.out.println("mUP: " + num);
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check monster doesn't exceed boundary
			if (this.y <= 0)
				break;

			// Move the monster up
			monsterMove(0, -1);
		}
	}

	/**
	 * Moves the monster down the board
	 * 
	 * @param num
	 */
	public void moveDown(int num) {
		// Output direction and number
		System.out.println("mDN: " + num);
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check monster doesn't exceed boundary
			if (this.y >= 15)
				break;

			// Move the monster down
			monsterMove(0, 1);
		}
	}

	/**
	 * Moves the monster to the left
	 * 
	 * @param num
	 */
	public void moveLeft(int num) {
		// Output direction and number
		System.out.println("mLE: " + num);
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check monster doesn't exceed boundary
			if (this.x <= 0)
				break;

			// Move the monster left
			monsterMove(-1, 0);
		}
	}

	/**
	 * Moves the monster to the right
	 * 
	 * @param num
	 */
	public void moveRight(int num) {
		// Output direction and number
		System.out.println("mRI: " + num);
		
		// Movement loop
		for (int i = 0; i < num; i++) {
			// Check monster doesn't exceed boundary
			if (this.x >= 15)
				break;

			// Move the monster right
			monsterMove(1, 0);
		}
	}
}
