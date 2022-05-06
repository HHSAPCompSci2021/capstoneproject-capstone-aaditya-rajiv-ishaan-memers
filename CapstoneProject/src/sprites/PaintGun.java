package sprites;

public class PaintGun extends Sprite {
	int ammo, reloadTime, velocity;
	PaintBlock stroke;
	
	public PaintGun(Avatar a, int capacity, int rTime, int vel, PaintBlock str) {
		super((int)a.x, (int)a.y, (int)a.width, (int)a.height);
		
		ammo = capacity;
		reloadTime = rTime;
		velocity = vel;
		
		stroke = str;
	}

}
