package monster.java.client.gui;

import monster.java.client.MonsterGame;

public class MainMenu extends Menu {

	public MainMenu(UIHandler uiHandler) {
		super(uiHandler);
	}

	@Override
	protected void processButtonValue(String value) {
		switch (value) {
		
		case "join_game":
			this.uiHandler.showLobby();
			break;
			
		case "view_hs":
			this.uiHandler.showHighScores();
			break;
			
		case "local_test":
			this.uiHandler.localTest();
			break;
			
		default:
			break;
		}
	}
	
	@Override
	protected boolean startCommandLine() {

		boolean exit = false;
		int option = 0;
		String action = "";
		
		do {
		
			// print menu
			System.out.println(
					  "=================\n"
					+ " == Main Menu == \n"
					+ "=================\n"
					+ "1. Join a game\n"
					+ "2. View highscores\n"
					+ "3. Local Test\n"
					+ "0. Exit"
			);
			
			System.out.print("Please enter your choice (0-2): ");
			
			// sanitize input
			try {
				option = Integer.parseInt(MonsterGame.instance.sc.nextLine());
				if (option < 0 || option > 3) 
					throw new NumberFormatException();
				exit = true;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number from 0-3.");
			}
				
		} while (!exit);
		
		// change option into action string
		// only temporary, will get string from button
		// once GUI is implemented
		switch(option) {
		
		case 1:
			action = "join_game";
			break;
		
		case 2:
			action = "view_hs";
			break;
			
		case 3:
			action = "local_test";
			break;
		
		default:
			System.exit(0);
		}
		
		this.processButtonValue(action);
		return true;
	}

}
