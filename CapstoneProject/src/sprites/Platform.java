
package sprites;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;

/** Platform to form the map
	@author Aaditya Raj, Ishaan Singh
	@version 2
*/
public class Platform extends Sprite {
	
	private ArrayList<PaintBlock> border;
	
	/** Constructs a platform for drawing on the game screen 
	 * @pre width and height are multiples of 10
	 * @param x the x-coordinate of the upper left corner of the platform
	 * @param y the y-coordinate of the upper left corner of the platform
	 * @param width the width of the platform
	 * @param height the height of the platform -- typically constant at 20px
	 */
	public Platform(double x, double y, double width, double height) {
		super((int) x, (int) y, (int) width, (int) height);
		border = new ArrayList<PaintBlock>();
		int widthBlocks = (int) (width/PaintBlock.LENGTH + 0.5);
		int heightBlocks = (int) ((height - PaintBlock.LENGTH * 2)/PaintBlock.LENGTH + 0.5);
		for (int i = 0; i < widthBlocks; i++) {
			border.add(new PaintBlock((int) (x + i * PaintBlock.LENGTH + 0.5), (int) (y + 0.5), PaintBlock.LENGTH));
			border.add(new PaintBlock((int) (x + i * PaintBlock.LENGTH + 0.5), (int) (y + height - PaintBlock.LENGTH + 0.5), PaintBlock.LENGTH));
		}
		for (int i = 0; i < heightBlocks; i++) {
			border.add(new PaintBlock((int) (x + 0.5), (int) (y + PaintBlock.LENGTH + i * PaintBlock.LENGTH + 0.5), PaintBlock.LENGTH));
			border.add(new PaintBlock((int) (x + width - PaintBlock.LENGTH + 0.5), (int) (y + PaintBlock.LENGTH + i * PaintBlock.LENGTH + 0.5), PaintBlock.LENGTH));
		}
		
	}
	
	/**
	 * 
	 * @pre bullet has a color field that is set.
	 */
	public void paint(PaintBlock bullet) {
		for (PaintBlock block : border) {
			if (block.intersects(bullet)) {
				block.fill(bullet.getColor());
			}
		}
	}
	
	public boolean contains(PaintBlock bullet) {
		Rectangle2D rect = new Rectangle2D.Double(x + PaintBlock.LENGTH/2, y + PaintBlock.LENGTH/2, width - 2 * PaintBlock.LENGTH/2, height - 2 * PaintBlock.LENGTH/2);
		return (rect.contains(bullet.x, bullet.y) && rect.contains(bullet.x + bullet.width, bullet.y) &&
				rect.contains(bullet.x + bullet.width, bullet.y + bullet.height) && rect.contains(bullet.x, bullet.y + bullet.height));
	}
	
	
	public int numBlocksWithColor(Color c) {
		int total = 0;
		for (PaintBlock b : border) {
			if (b.getColor() != null && b.getColor().equals(c)) {
				total++;
			}
		}
		return total;
	}
	
	/** Draws the platform on the screen
	 * 
	 * @param marker the PApplet that draws the platform
	 */
	public void draw(PApplet marker) {
		marker.push();
		marker.color(255,0,0);
		marker.rect((float)x,(float)y,(float)width,(float)height);
		marker.pop();                                             
		for (PaintBlock block : border) {
			if (!(block.getColor() == null)) {
				block.draw(marker);
			}
		}
	}

	public ArrayList<PaintBlock> getBorder() {
		return border;
	}

}
