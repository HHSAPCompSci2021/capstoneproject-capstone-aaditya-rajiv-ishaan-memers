package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PaintBomb extends Sprite {
	
	private double explosionRadius;
	private boolean isThrown;
	private Color c;
	private double xVel, yVel;
	private double velocity;

	
	
	public PaintBomb(int x, int y, int length, int r, double velocity, Color c) {
		super(x, y, length, length);
		this.velocity = velocity;
		this.explosionRadius = r;
		this.c = c;
		isThrown = false;
		xVel = 0;
		yVel = 0;
	}
	
	public ArrayList<PaintBlock> blowUp() {
		return null;
	}
	
	public void launch(Point2D mouseClick, Point2D startPoint) {
		// set vx and vy based off mouse click and velocity field
		isThrown = true;
		double run = mouseClick.getX() - startPoint.getX();
		double rise = mouseClick.getY() - startPoint.getY();
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


}
