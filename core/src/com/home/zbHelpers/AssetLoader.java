package com.home.zbHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	// Unico archivo que contiene todas las imagenes
	// Es decir la textura contiene el pasto, el pajaro, el mundo, etc
	public static Texture texture;
	public static Texture logoTexture;

	public static Sound dead;
	public static Sound flap;
	public static Sound coin;

	public static TextureRegion logo;
	public static TextureRegion zbLogo;
	
	public static TextureRegion playButtonUp;
	public static TextureRegion playButtonDown;
	
	// Es una seleccion de la textura, el pasto por ejemplo
	public static TextureRegion bg;
	public static TextureRegion grass;

	// A partir de juntar varias textureRegion se puede armar una animacion
	public static Animation birdAnimation;

	public static TextureRegion bird;
	public static TextureRegion birdDown;
	public static TextureRegion birdUp;

	public static TextureRegion skullUp;
	public static TextureRegion skullDown;
	public static TextureRegion bar;

	// Usados para dibujar las letras
	public static BitmapFont font;
	public static BitmapFont shadow;

	public static Preferences prefs;

	private static String HIGH_SCORE_PREFERENCE = "highScore";

	public static void load() {
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

		playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
		playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
		zbLogo.flip(false, true);

		bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);

		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);

		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		birdDown.flip(false, true);

		bird = new TextureRegion(texture, 153, 0, 17, 12);
		bird.flip(false, true);

		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdUp.flip(false, true);

		// creates an array of TextureRegions
		TextureRegion[] birds = { birdDown, bird, birdUp };

		// Creates a new Animation in which each frame is 0.06 seconds long, using the above array.
		birdAnimation = new Animation(0.06f, birds);

		// Sets play mode to be ping pong, in which we will see a bounce.
		// We gave the Animation 3 frames. It will now change frames
		// every 0.06 seconds (down, middle, up, middle, down ...).
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		skullUp = new TextureRegion(texture, 192, 0, 24, 14);

		// Create by flipping existing skullUp
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true);

		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

		// These will load the files and change them to an appropriate size for us to use:
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("ZombieBird");

		// Provide default high score of 0
		if (! prefs.contains(HIGH_SCORE_PREFERENCE)) {
			prefs.putInteger(HIGH_SCORE_PREFERENCE, 0);
		}
	}

	// Receives an integer and maps it to the String highScore in prefs
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	// Retrieves the current high score
	public static int getHighScore() {
		return prefs.getInteger(HIGH_SCORE_PREFERENCE);
	}

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		shadow.dispose();
	}
}