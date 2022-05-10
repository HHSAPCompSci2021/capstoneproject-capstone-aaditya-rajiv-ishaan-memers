
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
	
	/**
	 * 
	 * @param owner the owner of this Paintblock
	 * @param length the length of this PaintBlock
	 * @param velocity the velocity 
	 * @param mouseClick the location
	 */
	public PaintBlock(Avatar owner, int length, double velocity, Point2D mouseClick) {
		super((int) owner.getCenterX(), (int) owner.getCenterY(), length, length);
		this.c = owner.getColor();
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
	
	
	
}
