package monster.java.client.util;

public final class Sprite {
	private final String name;
    private final int x;
    private final int y;

    public Sprite(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sprite sprite = (Sprite) o;

        if (x != sprite.x) {
            return false;
        }
        if (y != sprite.y) {
            return false;
        }
        if (name != null ? !name.equals(sprite.name) : sprite.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
