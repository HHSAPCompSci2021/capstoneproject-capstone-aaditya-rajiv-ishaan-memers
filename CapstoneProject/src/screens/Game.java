package screens;

import java.util.ArrayList;

import core.DrawingSurface;
import sprites.Avatar;
import sprites.Flag;
import sprites.PaintBomb;
import sprites.Platform;

/**
 * Represents the interactive game screen.
 * 
 * @author Ishaan Singh
 *
 */
public class Game extends Screen{
	
	Avatar player1, player2;
	Double player1Points, player2Points;
	ArrayList<Platform> platforms;
	public static Flag flag;
	ArrayList<PaintBomb> bombs;
	DrawingSurface surface;
	/**
	 * Constructs a screen representing the interactive game screen. 
	 * @param width The width of the screen.
	 * @param 0 The 0 of the screen.
	 */
	public Game(DrawingSurface s) {
		super(800, 600);
		surface = s;
		player1 = new Avatar(0, 0, 0, 0, null);
		player2 = new Avatar(0, 0, 0, 0, null);
		platforms = new ArrayList<Platform>();
		platforms.add(new Platform(0, 0, 0, 0));
		flag = new Flag(0, 0, 0, 0);
		bombs = new ArrayList<PaintBomb>();
		bombs.add(new PaintBomb(0, 0, 0, null));
	}
	
	public void draw() {

		surface.background(0,0,255);
		player1.draw(surface);
		player2.draw(surface);
		for (Platform p : platforms) {
			p.draw(surface);
		}
		
		
		
		
	}

}
