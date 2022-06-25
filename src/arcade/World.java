package arcade;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.GridPane;

public abstract class World extends GridPane {
	
	int childrenSize = getChildren().size();
	private AnimationTimer timer;
	
	public abstract void act();
	
	public World() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				act();
				for(int i=0; i<childrenSize; i++) {
					((World)getChildren().get(i)).act();
				}
			}
		};
	}
}
		