
package sprites;
import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

/** Platform to form the map
	@author Aaditya Raj, Ishaan Singh
	@version 2
*/
public class Platform {
	
	double x, y, width, height;
	ArrayList<PaintBlock> border;
	
	/** Constructs a platform for drawing on the game screen
	 * 
	 * @param x the x-coordinate of the upper left corner of the platform
	 * @param y the y-coordinate of the upper left corner of the platform
	 * @param width the width of the platform
	 * @param height the height of the platform -- typically constant at 20px
	 */
	public Platform(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
	
	public boolean insidePlatform(PaintBlock bullet) {
		return (isPointInside(bullet.x, bullet.y) && isPointInside(bullet.x + bullet.width, bullet.y) && isPointInside(bullet.x + width, bullet.y + height) && isPointInside(bullet.x, bullet.y + height));
	}
	
	private boolean isPointInside(double x, double y) {
		return (x >= this.x + PaintBlock.LENGTH && y >= this.y + PaintBlock.LENGTH && x <= this.x + width - PaintBlock.LENGTH && y <= this.y + height - PaintBlock.LENGTH);
	}
	
	public int numBlocksWithColor(Color c) {
		int total = 0;
		for (PaintBlock b : border) {
			if (b.getColor().equals(c)) {
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
		marker.color(Color.GRAY.getRGB());
		marker.rect((float)x,(float)y,(float)width,(float)height);
		marker.pop();
		for (PaintBlock block : border) {
			if (!block.getColor().equals(null)) {
				block.draw(marker);
			}
		}
	}
}
