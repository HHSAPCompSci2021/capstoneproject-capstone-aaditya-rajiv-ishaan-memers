package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import sprites.Sprite;

public class Avatar extends Sprite {
	private PaintGun gun;
	private PaintBomb bombHeld;
	private Color playerColor;
	private int health, speed;
	private int baseX, baseY;
	private int xVel, yVel;
	private boolean onPlatform;
	public static final double GRAVITY = 0.6;

	
	public Avatar(int x, int y, int w, int h, Color color) {
		super(x, y, w, h);
		playerColor = color;
		xVel = 0;
		yVel = 0;
		// TODO Auto-generated constructor stub
	}

	public void walk(int dir) {
		xVel = dir;
	}

	public void jump() {
		yVel -= 1.6;
	}

	public void act(List<Sprite> gameObstacles) {
		
		yVel += GRAVITY; // gravity
		
		x += xVel;
		y += yVel;
		
		for (Sprite sprite : gameObstacles) {
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
	
	public void draw(PApplet drawer) {
		
	}
	public void throwBomb(Point2D startPoint, Point2D mouseClick) {
		bombHeld.launch(startPoint, mouseClick);
		bombHeld = null;
 	}
	
	public void shoot(Point2D point) {
		gun.shoot(point);
	}
	public int setHealth(ArrayList<PaintBlock> blocks) {
		return health - 12 * blocks.size();
	}
	
	public void respawn() {
		super.moveToLocation(baseX, baseY);
	}
	
	public int getHealth() {
		return health;
	}
	
	public Color getColor() {
		return playerColor;
	}
	
	
}
