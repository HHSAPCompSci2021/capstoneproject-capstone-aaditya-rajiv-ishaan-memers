
package sprites;

import java.awt.Color;
import java.awt.geom.Point2D;


import processing.core.PApplet;
/** Gun that can shoot which the player has
	@author Rajiv Venkatesh, Aaditya Raj
	@version 2
*/
public class PaintGun extends Sprite {
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
	public PaintGun(int x, int y, int width, int height, int capacity, int rTime, int vel, int strokeLength) {
		super(x, y, width, height);
		this.maxAmmo = capacity;
		this.ammo = capacity;
		this.reloadTime = rTime;
		this.velocity = vel;
		this.stroke = strokeLength;
	}

	/**
	 * Returns a PaintBlock bullet with the correct velocities once the user has clicked to shoot.
	 * 
	 * @param point The mouse-click point where the user clicked. 
	 * @return A PaintBlock bullet.
	 */
	public PaintBlock shoot(Point2D point, Color ownerColor) {
			if(ammo > 0) {
				PaintBlock bullet = new PaintBlock((int) x, (int) y, ownerColor, stroke, velocity, point);
				ammo--;
				return bullet;
			}
		return null;
		
	}
	
	/**
	 * Draws this paint gun to the screen
	 * @param drawer The PApplet to draw onto.
	 */
	public void draw(PApplet drawer, Point2D ownerLoc) {
		super.draw(drawer);
		x = ownerLoc.getX() + 10;
		y = ownerLoc.getY() + 10;
		if (counter % reloadTime == 0) {
			if (ammo < maxAmmo) {
				ammo++;
			}
		}
		counter++;
	}
	

	
	
	
	

}