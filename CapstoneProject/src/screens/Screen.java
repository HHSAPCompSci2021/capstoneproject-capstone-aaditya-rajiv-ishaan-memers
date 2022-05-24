package screens;

import g4p_controls.GButton;
import g4p_controls.GEvent;
import networking.frontend.NetworkMessenger;

public abstract class Screen {

	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	private NetworkMessenger nm;
	
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
	
	public void mousePressed(int mouseX, int mouseY) {
		
	}
	
	public void mouseMoved() {
		
	}
	
	public void mouseDragged() {
		
	}
	
	public void mouseReleased() {
		
	}

	public void setNetworkMessenger(NetworkMessenger nm) {
		// TODO Auto-generated method stub
		this.nm = nm;
	}

	public abstract void handleButtonEvents(GButton button, GEvent event);


	
	
	
}
