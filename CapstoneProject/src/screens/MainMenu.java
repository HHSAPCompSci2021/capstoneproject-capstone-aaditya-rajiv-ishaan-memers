/** The object of the game
  @author Aaditya Raj
  @version 2
*/
package screens;



import java.awt.Point;
import java.awt.Rectangle;

import core.DrawingSurface;
import g4p_controls.GButton;
import g4p_controls.GEvent;


public class MainMenu extends Screen {

	private DrawingSurface surface;
	
	private Rectangle button, button2;

	public MainMenu(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;

		button = new Rectangle(800/2-100,600/2-50,200,100);
		button2 = new Rectangle(800/2-100,400,200,100);
	}


	public void draw() {

		surface.background(255,100,0);
		
		surface.rect(button.x, button.y, button.width, button.height, 10, 10, 10, 10);
		surface.rect(button2.x, button2.y, button2.width, button2.height, 10, 10, 10, 10);

		surface.fill(0);
		String str = "Create Room!";
		float w = surface.textWidth(str);
		surface.text(str, button.x+button.width/2-w/2, button.y+button.height/2);
		
	}



	
	public void mousePressed(int mouseX, int mouseY) {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (button.contains(p)) {
			System.out.println("CLICKED BEGIN");
			surface.switchScreen(ScreenSwitcher.ROOM_SCREEN);
		} else if (button2.contains(p)) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
		}
	}


	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}

