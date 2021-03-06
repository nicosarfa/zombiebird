package com.home.zbHelpers;

import com.badlogic.gdx.InputProcessor;
import com.home.gameobjects.Bird;
import com.home.gameworld.GameWorld;

public class InputHandler implements InputProcessor {

	private GameWorld myWorld;
	private Bird myBird;

	public InputHandler(GameWorld myWorld) {
		// myBird now represents the gameWorld's bird.
		this.myWorld = myWorld;
		myBird = myWorld.getBird();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (myWorld.isReady()) {
			myWorld.start();
		}

		myBird.onClick();

		if (myWorld.isGameOver() || myWorld.isHighScore()) {
			// Reset all variables, go to GameState.READY
			myWorld.restart();
		}

		return true; // Return true to say we handled the touch.
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}