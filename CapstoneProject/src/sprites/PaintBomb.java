/** The bombs that spawn on the map which the player can pick up
  @author Ishaan Singh
  @version 1
*/
package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PaintBomb extends Sprite {
	
	private double explosionRadius, velocity;
	private boolean isThrown;
	private Color c;

	
	/** Constructs a new PaintBomb objects
	 * 
	 * @param x the x-coordinate of the initial location of the PaintBomb
	 * @param y the y-coordinate of the initial location of the PaintBomb
	 * @param r the radius of the paintbomb
	 * @param c the color of this PaintBomb
	 */
	public PaintBomb(int x, int y, int r, Color c) {
		super(x, y, r, r);
		this.explosionRadius = r;
		this.c = c;
		isThrown = false;
	}
	
	/** Scatters Paintbombs with the specified radius
	 * 
	 * @returnan ArrayList of paintblocks to be drawn on the map and acted upon
	 */
	public ArrayList<PaintBlock> blowUp() {
		return null;
	}
	
	/** Throws this bomb
	 * 
	 * @param point the point at which the bomb is being thrown
	 */
	public void launch(Point2D point) {
		// set vx and vy based off mouse click and velocity field
		isThrown = true;
	}


}
