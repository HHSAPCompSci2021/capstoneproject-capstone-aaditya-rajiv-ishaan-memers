
package sprites;

import processing.core.PApplet;
import processing.core.PImage;

/** The flag that the players race to collect.
	@author Rajiv Venkatesh
	@version 2
*/
public class Flag extends PaintGun {

	private static int capacity = 10;
	private static int vel = 7;
	private static int str = 10;
	private int baseX;
	private int baseY;

	/**
	 * Constructs a new flag object.
	 * @param x The x-coordinate of the upper-left corner.
	 * @param y The y-coordinate of the upper-left corner.
	 * @param width The width of the flag.
	 * @param height The height of the flag.
	 */
	public Flag(int x, int y, int width, int height) {
		super(x, y, width, height, capacity, vel, str);
		baseX = x;
		baseY = y;
	}
	
	public void draw(PApplet drawer, int x, int y) {
		super.moveToLocation(x, y);
		this.draw(drawer);
	}
	
	public void draw(PApplet drawer) {
		drawer.fill(0, 0, 0);
		drawer.rect((float)this.x, (float)this.y, (float) (width/7d), (float) height);
		drawer.fill(255, 0, 0);
		drawer.rect((float)(this.x + width/7d), (float)(this.y), 
				(float) (6*width/7d), (float) (height/2d));
	}
	
	public void reset() {
		this.x = baseX;
		this.y = baseY;
	}
}
