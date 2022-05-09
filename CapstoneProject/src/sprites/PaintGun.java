
package sprites;

import java.awt.geom.Point2D;


import processing.core.PApplet;
/** Gun that can shoot which the player has
	@author Rajiv Venkatesh, Aaditya Raj
	@version 2
*/
public class PaintGun extends Sprite {
	private Avatar owner;
	private int ammo, maxAmmo, reloadTime, velocity, stroke, counter;
	
	/**
	 * 
	 * @param x The x coordinate of the starting position of the PaintGun. 
	 * @param y The y coordinate of the starting position of the PaintGun.
	 * @param width The width of the paint gun.
	 * @param height The height of the paint gun.
	 * @param capacity the amount of ammunition this gun can hold
	 * @param rTime the reload time of the gun
	 * @param vel the muzzle velocity of bullets from this gun
	 * @param str the size of "splotches" on the ground from this
	 */
	public PaintGun(int x, int y, int width, int height, int capacity, int rTime, int vel, PaintBlock str) {
		super(x, y, width, height);
	}

	/**
	 * Returns a PaintBlock bullet with the correct velocities once the user has clicked to shoot.
	 * 
	 * @param point The mouse-click point where the user clicked. 
	 * @return A PaintBlock bullet.
	 */
	public PaintBlock shoot(Point2D point) {
		if (owner != null) {
			if(ammo > 0) {
				PaintBlock bullet = new PaintBlock(owner, stroke, velocity, point);
				ammo--;
				return bullet;
			}
		}
		return null;
		
	}
	
	/**
	 * Draws this paint gun to the screen
	 * @param drawer The PApplet to draw onto.
	 */
	public void draw(PApplet drawer) {
		super.draw(drawer);
		if (owner != null) {
			super.x = owner.getCenterX();
			super.y = owner.getCenterY();
		}
		if (counter % reloadTime == 0) {
			if (ammo < maxAmmo) {
				ammo++;
			}
		}
		counter++;
	}
	
	/**
	 * Sets the Avatar owner of this paint gun.
	 * @param a The owner.
	 */
	public void setOwner(Avatar a) {
		owner = a;
	}
	
	
	
	

}