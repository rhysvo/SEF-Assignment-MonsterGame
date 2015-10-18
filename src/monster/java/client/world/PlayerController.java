package monster.java.client.world;
/**
 * Allows control of the local player
 * @author Rhys
 *
 */
public class PlayerController {
	
	private Entity player;	
	
	public PlayerController(Entity player) {
		this.player = player;
	}
	
	public boolean moveUp(){
		player.update(0, -1);
		return true;
	}
	
	public boolean moveDown(){
		player.update(0, 1);
		return true;
	}
	
	public boolean moveLeft(){
		player.update(-1, 0);
		return true;
	}
	
	public boolean moveRight(){
		player.update(1, 0);
		return true;
	}

	public Object getPlayer() {
		return player;
	}
}
