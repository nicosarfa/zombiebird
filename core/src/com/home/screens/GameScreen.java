package com.home.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.home.gameworld.GameRenderer;
import com.home.gameworld.GameWorld;
import com.home.zbHelpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;

	private float runTime = 0;

	public GameScreen() {
		Gdx.app.log("GameScreen", "Attached");

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;

		// Aca divide el alto del juego por la proporcion que hay entre el
		// tamano de la pantalla y el del juego.
		// screenWidth = 1080px y gameWidth = 136
		// La relacion es aproximadamente 8
		Float gameHeight = screenHeight / (screenWidth / gameWidth);

		Float midPointY = gameHeight / 2; // El punto medio es la mitad del ancho del juego

        world = new GameWorld(midPointY.intValue()); // initialize world
        renderer = new GameRenderer(world, gameHeight.intValue(), midPointY.intValue()); // initialize renderer

        Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
	}

	// This is the game loop!
	@Override
	public void render(float delta) {
		// We are passing in delta to our update method so that we can
		// perform frame-rate independent movement. 
		runTime += delta;

		world.update(delta); // GameWorld updates
		renderer.render(runTime); // GameRenderer renders

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