
package sprites;

/** The flag that the players race to collect.
	@author Rajiv Venkatesh
	@version 2
*/
public class Flag extends PaintGun{

	private static int capacity;
	private static int rTime;
	private static int vel;
	private static int str;

	/**
	 * Constructs a new flag object.
	 * @param x The x-coordinate of the upper-left corner.
	 * @param y The y-coordinate of the upper-left corner.
	 * @param width The width of the flag.
	 * @param height The height of the flag.
	 */
	public Flag(int x, int y, int width, int height) {
		super(x, y, width, height, capacity, rTime, vel, str);
	}

}
