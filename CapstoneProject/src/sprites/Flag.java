
package sprites;

import processing.core.PApplet;
import processing.core.PImage;

/** The flag that the players race to collect.
	@author Rajiv Venkatesh
	@version 2
*/
public class Flag extends PaintGun {

	private static int capacity = 10;
	private static int vel = 10;
	private static int str = 10;

	/**
	 * Constructs a new flag object.
	 * @param x The x-coordinate of the upper-left corner.
	 * @param y The y-coordinate of the upper-left corner.
	 * @param width The width of the flag.
	 * @param height The height of the flag.
	 */
	public Flag(int x, int y, int width, int height) {
		super(x, y, width, height, capacity, vel, str);
	}
	
	public void draw(PApplet drawer, int x, int y) {
		drawer.fill(0, 0, 0);
		drawer.rect(x, y, 25, 200);
		drawer.fill(255, 0, 0);
		drawer.rect(x+25, y, 150, 75);
	}

}
