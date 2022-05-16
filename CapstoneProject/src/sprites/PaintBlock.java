
package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;

import processing.core.PApplet;

/** The basic unit of the game
	@author Aaditya Raj, Rajiv Venkatesh
	@version 2
*/
public class PaintBlock extends Sprite {
	
	private Color c;
	private double xVel, yVel;
	public static final int LENGTH = 10;
	public static final int VELOCITY = 10;
	
	/**
	 * 
	 * @param owner the owner of this Paintblock
	 * @param length the length of this PaintBlock
	 * @param velocity the velocity 
	 * @param mouseClick the location
	 */
	public PaintBlock(int x, int y, Color c, int length, double velocity, Point2D mouseClick) {
		super(x, y, length, length);
		this.c = c;
		
		double run = mouseClick.getX() - x;
		double rise = mouseClick.getY() - y;
		
		double angle = Math.atan(rise/run);

		this.xVel = Math.cos(angle)*VELOCITY;
		this.yVel = Math.sin(angle)*VELOCITY;
		
		if(run < 0) {
			xVel *= -1;
			yVel *= -1;
		}
		
	}
	
	public PaintBlock(int x, int y, int length) {
		super(x, y, length, length);
		this.c = null;
		xVel = 0;
		yVel = 0;
	}
	
	/**
	 * Draws this PaintBlock
	 */
	public void draw(PApplet marker) {
		marker.push();
		marker.fill(c.getRed(), c.getGreen(), c.getBlue());
		marker.rect((float) super.x, (float) super.y, (float) super.width, (float) super.height);
		marker.pop();
		super.moveByAmount(xVel, yVel);
	}
	
	/** Changes the color of this block
	 * 
	 * @param c the new color
	 */
	public void fill(Color c) {
		this.c = c;
	}
	
	public Color getColor() {
		return c;
	}
	
	
}
