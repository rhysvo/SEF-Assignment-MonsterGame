package monster.java.client.gui;

public class Button {
	
	private int x, y;
	private int w, h;
	private String value;
	@SuppressWarnings("unused")
	private String text;

	// private Texture tx = TextureLoader.get('../res/button.png');
	// private Texture tx_sel = TextureLoader.get('../res/button_sel.png');
	
	/**
	 * 
	 * @param x coordinate (pixels)
	 * @param y coordinate (pixels)
	 * @param w width (pixels)
	 * @param h height (pixels)
	 * @param text to display
	 * @param value return value
	 */
	public Button(int x, int y, int w, int h, String text, String value) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.text = text;
		this.value = value;
	}
	
	/**
	 * Draw the button with appropriate coordinates and sizes
	 * If selected is true, button will use selected texture.
	 * @param selected
	 */
	public void draw(boolean selected) {
		/* 
		 * Draw button beginning at x, y and ending at x + w, y + h
		 * with the text positioned at 
		 * (x + w/2 - text_w/2), (y + w/2 - text_h/2)
		 * if not selected, use tx texture; else tx_sel texture
		 */
	}
	
	/**
	 * Get the return string for checking
	 * @return string return value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Check if the mouse is hovering over this button
	 * @param mouseX
	 * @param mouseY
	 * @return true if mouse is over
	 */
	public boolean isMouseOver(int mouseX, int mouseY) {
		return mouseX > x && mouseX < x + w
				&& mouseY > y && mouseY < y + h;
	}

}
