package com.home.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.home.gameobjects.Bird;
import com.home.gameobjects.Grass;
import com.home.gameobjects.Pipe;
import com.home.gameobjects.ScrollHandler;
import com.home.zbHelpers.AssetLoader;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointY;
	private int gameHeight;

	// Game Objects
	private Bird bird;

	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;

	// Game Assets
	private TextureRegion bg;
	private TextureRegion grass;
	private Animation birdAnimation;

	private TextureRegion birdMid;
	private TextureRegion birdDown;
	private TextureRegion birdUp;

	private TextureRegion skullUp;
	private TextureRegion skullDown;
	private TextureRegion bar;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		this.myWorld = world;
		this.gameHeight = gameHeight;
		this.midPointY = midPointY;

		cam = new OrthographicCamera();

		// The three arguments are asking:
		//     1. whether you want an Orthographic projection (we do)
		//     2. what the width should be
		//     3. what the height should be. This is the size of our game world.
		// We will make changes to this at a later time. This is simply for
		// illustration. Remember that we set our screen resolution in
		// DesktopLauncher.java to 272 x 408.
		// This means that everything in our game will scale by a
		// factor of 2x when drawn.

		cam.setToOrtho(true, 136, 204);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		scroller = myWorld.getScroller();

		frontGrass = scroller.getFrontGrass();
		backGrass = scroller.getBackGrass();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
	}

	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		birdDown = AssetLoader.birdDown;
		birdUp = AssetLoader.birdUp;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		bar = AssetLoader.bar;
    }

	public void render(float runTime) {
		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin ShapeRenderer
		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		shapeRenderer.rect(0, 0, 136, midPointY + 66);

		// Draw Grass
		shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 66, 136, 11);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 77, 136, 52);

		// End ShapeRenderer
		shapeRenderer.end();

		// Begin SpriteBatch
		batcher.begin();

		// Disable transparency
		// This is good for performance when drawing images that do not require
		// transparency.
		batcher.disableBlending();
		batcher.draw(bg, 0, midPointY + 23, 136, 43);

		// 1. Draw Grass
		drawGrass();

		// 2. Draw Pipes
		drawPipes();
		batcher.enableBlending();

		// 3. Draw Skulls (requires transparency)
		drawSkulls();
		
		// The bird needs transparency, so we enable that again.
		// Draw bird at its coordinates. Retrieve the Animation object from
		// AssetLoader
		// Pass in the runTime variable to get the current frame.

		if (bird.shouldntFlap()) {
			batcher.draw(birdMid, bird.getX(), bird.getY(),
					bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
					bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
		} else {
			batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(),
					bird.getY(), bird.getWidth() / 2.0f,
					bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
					1, 1, bird.getRotation());
		}

		// For write text in screen
		// AssetLoader.shadow.draw(batcher, "hello world", x, y);

		// Convert integer into String
		String score = Integer.toString(myWorld.getScore());

		// Draw shadow first
		AssetLoader.shadow.draw(batcher, score,
				(136 / 2) - (3 * score.length()), 12);

		// Draw text
		AssetLoader.font.draw(batcher, score,
				(136 / 2) - (3 * score.length() - 1), 11);

		// End SpriteBatch
		batcher.end();
		
		// TODO: Remove this
//		shapeRenderer.begin(ShapeType.Filled);
//		shapeRenderer.setColor(Color.RED);

//		shapeRenderer.circle(bird.getBoundingCircle().x,
//				bird.getBoundingCircle().y, bird.getBoundingCircle().radius);

		/*
		 * Excuse the mess below. Temporary code for testing bounding
		 * rectangles.
		 */
		// Bar up for pipes 1 2 and 3
//		shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
//			pipe1.getBarUp().width, pipe1.getBarUp().height);
//		shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
//			pipe2.getBarUp().width, pipe2.getBarUp().height);
//		shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
//			pipe3.getBarUp().width, pipe3.getBarUp().height);

		// Bar down for pipes 1 2 and 3
//		shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
//			pipe1.getBarDown().width, pipe1.getBarDown().height);
//		shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
//			pipe2.getBarDown().width, pipe2.getBarDown().height);
//		shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
//			pipe3.getBarDown().width, pipe3.getBarDown().height);

		// Skull up for Pipes 1 2 and 3
//		shapeRenderer.rect(pipe1.getSkullUp().x, pipe1.getSkullUp().y,
//			pipe1.getSkullUp().width, pipe1.getSkullUp().height);
//		shapeRenderer.rect(pipe2.getSkullUp().x, pipe2.getSkullUp().y,
//			pipe2.getSkullUp().width, pipe2.getSkullUp().height);
//		shapeRenderer.rect(pipe3.getSkullUp().x, pipe3.getSkullUp().y,
//			pipe3.getSkullUp().width, pipe3.getSkullUp().height);

		// Skull down for Pipes 1 2 and 3
//		shapeRenderer.rect(pipe1.getSkullDown().x, pipe1.getSkullDown().y,
//			pipe1.getSkullDown().width, pipe1.getSkullDown().height);
//		shapeRenderer.rect(pipe2.getSkullDown().x, pipe2.getSkullDown().y,
//			pipe2.getSkullDown().width, pipe2.getSkullDown().height);
//		shapeRenderer.rect(pipe3.getSkullDown().x, pipe3.getSkullDown().y,
//			pipe3.getSkullDown().width, pipe3.getSkullDown().height);
//
//		shapeRenderer.end();
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
				frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(),
				backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawSkulls() {
		// Temporary code! Sorry about the mess :)
		// We will fix this when we finish the Pipe class.

		batcher.draw(skullUp, pipe1.getX() - 1,
				pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe1.getX() - 1,
				pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe2.getX() - 1,
				pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

		batcher.draw(skullUp, pipe3.getX() - 1,
				pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
		batcher.draw(skullDown, pipe3.getX() - 1,
				pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}

	private void drawPipes() {
		// Temporary code! Sorry about the mess :)
		// We will fix this when we finish the Pipe class.
		batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
				pipe1.getHeight());
		batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
				pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

		batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
				pipe2.getHeight());
		batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
				pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

		batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
				pipe3.getHeight());
		batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
				pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
	}
//	public void render() {
//        Gdx.app.log("GameRenderer", "render");
//
//        /*
//         * 1. We draw a black background. This prevents flickering.
//         */
//
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        /*
//         * 2. We draw the Filled rectangle
//         */
//
//        // Tells shapeRenderer to begin drawing filled shapes
//        shapeRenderer.begin(ShapeType.Filled);
//
//        // Chooses RGB Color of 87, 109, 120 at full opacity
//        shapeRenderer.setColor(87 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);
//
//        // Draws the rectangle from myWorld (Using ShapeType.Filled)
//        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,
//                myWorld.getRect().width, myWorld.getRect().height);
//
//        // Tells the shapeRenderer to finish rendering
//        // We MUST do this every time.
//        shapeRenderer.end();
//
//        /*
//         * 3. We draw the rectangle's outline
//         */
//
//        // Tells shapeRenderer to draw an outline of the following shapes
//        shapeRenderer.begin(ShapeType.Line);
//
//        // Chooses RGB Color of 255, 109, 120 at full opacity
//        shapeRenderer.setColor(255 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);
//
//        // Draws the rectangle from myWorld (Using ShapeType.Line)
//        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,
//                myWorld.getRect().width, myWorld.getRect().height);
//
//        shapeRenderer.circle(myWorld.getCircle().x, myWorld.getCircle().y,
//        		myWorld.getCircle().radius);
//        
//        shapeRenderer.end();
//	}
}