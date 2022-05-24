
package sprites;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;

/** The bombs that spawn on the map which the player can pick up
	@author Aaditya Raj
	@version 1
*/
public class PaintBomb extends PaintBlock {

	private static final int RADIUS = 8;

	
	/** Constructs a new PaintBomb objects
	 * 
	 * @param x the x-coordinate of the initial location of the PaintBomb
	 * @param y the y-coordinate of the initial location of the PaintBomb
	 * @param r the radius of the paintbomb
	 * @param c the color of this PaintBomb
	 */
	public PaintBomb(int x, int y, Point2D mouse, Color c) {
		super(x, y, c, PaintBlock.LENGTH * 6, PaintBlock.VELOCITY, mouse);
	}
	
	/** Scatters Paintbombs with the specified radius
	 * 
	 * @return An ArrayList of paintblocks to be drawn on the map and acted upon
	 */
	public ArrayList<PaintBlock> blowUp() {
		ArrayList<PaintBlock> blocks = new ArrayList<PaintBlock>();
		for (int angle = 0; angle < 360; angle += 36) {
			double x = RADIUS * Math.cos(Math.toRadians(angle)) + getCenterX();
			double y = RADIUS * Math.sin(Math.toRadians(angle)) + getCenterY();
			PaintBlock bullet = new PaintBlock((int) getCenterX(), (int) getCenterY(), getColor(),
					PaintBlock.LENGTH * 2, PaintBlock.VELOCITY, new Point2D.Double(x, y));
			blocks.add(bullet);
		}
		return blocks;
	}	
	
	public void draw(PApplet marker) {
		marker.push();
		marker.fill(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		marker.circle((float) x, (float) y, (float) getWidth());
		marker.pop();
		super.moveByAmount(getVelocities()[0], getVelocities()[1]);
	}
	


}
