
package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import screens.Game;
import sprites.Sprite;

/** The players in the game
	@author Ishaan Singh and Aaditya Raj
	@version 3
*/
public class Avatar extends Sprite {
	private PaintGun gun;
	private PaintBomb bombHeld;
	private Color playerColor;
	private int health;
	private int baseX, baseY;
	private int yVel;
	private double prevX, prevY;
	private int scale;
	public static final double GRAVITY = 1.98;
	private final int xVel = 5;
	private boolean onPlatform;

	/**
	 * 
	 * @param x x-coord of starting location of the Avatar
	 * @param y y-coord of starting location of the Avatar
	 * @param w width of Avatar
	 * @param h height of Avatar
	 * @param color red/blue for one of the two characters in the game
	 */
	public Avatar(PImage image, int x, int y, int w, int h, Color color) {
		super(image, x, y, w, h);
		gun = new PaintGun(x + w, y + 150, w/4, h/2, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH);
		
		baseX = x;
		baseY = y;
		playerColor = color;
		yVel = 0;
		scale = 1;
		health = 100;
	}
	
	
	
	/**
	 * 
	 * @param dir left, right
	 */
	public void walk(boolean right) {
		x += xVel  * scale * (right ? 1 : -1);
	}

	/** Avatar jumps as a result 
	 * 
	 */
	public void jump() {
	
		yVel -= 25;
		
	}

	/**
	 * 
	 * @param gameObstacles the sprites to act on
	 */
	public void act(ArrayList<Sprite> gameObstacles) {
		yVel += GRAVITY; 
		y += yVel * scale;
		
		boolean noIntersection = true;
		for (Sprite sprite : gameObstacles) {
			if (super.intersects(sprite)) {
				noIntersection = false;
				if (y > prevY && y + height > sprite.y && y + height - yVel * scale <= sprite.y) {
					onPlatform = true;
					y = sprite.y - height;
				} else if (y < prevY && y < sprite.y + sprite.height && y - yVel * scale >= sprite.y + sprite.height) {
					onPlatform = false;
					y = sprite.y + sprite.height;
				}
				if (x > prevX && x + width > sprite.x && x + width - xVel * scale <= sprite.x) {
					x = sprite.x - super.width; 					
				} else if (x < prevX && x < sprite.x + sprite.width && x + xVel * scale >= sprite.x + sprite.width) {
					x = sprite.x + sprite.width;
				}
				yVel = 0;
			} 
		}
		if (noIntersection) {
			onPlatform = false;
		}
		
		prevX = x;
		prevY = y;
		
	}
	

	
	/**
	 * Draws the avatar in the game
	 * @param drawer the PApplet that draws the Avatar
	 */
	public void draw(PApplet drawer) {
	 	super.draw(drawer);
	 	if(!hasFlag())
	 		gun.draw(drawer, this.x, this.y);
	 	else
	 		gun.draw(drawer, (int)this.x, (int)this.y);
	}
	
	/** Throws a bomb
	 * 
	 * @param startPoint the starting point
	 * @param mouseClick the location of the throw
	 */
	public void throwBomb(Point2D mouseClick) {
		bombHeld.launch(mouseClick);
		bombHeld = null;
 	}
	
	/**
	 * 
	 * @param point
	 */
	public PaintBlock shoot(Point2D point) {
		return gun.shoot(point, playerColor);
	}
	
	public boolean onPaint(Platform p) {
		for (PaintBlock block : p.getBorder()) {
			if (intersects(block) && block.getColor() != null && block.getColor().equals(getColor())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean loseHealth() {
		health -= 10;
		if (health <= 0) {
			health = 100;
			respawn();
			return true;
		} else {
			return false;
		}
	}

	/** Respawns this Avatar back to their home base when they die with full health
	 * 
	 */
	public void respawn() {
		super.moveToLocation(baseX, baseY);
		gun = new PaintGun((int) (x + 200), (int)(y + 150), 50, 100, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH);
	}
	
	/** Gets the health of this avatar
	 * 
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
	
	public boolean onPlatform() {
		return onPlatform;
		
	}
	
	/** Gets the color of this avatar
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
		scale /= 2;
	}
	
	public boolean isBoosted() {
		return (scale == 2);
	}
	
	public boolean hasFlag() {
		return (gun instanceof Flag);
	}
	
}
