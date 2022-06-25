package arcade;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameBoard extends Application implements EventHandler<KeyEvent> {
	
	private int frames = 3600;

	private class Timer extends AnimationTimer {
		private int FRAMES_PER_SEC = 60;
		private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
		private long last = 0;

		@Override
		public void handle(long now) {
			frames--;
			
			if (pacman.getScore() >= 40) {
				gameOver("YOU WON! Score = 40");
			}
			
			if (frames == 0) {
				//timer.stop();
				gameOver("Time up!");
			}
			
			if (frames % FRAMES_PER_SEC == 0) {
				setTimeDisplay(frames/(int)FRAMES_PER_SEC);
			}
			
			if (now - last > INTERVAL) {
				pacman.act();
				
				int random = (int)(Math.random()*500);
				if(random < 2) {
					for (Actor[] actors: world) {
						for (Actor actor: actors) {
							if (actor != null && actor.getClass().equals(Coin.class)) {								
								actor.act();
							}
						}
					}					
				}
				
				last = now;
			}
		}
	}

	private Timer timer; // object of the AnimationTimer private class

	private final String GHOST_VALUE = "G";
	private final String WALL_VALUE = "W";
	private final String PACMAN_VALUE = "P";
	private final String COIN_VALUE = "C";
	private static final int NUM_GHOSTS = 2;

	// Grid Pane properties

	private static int numBlocks = 19;
	private static int blockSize = 25;

	private final int GRID_OFFSET = 20;
	
	private static int pacmanSize = 20;
	// loads images
		//private Image ghostImage = new Image("resources/PacmanGhost.png", pacmanSize, pacmanSize, false, false);
		private Image wallImage = new Image("resources/PacmanWall.png");
		private Image pacmanImage = new Image("resources/Pacman0.png", pacmanSize, pacmanSize, false, false);
		private Image pacman90 = new Image("resources/Pacman90.png", pacmanSize, pacmanSize, false, false);
		private Image pacman180 = new Image("resources/Pacman180.png", pacmanSize, pacmanSize, false, false);
		private Image pacman270 = new Image("resources/Pacman270.png", pacmanSize, pacmanSize, false, false);
		private Image coinImage = new Image("resources/PacmanCoin.png");

	// Game Properties
	private final int SCREEN_SIZE = numBlocks * blockSize;
	private Pacman pacman;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Text score;
	private Text timeDisplay;

	// Main Data Structures
	// private ImageView[][] gameBoard = new ImageView[numBlocks][numBlocks];
	private Actor[][] world = new Actor[numBlocks][numBlocks];
	private String arr[][] = new String[numBlocks][numBlocks];

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Pacman");
		Group root = new Group();

		// Pacman Grid - Walls, Coins, Pacman
		initializeInputArray();
		initializeActorArray(root);
		root.getChildren().add(pacman);
//		root.getChildren().addAll(enemies);
		
		score = new Text();
		timeDisplay = new Text();
		
		setScore(0);
		setTimeDisplay(0);
		root.getChildren().add(score);
		root.getChildren().add(timeDisplay);

		// Stage & Scene
		Scene scene = new Scene(root, numBlocks * blockSize, numBlocks * blockSize + 30);
		scene.setFill(Color.BLACK);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

		root.setOnKeyPressed(this);
		root.setOnKeyReleased(this);
		root.requestFocus();
		
		// TIMER
		timer = new Timer();
		timer.start();
	}

	public Actor[][] getWorld() {
		return world;
	}

	public void initializeInputArray() {
		File f = new File("bin/resources/PacmanLevel1.txt");
		Scanner sc;
		try {
			sc = new Scanner(f);
			for (int i = 0; i < numBlocks; i++) {
				for (int j = 0; j < numBlocks; j++) {
					arr[i][j] = sc.next();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("World is ending!");
		}

	}
	
	public void printArray() {
		for (int row = 0; row < numBlocks; row++) {
			for (int column = 0; column < numBlocks; column++) {
				System.out.println(arr[row][column]);
			}
		}
	}

	public void initializeActorArray(Group root) {
		for (int row = 0; row < arr.length; row++) {
			for (int column = 0; column < arr.length; column++) {
				if (arr[row][column].equals(WALL_VALUE)) {
					world[row][column] = new WallBlock(column * blockSize, row * blockSize);
					root.getChildren().add(world[row][column]);
				} else if (arr[row][column].equals(COIN_VALUE)) {
					world[row][column] = new Coin(column * blockSize, row * blockSize);
					root.getChildren().add(world[row][column]);
				} else if (arr[row][column].equals(PACMAN_VALUE)) {
					pacman = new Pacman(this, column * blockSize, row * blockSize);
					world[row][column] = null;
				} else if (arr[row][column].equals(GHOST_VALUE)) { 
					world[row][column] = null;
					Enemy e = new Enemy(this, column * blockSize, row * blockSize);
					enemies.add(e);
				} else {
					world[row][column] = null;
				}
			}
		}
	}
	
	@Override
	public void handle(KeyEvent e) {
		pacman.react(e);
	}
	
	public void setScore(int newScore) {
		score.setText("Score: " + newScore + "");
		score.setX(10);
		score.setY(numBlocks*blockSize + 22);
		score.setFill(Color.WHITE);
		score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
	}
	
	public void setTimeDisplay(int seconds) {
		int minutes = seconds/60;
		int secondsLeft = seconds-(60 * minutes);
		String timeText = minutes + ":";
		if (secondsLeft < 10) timeText += "0" + secondsLeft;
		else timeText += secondsLeft;
		timeDisplay.setText(timeText);
		timeDisplay.setX((numBlocks-2)*blockSize +5);
		timeDisplay.setY(numBlocks*blockSize + 22);
		timeDisplay.setFill(Color.WHITE);
		timeDisplay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
	}

	public void gameOver(String message) {
		timer.stop();
		score.setText("Score: " + pacman.getScore() + " --- " + /*" ---GAME OVER--- " +*/ message);
	}

}
