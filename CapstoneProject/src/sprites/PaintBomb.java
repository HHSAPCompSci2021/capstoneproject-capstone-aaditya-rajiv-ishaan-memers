package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PaintBomb extends Sprite {
	
	private double explosionRadius;
	private boolean isThrown;
	private Color c;
	
	public static double velocity;

	
	
	public PaintBomb(int x, int y, int r, Color c) {
		super(x, y, r, r);
		this.explosionRadius = r;
		this.c = c;
		isThrown = false;
	}
	
	public ArrayList<PaintBlock> blowUp() {
		return null;
	}
	
	public void launch(Point2D point) {
		// set vx and vy based off mouse click and velocity field
		isThrown = true;
	}

}
