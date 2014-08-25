package com.home.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;

public class GameWorld {

    private Rectangle rect = new Rectangle(0, 0, 17, 12);
    private Circle circle = new Circle(20, 20, 20);

	public void update(float delta) {
        Gdx.app.log("GameWorld", "update");

        rect.x ++;
        circle.x ++;

        if (rect.x > 137) {
            rect.x = 0;
        }

        if (circle.x > 157) {
        	circle.x = 20;
        }
	}

	public Rectangle getRect() {
		return rect;
	}

	public Circle getCircle() {
		return circle;
	}
}