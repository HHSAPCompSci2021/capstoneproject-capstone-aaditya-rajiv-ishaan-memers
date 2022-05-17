package sprites;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;


/**
 * 
 * @author Mr. Shelby
 *
 */
public class Sprite extends Rectangle2D.Double {
	
	// FIELDS
	private PImage image;
	private float rotation;
	
	// CONSTRUCTORS
	
	public Sprite(int x, int y, int w, int h) {
		this(null, x, y, w, h);
		rotation = 0;
	}
	
	public Sprite(PImage img, int x, int y, int w, int h) {
		super(x,y,w,h);
		image = img;
		rotation = 0;
	}
	
	
	// METHODS	
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}
	
	public void setRotation(double rotation) {
		this.rotation = (float) rotation;
	}
	
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	
	public void draw(PApplet g) {
		g.push();
		g.translate((float) x, (float) y);
		g.rotate(rotation);
		if (image != null)
			g.image(image,0,0,(float)width,(float)height);
		else {
			g.fill(100);
			g.rect(0,0,(float)width,(float)height);
		}
		g.pop();
	}
	

	
	
	
}










