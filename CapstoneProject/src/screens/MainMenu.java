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
	
	private Rectangle button, button2, button3;


	public MainMenu(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		
		button = new Rectangle(800/2-100, 100, 200, 100);
		button2 = new Rectangle(800/2-100,600/2-50,200,100);
		button3 = new Rectangle(800/2-100,400,200,100);
	}


	public void draw() {

		surface.background(255,100,0);
		
		surface.rect(button.x, button.y, button.width, button.height, 10, 10, 10, 10);
		surface.rect(button2.x, button2.y, button2.width, button2.height, 10, 10, 10, 10);
		surface.rect(button3.x, button3.y, button3.width, button3.height, 10, 10, 10, 10);

		surface.fill(0);
		String str = "Create Room!";
		float w = surface.textWidth(str);
		surface.text(str, button.x+button.width/2-w/2, button.y+button.height/2);
		String str2 = "Join Room!";
		w = surface.textWidth(str2);
		surface.text(str2, button2.x+button2.width/2-w/2, button2.y+button2.height/2);
		String str3 = "Go to game (will not exist in final)!!";
		w = surface.textWidth(str3);
		surface.text(str3, button3.x+button3.width/2-w/2, button3.y+button3.height/2);
		
	}



	
	public void mousePressed(int mouseX, int mouseY) {
		Point p = surface.actualCoordinatesToAssumed(new Point(surface.mouseX,surface.mouseY));
		if (button.contains(p)) {
			surface.switchScreen(ScreenSwitcher.CREATE_ROOM);
		} else if (button2.contains(p)) {
			surface.switchScreen(ScreenSwitcher.JOIN_ROOM);
		} else if (button3.contains(p)) {
			surface.switchScreen(ScreenSwitcher.GAME_SCREEN);
		}
	}


	@Override
	public void handleButtonEvents(GButton button, GEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}

