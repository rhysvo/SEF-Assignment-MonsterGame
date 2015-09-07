package monster.java.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

/**
 * The Interface that the user interacts with, control
 * navigation and will be the first point of contact.
 * 
 * Also acts as a superclass to other GUI elements
 * @author Alex Matheson
 *
 */
public abstract class Menu {
	
	private List<Button> buttons = new ArrayList<Button>();
	private int selected = 0;
	protected UIHandler uiHandler;
	
	// private Texture background = TextureLoader.get('../res/menu_bg.png');
	
	protected Menu(UIHandler uiHandler) {
		this.uiHandler = uiHandler;
	}
	
	/**
	 * Draw the menu background and all the buttons on it.
	 * Draws button with the index _selected_ with it's selected texture
	 * variant.
	 */
	public void draw() {
		// lwjgl draw function for background
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(i == selected);
		}
	}
	
	/**
	 * Add a button to the menu.
	 * Assumes buttons are added from top -> bottom, left -> right
	 * for keyboard navigation purposes.
	 * @param button to add
	 */
	public void addButton(Button button) {
		this.buttons.add(button);
	}
	
	/**
	 * Add a button at a specific index.
	 * @param index
	 * @param button
	 */
	public void addButton(int index, Button button) {
		this.buttons.add(index, button);
	}
	
	/**
	 * Handles all keyboard and mouse inputs in this menu
	 * @param delta
	 */	
	public void pollInput(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) 
				|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			
			selected++;
			
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP) 
				|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			
			selected--;
		
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			
			this.processButtonValue(buttons.get(selected).getValue());
			
		} else {
			
			for (int i = 0; i < buttons.size(); i++) {	
				Button button = buttons.get(i);				
				if (button.isMouseOver(1, 1)) {
					this.selected = i;
					if (true /* mouse is down */) {
						this.processButtonValue(button.getValue());
					}
				}				
			}			
		}
	}
	
	/**
	 * Process the value from a button to determine what to do next
	 * @param value from button
	 */
	protected abstract void processButtonValue(String value);

	/**
	 * Run this menu from the command line, taking input numbers.
	 * Will be deprecated once GUI is finished.
	 */
	protected abstract void startCommandLine();
	
}
