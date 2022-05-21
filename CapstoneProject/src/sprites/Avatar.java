
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

/** The players in the game
	@author Ishaan Singh and Aaditya Raj
	@version 3
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
	private final int xVel = 10;
	private boolean onPlatform;
	private int flagCaptures;
	private int numDeaths;

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
		gun = new PaintGun(x + w, y + 150, w/4, h/2, 5, PaintBlock.VELOCITY, PaintBlock.LENGTH * 2);
		
		baseX = x;
		baseY = y;
		playerColor = color;
		yVel = 0;
		scale = 1;
		health = 100;
		flagCaptures = 0;
		numDeaths = 0;
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
	
		yVel -= 50;
		
	}

	/**
	 * 
	 * @param gameObstacles the sprites to act on
	 */
	public void act(ArrayList<Sprite> gameObstacles) {
		yVel += GRAVITY; 
		y += yVel * scale;
		
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
		bomb.launch(mouseClick);
		bomb = null;
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
				System.out.println("ON PAINT TRUE");
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
		numDeaths++;
	}
	
	public void reset() {
		super.moveToLocation(baseX, baseY);
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
		scale *= 2;
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
	
	public Point getBase() {
		return new Point(baseX, baseY);
	}
	
	public int getCaptures() {
		if (flagCaptures <= 1) {
			return flagCaptures;
		}
		return 1;
	}
	
	public void touchdown() {
		flagCaptures++;
		reset();
	}
	
	public int getNumDeaths() {
		return numDeaths;
	}
	
	public boolean captured() {
		if (getCaptures() == 1) {
			return true;
		}
		return false;
	}
	
	public PaintBomb getBomb() {
		return bomb;
	}
}
