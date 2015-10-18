package monster.java.client.gui;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import monster.java.client.MonsterGame;

/**
 * Overlays a UI on the game screen
 * Called after game frame render
 * @author Alex
 *
 */
public class GameOverlay {
	
	public float time;
	TrueTypeFont font;
	public String data = null;
	public int size;
	
	public GameOverlay(int size) {
		time = 0;
		font = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), false);
		this.size = size * MonsterGame.TILE_SIZE;
	}
	
	/**
	 * Update the time. Assumes 60fps
	 */
	public void updateTime() {
		time += 1F / 60F;
	}
	
	/**
	 * Draw the current survived time on the screen
	 */
	public void drawTime() {		
		Color.white.bind();

		// arb textures need to be disabled to draw fonts for some
		// dumb reason >:(
		GL11.glDisable(GL_TEXTURE_RECTANGLE_ARB);
		font.drawString(80, 0, String.format("%.02fs", time), Color.white);	
		glEnable(GL_TEXTURE_RECTANGLE_ARB);	
	}
	
	/**
	 * Set the win data, will be called after game over
	 * @param data
	 */
	public void setWinData(String data) {
		this.data = data;
	}
	
	/**
	 * Draw the scores at the end of the game
	 */
	public void drawWinners() {
		if (data == null)
			return;
		
		GL11.glDisable(GL_TEXTURE_RECTANGLE_ARB);
		
		int x = size / 3, y = 80, i = 1;
		for (String player : data.split(",")) {
			String[] temp = player.split(":");
			String name = temp[0];
			String time = temp[1];
			
			font.drawString(x, y, i + ". " + name + " : " + time + "s", Color.black);
			
			y += 80;
			i++;
		}
		glEnable(GL_TEXTURE_RECTANGLE_ARB);	
	}

}
