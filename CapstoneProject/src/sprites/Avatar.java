
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
		gun = new PaintGun(x + 10, y + 150, w/4, h/2, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH);
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
	
		yVel -= 16;
		
	}

	/**
	 * 
	 * @param gameObstacles the sprites to act on
	 */
	public void act(ArrayList<Sprite> gameObstacles) {
		yVel += GRAVITY; 
		y += yVel * scale;
		
		
		for (Sprite sprite : gameObstacles) {
			if (super.intersects(sprite)) {
				if (y > prevY && y + height > sprite.y && y + height - yVel * scale <= sprite.y) {
					super.y = sprite.y - height;
				} else if (y < prevY && y < sprite.y + sprite.height && y - yVel * scale >= sprite.y + sprite.height) {
					super.y = sprite.y + sprite.height;
				}
				if (x > prevX && x + width > sprite.x && x + width - xVel * scale <= sprite.x) {
					super.x = sprite.x - super.width; 					
				} else if (x < prevX && x < sprite.x + sprite.width && x + xVel * scale >= sprite.x + sprite.width) {
					x = sprite.x + sprite.width;
				}
				yVel = 0;
			}
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
	 	gun.draw(drawer, this.x, this.y);
	 	
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

	
	
	public void changeHealth(double amount) {
		health += amount;
		if (health <= 0) {
			respawn();
			health = 100;
		}
	}

	/** Respawns this Avatar back to their home base when they die
	 * 
	 */
	public void respawn() {
		
		super.moveToLocation(baseX, baseY);
	}
	
	/** Gets the health of this avatar
	 * 
	 * @return the health
	 */
	public int getHealth() {
		return health;
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
	
}
