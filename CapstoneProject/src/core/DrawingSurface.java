package core;


import java.awt.Point;
import java.util.ArrayList;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;
import screens.CreateRoom;
import screens.Game;
import screens.MainMenu;
import screens.Screen;
import screens.ScreenSwitcher;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	public float ratioX, ratioY;
		
	private ArrayList<Integer> keys;
	
	
	private Screen activeScreen;
	private ArrayList<Screen> screens;

	
	public DrawingSurface() {
		
		screens = new ArrayList<Screen>();
		
		keys = new ArrayList<Integer>();
		
		
		MainMenu screen1 = new MainMenu(this);
		screens.add(screen1);
		
		CreateRoom screen2 = new CreateRoom(this);
		screens.add(screen2);
		
		Game screen3 = new Game(this);
		screens.add(screen3);
		
		activeScreen = screens.get(0);
	}
	
	public void setup() {
		for (Screen s : screens)
			s.setup();
	}
	
	public void draw() {
		ratioX = (float)width/activeScreen.DRAWING_WIDTH;
		ratioY = (float)height/activeScreen.DRAWING_HEIGHT;

		push();
		
		scale(ratioX, ratioY);
		
		activeScreen.draw();
		
		pop();
	}
	
	public void keyPressed() {
		keys.add(keyCode);
		if (key == ESC)  // This prevents a processing program from closing on escape key
			key = 0;
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	public void mousePressed() {
		activeScreen.mousePressed((int)(mouseX*(1/ratioX)), (int)(mouseY*(1/ratioY)));
	}

	
	public void mouseMoved() {
		activeScreen.mouseMoved();
	}
	
	public void mouseDragged() {
		activeScreen.mouseDragged();
	}
	
	public void mouseReleased() {
		activeScreen.mouseReleased();
	}
	
	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int)(assumed.getX()*ratioX), (int)(assumed.getY()*ratioY));
	}

	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int)(actual.getX()/ratioX) , (int)(actual.getY()/ratioY));
	}

	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
		System.out.println(activeScreen);
	}

}
