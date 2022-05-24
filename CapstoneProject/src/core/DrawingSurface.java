package core;


import java.awt.Point;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import g4p_controls.GButton;
import g4p_controls.GEvent;
import networking.backend.PeerDiscovery;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;
import screens.CreateRoom;
import screens.Game;
import screens.JoinRoom;
import screens.MainMenu;
import screens.Screen;
import screens.ScreenSwitcher;
import screens.WinOrLoseScreen;

public class DrawingSurface extends PApplet implements ScreenSwitcher, NetworkListener {

	public float ratioX, ratioY;
		
	private ArrayList<Integer> keys;
	

	public final int RIGHT_SIDE = 1;
	public final int LEFT_SIDE = 0;
	public int perspective;
	
	
	private Screen activeScreen;
	private ArrayList<Screen> screens;

	public String playerUsername;

	public String opponentUsername;
	
	private static final int BROADCAST_PORT = 4444;
	
	public static PeerDiscovery discover;
	


	
	public DrawingSurface() {
		
		screens = new ArrayList<Screen>();
		
		keys = new ArrayList<Integer>();
		
		
		MainMenu screen1 = new MainMenu(this);
		screens.add(screen1);
		
		CreateRoom screen2 = new CreateRoom(this);
		screens.add(screen2);
		
		JoinRoom screen3 = new JoinRoom(this);
		screens.add(screen3);
		
		Game screen4 = new Game(this);
		screens.add(screen4);
		
		WinOrLoseScreen screen5 = new WinOrLoseScreen(this);
		screens.add(screen5);
		
		activeScreen = screens.get(0);
		
		try {
			discover = new PeerDiscovery(InetAddress.getByName("255.255.255.255"),BROADCAST_PORT);
			System.out.println("\nBroadcast discovery server running on " + BROADCAST_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("\nError starting broadcast discovery server on port " + BROADCAST_PORT + "\nCannot discover or be discovered.");
		}
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
		if (i == ScreenSwitcher.GAME_SCREEN) {
			((Game) activeScreen).setActivePlayer(perspective);
		}
		if (i == ScreenSwitcher.WIN_SCREEN) {
			((WinOrLoseScreen) activeScreen).setPlayerScore(((Game) screens.get(ScreenSwitcher.GAME_SCREEN)).getActivePlayerScore());
			((WinOrLoseScreen) activeScreen).setOpponentScore(((Game) screens.get(ScreenSwitcher.GAME_SCREEN)).getOpponentScore());
		}
	}
	
	public void handleButtonEvents(GButton button, GEvent event) {
		activeScreen.handleButtonEvents(button, event);
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		// TODO Auto-generated method stub
		screens.get(3).setNetworkMessenger(nm);
		
	}
	
	public void setPerspective(int perspective) {
		this.perspective = perspective;
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		// TODO Auto-generated method stub
		
	}

	public void setPlayerUsername(String text) {
		// TODO Auto-generated method stub
		this.playerUsername = text;
	}

	public void setOpponentUsername(String opponentUsername) {
		// TODO Auto-generated method stub
		this.opponentUsername = opponentUsername;
	}
}


