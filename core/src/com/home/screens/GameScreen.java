package com.home.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.home.gameworld.GameRenderer;
import com.home.gameworld.GameWorld;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;

	public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");

        world = new GameWorld(); // initialize world
        renderer = new GameRenderer(world); // initialize renderer
	}

	// This is the game loop!
	@Override
	public void render(float delta) {
		// We are passing in delta to our update method so that we can perform frame-rate independent movement. 
		world.update(delta); // GameWorld updates 
		renderer.render(); // GameRenderer renders

		// Covert Frame rate to String, print it
        // Gdx.app.log("GameScreen FPS", (1/delta) + "");
	}

	@Override
	public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
	}

	@Override
	public void show() {
        Gdx.app.log("GameScreen", "show called");
	}

	@Override
	public void hide() {
        Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	public void pause() {
        Gdx.app.log("GameScreen", "pause called");
	}

	@Override
	public void resume() {
        Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	public void dispose() {
        // Leave blank
	}
}