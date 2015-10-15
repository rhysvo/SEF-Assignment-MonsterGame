package monster.java.server;

import org.junit.Test;

public class JUnit {
	@Test
	public void monsterInDomain() {
		// Verify that monster does not move outside the grid
	}
	
	@Test
	public void monsterMoveToPlayer() {
		// Verify that monster always moves towards nearest player
	}
	
	@Test
	public void gameStartWithMaxPlayers() {
		// Verify that the game does not start until the specified number of players join
	}
	
	@Test
	public void gameStopSinglePlayer() {
		// Verify that the game stops after all players are dead
	}
}
