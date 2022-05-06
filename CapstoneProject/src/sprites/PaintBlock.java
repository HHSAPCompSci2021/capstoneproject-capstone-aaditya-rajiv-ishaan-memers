package sprites;
import java.awt.Color;

public class PaintBlock extends Sprite {
	
	private Color c;
	double length;
	
	
	public PaintBlock(int x, int y, Color c, int length) {
		super(x, y, length, length);
		this.c = c;
		this.length = length;
	}
	
	public void fill(Color c) {
		this.c = c;
	}
	
	
}
