
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
	public static final int VELOCITY = 15;
	
	/**
	 * 
	 * @param owner the owner of this Paintblock
	 * @param length the length of this PaintBlock
	 * @param velocity the velocity 
	 * @param mouseClick the location
	 */
	public PaintBlock(int x, int y, double maxAngle, Color c, int length, double velocity, Point2D mouseClick) {
		super(x, y, length, length);
		this.c = c;
		
		double run = mouseClick.getX() - x;
		double rise = mouseClick.getY() - y;
		
		double angle = Math.abs(Math.atan(rise/run));

		if(mouseClick.getX() < x) {
			this.xVel = -Math.cos(angle)*VELOCITY;
		} else {
			this.xVel = Math.cos(angle)*VELOCITY;
		}
		
		if(mouseClick.getY() < y) {
			this.yVel = -Math.sin(angle)*VELOCITY;
		} else {
			this.yVel = Math.sin(angle)*VELOCITY;
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
	
	public double getAngle() {
		return Math.atan(yVel/xVel);
	}
	
	
}
