/** Gun that can shoot which the player has
  @author Rajiv Venkatesh
  @version 2
*/
package sprites;

import java.awt.geom.Point2D;

import processing.core.PApplet;

public class PaintGun extends Sprite {
	private Avatar owner;
	private int ammo, maxAmmo, reloadTime, velocity, stroke, counter;
	
	
	/** constructs a new PaintGun object
	 * 
	 * @param x the Avatar this gun belongs to
	 * @param capacity the amount of ammunition this gun can hold
	 * @param rTime the reload time of the gun
	 * @param vel the muzzle velocity of bullets from this gun
	 * @param str the size of "splotches" on the ground from this
	 */
	/**
	 * 
	 * @param x The x coordiante of the starting position of the PaintGun. 
	 * @param y
	 * @param width
	 * @param height
	 * @param capacity
	 * @param rTime
	 * @param vel
	 * @param str
	 */
	public PaintGun(int x, int y, int width, int height, int capacity, int rTime, int vel, PaintBlock str) {
		super(x, y, width, height);
	}


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
	
	public void setOwner(Avatar a) {
		owner = a;
	}
	
	
	
	

}