package sprites;
import java.util.ArrayList;

import processing.core.PImage;

public class PaintGun extends Sprite {
	int ammo, reloadTime, velocity;
	ArrayList<PaintBlock> storage;
	
	public PaintGun(Avatar a, int capacity, int rTime, int vel, PaintBlock str) {
		super((int)a.x, (int)a.y, (int)a.width, (int)a.height);
		
		ammo = capacity;
		reloadTime = rTime;
		velocity = vel;
		
		storage = new ArrayList<PaintBlock>();
	}

	public void shoot() {
		
		ammo--;
		
	}

	
	

}
