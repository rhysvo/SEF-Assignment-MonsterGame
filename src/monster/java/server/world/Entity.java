package monster.java.server.world;

public class Entity {
	
	protected int x, y;
	private String name;
	
	public Entity() {
		// TODO
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

}
