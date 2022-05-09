/** Gun that can shoot which the player has
  @author Rajiv Venkatesh
  @version 2
*/
package sprites;

import java.awt.geom.Point2D;

public class PaintGun extends Sprite {
	private Avatar owner;
	private int ammo, reloadTime, velocity, stroke;
	
	/** constructs a new PaintGun object
	 * 
	 * @param a the Avatar this gun belongs to
	 * @param capacity the amount of ammunition this gun can hold
	 * @param rTime the reload time of the gun
	 * @param vel the muzzle velocity of bullets from this gun
	 * @param str the size of "splotches" on the ground from this
	 */
	public PaintGun(Avatar a, int capacity, int rTime, int vel, PaintBlock str) {
		super((int)a.x, (int)a.y, (int)a.width, (int)a.height);
		
		ammo = capacity;
		reloadTime = rTime;
		velocity = vel;
	}

	/** Shoots a PaintBlock from this gun at the specified point
 	 * 
	 * @param point the point at which to shoot 
	 */
	public void shoot(Point2D point) {
		if(ammo > 0) {
			PaintBlock bullet = new PaintBlock(owner, stroke, velocity, point);
			
			ammo--;
		}
		
	}

}