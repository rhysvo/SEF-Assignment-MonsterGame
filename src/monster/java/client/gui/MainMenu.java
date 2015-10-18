package monster.java.client.gui;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import monster.java.server.MonsterServer;
import monster.java.server.net.NetworkPlayer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainMenu extends Application {

	

	private GameMenu gameMenu;




	public void begin() {
		launch();
		
	}

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane root = new Pane();
		root.setPrefSize(800, 600);

		InputStream is = Files.newInputStream(Paths.get("src/res/MenuBackground2.jpg"));
		Image img = new Image(is);
		is.close();

		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);

		gameMenu = new GameMenu();
		gameMenu.setVisible(true);

		root.getChildren().addAll(imgView, gameMenu);

		Scene scene = new Scene(root);
		
		/**
		 * Press Esc to Exit Menu
		 */
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				System.out.println("EXIT");
				System.exit(0);
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static class GameMenu extends Parent {

		public static String IPAdr = null;
		public static String playerNumbers = null;

		UIHandler uiHandler = new UIHandler(null);

		public GameMenu() {
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
			VBox menu2 = new VBox(10);
			VBox menu3 = new VBox(10);

			menu0.setTranslateX(270);
			menu0.setTranslateY(215);
			
			menu1.setTranslateX(100);
			menu1.setTranslateY(200);
			
			menu2.setTranslateX(100);
			menu2.setTranslateY(200);
			
			menu3.setTranslateX(250);
			menu3.setTranslateY(250);
			
			final int offset = 400;

			menu1.setTranslateX(offset);

			
			/**
			 * Menu 0
			 */
			MenuButton btnPlay = new MenuButton(" PLAY GAME");
			btnPlay.setOnMouseClicked(event -> {
				getChildren().add(menu1);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
                
				System.out.println("PLAY GAME");

			});	

			MenuButton btnHighScores = new MenuButton(" HIGHSCORES");
			btnHighScores.setOnMouseClicked(event -> {
				
				System.out.println("HIGHSCORES");
				this.uiHandler.showHighScores();

			});

			MenuButton btnExit = new MenuButton(" EXIT");
			btnExit.setOnMouseClicked(event -> {
				System.out.println("EXIT");
				System.exit(0);
			});

			
			/**
			 * Menu 1
			 */		
			Text ipHeading = new Text("Enter IP/Host of Server or localhost:");
			ipHeading.setFill(Color.WHITE);
			TextField IPAdress = new TextField();
			IPAdr = IPAdress.getText();

			//User Feedback
			final Label connectionStatus = new Label();
			
			MenuButton btnCreateGame = new MenuButton(" CREATE ONLINE GAME");
			btnCreateGame.setOnMouseClicked(event -> {
				
				System.out.println("CREATE ONLINE GAME");
				
			        if ((IPAdress.getText() != null && !IPAdress.getText().isEmpty())) {
						IPAdr = IPAdress.getText();
			        	
			        	
			        	getChildren().add(menu2);

		                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
		                tt.setToX(menu1.getTranslateX() - offset);

		                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
		                tt1.setToX(menu1.getTranslateX());

		                tt.play();
		                tt1.play();

		                tt.setOnFinished(evt -> {
		                    getChildren().remove(menu1);
		                });
			            
			        } else {
			            connectionStatus.setText("You have not entered an IP");
			        }			
			});	
			
			MenuButton btnJoinOnline = new MenuButton(" JOIN ONLINE GAME");
			btnJoinOnline.setOnMouseClicked(event -> {
				
				System.out.println("JOIN ONLINE GAME");
				
			        if ((IPAdress.getText() != null && !IPAdress.getText().isEmpty())) {
						IPAdr = IPAdress.getText();
						
			        	this.uiHandler.runClient();
			        	
			        	getChildren().add(menu3);

		                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
		                tt.setToX(menu1.getTranslateX() - offset);

		                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
		                tt1.setToX(menu1.getTranslateX());

		                tt.play();
		                tt1.play();

		                tt.setOnFinished(evt -> {
		                    getChildren().remove(menu1);
		                });
			            
			        } else {
			            connectionStatus.setText("You have not entered an IP");
			        }			
			});
			
			MenuButton btnBack1 = new MenuButton(" BACK");
			btnBack1.setOnMouseClicked(event -> {
				getChildren().add(menu1);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt.setToX(menu1.getTranslateX() + offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu1.getTranslateX());

				tt.play();
				tt1.play();

				tt.setOnFinished(evt -> {
					getChildren().remove(menu1);
				});
				System.out.println("BACK");
			});

			
			
			/**
			 * Menu 2
			 */
			Text playersHeading = new Text("How many players?");
			playersHeading.setFill(Color.WHITE);
			TextField numOfPlayers = new TextField();
			final Label connectionStatus2 = new Label();	
			Text connectionIP = new Text();
			connectionIP.setFill(Color.WHITE);
			
			MenuButton btnConnect = new MenuButton(" CONNECT");
			btnConnect.setOnMouseClicked(event -> {
				
				System.out.println("CONNECT");
				
				
			        if ((numOfPlayers.getText() != null && !numOfPlayers.getText().isEmpty())) {
			        	
			        	playerNumbers = numOfPlayers.getText();
			        	
			        	@SuppressWarnings("rawtypes")
						Task task = new Task<Void>() {
			        	    @Override public Void call() {
			        	    	MonsterServer.runServer();
			        	        
			        	        return null;
			        	    }
			        	};
			        	

			        	
			        	if (Integer.parseInt(playerNumbers) < 1)
			        	{
			        		connectionStatus2.setText("You must have at least one player");
			        	}
			        	else if (Integer.parseInt(playerNumbers) > 4)
			        	{
			        		connectionStatus2.setText("Maximum 4 players");
			        	}
			        	else if (Integer.parseInt(playerNumbers) == 1)
			        	{
			        		new Thread(task).start();
			        		this.uiHandler.runClient();
			        	}
			        	else
			        	{
			        		new Thread(task).start();

			        		this.uiHandler.runClient();
			        		
			        		connectionIP.setText("Other players can connect through: " + NetworkPlayer.socketID);
			        
			        		getChildren().add(menu3);

			                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
			                tt.setToX(menu2.getTranslateX() - offset);

			                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu3);
			                tt1.setToX(menu2.getTranslateX());

			                tt.play();
			                tt1.play();

			                tt.setOnFinished(evt -> {
			                    getChildren().remove(menu2);
			                });			        		
			        		
			        	}

			        	
			        } else {
			            connectionStatus.setText("You have not entered the number of players");
			        }			
			});	
			
			MenuButton btnBack2 = new MenuButton(" BACK");
			btnBack2.setOnMouseClicked(event -> {
				getChildren().add(menu1);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu1.getTranslateX() + offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu2.getTranslateX());

				tt.play();
				tt1.play();

				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
				System.out.println("BACK");
			});
			
			
			
			/**
			 * Menu 3
			 */
			Text waitingToJoinGame = new Text("Your game will start as soon as all \nplayers are connected");
			waitingToJoinGame.setFill(Color.WHITE);
			/* Connection IP is created in menu2 */

			
			menu0.getChildren().addAll(btnPlay, btnHighScores, btnExit);
			menu1.getChildren().addAll(ipHeading, IPAdress, btnCreateGame, btnJoinOnline,  btnBack1, connectionStatus);
			menu2.getChildren().addAll(playersHeading, numOfPlayers, btnConnect, connectionStatus2, btnBack2);
			menu3.getChildren().addAll(waitingToJoinGame, connectionIP);


			Rectangle window = new Rectangle(800, 600);
			window.setFill(Color.GREY);
			window.setOpacity(0.2);
			
			getChildren().addAll(window, menu0);
			
		}
	}

}
