
package sprites;

import processing.core.PApplet;
import processing.core.PImage;

/** The flag that the players race to collect.
	@author Rajiv Venkatesh
	@version 2
*/
public class Flag extends PaintGun {

	private final static int capacity = 2;
	private final static int vel = PaintBlock.VELOCITY * 2;
	private final static int str = (int) (PaintBlock.LENGTH * 3);
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
		super(null, x, y, width, height, capacity, vel, str);
		baseX = x;
		baseY = y;
	}
	
	/** other draw method so that Flag follows player who has it 
	 * 
	 * @param drawer the drawing surface
	 * @param x the x-coord of the player
	 * @param y the y-coord of the player
	 */
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
	
	/** Resets the flag back to the set location without changing anything lse
	 * 
	 */
	public void reset() {
		super.x = baseX;
		super.y = baseY;
	}
}
