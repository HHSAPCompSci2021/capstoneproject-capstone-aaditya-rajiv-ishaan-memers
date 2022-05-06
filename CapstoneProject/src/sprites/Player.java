package sprites;


import java.util.List;

import processing.core.PImage;

public class Player extends Sprite {

	public static final int PLAYER_WIDTH = 40;
	public static final int PLAYER_HEIGHT = 60;
	public static final double GRAVITY = 0.6;

	private double xVel, yVel;
	private boolean onPlatform;

	public Player(PImage img, int x, int y) {
		super(img, x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
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
