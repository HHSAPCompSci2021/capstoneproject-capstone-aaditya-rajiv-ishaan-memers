package sprites;

import java.awt.geom.Point2D;

import processing.core.PApplet;

public class PaintGun extends Sprite {
	private Avatar owner;
	private int ammo, maxAmmo, reloadTime, velocity, stroke, counter;
	
	public PaintGun(int x, int y, int width, int height, int capacity, int rTime, int vel, PaintBlock str) {
		super(x, y, width, height);
		
		ammo = capacity;
		maxAmmo = capacity;
		reloadTime = rTime;
		velocity = vel;
		counter = 0;
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
