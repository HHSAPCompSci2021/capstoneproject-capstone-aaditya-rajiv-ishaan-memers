package sprites;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream:CapstoneProject/src/sprites/Avatar.java
=======
import processing.core.PImage;
import sprites.Sprite;

>>>>>>> Stashed changes:CapstoneProject/src/Avatar.java
public class Avatar extends Sprite {
	private PaintGun gun;
	private ArrayList<PaintBomb> arsenal;
	private Color playerColor;
	private int health, speed;
	private int baseX, baseY;
	private int xVel, yVel;
	private boolean onPlatform;
	public static final double GRAVITY = 0.6;

	
	public Avatar(int x, int y, int w, int h) {
		super(x, y, w, h);
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
	
	public void throwBomb(PaintBomb bomb) {
		
 	}
	
	public void shoot() {
		gun.shoot();
	}
	
	
}
