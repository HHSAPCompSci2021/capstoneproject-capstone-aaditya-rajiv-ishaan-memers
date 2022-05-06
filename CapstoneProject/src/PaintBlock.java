import java.awt.Color;

public class PaintBlock extends Sprite {
	
	private Color c;
	double length;
	
	
	public PaintBlock(Color c, double length) {
		this.c = c;
		this.length = length;
	}
	
	public void fill(Color c) {
		this.c = c;
	}
	
	
}
