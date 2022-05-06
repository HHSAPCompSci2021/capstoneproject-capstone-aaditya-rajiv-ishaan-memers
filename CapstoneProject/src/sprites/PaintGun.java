package sprites;

import java.awt.geom.Point2D;

public class PaintGun extends Sprite {
	private Avatar owner;
	private int ammo, reloadTime, velocity, stroke;
	
	public PaintGun(Avatar a, int capacity, int rTime, int vel, PaintBlock str) {
		super((int)a.x, (int)a.y, (int)a.width, (int)a.height);
		
		ammo = capacity;
		reloadTime = rTime;
		velocity = vel;
	}

	public void shoot(Point2D point) {
		if(ammo > 0) {
			PaintBlock bullet = new PaintBlock(owner, stroke, velocity, point);
			
			ammo--;
		}
		
	}

	
	

}
