package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;

import processing.core.PApplet;

public class PaintBlock extends Sprite {
	
	private Color c;
	private double xVel, yVel;
	
	
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
	
	public void draw(PApplet marker) {
		marker.push();
		marker.fill(c.getRed(), c.getGreen(), c.getBlue());
		marker.rect((float) super.x, (float) super.y, (float) super.width, (float) super.height);
		marker.pop();
		super.moveByAmount(xVel, yVel);
	}
	
	public void fill(Color c) {
		this.c = c;
	}
	
	
	
}
