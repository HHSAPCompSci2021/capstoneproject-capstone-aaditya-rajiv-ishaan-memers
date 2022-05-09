package screens;

public abstract class Screen {

	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	/**
	 * Constructs a new screen. 
	 * @param width The width of the screen.
	 * @param height The height of the screen.
	 */
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	public void setup() {
		
	}
	
	public void draw() {
		
	}
	
	public void mousePressed() {
		
	}
	
	public void mouseMoved() {
		
	}
	
	public void mouseDragged() {
		
	}
	
	public void mouseReleased() {
		
	}
	
	
	
}
