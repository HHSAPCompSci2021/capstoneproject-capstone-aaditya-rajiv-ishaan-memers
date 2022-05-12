package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import core.DrawingSurface;
import sprites.Avatar;
import sprites.Flag;
import sprites.PaintBomb;
import sprites.Platform;

/**
 * Represents the interactive game screen.
 * 
 * @author Ishaan Singh and Aaditya Raj
 *
 */
public class Game extends Screen{
	
	Avatar player1, player2;
	Double player1Points, player2Points;
	ArrayList<Platform> platforms;
	Flag flag;
	ArrayList<PaintBomb> bombs;
	DrawingSurface surface;
	/**
	 * Constructs a screen representing the interactive game screen. 
	 * @param width The width of the screen.
	 * @param 0 The 0 of the screen.
	 */
	public Game(DrawingSurface s) {
		super(1600, 1200);
		surface = s;
		player1 = new Avatar(0, 0, 0, 0, null);
		player2 = new Avatar(0, 0, 0, 0, null);
		platforms = new ArrayList<Platform>();
		platforms.add(new Platform(1200, 200, 400, 100));
		flag = new Flag(10, 10, 100, 100);
		bombs = new ArrayList<PaintBomb>();
		bombs.add(new PaintBomb(0, 0, 0, null));
	}
	
	public void draw() {

		surface.image(surface.loadImage("img/background.png"), 1, 0);
		int  player1Score = 0;
		int  player2Score = 0; 
		for(Platform p : platforms) {
			p.draw(surface);
			player1Score += p.numBlocksWithColor(player1.getColor());
			player2Score += p.numBlocksWithColor(player2.getColor());
		}
		player1Score = (player1Score * 10000 / 48000);// Area/ windowSize
		player2Score = (player2Score * 10000 / 48000);// Area/ windowSize

		
		flag.draw(surface);
		
		if (surface.isPressed(KeyEvent.VK_A)) {
			player1.walk(-2);
		}
		if (surface.isPressed(KeyEvent.VK_D)) {
			player1.walk(2);
		}
		if (surface.isPressed(KeyEvent.VK_W)) {
			player1.jump();
		}
		
		if (surface.isPressed(KeyEvent.VK_LEFT)) {
			player2.walk(-2);
		}
		if (surface.isPressed(KeyEvent.VK_RIGHT)) {
			player2.walk(2);
		}
		if (surface.isPressed(KeyEvent.VK_UP)) {
			player2.jump();
		}
		
		
		
	}

}
