package monster.java.client.gui;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * Overlays a UI on the game screen
 * Called after game frame render
 * @author Rhys
 *
 */
public class GameOverlay {
	
	public float time;
	TrueTypeFont font;
	
	public GameOverlay() {
		time = 0;
		font = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), false);
	}
	
	public void updateTime() {
		time += 1F / 60F;
	}
	
	public void drawTime() {		
		Color.white.bind();

		GL11.glDisable(GL_TEXTURE_RECTANGLE_ARB);
		font.drawString(80, 0, String.format("%.02f", time), Color.white);	
		glEnable(GL_TEXTURE_RECTANGLE_ARB);	
	}
	
	public void drawWinners() {
		
	}

}
