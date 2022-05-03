import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PaintBomb extends Sprite {
	
	private double explosionRadius;
	public static double velocity;
	private boolean isThrown;
	private Color c;
	
	
	public PaintBomb(double r, double velocity, Color c) {
		super();
		this.explosionRadius = r;
		this.velocity = velocity;
		this.c = c;
	}
	
	public ArrayList<PaintBlock> blowUp() {
		return null;
	}
	
	public void launch(Point2D point) {
		// set vx and vy based off mouse click and velocity field
		isThrown = true;
	}

}
