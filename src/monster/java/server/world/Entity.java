package monster.java.server.world;

public class Entity {
	
	protected int x, y;
	private int rank;
	private String name;
	private boolean alive;
	
	public Entity() {
		this.alive = true;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void addPos(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int X() {
		return this.x;
	}
	
	public int Y() {
		return this.y;
	}

	public void kill() {
		this.alive = false;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setRank(int r) {
		this.rank = r;
	}
	
	public int getRank() {
		return this.rank;
	}

}
