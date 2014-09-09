package com.home.zombiebird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.home.screens.SplashScreen;
import com.home.zbHelpers.AssetLoader;

public class ZBGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("ZBGame", "created");
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}