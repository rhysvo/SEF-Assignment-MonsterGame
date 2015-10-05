package monster.java.client.gui;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import javafx.animation.TranslateTransition;
import javafx.application.Application;

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
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainMenu extends Application {

	

	private GameMenu gameMenu;




	public void startCommandLine() {
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
		
		UIHandler uiHandler = new UIHandler(null);

		public GameMenu() {
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
			VBox menu2 = new VBox(10);

			menu0.setTranslateX(270);
			menu0.setTranslateY(215);

			menu1.setTranslateX(100);
			menu1.setTranslateY(200);
			
			menu2.setTranslateX(100);
			menu2.setTranslateY(200);


			final int offset = 400;

			menu1.setTranslateX(offset);

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

			MenuButton btnBack = new MenuButton(" BACK");
			btnBack.setOnMouseClicked(event -> {
				getChildren().add(menu0);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt.setToX(menu1.getTranslateX() + offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu1.getTranslateX());

				tt.play();
				tt1.play();

				tt.setOnFinished(evt -> {
					getChildren().remove(menu1);
				});
				System.out.println("BACK");
			});
			
			MenuButton btnLocalTest = new MenuButton(" LOCAL TEST");
			btnLocalTest.setOnMouseClicked(event -> {
				
				System.out.println("LOCAL TEST");
				this.uiHandler.localTest();
				
			});
			
			MenuButton btnOnline = new MenuButton(" ONLINE GAME");
			btnOnline.setOnMouseClicked(event -> {
				
				
				System.out.println("ONLINE GAME");
				
				
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

			});
					

			Label label1 = new Label("Enter IP/Host of Server:");
			
			TextField IPAdress = new TextField();
			IPAdr = IPAdress.getText();

			//User Feedback
			final Label label = new Label();
			

			MenuButton btnJoinOnline = new MenuButton(" JOIN ONLINE GAME");
			btnJoinOnline.setOnMouseClicked(event -> {
				
				System.out.println("JOIN ONLINE GAME");
				
				
			        if ((IPAdress.getText() != null && !IPAdress.getText().isEmpty())) {
			        	
			        	label.setText("SUCCES");
			        	this.uiHandler.showLobby();
			            
			        } else {
			            label.setText("You have not entered an IP");
			        }			
			});
			
			
			MenuButton btnBack1 = new MenuButton(" BACK");
			btnBack1.setOnMouseClicked(event -> {
				getChildren().add(menu1);

				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu2.getTranslateX() + offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu2.getTranslateX());

				tt.play();
				tt1.play();

				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
				System.out.println("BACK");
			});


			menu0.getChildren().addAll(btnPlay, btnLocalTest, btnHighScores, btnExit);
			menu1.getChildren().addAll(btnOnline, btnLocalTest, btnBack);
			menu2.getChildren().addAll(label1, IPAdress, btnJoinOnline,  btnBack1, label);

			Rectangle bg = new Rectangle(800, 600);
			bg.setFill(Color.GREY);
			bg.setOpacity(0.2);

			getChildren().addAll(bg, menu0);
		}
	}

}
