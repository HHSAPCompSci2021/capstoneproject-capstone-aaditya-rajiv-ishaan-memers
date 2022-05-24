
package sprites;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import screens.Game;
import sprites.Sprite;

/**
 * The players in the game
 * 
 * @author Ishaan Singh and Aaditya Raj
 * @version 3
 */
public class Avatar extends Sprite {
	private PaintGun gun;
	private PaintBomb bomb;
	private Color playerColor;
	private int health;
	private int baseX, baseY;
	private int yVel;
	private double prevX, prevY;
	private int scale;
	public static final double GRAVITY = 1.98;
	private static final int BOMB_LIMIT = 200;
	private final int xVel = 10;
	private boolean onPlatform;
	private int flagCaptures;
	private int numDeaths;
	private PImage paintGunImage;
	private int timer;

	/**
	 * 
	 * @param x     x-coord of starting location of the Avatar
	 * @param y     y-coord of starting location of the Avatar
	 * @param w     width of Avatar
	 * @param h     height of Avatar
	 * @param color red/blue for one of the two characters in the game
	 */
	public Avatar(PImage avatarImage, PImage paintGunImage, int x, int y, int w, int h, Color color) {
		super(avatarImage, x, y, w, h);
		this.paintGunImage = paintGunImage;
		gun = new PaintGun(paintGunImage, x + w, y + 150, w/4, h/2, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH * 2);

		baseX = x;
		baseY = y;
		playerColor = color;
		yVel = 0;
		scale = 1;
		health = 70;
		flagCaptures = 0;
		numDeaths = 0;
		timer = 0;
	}
	
	

	/**
	 * 
	 * @param dir left, right
	 */
	public void walk(boolean right) {
		x += (xVel * scale * (right ? 1 : -1));
		System.out.println(x);
	}

	/**
	 * Avatar jumps as a result
	 * 
	 */
	public void jump() {

		yVel -= 40;

	}
	
	public void fall() {
		yVel += GRAVITY;
		y += yVel;
	}

	/**
	 * 
	 * @param gameObstacles the sprites to act on
	 */
	public void act(ArrayList<Sprite> gameObstacles) {
		onPlatform = false;
		for (Sprite sprite : gameObstacles) {
			if (super.intersects(sprite)) {
				if (y > prevY && y + height > sprite.y && y + height - yVel * scale <= sprite.y) {
					onPlatform = true;
					y = sprite.y - height;
					yVel = 0;
				} else if (y < prevY && y < sprite.y + sprite.height && y - yVel * scale >= sprite.y + sprite.height) {
					y = sprite.y + sprite.height;
					yVel = 0;
				}
				if (x > prevX && x + width > sprite.x && x + width - xVel * scale <= sprite.x) {
					x = sprite.x - super.width;
				} else if (x < prevX && x < sprite.x + sprite.width && x + xVel * scale >= sprite.x + sprite.width) {
					x = sprite.x + sprite.width;
				}

			}
		}

		prevX = x;
		prevY = y;

	}

	/**
	 * Draws the avatar in the game
	 * 
	 * @param drawer the PApplet that draws the Avatar
	 */
	public void draw(PApplet drawer, String username) {
		drawer.push();
		drawer.textSize(32);
		drawer.text(username, (float) x, (float) (y-10));
		drawer.pop();
		super.draw(drawer);
		if (canThrowBomb() && !(gun instanceof Flag)) {
			gun.setRect(gun.x, gun.y, width/2, height);
		} else if (!(gun instanceof Flag)){
			gun.setRect(gun.x, gun.y, width/4, height/2);
		}
		if (!hasFlag())
			gun.draw(drawer, this.x, this.y);
		else
			gun.draw(drawer, (int) this.x, (int) this.y);
		timer++;
	}

	/**
	 * Throws a bomb
	 * 
	 * @param startPoint the starting point
	 * @param mouseClick the location of the throw
	 */
	public PaintBomb throwBomb(Point2D mouseClick) {
		timer = 0;
		return new PaintBomb((int) getCenterX(), (int) getCenterY(), mouseClick, getColor());
	}
	
	public boolean canThrowBomb() {
		return (timer > BOMB_LIMIT && !(gun instanceof Flag));
	}

	/**
	 * 
	 * @param point
	 */
	public PaintBlock shoot(Point2D point) {
		if (! (gun instanceof Flag)) {
			return gun.shoot(point, playerColor);
		}
		return gun.shoot(new Point2D.Double(getCenterX(), getCenterY()), point, playerColor);
	}

	public boolean onPaint(Platform p) {
		for (PaintBlock block : p.getBorder()) {
			if (intersects(block) && block.getX() != p.getX() &&
					block.getX()
					!= p.getX() + p.getWidth() - PaintBlock.LENGTH && block.getColor() != null && getColor() != null
					&& block.getColor().equals(getColor())) {
				System.out.println("ON PAINT TRUE");
				return true;
			}
		}
		return false;
	}

	public boolean loseHealth(Avatar opponent) {
		if (opponent.hasFlag()) {
			health -= 14;
		} else {
			health -= 10;
		}
		System.out.println(health);
		if (health <= 0) {
			respawn();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Respawns this Avatar back to their home base when they die with full health
	 * 
	 */
	public void respawn() {
		health = 70;
		super.moveToLocation(baseX, baseY);
		gun = new PaintGun(paintGunImage,(int) (x + 200), (int) (y + 150), 50, 100, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH * 2);
		numDeaths++;
	}


	
	/** Gets the health of this avatar

	/**
	 * Gets the health of this avatar

	 * 
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	public boolean onPlatform() {
		return onPlatform;

	}

	/**
	 * Gets the color of this avatar
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return playerColor;
	}

	public void collectFlag() {
		gun = Game.flag;
	}

	public void boost() {
		scale = 2;
	}

	public void undoSpeedBoost() {
		scale = 1;
	}

	public boolean isBoosted() {
		return (scale == 2);
	}

	public boolean hasFlag() {
		return (gun instanceof Flag);
	}

	public Point getBase() {
		return new Point(baseX, baseY);
	}

	public int getCaptures() {
		return flagCaptures;
	}

	public void touchdown() {
		flagCaptures++;
		super.moveToLocation(baseX, baseY);
		gun = new PaintGun(paintGunImage, (int) (x + 200), (int)(y + 150), 50, 100, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH * 2);
	}

	public int getNumDeaths() {
		return numDeaths;
	}


	public PaintBomb getBomb() {
		return bomb;
	}
}
