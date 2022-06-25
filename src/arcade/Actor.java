package arcade;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public abstract class Actor extends ImageView {

	protected GameBoard gameBoard;
	protected int xPos;
	protected int yPos;

	
	public Actor(int xPos, int yPos) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		setX(xPos);	
		setY(yPos);
	}

	public abstract void act();

	public abstract void react(KeyEvent e);
			
	protected int getYindex() {
		return yPos/25;
	}

	protected int getXindex() {
		return xPos/25;
	}

	public GameBoard getWorld() {
		return gameBoard;
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
}
