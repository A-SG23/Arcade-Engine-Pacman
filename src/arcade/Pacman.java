package arcade;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Pacman extends Actor {

	private static Image pacman0 = new Image("resources/Pacman0.png");
	private static Image pacman90 = new Image("resources/Pacman90.png");
	private static Image pacman180 = new Image("resources/Pacman180.png");
	private static Image pacman270 = new Image("resources/Pacman270.png");

	private int score = 0;
	private int direction; // E=1, N=2, W=3, S=4
	private boolean isBlocked = false;
	
	public Pacman(GameBoard gameBoard, int xPos, int yPos) {
		super(xPos, yPos);
		// orientation = 0;
		this.gameBoard = gameBoard;
		this.setImage(pacman0);
		direction = 1;	//initially moves right		
	}
	
	public void move(int distance) throws Exception {

		int xIndex = xPos/25;
		int yIndex = yPos/25;
		boolean inXChannel = yPos % 25 <3 || yPos % 25 >22;
		boolean inYChannel = xPos % 25 <3 || xPos % 25 >22;
				
		if(isBlocked) return;
		Actor[][] world = gameBoard.getWorld();
		
		// Detect collision with Wall
		if(direction==1 && inXChannel && !(world[yIndex][xIndex+1] instanceof WallBlock)) {
			xPos += distance;
		} else if (direction == 2 && inYChannel && !(world[yIndex][xIndex] instanceof WallBlock)) {
			yPos-= distance;
		}else if (direction==3 && inXChannel && !(world[yIndex][xIndex] instanceof WallBlock)) {
			xPos-= distance;
		}else if (direction==4 && inYChannel && !(world[yIndex+1][xIndex] instanceof WallBlock)) {
			yPos+=distance;
		} else {		
//			System.out.printf("(%d, %d)-(%b, %b)", yPos, xPos, inXChannel, inYChannel);
			isBlocked = true;
		}
		
		// Detect collision with Coin
		if(world[yIndex][xIndex] instanceof Coin && ((Coin)world[yIndex][xIndex]).isSpecial()) {
			Coin c = (Coin) world[yIndex][xIndex];
			world[yIndex][xIndex] = null;
			c.imageProperty().set(null);
			//c.act();
			score += 2;
			//play a sound effect?
		} else if (world[yIndex][xIndex] instanceof Coin && ((Coin)world[yIndex][xIndex]).isDangerous()) {
			Coin c = (Coin) world[yIndex][xIndex];
			world[yIndex][xIndex] = null;
			c.imageProperty().set(null);
			//c.act();
			// TODO: Game ends!
			throw new Exception("GAME OVER! You ate a poison pill!");
			//play a sound effect?
		} else if (world[yIndex][xIndex] instanceof Coin) { //neither special nor dangerous
			Coin c = (Coin) world[yIndex][xIndex];
			world[yIndex][xIndex] = null;
			c.imageProperty().set(null);
			//c.act();
			score += 1;
		}
		gameBoard.setScore(score);
		setX(xPos);
		setY(yPos);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getScore() {
		return score;
	}

	@Override
	public void act() {
		try {
			move(1);		
		} catch (Exception e) {
			gameBoard.gameOver(e.getMessage());
		}
	
	}

	@Override
	public void react(KeyEvent e) {
		
		KeyCode key = e.getCode();
		isBlocked = false;

		// If close to X or Y channel, snap to position
		if(yPos % 25 <5 || yPos % 25 >20)
			yPos = Math.round(yPos/25f)*25;
		if(xPos % 25 <5 || xPos % 25 >20)
			xPos = Math.round(xPos/25f)*25;
		
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {

			if (key == KeyCode.UP) {
				setDirection(2);
				setImage(pacman90);
				
			} else if (key == KeyCode.LEFT) {
				setDirection(3);
				setImage(pacman180);
				
			} else if (key == KeyCode.DOWN) {
				setDirection(4);
				setImage(pacman270);
				
			} else if (key == KeyCode.RIGHT) {
				setDirection(1);
				setImage(pacman0);
			}
		}
		setX(xPos);
		setY(yPos);

		
	}
}