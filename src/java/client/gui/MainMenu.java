package java.client.gui;

import java.util.Scanner;

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
			
		default:
			break;
		}
	}

	@Override
	protected void startCommandLine() {

		boolean exit = false;
		int option = 0;
		String action = "";
		
		Scanner input = new Scanner(System.in);
		
		do {
		
			System.out.println(
					" == Main Menu == \n"
					+ "================="
					+ "1. Join a game\n"
					+ "2. View highscores\n"
					+ "0. Exit"
			);
			
			try {
				option = Integer.parseInt(input.nextLine());
				if (option < 0 || option > 2) 
					throw new NumberFormatException();
				exit = true;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number from 0-2.");
			}
				
		} while (!exit);
		
		input.close();
		
		switch(option) {
		
		case 1:
			action = "join_game";
			break;
		
		case 2:
			action = "view_hs";
			break;
		
		default:
			System.exit(0);
		}
		
		this.processButtonValue(action);
	}

}
