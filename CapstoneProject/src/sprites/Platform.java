package sprites;
import java.util.ArrayList;


public class Platform {
	
	double x, y, width, height;
	ArrayList<PaintBlock> border;
	
	public Platform(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		border = new ArrayList<PaintBlock>();
	}
	
}
