package sprites;


import java.util.List;

import processing.core.PImage;

public class Character extends Sprite {

	public static final int CHARACTER_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;
	public static final double GRAVITY = 0.6;

	private double xVel, yVel;
	private boolean onPlatform;

	public Character(PImage img, int x, int y) {
		super(img, x, y, CHARACTER_WIDTH, MARIO_HEIGHT);
		xVel = 0;
		yVel = 0;
	}

	// METHODS
	public void walk(int dir) {
		xVel =   dir;
	}

	public void jump() {
		yVel -= 1.6;
	}

	public void act(List<Sprite> obstacles) {
		
		yVel += GRAVITY; // gravity
		
		x += xVel;
		y += yVel;
		
		for (Sprite sprite : obstacles) {
			if (super.intersects(sprite)) {
				yVel = 0;
				super.y = sprite.y - super.height;
				onPlatform = true;
			}
		}
		if (yVel != 0) {
			onPlatform = false;
		}
		
	}


}
