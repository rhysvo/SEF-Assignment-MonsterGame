package monster.java.client.util;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;

import de.matthiasmann.twl.utils.PNGDecoder;

/**
 * Loads textures into a HashMap from
 * a sprite sheet (?), which can be accessed
 * globally. 
 * @author Alex
 *
 */
public class TextureLoading {

	public int spritesheet;
	public final Map<String, Sprite> spriteMap = new HashMap<String, Sprite>();
	private final String SPRITESHEET_IMAGE_LOCATION = "src/res/textures/spritesheet.png";

	// Reads in the sprite sheet from file
	public static int glLoadTextureLinear(String location) {
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texture);
		InputStream in = null;
		try {
			in = new FileInputStream(location);
			PNGDecoder decoder = new PNGDecoder(in);
			ByteBuffer buffer = BufferUtils.createByteBuffer(
					4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4,
					PNGDecoder.Format.RGBA);
			buffer.flip();
			glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER,
					GL_NEAREST);
			glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER,
					GL_NEAREST);
			glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA,
					decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA,
					GL_UNSIGNED_BYTE, buffer);
		} catch (FileNotFoundException e) {
			System.err.println("Texture file could not be found.");
			return -1;
		} catch (IOException e) {
			System.err.print("Failed to load the texture file.");
			return -1;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
		return texture;

	}

	public TextureLoading() {
		spritesheet = TextureLoading
				.glLoadTextureLinear(SPRITESHEET_IMAGE_LOCATION);

		Sprite monster = new Sprite("monster", 0, 0);
		spriteMap.put("monster", monster);

		Sprite p1 = new Sprite("p1", 0, 32);
		spriteMap.put("p1", p1);

		Sprite p2 = new Sprite("p2", 0, 64);
		spriteMap.put("p2", p2);

		Sprite p3 = new Sprite("p3", 32, 0);
		spriteMap.put("p3", p3);

		Sprite p4 = new Sprite("p4", 64, 0);
		spriteMap.put("p4", p4);

		Sprite wall = new Sprite("wall", 32, 32);
		spriteMap.put("wall", wall);

		Sprite empty = new Sprite("empty", 32, 64);
		spriteMap.put("empty", empty);
	}

	public Sprite getSprite(String key) {
		return spriteMap.get(key);
	}

}
