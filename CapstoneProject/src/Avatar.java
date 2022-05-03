import java.awt.Color;
import java.util.ArrayList;

import sprites.Sprite;

public class Avatar extends Sprite {
	private PaintGun gun;
	private ArrayList<PaintBomb> arsenal;
	private Color playerColor;
	private int health, speed;
	private int baseX, baseY;
	
	public Avatar(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
}
