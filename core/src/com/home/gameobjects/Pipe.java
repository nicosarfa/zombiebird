package com.home.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {

	public static final int VERTICAL_GAP = 45;
	public static final int SKULL_WIDTH = 24;
	public static final int SKULL_HEIGHT = 11;

	private Random r;

	// Used for detect collision
	private Rectangle skullUp;
	private Rectangle skullDown;
	private Rectangle barUp;
	private Rectangle barDown;

	private float groundY;

	private boolean isScored = false;

	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Pipe(float x, float y, int width, int height,
			float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
		// Initialize a Random object for Random number generation
		this.r = new Random();

		this.skullUp = new Rectangle();
	    this.skullDown = new Rectangle();
	    this.barUp = new Rectangle();
	    this.barDown = new Rectangle();
	    this.groundY = groundY;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		
		// The set() method allows you to set the top left corner's x, y
        // coordinates,
        // along with the width and height of the rectangle

		barUp.set(position.x, position.y, width, height);
		barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
				groundY - (position.y + height + VERTICAL_GAP));

		// Our skull width is 24. The bar is only 22 pixels wide. So the skull
		// must be shifted by 1 pixel to the left (so that the skull is centered
		// with respect to its bar).

		// This shift is equivalent to: (SKULL_WIDTH - width) / 2
		skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
				- SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
		skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
				SKULL_WIDTH, SKULL_HEIGHT);
	}
	
	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);

		// Change the height to a random number
		height = r.nextInt(90) + 15;

		isScored = false;
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
					|| Intersector.overlaps(bird.getBoundingCircle(), barDown)
					|| Intersector.overlaps(bird.getBoundingCircle(), skullUp)
					|| Intersector.overlaps(bird.getBoundingCircle(), skullDown));
        }

        return false;
    }

	public void onRestart(float x, int scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public void setScored(boolean isScored) {
		this.isScored = isScored;
	}

	public boolean isScored() {
		return isScored;
	}
	
	public Rectangle getSkullUp() {
		return skullUp;
	}

	public Rectangle getSkullDown() {
		return skullDown;
	}

	public Rectangle getBarUp() {
		return barUp;
	}

	public Rectangle getBarDown() {
		return barDown;
	}
}