package arcade;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class WallBlock extends Actor {
	
	private Image image = new Image("resources/PacmanWall.png");

	public WallBlock(int xPos, int yPos) {
		super(xPos, yPos);
		this.setImage(image);
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void react(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
