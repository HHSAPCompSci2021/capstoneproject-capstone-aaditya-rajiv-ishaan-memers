
package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;

/** The bombs that spawn on the map which the player can pick up
	@author Aaditya Raj
	@version 1
*/
public class PaintBomb extends Sprite {
	
	private double explosionRadius, velocity;
	private double xVel, yVel;
	private boolean isThrown;
	private Color c;
	private Avatar owner;
	private static final int RADIUS = 8;

	
	/** Constructs a new PaintBomb objects
	 * 
	 * @param x the x-coordinate of the initial location of the PaintBomb
	 * @param y the y-coordinate of the initial location of the PaintBomb
	 * @param r the radius of the paintbomb
	 * @param c the color of this PaintBomb
	 */
	public PaintBomb(Avatar a, int x, int y, Color c) {
		super(x, y, RADIUS, RADIUS);
		this.explosionRadius = RADIUS*8;
		this.c = c;
		isThrown = false;
		owner = a;
	}
	
	/** Scatters Paintbombs with the specified radius
	 * 
	 * @return An ArrayList of paintblocks to be drawn on the map and acted upon
	 */
	public ArrayList<PaintBlock> blowUp() {
		return null;
	}	
	
	/** Throws this bomb
	 * 
	 * @param point the point at which the bomb is being thrown
	 */
	public void launch(Point2D mouseClick) {
		if (owner != null) {
			// set vx and vy based off mouse click and velocity field
			double run = mouseClick.getX() - owner.getCenterX();
			double rise = mouseClick.getY() - owner.getCenterY();
			double currentSpeed = Math.sqrt(Math.pow(rise, 2) + Math.pow(run, 2));
			if (currentSpeed > velocity) {
				rise *= velocity/currentSpeed;
				run *= velocity/currentSpeed;
			} else {
				rise *= velocity/currentSpeed;
				run *=  velocity/currentSpeed;
			}
			this.xVel = run;
			this.yVel = rise;
			isThrown = true;
		}
		
	}
	
	public void draw(PApplet drawer) {
		if (owner != null) {
			if (isThrown) {
				super.draw(drawer);
			}
		}
		else if (owner == null) {
			super.draw(drawer);
		}
	
	}


}
