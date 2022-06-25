package arcade;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Coin extends Actor {
	
	private Image coin = new Image("resources/PacmanCoin.png");
	//private Image coin = new Image("resources/pacmanCoin2.png");
	private Image coinGif = new Image("resources/PacmanBitcoin.png");
	private Image rainbowCoin = new Image("resources/pacmanRainbowCoinGif.gif");
//	private Image clover = new Image("resources/pacmanCloverGif.gif");
	
	private boolean isSpecial = false;
	private boolean isDangerous = false;
	
	
	public Coin(int xPos, int yPos) {
		super(xPos, yPos);
		this.setImage(coin);
	}
	
	public boolean isSpecial() {return isSpecial;}
	public boolean isDangerous() {return isDangerous;}
	public void setIsSpecial(boolean b) {isSpecial = b;}
	public void setIsDangerous(boolean b) {isDangerous = b;}
	
	@Override
	public void act() {

		// Reset
		this.setImage(coin);
		isSpecial = false;
		isDangerous = false;

		// Random flip
		int random = (int)(Math.random()*20);
				
		if (random == 1) {
			this.setImage(rainbowCoin);
			isDangerous = true;
		}
		
		if (random == 2) {
			this.setImage(coinGif);
			isSpecial = true;
		}

	}
	@Override
	public void react(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
