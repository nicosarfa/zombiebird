package com.home.gameworld;

import com.home.gameobjects.Bird;
import com.home.gameobjects.ScrollHandler;
import com.home.zbHelpers.AssetLoader;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private boolean isAlive = true;
//	private Rectangle rect = new Rectangle(0, 0, 17, 12);
//	private Circle circle = new Circle(20, 20, 20);

	public GameWorld(int midPointY) {
		// Initialize here the bird
		bird = new Bird(33, midPointY - 5, 17, 12);
		scroller = new ScrollHandler(midPointY + 66);
	}
//	public void update(float delta) {
//        Gdx.app.log("GameWorld", "update");
//
//        rect.x ++;
//        circle.x ++;
//
//        if (rect.x > 137) {
//            rect.x = 0;
//        }
//
//        if (circle.x > 157) {
//        	circle.x = 20;
//        }
//	}

	public void update(float delta) {
		bird.update(delta);
		scroller.update(delta);

		if (isAlive && scroller.collides(bird)) {
			// Clean up on game over
			scroller.stop();
			AssetLoader.dead.play();
			isAlive = false;
		}
	}

	public Bird getBird() {
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}
}