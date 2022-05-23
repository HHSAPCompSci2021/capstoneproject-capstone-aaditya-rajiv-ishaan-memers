
package sprites;

import java.awt.Color;
import java.awt.geom.Point2D;


import processing.core.PApplet;
import processing.core.PImage;
/** Gun that can shoot which the player has
	@author Rajiv Venkatesh, Aaditya Raj, Ishaan Singh
	@version 2
*/
public class PaintGun extends Sprite {
	private int ammo, maxAmmo, velocity, stroke, counter;
	private boolean onRight;
	private final int reloadTime = 60;
	
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
	
	
	public PaintGun(int x, int y, int width, int height, int capacity, int vel, int strokeLength) {
		super(x, y, width, height);
		this.maxAmmo = capacity;
		this.ammo = capacity;
		this.velocity = vel;
		this.stroke = strokeLength;
		onRight = true;
	}

	/**
	 * Returns a PaintBlock bullet with the correct velocities once the user has clicked to shoot.
	 * 
	 * @param point The mouse-click point where the user clicked. 
	 * @return A PaintBlock bullet.
	 */
	public PaintBlock shoot(Point2D click, Color ownerColor) {
		System.out.println("Ammo: " + ammo);
		if(ammo > 0) {
			if(click.getX() < x) {
				onRight = false;
			} else {
				onRight = true;
			}
			
			PaintBlock bullet;
			
			if(onRight) {
				bullet = new PaintBlock((int) (x+width), (int) y, 60, ownerColor, stroke, velocity, click);	
			} else {
				bullet = new PaintBlock((int) x, (int) y, 60, ownerColor, stroke, velocity, click);
					
			}
			
			ammo--;
			
			return bullet;
		}
		
		return null;
	}
	
	/**
	 * Draws this paint gun to the screen
	 * @param drawer The PApplet to draw onto.
	 */
	public void draw(PApplet drawer, double x, double y) {
		if(!(this instanceof Flag))
			super.draw(drawer);
		
		if(onRight) {
			this.x = x + 150;
		} else {
			this.x = x - 10;
		}
		
		this.y = y - 10;
		
		if (counter % reloadTime == 0) {
			if (ammo == 0) {
				ammo = maxAmmo;
			}
		}
		counter++;
	}

	public PaintBlock shoot(Point2D start , Point2D end, Color playerColor) {
		// TODO Auto-generated method stub
		System.out.println("Ammo: " + ammo);
		if(ammo > 0) {
			
			PaintBlock bullet;
			
				bullet = new PaintBlock((int) start.getX(), (int) start.getY(), 60, playerColor, stroke, velocity, end);
					
			
			ammo--;
			
			return bullet;
		}
		
		return null;
	}
	
	
	
	

}