
package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import screens.Game;
import sprites.Sprite;

/** The players in the game
	@author Ishaan Singh
	@version 3
*/
public class Avatar extends Sprite {
	private PaintGun gun;
	private PaintBomb bombHeld;
	private Color playerColor;
	private int health;
	private int baseX, baseY;
	private int xVel, yVel;
	private boolean onPlatform;
	public static final double GRAVITY = 0.6;

	/**
	 * 
	 * @param x x-coord of starting location of the Avatar
	 * @param y y-coord of starting location of the Avatar
	 * @param w width of Avatar
	 * @param h height of Avatar
	 * @param color red/blue for one of the two characters in the game
	 */
	public Avatar(int x, int y, int w, int h, Color color) {
		super(x, y, w, h);
		gun = new PaintGun(x + 10, y, w/2, h/2, 5, 5, 5, PaintBlock.LENGTH);
		playerColor = color;
		xVel = 0;
		yVel = 0;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param dir left, right
	 */
	public void walk(int dir) {
		xVel = dir;
	}

	/** Avatar jumps as a result 
	 * 
	 */
	public void jump() {
		yVel -= 1.6;
	}

	/**
	 * 
	 * @param gameObstacles the sprites to act on
	 */
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
	
	/**
	 * Draws the avatar in the game
	 * @param drawer the PApplet that draws the Avatar
	 */
	public void draw(PApplet drawer) {
	 	super.draw(drawer);
	 	gun.draw(drawer, new Point2D.Double(x, y));
	 	
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
	
	/** Sets the health (useful when respawning)
	 * 
	 * @param blocks calc tool
	 * @return 
	 */
	public void setHealth(ArrayList<PaintBlock> blocks) {
		health -= (10 * blocks.size());
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
	
	public void swim() {
		xVel *= 4;
		yVel *= 4;
	}
	
	
}
