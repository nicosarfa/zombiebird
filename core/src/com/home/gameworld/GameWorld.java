package com.home.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.home.gameobjects.Bird;
import com.home.gameobjects.ScrollHandler;
import com.home.zbHelpers.AssetLoader;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;

	private Rectangle ground;
	public int midPointY;

	private int score;

	private GameState currentState;
//	private Rectangle rect = new Rectangle(0, 0, 17, 12);
//	private Circle circle = new Circle(20, 20, 20);

	public enum GameState {
		READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midPointY) {
		currentState = GameState.READY;
		// Initialize here the bird
		bird = new Bird(33, midPointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midPointY + 66);

		this.midPointY = midPointY;

		ground = new Rectangle(0, midPointY + 66, 136, 11);
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
		switch (currentState) {
			case READY:
				updateReady(delta);
				break;
			case RUNNING:
				updateRunning(delta);
				break;
			default:
				break;
		}
	}

	private void updateReady(float delta) {
		// nothing at the moment
	}

	private void updateRunning(float delta) {

		// Add a delta cap so that if our game takes too long
        // to update, we will not break our collision detection.

		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (bird.isAlive() && scroller.collides(bird)) {
			// Clean up on game over
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
		}

		if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			scroller.stop();
			bird.die();
			bird.decelerate();
			currentState = GameState.GAMEOVER;

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				currentState = GameState.HIGHSCORE;
			}
		}
	}

	public Bird getBird() {
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}
	
	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public  boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public void start() {
		currentState = GameState.RUNNING;
    }

	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		currentState = GameState.READY;
    }
}