package arcade;
import javafx.stage.*;

import java.util.List;

import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
public class Enemy extends Actor {
	private Image image = new Image("resources/PacmanGhost.png", 20, 20, false, false);
	private int direction;
	private int xPos;
	private int yPos;
	int xIndex = xPos/25;
	int yIndex = yPos/25;
	
	
	public Enemy(GameBoard gameBoard, int xPos, int yPos) {
		super(xPos, yPos);
		this.gameBoard = gameBoard;
		this.setImage(image);
		direction = 2;	//initially moves up
	}

	@Override
	public void act() {
		move(1);
	}

	public void move(int distance) {
		
		boolean inXChannel = yPos % 25 <3 || yPos % 25 > 22;
		boolean inYChannel = xPos % 25 <3 || xPos % 25 > 22;
		
		Actor[][] world = gameBoard.getWorld();
		
		if(direction==1 && inXChannel && !(world[yIndex][xIndex+1] instanceof WallBlock)) {
			xPos += distance;
		}else if (direction==2 && inYChannel && !(world[yIndex][xIndex] instanceof WallBlock)) {
			yPos-= distance;
		}else if (direction==3 && inXChannel && !(world[yIndex][xIndex] instanceof WallBlock)) {
			xPos-= distance;
		}else if (direction==4 && inYChannel && !(world[yIndex+1][xIndex] instanceof WallBlock)) {
			yPos+=distance;
		} else {
			//System.out.printf("(%d, %d)-(%b, %b)", yPos, xPos, inXChannel, inYChannel);
			//isBlocked = true;
		}
		setX(xPos);
		setY(yPos);
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	@Override
	public void react(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
